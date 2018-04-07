package edu.upi.cs.drake.anithings.data

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import edu.upi.cs.drake.anithings.common.NETWORK_EXECUTOR
import edu.upi.cs.drake.anithings.common.ioThread
import edu.upi.cs.drake.anithings.common.mainThread
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

/*
* a generic class to provide resource from cached source/room and the network
* read more at https://developer.android.com/topic/libraries/architecture/guide.html
* ResultType: Type for the Resource data
* RequestType: Type for the API response
* */

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor() {

    private val result = PublishSubject.create<Resource<ResultType>>()

    init {
        this.loadFromDb().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
                    if (shouldFetch(value)) {
                        fetchFromNetwork()
                    } else {
                        result.onNext(Resource.Success(value))
                    }
                })
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        result.onNext(Resource.Loading(null))
        apiResponse
                .subscribeOn(Schedulers.from(NETWORK_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    ioThread {
                        saveCallResult(response)
                        mainThread {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            loadFromDb().subscribeOn(Schedulers.from(NETWORK_EXECUTOR))
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        it ->

                                        result.onNext(Resource.Success(it))
                                    })
                        }
                    }
                }, { error ->
                    onFetchFailed()
                    result.onNext(Resource.Error(error.localizedMessage, null))
                })
    }

    fun asFlowable(): Flowable<Resource<ResultType>> {
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    // Called to get the cached data from the database
    @MainThread
    protected abstract fun loadFromDb(): Single<ResultType>

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): Single<RequestType>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    protected open fun onFetchFailed() {}
}