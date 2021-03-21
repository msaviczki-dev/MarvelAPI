package com.msaviczki.marvel_api.datasource

interface DataSourceManager<DATA> {
    fun save(item: DATA)
    fun get(): DATA?
    fun remove(item: DATA): Boolean
}