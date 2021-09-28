package com.github.romandezhin.olltvdemo.data.repository.datasource

import com.github.romandezhin.olltvdemo.data.model.Item

interface ProgramDataStore {

    suspend fun getItemList(borderId: Int, direction: Int): List<Item>
}