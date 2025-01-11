package com.example.russian.main.tool

interface MyComparable<T> {
    val data: T
    fun compare(other: T): Boolean
}