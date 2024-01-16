package com.roche.controller;

import static com.roche.util.Constants.STATS_OUTPUT_SUCCESSFUL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roche.dao.AuditRepository;
import com.roche.entity.AuditEntity;
import com.roche.util.ResponseUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stats/")
public class StatsController
{
    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;
    public StatsController(AuditRepository auditRepository, ObjectMapper objectMapper) {
        this.auditRepository = auditRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("most-frequent-request")
    public ResponseEntity getMostFrequentRequests(
        @RequestParam(required = false, defaultValue = "GENERATE_FIZZ_BUZZ_ALGO") String identifier)
        throws JsonProcessingException
    {
        List<AuditEntity> requests = auditRepository.findByIdentifier(identifier);
        Map<String, Integer> requestToFrequencyMap = new HashMap<>();
        AtomicReference<String> mostUsedRequest = new AtomicReference<>();
        AtomicInteger mostUsedRequestCount = new AtomicInteger();

        Optional.ofNullable(requests).orElse(Collections.emptyList()).stream().forEach(request -> {
            int count = requestToFrequencyMap.compute(request.getRequestBody(),
                (k, v) -> null == v ? 1 : v + 1);

            if (count > mostUsedRequestCount.get()) {
                mostUsedRequestCount.set(count);
                mostUsedRequest.set(request.getRequestBody());
            }
        });


        Map<String, Object> response = new LinkedHashMap<>();
        response.put("hits", mostUsedRequestCount.get());
        response.put("params", objectMapper.readValue(mostUsedRequest.get(), Map.class));
        return ResponseUtil.generateResponse(STATS_OUTPUT_SUCCESSFUL, response, HttpStatus.OK);
    }
}
