package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.dataSource.room.ConverterDatabase
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val database: ConverterDatabase
) {

    suspend fun getAccounts(): List<AccountDbo> {
        return database.accountDao().getAll()
    }

    suspend fun setAccounts(vararg accounts: AccountDbo) {
        database.accountDao().insertAll(*accounts)
    }

    suspend fun setDefaults() {
        database.accountDao().insertAll(AccountDbo("RUB", 75000.0))
    }

}