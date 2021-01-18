package com.vorobyoff.weather.data.datasource.network.adapter

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CallDelegate<In, Out>(protected val proxy: Call<In>) : Call<Out> {

    final override fun enqueue(callback: Callback<Out>): Unit = enqueueImpl(callback)

    override fun execute(): Response<Out> = throw NotImplementedError()

    override fun isExecuted() = proxy.isExecuted

    override fun isCanceled() = proxy.isCanceled

    final override fun clone(): Call<Out> = cloneImpl()

    override fun request(): Request = proxy.request()

    abstract fun enqueueImpl(callback: Callback<Out>)

    override fun cancel(): Unit = proxy.cancel()

    abstract fun cloneImpl(): Call<Out>
}