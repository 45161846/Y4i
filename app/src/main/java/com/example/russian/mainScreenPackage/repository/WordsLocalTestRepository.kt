package com.example.russian.mainScreenPackage.repository

import com.example.russian.architecture.data.olddata.entity.Word

class WordsLocalTestRepository : WordsLocalMainScreenRepository(
    words = testWords()
){

    init {
        isLoadingInProcess = false
        allWords
    }

    override fun getCurrentWords(): List<Word> {
        return allWords
    }
}

private fun testWordsValues() = listOf(
    "с*ряду",
    "с*тремглав",
    "с__змальства;ы*| и;",
    "с__знова;ы*| и;",
    "тот*час",
    "черес*чур",
    "без ведома",
    "без запроса",
    "информированность работников - информация для_работников",
    "иронический прием - ироничный взгляд",
    "искусный мастер - искусственный смех",
    "исполнительский состав - исполнительный орган",
    "исходный момент - исходящий звонок",
    "каменистый берег - каменный дом",
    "комфортный перелет - комфортабельный отель",
    "локтЕй",
    "лыжнЯ",
    "мЕстностей",
    "намЕрение"
)

private fun winRateMap() = mapOf(
    "с*ряду" to 0.8F,
    "с*тремглав" to 0.7F,
    "с__змальства;ы*| и;" to 0.2F,
    "с__знова;ы*| и;" to 0.3F,
    "тот*час" to 0.6F,
    "черес*чур" to 0.8F,
    "без ведома" to -1F,
    "без запроса" to -1F,
    "информированность работников - информация для_работников" to 0F,
    "иронический прием - ироничный взгляд" to 0.45F,
    "искусный мастер - искусственный смех" to 0.67F,
    "исполнительский состав - исполнительный орган" to 0.9F,
    "исходный момент - исходящий звонок" to 1F,
    "каменистый берег - каменный дом" to 0.7F,
    "комфортный перелет - комфортабельный отель" to 0.75F,
    "локтЕй" to 0.13F,
    "лыжнЯ" to 0.56F,
    "мЕстностей" to 0.8F,
    "намЕрение" to 0.8F
)

private fun calcTopic(ind: Int) =
    if (ind < 8) 0 else if (ind < 15) 1 else 2

fun testWords(): List<Word> {
    val rows = testWordsValues()
    val winRates = winRateMap()
    return List<Word>(rows.size) {
        Word(
            id = it,
            rows[it],
            calcTopic(it),
            0,
            0,
            winRates[rows[it]] ?: 0F
        )
    }
}