package com.itis.androidtestproject.model

sealed interface BaseModel {
    data class Anime(
        val id: Int,
        val title: String,
        val author: String,
        val rating: Double,
        val description: String,
        val poster: String
    ): BaseModel

    data class Ad(
        val title: String,
        val description: String,
        val photo: String
    ): BaseModel
}