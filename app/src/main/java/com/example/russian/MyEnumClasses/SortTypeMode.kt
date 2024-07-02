package com.example.russian.MyEnumClasses

enum class SortTypeMode {

    DIRECT, REVERSED, UNSPECIFIED

}

fun nextMode(currentMode: SortTypeMode) =
    when(currentMode){
        SortTypeMode.DIRECT -> SortTypeMode.REVERSED
        SortTypeMode.REVERSED -> SortTypeMode.DIRECT
        SortTypeMode.UNSPECIFIED -> SortTypeMode.DIRECT
    }

fun modeToDefault() = SortTypeMode.UNSPECIFIED