package com.example.maths_quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(percentage: String, questions: List<MathQuestion>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        Text("Your Quiz Result", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(6.dp))
        Text("Overall Score: $percentage %")
        questions.forEachIndexed { _, mathQuestion ->
            if (mathQuestion.isCorrect()) {
                Text(
                    text = "Your answer is correct. ${mathQuestion.question} answer is ${mathQuestion.correctAnswer}.",
                    color = Color.Green
                )
            } else {
                Text(
                    text = "Your answer is incorrect. ${mathQuestion.question} answer is ${mathQuestion.correctAnswer}",
                    color = Color.Red
                )
            }
        }
    }
}