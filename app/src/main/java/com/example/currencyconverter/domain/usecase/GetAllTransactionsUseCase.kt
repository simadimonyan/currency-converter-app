package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import com.example.currencyconverter.domain.repository.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend fun getAll(): List<TransactionDbo> {
        return transactionRepository.getTransactions()
    }

}