package com.github.romandezhin.olltvdemo.domain.repository

import com.github.romandezhin.olltvdemo.domain.model.Program

interface ProgramRepository {
    suspend fun fetch(borderId: Int, direction: Int): List<Program>
}