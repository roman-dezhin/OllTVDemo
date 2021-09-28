package com.github.romandezhin.olltvdemo.data.model.mapper

interface Mapper<S, R> {

    fun map(source: S): R
}