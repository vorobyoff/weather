package com.vorobyoff.weather.data.datasource.remote.adapter

import com.vorobyoff.weather.domain.wrapper.HttpException
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.Result.Failure
import com.vorobyoff.weather.domain.wrapper.Result.Success
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, Result<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<Result<T>>): Unit =
        proxy.enqueue(ResultCallback(this, callback))

    override fun cloneImpl(): ResultCall<T> = ResultCall(proxy.clone())

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<Result<T>>
    ) : Callback<T> {

        @Suppress("unchecked_cast")
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result: Result<T> = if (response.isSuccessful) Success.HttpResponse(
                url = call.request().url.toString(),
                statusMessage = response.message(),
                value = response.body() as T,
                statusCode = response.code()
            ) else Failure.HttpError(
                HttpException(
                    statusCode = response.code(),
                    statusMessage = response.message(),
                    url = call.request().url.toString()
                )
            )

            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result: Failure<out Throwable> = when (error) {
                is retrofit2.HttpException -> Failure.HttpError(
                    HttpException(error.code(), error.message(), cause = error)
                )
                else -> Failure.Error(error)
            }

            callback.onResponse(proxy, Response.success(result))
        }
    }

    override fun timeout(): Timeout = proxy.timeout()
}