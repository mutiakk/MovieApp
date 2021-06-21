package com.example.movieappbisa.util

import androidx.test.espresso.idling.CountingIdlingResource

object Idling {
    private const val RESOURCE = "GLOBAL"
    val idlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }
}

