package com.infopolis.infopolis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.infopolis.infopolis.ui.theme.InfopolisTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InfopolisTheme {
                val navController = rememberNavController()
                NavigationGraph(navController = navController)
            }
        }
    }
}
