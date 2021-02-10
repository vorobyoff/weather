package com.vorobyoff.weather.presentation.models

sealed class State<out T> {
    object Loading : State<Nothing>()
    class Successed<T>(val value: T) : State<T>()
    class Errored<E : Throwable>(val error: E) : State<Nothing>()
}