package com.roche.test.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roche.controller.FizzBuzzController;
import com.roche.dto.FizzBuzzRequestDTO;
import com.roche.service.FizzBuzzService;
import com.roche.util.SpringContextUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FizzBuzzController.class)
public class TestFizzBuzzController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FizzBuzzService service;

    @MockBean
    private MessageSource messageSource;

    @Test
    void shouldReturnStringsGeneratedWithFizzBuzzAlgo() throws Exception
    {
        List<String> generatedStrings = validResponse();
        when(service.process(anyInt(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(generatedStrings);
        try (MockedStatic<SpringContextUtil> mockedFactory = Mockito.mockStatic(SpringContextUtil.class)) {
            mockedFactory.when(() -> SpringContextUtil.getBean(any())).thenReturn(messageSource);
            this.mockMvc.perform(
                    post("/fizz-buzz/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new FizzBuzzRequestDTO(3,5,15,"fizz","buzz")))).andDo(print()).andExpect(status().isOk())
                        .andExpect(content().string(containsString(asJsonString(generatedStrings))));
        }

    }

    @Test
    void shouldReturnErrorWithFizzBuzzAlgo() throws Exception
    {
        try (MockedStatic<SpringContextUtil> mockedFactory = Mockito.mockStatic(SpringContextUtil.class))
        {
            mockedFactory.when(() -> SpringContextUtil.getBean(any())).thenReturn(messageSource);
            this.mockMvc.perform(
                    post("/fizz-buzz/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new FizzBuzzRequestDTO(3, 5, 0, "", "buzz"))))
                .andDo(print())
                .andExpect(status().isPreconditionFailed());
        }


    }

    private List<String> validResponse() {
        return new ArrayList<>(Arrays.asList("1","fizz","buzz","fizz","5","fizzbuzz"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
