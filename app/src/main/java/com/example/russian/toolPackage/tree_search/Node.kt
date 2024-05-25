package com.example.russian.toolPackage.tree_search

data class Node<T>(val value: T?,
                   var isLeaf: Boolean,
                   val children: MutableMap<T, Node<T>> = mutableMapOf())