package com.github.romandezhin.olltvdemo.data.model.mapper

import com.github.romandezhin.olltvdemo.data.model.Item
import com.github.romandezhin.olltvdemo.domain.model.Program

class ProgramMapper : Mapper<List<Item>, List<Program>> {
    override fun map(source: List<Item>): List<Program> {
        return source.map { map(it) }
    }

    private fun map(source: Item): Program {
        return Program(
            source.id,
            source.icon,
            source.name
        )
    }
}