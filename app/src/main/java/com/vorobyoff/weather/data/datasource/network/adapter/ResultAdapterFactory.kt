package com.vorobyoff.weather.data.datasource.network.adapter

import retrofit2.Call
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import com.vorobyoff.weather.domain.wrapper.Result
import retrofit2.CallAdapter

class ResultAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) == Call::class) {
            if (returnType is ParameterizedType) {
                val callInnerType: Type = getParameterUpperBound(0, returnType)

                if (getRawType(callInnerType) == Result::class) {
                    if (callInnerType is ParameterizedType) {
                        val resultInnerType: Type = getParameterUpperBound(0, callInnerType)

                        return ResultCallAdapter<Any?>(resultInnerType)
                    }

                    return ResultCallAdapter<Nothing>(Nothing::class.java)
                }
            }
        }

        return null
    }

    private class ResultCallAdapter<R>(private val type: Type) : CallAdapter<R, Call<Result<R>>> {
        override fun adapt(call: Call<R>): Call<Result<R>> = ResultCall(call)

        override fun responseType() = type
    }
}