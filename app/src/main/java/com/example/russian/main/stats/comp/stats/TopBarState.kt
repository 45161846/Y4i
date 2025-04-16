package com.example.russian.main.stats.comp.stats

sealed class TopBarState{

    data object Hide: TopBarState()

    sealed class Show: TopBarState(){
        data class ShowSearch(
            val search: (String) -> Unit,
            val navigateToFilter: () -> Unit
        ): Show()

        data object ShowPrac: Show()
    }

}

sealed class BottomBarState{

    data object Hide: BottomBarState()
    data object Show: BottomBarState()

    companion object{

        fun valueOf(show: Boolean): BottomBarState{
            return if (show){
                BottomBarState.Show
            }else{
                BottomBarState.Hide
            }
        }
    }

}
