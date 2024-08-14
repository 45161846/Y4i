package com.example.russian.MyEnumClasses

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
fun changeSortTypesAfterClickOn(array: Array<SortType>,index: Int){
    array.forEachIndexed{ind, type ->
        if(ind == index){
            array[ind].changeAfterClick()
        }else{
            array[ind].mode = SortTypeMode.UNSPECIFIED
        }
    }
}

fun getSortTypeFromArray(array: Array<SortType>): SortType{
    array.forEach{
        if(it.mode != SortTypeMode.UNSPECIFIED){
            return it
        }
    }
    throw RuntimeException("cannot find any available sort types in array: ${array.contentToString()}")
}

fun getSortTypeModes(array: Array<SortType>): Array<SortTypeMode>{
    return Array(array.size){
        array[it].mode
    }
}

fun defaultSortType() = arrayOf(
    SortType(SortTypesEnum.ALPHABETICAL, mode = SortTypeMode.DIRECT),
    SortType(SortTypesEnum.WIN_RATE)
)

