package com.example.russian.game.back.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.russian.main.Id
import com.example.russian.main.TaskType


@Entity(
    foreignKeys = [ForeignKey(
        entity = MyTask::class,
        onUpdate = ForeignKey.NO_ACTION,
        onDelete = ForeignKey.CASCADE,
        parentColumns = ["taskId"],
        childColumns = ["taskId"]
    )],
    indices = [Index(value = ["taskId"], unique = true)]
)
class Statistics(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "statsId")
    var id: Id = Id(0L),

    @ColumnInfo(name = "taskId")
    val taskId: Id,

    @ColumnInfo(name = "attempts")
    val attempts: Int = 0,

    @ColumnInfo(name = "correct")
    val correct: Int = 0,

    @ColumnInfo(name = "displayable-text")
    val displayableText: String,

    val type: TaskType
) {
    fun winRate(): Double {
        if (attempts == 0) return 0.0
        return (correct.toDouble() / attempts.toDouble())
    }
}

//class WordStatistics(
//    @Embedded
//    val word: MyTask,
//    @Relation(
//        parentColumn = "taskId",
//        entityColumn = "taskId",
//    )
//    val stats: Statistics
//)