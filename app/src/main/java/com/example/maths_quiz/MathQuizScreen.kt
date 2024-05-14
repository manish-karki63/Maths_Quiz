package com.example.maths_quiz


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class MathQuestion(val question: String, val correctAnswer: String, val operation: Operation) {
    var userAnswer: String? = null

    fun isCorrect(): Boolean {
        return userAnswer == correctAnswer
    }
}

enum class Operation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathQuizScreen(navController: NavController? = null) {
    val questions = remember {
        listOf(
            MathQuestion("2 + 3 = ?", "5", Operation.ADD),
            MathQuestion("10 - 5 = ?", "5", Operation.SUBTRACT),
            MathQuestion("2 * 4 = ?", "8", Operation.MULTIPLY),
            MathQuestion("15 / 3 = 5?", "Correct", Operation.DIVIDE),
            MathQuestion("7 * 3 = ?", "21", Operation.MULTIPLY)
        )
    }

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
                if (question.operation == Operation.DIVIDE) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(question.question)
                        RadioGroup(
                            options = listOf("Correct", "Incorrect"),
                            selectedOption = question,
                            onOptionSelected = { answer ->
                                questions[index].userAnswer = answer
                            }
                        )
                    }
                } else {
                    MathQuestion(
                        question = question.question,
                        onAnswerChanged = { answer ->
                            questions[index].userAnswer = answer
                        }
                    )
                }
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

@Composable
fun RadioGroup(
    options: List<String>,
    selectedOption: MathQuestion,
    onOptionSelected: (String) -> Unit
) {
    options.forEach { option ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            RadioButton(
                selected = (option == selectedOption.userAnswer),
                onClick = {
                    onOptionSelected(option)
                },
                modifier = Modifier.clickable {
                    onOptionSelected(option)
                }
            )
            Text(text = option, modifier = Modifier.padding(start = 8.dp))
        }
    }
}