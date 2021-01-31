package com.vorobyoff.weather.data.datasource.remote.adapter

import com.vorobyoff.weather.domain.wrapper.HttpException
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.Result.Failure
import com.vorobyoff.weather.domain.wrapper.Result.Failure.Error
import com.vorobyoff.weather.domain.wrapper.Result.Failure.HttpError
import com.vorobyoff.weather.domain.wrapper.Result.Success.HttpResponse
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, Result<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<Result<T>>): Unit =
        proxy.enqueue(ResultCallback(this, callback))

    override fun cloneImpl(): ResultCall<T> = ResultCall(proxy.clone())

    override fun timeout(): Timeout = proxy.timeout()

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<Result<T>>
    ) : Callback<T> {

        @Suppress("unchecked_cast")
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result: Result<T> =
                if (response.isSuccessful) HttpResponse(
                    value = response.body() as T,
                    statusCode = response.code(),
                    statusMessage = response.message(),
                    url = call.request().url.toString()
                ) else handleError(
                    code = response.code(),
                    message = response.message(),
                    url = call.request().url.toString()
                )

            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result: Failure<out Throwable> =
                if (error !is retrofit2.HttpException) Error(error)
                else handleError(code = error.code(), message = error.message(), cause = error)

            callback.onResponse(proxy, Response.success(result))
        }

        private fun handleError(
            code: Int,
            url: String? = null,
            message: String? = null,
            cause: Throwable? = null
        ) = HttpError(
            HttpException(statusCode = code, statusMessage = message, cause = cause, url = url)
        )
    }
}