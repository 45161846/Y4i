package com.example.russian.toolPackage

import com.example.russian.MyTaskNarechia
import com.example.russian.database.Word
import kotlin.random.Random

class WordToTaskMapper {

    fun wordToNarechieTask(w: Word): MyTaskNarechia {
        return MyTaskNarechia(id = w.id, data = w.value)
    }

    fun toContextTask(input: String): TaskInterface{
        return if(Random.nextBoolean()){
            inputToContextParonimTask(input)
        }else{
            inputToParonimContextTask(input)
        }
    }
    private fun inputToParonimContextTask(input: String): ByContextTaskInterface{

        val options = createParonimsList(splitIntoSingleParonims(input))
        val correctPair = options.similar.random()
        val correctContext = correctPair.context
        val allContexts = options.getAllcontexts()
        val ind = allContexts.indexOf(correctContext)

        return ByContextTaskInterface(
            correctPair.paronim,
            allContexts,
            ind
        )

    }

    private fun inputToContextParonimTask(input: String): ContextByTaskInterface{
        val options = createParonimsList(splitIntoSingleParonims(input))
        val correctPair = options.similar.random()
        val correctParonim = correctPair.paronim
        val allParonims = options.getAllparonims()
        val ind = allParonims.indexOf(correctParonim)

        return ContextByTaskInterface(
            correctPair.context,
            allParonims,
            ind
        )
    }

    private fun splitIntoSingleParonims(input: String): List<String>{
        val q = input.trim().split(" - ")
        return q
    }

    private fun createParonimsList(l: List<String>): ParonimSequence{
        val res = List(l.size){
            val s = l[it].split(" ")
            Paronim(s[0], s[1])
        }
        return ParonimSequence(res)
    }

}