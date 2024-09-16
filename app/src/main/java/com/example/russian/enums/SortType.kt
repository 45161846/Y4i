package com.example.russian.enums

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


fun changeSortTypesAfterClickOn(array: Array<SortType>,index: Int): Array<SortType>{
    array.clone().forEachIndexed{ind, type ->
        if(ind == index){
            type.changeAfterClick()
        }else{
            if (type.mode != SortTypeMode.UNSPECIFIED){
                type.mode = SortTypeMode.UNSPECIFIED
            }
        }
    }
    return array
}

fun changeSortTypesAfterClickOn(list: List<SortType>,index: Int): List<SortType>{
    list.forEachIndexed{ind, type ->
        if(ind == index){
            type.changeAfterClick()
        }else{
            if (type.mode != SortTypeMode.UNSPECIFIED){
                type.mode = SortTypeMode.UNSPECIFIED
            }
        }
    }
    return list
}

fun getSortTypeFromList(list: List<SortType>): SortType{
    list.forEach{
        if(it.mode != SortTypeMode.UNSPECIFIED){
            return it
        }
    }
    throw RuntimeException("cannot find any available sort types in list: $list")
}

fun getSortTypeModes(list: List<SortType>) = list.map {
    it.mode
}.toTypedArray()


fun defaultSortType() = arrayOf(
    SortType(SortTypesEnum.ALPHABETICAL, mode = SortTypeMode.DIRECT),
    SortType(SortTypesEnum.WIN_RATE)
)

