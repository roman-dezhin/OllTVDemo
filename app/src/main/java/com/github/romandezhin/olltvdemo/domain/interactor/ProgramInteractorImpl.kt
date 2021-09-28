package com.github.romandezhin.olltvdemo.domain.interactor

import com.github.romandezhin.olltvdemo.domain.exception.NetworkConnectionException
import com.github.romandezhin.olltvdemo.domain.model.Program
import com.github.romandezhin.olltvdemo.domain.repository.ProgramRepository

class ProgramInteractorImpl(private val repository: ProgramRepository) : ProgramInteractor {
    override suspend fun fetch(borderId: Int, direction: Int): Result<List<Program>> =
        try {
            Result.Success(repository.fetch(borderId, direction))
        } catch (e: Exception) {
            if (e is NetworkConnectionException)
                Result.Error(NetworkConnectionException())
            else
                Result.Error(e)
        }
}