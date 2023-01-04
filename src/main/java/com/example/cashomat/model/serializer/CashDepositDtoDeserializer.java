package com.example.cashomat.model.serializer;

import com.example.cashomat.model.Deposit;
import com.example.cashomat.model.dto.CashDepositDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CashDepositDtoDeserializer extends JsonDeserializer<CashDepositDto> {
    @Override
    public CashDepositDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<HashMap<String,Integer>> typeRef = new TypeReference<>() {};
        CashDepositDto cashDepositDto = new CashDepositDto();
        cashDepositDto.setDeposits( mapper.convertValue(node, typeRef).entrySet().stream()
                .map(e -> new Deposit(Integer.parseInt(e.getKey()), e.getValue())).collect(Collectors.toSet()));
        return cashDepositDto;
    }
}
