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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

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
fun MathQuizScreen(onNavigateToResult: () -> Unit, questions: List<MathQuestion>) {

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
                            selected = question.userAnswer ?: "",
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
                onClick = onNavigateToResult
            ) {
                Text("Submit")
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
    selected: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("Correct", "Incorrect")
    var selectedOption by remember { mutableStateOf(selected) }
    options.forEach { option ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            RadioButton(
                selected = (option == selectedOption),
                onClick = {
                    selectedOption = option
                    onOptionSelected(option)
                },
                modifier = Modifier.clickable {
                    selectedOption = option
                    onOptionSelected(option)
                }
            )
            Text(text = option, modifier = Modifier
                .padding(start = 8.dp)
                .clickable {
                    selectedOption = option
                    onOptionSelected(option)
                })
        }
    }
}