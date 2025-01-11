package com.example.russian.main.enums

enum class SortTypesEnum {

    ALPHABETICAL,
    WIN_RATE,

}


fun getDisplayableName(t: SortTypesEnum) = when(t){
    SortTypesEnum.ALPHABETICAL -> "Алфавиту"
    SortTypesEnum.WIN_RATE -> "Проценту выполнения"
    else -> throw IllegalArgumentException("Cannot get displayable name for this type: ${t.name}")
}