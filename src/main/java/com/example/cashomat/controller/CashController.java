package com.example.cashomat.controller;

import com.example.cashomat.model.dto.CashDepositDto;
import com.example.cashomat.model.dto.CashWithdrawalDto;
import com.example.cashomat.model.dto.WithdrawElementDto;
import com.example.cashomat.service.ATMService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CashController {

    private final ATMService atmService;

    public CashController(ATMService atmService) {
        this.atmService = atmService;
    }

    @PostMapping("/withdrawal")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CashWithdrawalDto> withdrawal(@Valid @RequestBody Integer amount)  {
        return ResponseEntity.ok(CashWithdrawalDto.builder()
                .withdrawElements(atmService.withdrawal(amount).stream().map(withdraw -> WithdrawElementDto.builder()
                        .bankNote(String.valueOf(withdraw.banknote()))
                        .count(withdraw.count())
                        .build()).collect(Collectors.toSet()))
                .build());
    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> deposit(@Valid @RequestBody CashDepositDto cashDepositDto)  {
        return ResponseEntity.ok(atmService.deposit(cashDepositDto.getDeposits()));
    }
}
