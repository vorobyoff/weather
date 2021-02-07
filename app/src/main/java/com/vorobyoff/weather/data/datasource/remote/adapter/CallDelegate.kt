package com.vorobyoff.weather.data.datasource.remote.adapter

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CallDelegate<In, Out>(protected val proxy: Call<In>) : Call<Out> {

    override fun execute(): Response<Out> = throw NotImplementedError()

    final override fun enqueue(callback: Callback<Out>): Unit = enqueueImpl(callback)
    abstract fun enqueueImpl(callback: Callback<Out>)

    final override fun clone(): Call<Out> = cloneImpl()
    abstract fun cloneImpl(): Call<Out>

    override fun request(): Request = proxy.request()

    override fun isExecuted() = proxy.isExecuted

    override fun isCanceled() = proxy.isCanceled

    override fun cancel(): Unit = proxy.cancel()
}