package com.example.russian.tool

interface MyComparable<T> {
    val data: T
    fun compare(other: T): Boolean
}