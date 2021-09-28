package com.github.romandezhin.olltvdemo.data.net

import com.github.romandezhin.olltvdemo.data.model.ProgramResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProgramService {

    @GET("demo")
    fun programAsync(
        @Query("serial_number") serialNumber: String,
        @Query("borderId") borderId: String,
        @Query("direction") direction: Int
    ): Deferred<Response<ProgramResponse>>
}