package com.example.cashomat.model.serializer;

import com.example.cashomat.model.dto.CashWithdrawalDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CashWithdrawalDtoSerializer extends JsonSerializer<CashWithdrawalDto> {
    @Override
    public void serialize(CashWithdrawalDto cashWithdrawalDto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        cashWithdrawalDto.getWithdrawElements().forEach(withdrawalValueDto -> {
            try {
                jsonGenerator.writeFieldName(withdrawalValueDto.getBankNote());
                jsonGenerator.writeNumber(withdrawalValueDto.getCount());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
        jsonGenerator.writeEndObject();
    }
}
