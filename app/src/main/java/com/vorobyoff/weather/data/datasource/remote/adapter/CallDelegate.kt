package com.vorobyoff.weather.data.datasource.remote.adapter

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CallDelegate<In, Out>(protected val proxy: Call<In>) : Call<Out> {

    final override fun enqueue(callback: Callback<Out>): Unit = enqueueImpl(callback)

    final override fun execute(): Response<Out> = throw NotImplementedError()

    final override fun isExecuted() = proxy.isExecuted

    final override fun isCanceled() = proxy.isCanceled

    final override fun request(): Request = proxy.request()

    final override fun clone(): Call<Out> = cloneImpl()

    final override fun cancel(): Unit = proxy.cancel()

    abstract fun enqueueImpl(callback: Callback<Out>)

    abstract fun cloneImpl(): Call<Out>
}