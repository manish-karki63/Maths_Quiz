package com.example.maths_quiz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MathQuizApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "quizScreen") {
        composable("quizScreen") { MathQuizScreen(navController) }
        composable("resultScreen/{percentage}") { backStackEntry ->
            val percentage = backStackEntry.arguments?.getString("percentage") ?: "0.00"
            ResultScreen(percentage)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathQuizScreen(navController: NavController? = null) {
    val questions = remember { listOf(
        MathQuestion("2 + 3 =", 5),
        MathQuestion("10 - 5 =", 5),
        MathQuestion("2 * 4 =", 8),
        MathQuestion("15 / 3 =", 5),
        MathQuestion("7 * 3 =", 21)
    ) }

    var showResult by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Math Quiz") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            questions.forEachIndexed { index, question ->
                MathQuestion(
                    question = question.toString(),
                    onAnswerChanged = { answer ->
                        questions[index].userAnswer = answer
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    showResult = true
                }
            ) {
                Text("Submit")
            }
            if (showResult) {
                val correctCount = questions.count { it.isCorrect() }
                val totalQuestions = questions.size
                val percentage = (correctCount.toFloat() / totalQuestions.toFloat()) * 100
                navController?.navigate("result/${String.format("%.2f", percentage)}")
            }
        }
    }
}

@Composable
fun MathQuestion(question: String, onAnswerChanged: (String) -> Unit) {
    var userAnswer by remember { mutableStateOf("") }

    Column {
        Text(question)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = userAnswer,
            onValueChange = {
                userAnswer = it
                onAnswerChanged(it)
            },
            label = { Text("Your Answer") }
        )
    }
}

data class MathQuestion(val question: String, val answer: Int, var userAnswer: String = "") {
    fun isCorrect(): Boolean {
        return userAnswer.toIntOrNull() == answer
    }
}

@Composable
fun ResultScreen(percentage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Your Quiz Result", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Overall Score: $percentage%")
    }
}

@Preview(showBackground = true)
@Composable
fun MathQuizAppPreview() {
    MathQuizApp()
}