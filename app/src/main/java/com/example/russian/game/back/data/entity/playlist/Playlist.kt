package com.example.russian.game.back.data.entity.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.russian.main.Id
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "new-playlist")
data class Playlist(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("playlistId")
    var id: Id = Id(0L),
    val remoteId: Id,
    val title: String,
    val capacity: Long,
    val description: String


) {
    fun shortName(): String {
        val parts = title.split(" ")[0]
        if (parts.length <= 2) {
            return title.split(" ")[0]
        }
        return title.substring(0..0)
    }
}
