package com.example.russian.game.back.data.entity.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.russian.main.Id
import com.example.russian.main.stats.comp.stats.TaskCardUiState


@Entity(tableName = "new-playlist")
data class Playlist(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("playlistId")
    var id: Id = Id(0L),

    override val title: String,

    val capacity: Long,

): PlaylistUiApi{
    fun shortName(): String{
        val parts = title.split(" ")[0]
        if(parts.length <= 2){
            return title.split(" ")[0]
        }
        return title.substring(0..0)
    }

    override val description: String
        get() = "У данного плейлиста поа нет описания"

}
