package com.example.russian.tasks.paronim

class ParonimSequence(
    val similar: List<Paronim>
){
    fun getAllcontexts(): List<String>{
        return List(similar.size){
            similar[it].context
        }
    }

    fun getAllparonims(): List<String>{
        return List(similar.size){
            similar[it].paronim
        }
    }
}