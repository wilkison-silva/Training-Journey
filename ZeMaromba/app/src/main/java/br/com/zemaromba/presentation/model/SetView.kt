package br.com.zemaromba.presentation.model

data class SetView(
    val id: Long,
    val quantity: Int,
    val repetitions: Int,
    val exerciseView: ExerciseView,
    val weight: Double,
    val observation: String,
    val completed: Boolean,
    val restingTime: Double
)