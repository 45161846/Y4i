package com.example.russian.mainScreenPackage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import com.example.russian.R
import com.example.russian.gameClasses.GameActivity
import com.example.russian.mainScreenPackage.screenDrawers.DefaultScaffold
import com.example.russian.mainScreenPackage.screenDrawers.DrawPracticeContent
import com.example.russian.mainScreenPackage.screenDrawers.DrawSettingsContent
import com.example.russian.mainScreenPackage.screenDrawers.StatsScaffold
import kotlinx.serialization.Serializable

data class BottomNavigationItem(
    val type_of_screen: ScreenType,
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector
)

class MainScreenActivity : ComponentActivity() {

    private lateinit var viewmodel: MyMainViewModelImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application

        viewmodel = MyMainViewModelImpl(
            app
        )

        setContent{
            MainScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        window.navigationBarColor = getColor(R.color.dark_background_2)
    }

    @Composable
    private fun MainScreen(){

        var index by remember {
            mutableIntStateOf(viewmodel.selectedScreenIndex.value ?: 1)
        }

        viewmodel.selectedScreenIndex.observe(this){
            index = it ?: 1
            window.statusBarColor = if(it == 2){
                getColor(R.color.light_background)
            }else{
                getColor(R.color.dark_background)
            }
        }

        val changeSelectedItemIndex = { it : Int ->
            viewmodel.selectedScreenIndex.value = it
        }

        var words by remember {
            mutableStateOf(viewmodel.repository.getCurrentWords())
        }

        viewmodel.repository.currentWords.observe(this){
            words = it
        }

        when(index){
            2 -> {
                StatsScaffold(
                    selectedItemIndex = index,
                    window = window,
                    context = this,
                    scope = rememberCoroutineScope(),
                    onSearch = { viewmodel.search(it) },
                    onClear = { viewmodel.resetCurrentWords() },
                    changeSelectedItemIndex = changeSelectedItemIndex,
                    contentList = words
                )
            }
            1 -> {
                DefaultScaffold(
                    selectedItemIndex = index,
                    displayableUI = {padding ->
                        DrawPracticeContent(paddingValues = padding, startGame = { startGame(it) })
                                    },
                    changeSelectedItemIndex)
            }
            0 -> DefaultScaffold(selectedItemIndex = index, displayableUI = {
                DrawSettingsContent(
                    paddingValues = it
                )
            }, changeSelectedItemIndex)
        }

    }
    private fun startGame(taskTopic: Int){
        val intent = Intent(this, GameActivity::class.java)
        val key = this.getString(R.string.game_activity_start_topic_key)
        intent.putExtra(key, taskTopic)
        this.startActivity(intent)
    }

}

@Serializable
open class ScreenType

@Serializable
object ScreenTypeSettings: ScreenType()
@Serializable
object ScreenTypePractice: ScreenType()
@Serializable
object ScreenTypeStats: ScreenType()