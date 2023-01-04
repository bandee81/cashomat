package com.example.cashomat.model.dto;

import com.example.cashomat.model.Deposit;
import com.example.cashomat.model.serializer.CashDepositDtoDeserializer;
import com.example.cashomat.model.validator.DepositConstraint;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Set;

@Data
@JsonDeserialize(using = CashDepositDtoDeserializer.class)
public class CashDepositDto {
    @DepositConstraint
    Set<Deposit> deposits;
}
