package com.vorobyoff.weather.presentation.models

data class TypedValuesVO(val metric: TypedValueVO, val imperial: TypedValueVO) {
    data class TypedValueVO(val unit: String, val value: Int)
}