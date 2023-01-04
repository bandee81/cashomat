package com.example.cashomat.model.dto;

import com.example.cashomat.model.serializer.CashWithdrawalDtoSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@JsonSerialize(using = CashWithdrawalDtoSerializer.class)
public class CashWithdrawalDto {
    @NonNull
    Set<WithdrawElementDto> withdrawElements;
}
