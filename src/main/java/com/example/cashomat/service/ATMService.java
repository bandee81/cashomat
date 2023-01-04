package com.example.cashomat.service;

import com.example.cashomat.model.Deposit;
import com.example.cashomat.model.Withdraw;

import java.util.Set;

public interface ATMService {
    int deposit(Set<Deposit> deposits);
    Set<Withdraw> withdrawal(int amount);

    void resetStorage();
}
