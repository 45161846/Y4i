package com.example.russian.enums

enum class SortTypesEnum {

    RANDOM,
    RANDOM_WEIGHTED,
    ALPHABETICAL,
    ALPHABETICAL_REVERSED,
    WIN_RATE,
    WIN_RATE_REVERSED

}


fun allSortTypeModes() = listOf(
    SortTypesEnum.ALPHABETICAL, SortTypesEnum.WIN_RATE
)

fun getDisplayableName(t: SortTypesEnum) = when(t){
    SortTypesEnum.ALPHABETICAL -> "Алфавиту"
    SortTypesEnum.WIN_RATE -> "Проценту выполнения"
    else -> throw IllegalArgumentException("Cannot get displayable name for this type: ${t.name}")
}