package com.github.romandezhin.olltvdemo.domain.interactor

import com.github.romandezhin.olltvdemo.domain.model.Program

interface ProgramInteractor {

    suspend fun fetch(borderId: Int, direction: Int): Result<List<Program>>
}