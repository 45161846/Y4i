package com.example.russian.main.enums

enum class SortTypeMode {

    DIRECT, REVERSED, UNSPECIFIED;

    fun nextMode(): SortTypeMode {
        return when(this){
            DIRECT -> REVERSED
            REVERSED -> DIRECT
            UNSPECIFIED -> DIRECT
        }
    }
}

fun nextMode(currentMode: SortTypeMode) =
    when(currentMode){
        SortTypeMode.DIRECT -> SortTypeMode.REVERSED
        SortTypeMode.REVERSED -> SortTypeMode.DIRECT
        SortTypeMode.UNSPECIFIED -> SortTypeMode.DIRECT
    }

