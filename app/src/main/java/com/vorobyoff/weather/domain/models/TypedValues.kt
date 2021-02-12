package com.vorobyoff.weather.domain.models

class TypedValues(val metric: TypedValue, val imperial: TypedValue) {
    class TypedValue(val unit: String, var value: Int)
}