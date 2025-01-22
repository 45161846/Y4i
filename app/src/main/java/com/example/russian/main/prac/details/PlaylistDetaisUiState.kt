package com.example.russian.main.prac.details

import com.example.russian.main.Id
import com.example.russian.main.prac.remote.PlaylistColor
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.stats.comp.stats.TaskCardUiState


sealed class PlaylistDetailsUiState {

    sealed class Local :PlaylistDetailsUiState(){
        data object Loading : Local()

        data class Details(
            val details: PlaylistOverView.Local,
            val content: PlaylistContent.Local
        ): Local()
    }

    sealed class Remote: PlaylistDetailsUiState(){
        data object Loading : Remote()

        data class Details(
            val details: PlaylistOverView.Remote,
            val content: PlaylistContent.Remote
        ) : Remote()
    }

}

sealed class PlaylistContent {
    sealed class Local : PlaylistContent() {

        data class Loading(
            val displaySetting: StatDisplaySetting
        ) : Local()
        data class Content(
            val cards: List<TaskCardUiState>
        ) : Local()

    }

    sealed class Remote : PlaylistContent() {
        data object Loading : Remote()
        data class Content(
            val cards: List<String>
        ) : Remote()
    }
}


sealed class PlaylistOverView {

    sealed class Local: PlaylistOverView(){
        data class OverView(
            val id: Id,
            val title: String,
            val description: String,
            val capacity: Int
        ) : Local()

        data object Loading: Local()
    }


    sealed class Remote: PlaylistOverView(){
        data class OverView(
            val title: String,
            val description: String,
            val color: PlaylistColor,
            val previewTasks: List<String>,
            val rating: Float,
            val capacity: Int
        ) : Remote()

        data object Loading: Remote()
    }



}


