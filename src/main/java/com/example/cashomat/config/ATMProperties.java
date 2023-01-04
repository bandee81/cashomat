package com.example.cashomat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix = "atm")
@Data
public class ATMProperties {
    private Map<String, Cassette> cassettes;

    public Set<Integer> getBankNotes(){
        return cassettes.values().stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(Cassette::getBanknote))
                .map(Cassette::getBanknote)
                .collect(Collectors.toSet());
    }
    public int getBankNoteCapacity(int banknote){
        return cassettes.values().stream()
                .sorted(Comparator.comparingInt(Cassette::getBanknote))
                .filter(cassette -> banknote == cassette.banknote)
                .mapToInt(Cassette::getMaxCapacity)
                .sum();
    }

    public int getMaxCapacity() {
        return cassettes.values().stream()
                .filter(Objects::nonNull)
                .mapToInt(Cassette::getMaxCapacity)
                .sum();
    }

    @Data
    public static class Cassette {
        private int banknote;
        private int maxCapacity;
    }
}
