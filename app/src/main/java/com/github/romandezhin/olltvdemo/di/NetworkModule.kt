package com.github.romandezhin.olltvdemo.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.github.romandezhin.olltvdemo.data.net.ConnectionManager
import com.github.romandezhin.olltvdemo.data.net.ConnectionManagerImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    private const val BASE_URL = "https://oll.tv/api/"
    private lateinit var retrofit: Retrofit
    lateinit var connectionManager: ConnectionManager


    fun initialize(app: Application) {
        connectionManager = ConnectionManagerImpl(getConnectivityManager(app))
        retrofit = getRetrofit(getOkHttpClient())
    }

    fun <T> getService(className: Class<T>): T = retrofit.create(className)

    private fun getConnectivityManager(context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    private fun getRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    private fun getOkHttpClient() =
        OkHttpClient().newBuilder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()

}
