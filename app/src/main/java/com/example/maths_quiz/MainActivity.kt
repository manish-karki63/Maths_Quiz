package com.example.maths_quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
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
fun MathQuizApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "quizScreen"
) {
    val questions = remember {
        listOf(
            MathQuestion("2 + 3 = ?", "5", Operation.ADD),
            MathQuestion("10 - 5 = ?", "5", Operation.SUBTRACT),
            MathQuestion("2 * 4 = ?", "8", Operation.MULTIPLY),
            MathQuestion("15 / 3 = 5?", "Correct", Operation.DIVIDE),
            MathQuestion("7 * 3 = ?", "21", Operation.MULTIPLY)
        )
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("quizScreen") {
            MathQuizScreen(
                onNavigateToResult = {
                    val correctCount = questions.count { it.isCorrect() }
                    val totalQuestions = questions.size
                    val percentage = (correctCount.toFloat() / totalQuestions.toFloat()) * 100
                    navController.navigate("resultScreen/${percentage}")
                },
                questions
            )
        }
        composable("resultScreen/{percentage}") { backStackEntry ->
            val percentage = backStackEntry.arguments?.getString("percentage") ?: "0.00"
            ResultScreen(percentage, questions)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MathQuizAppPreview() {
    MathQuizApp()
}