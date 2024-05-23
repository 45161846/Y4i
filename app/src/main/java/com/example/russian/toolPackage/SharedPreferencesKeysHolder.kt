package com.example.russian.toolPackage

class SharedPreferencesKeysHolder {

    fun topicShPKey(topic: Int): String{
        return "topic_${topic}"
    }

    fun firstStartKey():String{
        return "first_start"
    }

}