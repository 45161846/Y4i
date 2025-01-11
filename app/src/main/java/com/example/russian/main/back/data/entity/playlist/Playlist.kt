package com.example.russian.main.back.data.entity.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "new-playlist")
data class Playlist(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("playlistId")
    var id: Long = 0,

    val title: String,

    val capacity: Long,

){
    fun shortName(): String{
        val parts = title.split(" ")[0]
        if(parts.length <= 2){
            return title.split(" ")[0]
        }
        return title.substring(0..0)
    }
    fun shortDescription(): String{
        return if (title.length >= 15){
            title.substring(0..14) + "..."
        }else{
            title
        }
    }
}
