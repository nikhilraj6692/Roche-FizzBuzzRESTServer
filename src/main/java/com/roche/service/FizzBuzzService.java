package com.roche.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FizzBuzzService {

    public List<String> process(int multiple1, int multiple2, int limit, String replacement1,
        String replacement2) {
        log.info("FizzBuzz processing started");
        List<String> processed = IntStream.range(1, limit + 1).mapToObj(num -> {
            if (num % multiple1 == 0 && num % multiple2 == 0) {
                return replacement1 + replacement2;
            } else if (num % multiple1 == 0) {
                return replacement1;
            } else if (num % multiple2 == 0) {
                return replacement2;
            }
            return String.valueOf(num);
        }).collect(Collectors.toList());

        log.debug("Generated string :: {}", processed);
        log.info("FizzBuzz processing ended");
        return processed;
    }
}
