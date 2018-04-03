package edu.upi.cs.drake.anithings.data.local.entities

import android.arch.persistence.room.TypeConverter

class AnimeDataTypeConverter{
    @TypeConverter
    fun stringToListOfInt(list: String): List<Int> {
        val split = list.split(",")
        val result: MutableList<Int> = arrayListOf()
        split.forEach({
            result.add(it.toInt())
        })
        return result
    }

    @TypeConverter
    fun listOfIntToString(list: List<Int>): String {
        var result: String = ""
        var index = 0
        val size = list.size
        list.forEach({
            result = result + it.toString()
            if(index!=size-1){
                result = result + ","
            }
            index++
        })
        if(list.isEmpty()){
            return "0"
        }
        return result
    }
}