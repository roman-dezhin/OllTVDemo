package com.github.romandezhin.olltvdemo.data.repository

import com.github.romandezhin.olltvdemo.data.model.mapper.ProgramMapper
import com.github.romandezhin.olltvdemo.data.repository.datasource.ProgramDataStore
import com.github.romandezhin.olltvdemo.domain.model.Program
import com.github.romandezhin.olltvdemo.domain.repository.ProgramRepository

class ProgramRepositoryImpl(
    private val dataStore: ProgramDataStore,
    private val mapper: ProgramMapper
) : ProgramRepository {
    override suspend fun fetch(borderId: Int, direction: Int): List<Program> {
        return mapper.map(dataStore.getItemList(borderId, direction))
    }
}