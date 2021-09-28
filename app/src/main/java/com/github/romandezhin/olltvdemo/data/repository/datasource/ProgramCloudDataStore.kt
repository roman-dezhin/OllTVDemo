package com.github.romandezhin.olltvdemo.data.repository.datasource

import com.github.romandezhin.olltvdemo.data.model.Item
import com.github.romandezhin.olltvdemo.data.net.ConnectionManager
import com.github.romandezhin.olltvdemo.data.net.ProgramService
import com.github.romandezhin.olltvdemo.domain.exception.NetworkConnectionException

class ProgramCloudDataStore(
    private val connectionManager: ConnectionManager,
    private val service: ProgramService,
    private val serialNumber: String
) : ProgramDataStore {
    override suspend fun getItemList(borderId: Int, direction: Int): List<Item> {
        if (connectionManager.isNetworkAbsent()) {
            throw NetworkConnectionException()
        } else {
            val programs: List<Item>
            try {
                val responseAsync =
                    service.programAsync(serialNumber, borderId.toString(), direction)
                val result = responseAsync.await()
                if (result.isSuccessful) {
                    programs = if (result.body() != null) {
                        result.body()!!.items
                    } else {
                        emptyList()
                    }
                    return programs
                } else {
                    throw Exception("Error: " + result.message() + ", code: " + result.code())
                }
            } catch (exception: Exception) {
                throw exception
            }
        }
    }
}