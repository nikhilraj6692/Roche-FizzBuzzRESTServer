package com.roche.test.service;

import com.roche.service.FizzBuzzService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TestFizzBuzzService {
    @InjectMocks
    private FizzBuzzService fizzBuzzService;

    @Test
    public void testGenerateStringsWithFizzBuzzAlgo() {
        List<String> list = fizzBuzzService.process(3, 5, 20, "fizz","buzz");
        assertEquals(list.stream().filter(str->str.equals("fizz")).count(), 5);
        assertEquals(list.stream().filter(str->str.equals("buzz")).count(), 3);
        assertEquals(list.stream().filter(str->str.equals("fizzbuzz")).count(), 1);
    }


}
