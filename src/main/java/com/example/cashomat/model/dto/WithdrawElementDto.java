package com.example.cashomat.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class WithdrawElementDto {
    @NonNull
    String bankNote;
    @NonNull
    int count;
}
