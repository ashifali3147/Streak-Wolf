package com.tlw.streakwolf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tlw.streakwolf.ui.home.HomeScreen
import com.tlw.streakwolf.ui.theme.StreakWolfTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StreakWolfTheme {
                HomeScreen()
            }
        }
    }
}
