package com.example.russian.toolPackage

import com.example.russian.MyTaskNarechia
import com.example.russian.database.Word

class WordToTaskMapper {

    fun wordToNarechieTask(w: Word): MyTaskNarechia {
        return MyTaskNarechia(id = w.id, data = w.value)
    }

}