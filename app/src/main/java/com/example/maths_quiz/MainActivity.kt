package com.example.maths_quiz

import android.annotation.SuppressLint
import android.health.connect.datatypes.units.Percentage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathQuizApp()
        }
    }
}

@Composable
fun MathQuizApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "quizScreen") {
        composable("quizScreen") { MathQuizScreen(navController) }
        composable("resultScreen/{percentage}") { backStackEntry ->
            val percentage = backStackEntry.arguments?.getString("percentage") ?: "0.00"
            ResultScreen(navController, percentage)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MathQuizAppPreview() {
    MathQuizApp()
}