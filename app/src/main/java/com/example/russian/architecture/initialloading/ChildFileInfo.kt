package com.example.russian.architecture.initialloading

class ChildFileInfo(
    val value: String
) {

    val childFileName: String
    val topicNumber: Int
    val rowsCount: Int


    init {
        val splitValues = value.split(";")
        childFileName = splitValues[0]
        topicNumber = Integer.parseInt(splitValues[1])
        rowsCount = Integer.parseInt(splitValues[2])
    }

}