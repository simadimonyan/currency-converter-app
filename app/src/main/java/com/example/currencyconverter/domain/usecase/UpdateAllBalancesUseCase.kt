package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.entity.Balance
import com.example.currencyconverter.domain.repository.AccountRepository
import com.example.currencyconverter.ui.shared.state.RateStateHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateAllBalancesUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val rateStateHolder: RateStateHolder
) {

    suspend fun updateBalances() {
        val balances: MutableList<Balance> = mutableListOf()
        var accounts = accountRepository.getAccounts()
        if (accounts.isEmpty()) {
            accountRepository.setDefaults()
            accounts = accountRepository.getAccounts()
        }
        accounts.forEach {
            balances.add(Balance(it.code, it.amount))
        }
        rateStateHolder.updateBalances(balances)
    }

}