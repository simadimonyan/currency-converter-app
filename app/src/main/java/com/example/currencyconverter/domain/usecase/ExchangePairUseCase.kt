package com.example.currencyconverter.domain.usecase

import Currency
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.example.currencyconverter.domain.repository.AccountRepository
import com.example.currencyconverter.domain.repository.TransactionRepository
import com.example.currencyconverter.ui.shared.state.ExchangeStateHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangePairUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val exchangeStateHolder: ExchangeStateHolder,
    private val transactionRepository: TransactionRepository
) {

    suspend fun transfer(fromFunds: Currency, toTarget: Currency, targetAmount: Double) {
        val exchangeState = exchangeStateHolder.exchangeState.value
        val initialAccounts = accountRepository.getAccounts()

        val accounts = initialAccounts.toMutableList()

        var fundsIndex = 0
        var targetIndex = 0
        var fundsAccount = accounts.firstOrNull { it.code == fromFunds.name }.also { if (it != null) fundsIndex = accounts.indexOf(it) }
        var targetAccount = accounts.firstOrNull { it.code == toTarget.name }.also { if (it != null) targetIndex = accounts.indexOf(it) }

        val fundsNewAmount = (fundsAccount?.amount ?: 75000.0) - (targetAmount * exchangeState.oneBuyRate)
        fundsAccount = AccountDbo(fundsAccount?.code ?: "RUB", fundsNewAmount)

        val targetNewAmount = (targetAccount?.amount ?: 0.0) + targetAmount
        targetAccount = AccountDbo(toTarget.name, targetNewAmount)

        accounts[fundsIndex] = fundsAccount
        if (initialAccounts.any { it.code == toTarget.name }) {
            accounts[targetIndex] = targetAccount
        } else {
            accounts.add(targetAccount)
        }

        accountRepository.setAccounts(*accounts.toTypedArray())
        transactionRepository.addTransaction(
            fundsAccount.code,
            targetAccount.code,
            (targetAmount * exchangeState.oneBuyRate),
            targetAmount
        )
    }

}