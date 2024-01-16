package com.roche.test.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roche.controller.StatsController;
import com.roche.dao.AuditRepository;
import com.roche.entity.AuditEntity;
import com.roche.model.Identifier;
import com.roche.util.SpringContextUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = StatsController.class)
public class TestStatsController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditRepository repository;

    @MockBean
    private MessageSource messageSource;

    @Test
    void shouldReturnStatsWithMostFrequentRequest() throws Exception
    {
        when(repository.findByIdentifier(anyString())).thenReturn(validResponse());
        try (MockedStatic<SpringContextUtil> mockedFactory = Mockito.mockStatic(SpringContextUtil.class)) {
            mockedFactory.when(() -> SpringContextUtil.getBean(any())).thenReturn(messageSource);

            Map<String, Object> response = new HashMap<>();
            response.put("hits",3);
            String params = "{\"int1\":3,\"int2\":4,\"limit\":10,\"str1\":\"fizz\",\"str2\":\"buzz\"}";
            response.put("params", new ObjectMapper().readValue(params, Map.class));

            this.mockMvc.perform(
                    get("/stats/most-frequent-request")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(response))));
        }

    }

    private List<AuditEntity> validResponse() {
        return new ArrayList(){{
            add(new AuditEntity(UUID.randomUUID().toString(), new Date(), "{\"int1\":3,\"int2\":4,\"limit\":10,\"str1\":\"fizz\",\"str2\":\"buzz\"}", Identifier.GENERATE_FIZZ_BUZZ_ALGO.name()));
            add(new AuditEntity(UUID.randomUUID().toString(), new Date(), "{\"int1\":2,\"int2\":3,\"limit\":10,\"str1\":\"fizz\",\"str2\":\"buzz\"}", Identifier.GENERATE_FIZZ_BUZZ_ALGO.name()));
            add(new AuditEntity(UUID.randomUUID().toString(), new Date(), "{\"int1\":3,\"int2\":4,\"limit\":10,\"str1\":\"fizz\",\"str2\":\"buzz\"}", Identifier.GENERATE_FIZZ_BUZZ_ALGO.name()));
            add(new AuditEntity(UUID.randomUUID().toString(), new Date(), "{\"int1\":3,\"int2\":4,\"limit\":10,\"str1\":\"fizz\",\"str2\":\"buzz\"}", Identifier.GENERATE_FIZZ_BUZZ_ALGO.name()));
        }};
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
