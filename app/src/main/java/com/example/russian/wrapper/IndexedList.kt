package com.example.russian.wrapper

import kotlinx.coroutines.flow.MutableStateFlow

open class IndexedList<T>(
    val data: MutableList<T>
) {
    private var index = 0

    val currentElement = MutableStateFlow(data.last())

    fun hasPrev() = data.isNotEmpty().and(index > 0)

    fun hasNext() = index < data.lastIndex

    open fun previous(): T {
        index--
        updateCurrent()
        return data[index]
    }

    open fun next(): T {
        index++
        updateCurrent()
        return data[index]
    }

    open fun toLast(): T {
        index = data.lastIndex
        updateCurrent()
        return data[index]
    }

    open fun add(value: T) {
        data.add(value)
        index = data.lastIndex
        updateCurrent()
    }

    open fun addDontMove(value: T){
        add(value)
    }

    open fun pop(): T {
        index = data.lastIndex - 1
        updateCurrent()
        return data.removeLast()
    }

    open fun removeAt(ind: Int){

        val realInd = negativeIndex(ind)

        data.removeAt(realInd)
        if(realInd <= index){
            if (realInd == 0 && index == 0){
                index ++
            }else{
                index --
            }
        }
        updateCurrent()
    }

    fun replaceAt(ind: Int, value: T){
        data[negativeIndex(ind)] = value
        updateCurrent()
    }

    private fun updateCurrent() {
        currentElement.value = data[index]
    }

    private fun negativeIndex(ind: Int): Int{
        return if(ind < 0){
            data.size + ind
        }else{
            ind
        }
    }
}