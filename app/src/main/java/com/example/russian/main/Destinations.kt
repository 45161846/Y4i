package com.example.russian.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import kotlinx.serialization.Serializable

sealed class MainNavDestinations{

    @Serializable
    data object Settings: MainNavDestinations()

    @Serializable
    data object Prac: MainNavDestinations()

    @Serializable
    data object StatsScreen: MainNavDestinations()

    @Serializable
    data object StatsFilter: MainNavDestinations()

    @Serializable
    data object DetailsRemote: MainNavDestinations()

    @Serializable
    data object DetailsLocal: MainNavDestinations()

    val destinationSaver = Saver<MutableState<MainNavDestinations>, Int>(
        save = {
            when(it.value){
                is Settings -> 1
                is Prac -> 2
                is DetailsRemote -> 2
                is DetailsLocal -> 2
                is StatsScreen -> 3
                is StatsFilter -> 3
            }
        },
        restore = {
            when(it){
                1 -> mutableStateOf(Settings)
                2 -> mutableStateOf(Prac)
                3 -> mutableStateOf(StatsScreen)
                //can never happen
                else -> throw RuntimeException("Ну и как ты умудрился сохранить несуществующий экран с индексом $it?")
            }
        }
    )
}
