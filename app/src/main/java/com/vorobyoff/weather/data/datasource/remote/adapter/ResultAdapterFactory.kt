package com.vorobyoff.weather.data.datasource.remote.adapter

import com.vorobyoff.weather.domain.wrapper.Result
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = if (getRawType(returnType) == Call::class.java) {
        if (returnType is ParameterizedType) {
            val callInnerType: Type = getParameterUpperBound(0, returnType)
            val isResultClass: Boolean = getRawType(callInnerType) == Result::class.java

            if (isResultClass && callInnerType is ParameterizedType) {
                val resultInnerType: Type = getParameterUpperBound(0, callInnerType)
                ResultCallAdapter<Any?>(resultInnerType)
            } else ResultCallAdapter<Nothing>(Nothing::class.java)

        } else null
    } else null

    private class ResultCallAdapter<R>(private val type: Type) : CallAdapter<R, Call<Result<R>>> {
        override fun adapt(call: Call<R>): Call<Result<R>> = ResultCall(call)

        override fun responseType() = type
    }
}