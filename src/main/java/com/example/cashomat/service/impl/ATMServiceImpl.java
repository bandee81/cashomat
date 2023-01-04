package com.example.cashomat.service.impl;

import com.example.cashomat.config.ATMProperties;
import com.example.cashomat.exception.IllegalWithdrawalAmountException;
import com.example.cashomat.exception.MoneyStorageException;
import com.example.cashomat.model.Deposit;
import com.example.cashomat.model.Withdraw;
import com.example.cashomat.service.ATMService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ATMServiceImpl implements ATMService {

    private final Map<Integer, Integer> moneyStorage = new HashMap<>();
    private final ATMProperties atmProperties;

    public ATMServiceImpl(ATMProperties atmProperties) {
        this.atmProperties = atmProperties;
    }


    @Override
    public int deposit(Set<Deposit> deposits) {
        if(!isDepositPossible(deposits)) {
            throw new IllegalStateException("Money storage service error");
        }
        deposits.forEach(deposit -> {
            Integer amount = moneyStorage.getOrDefault(deposit.banknote(), 0);
            moneyStorage.put(deposit.banknote(),amount + deposit.count());
        });
        return getMoneyStorageTotalAmount();
    }

    @Override
    public synchronized Set<Withdraw> withdrawal(int amount) {
        if(amount <= 0 || amount % 1000 != 0) {
            throw new IllegalWithdrawalAmountException("Illegal amount value. " +
                    "Must be greater than zero and divisible by one thousand");
        }

        Set<Withdraw> withdraws = new HashSet<>();
        int amountLeft = amount;
        for (int banknote : atmProperties.getBankNotes()) {
            if(amountLeft == 0) break;
            int count = amountLeft / banknote;
            if(count > moneyStorage.getOrDefault(banknote, 0)) {
                count = moneyStorage.getOrDefault(banknote, 0);
            }
            amountLeft -= banknote * count;
            withdraws.add(new Withdraw(banknote, count));
        }

        if(amountLeft != 0) {
            throw new MoneyStorageException("The payment cannot be made");
        }

        withdraws.forEach(withdraw -> moneyStorage.put(withdraw.banknote(),
                moneyStorage.getOrDefault(withdraw.banknote(), 0) - withdraw.count()));

        return withdraws;
    }

    @Override
    public void resetStorage() {
        moneyStorage.clear();
    }

    private boolean isDepositPossible(Set<Deposit> deposits) {
        for(Deposit deposit :deposits) {
            Integer amount = moneyStorage.getOrDefault(deposit.banknote(), 0);
            if(amount + deposit.count() > atmProperties.getBankNoteCapacity(deposit.banknote())) {
                return false;
            }
        }
        return true;
    }

    private int getMoneyStorageTotalAmount() {
        return moneyStorage.entrySet().stream()
                .mapToInt(value -> value.getKey()* value.getValue())
                .sum();
    }

}
