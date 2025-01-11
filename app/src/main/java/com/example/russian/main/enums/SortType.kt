package com.example.russian.main.enums

class SortType(
    var type: SortTypesEnum,
    var mode: SortTypeMode = SortTypeMode.UNSPECIFIED
) {

    override fun toString(): String {
        return "Type: ${type.name}, mode: ${mode.name}"
    }

    fun changeAfterClick(){
        mode = nextMode(mode)
    }
}


fun getSortTypeFromList(list: List<SortType>): SortType {
    list.forEach{
        if(it.mode != SortTypeMode.UNSPECIFIED){
            return it
        }
    }
    throw RuntimeException("cannot find any available sort types in list: $list")
}


fun defaultSortType() = arrayOf(
    SortType(SortTypesEnum.ALPHABETICAL, mode = SortTypeMode.DIRECT),
    SortType(SortTypesEnum.WIN_RATE)
)

