package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.dataSource.room.ConverterDatabase
import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val database: ConverterDatabase
) {

    suspend fun addTransaction(from: String, to: String, amountFrom: Double, amountTo: Double) {
        database.transactionDao().insertAll(TransactionDbo(0, from, to, amountFrom, amountTo, LocalDateTime.now()))
    }

    suspend fun getTransactions(): List<TransactionDbo> {
        return database.transactionDao().getAll()
    }

}