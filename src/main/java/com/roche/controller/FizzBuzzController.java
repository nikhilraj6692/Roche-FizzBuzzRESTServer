package com.roche.controller;

import com.roche.annotation.Audit;
import com.roche.dto.FizzBuzzRequestDTO;
import com.roche.model.Identifier;
import com.roche.service.FizzBuzzService;
import com.roche.util.ResponseUtil;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.roche.util.Constants.FIZZ_BUZZ_OUTPUT_SUCCESSFUL;

@Slf4j
@Validated
@RestController
@RequestMapping("/fizz-buzz/")
public class FizzBuzzController {

    private final FizzBuzzService fizzBuzzService;

    public FizzBuzzController(final FizzBuzzService fizzBuzzService) {
        this.fizzBuzzService = fizzBuzzService;
    }

    @Audit(identifier = Identifier.GENERATE_FIZZ_BUZZ_ALGO)
    @PostMapping("generate")
    public ResponseEntity fizzBuzzAlgo(@RequestBody @Valid FizzBuzzRequestDTO request) {
        log.info("FizzBuzz algo started");
        List<String> response = fizzBuzzService.process(
            request.getInt1(),
            request.getInt2(),
            request.getLimit(),
            request.getStr1(),
            request.getStr2()
        );
        log.info("FizzBuzz algo ended");
        return ResponseUtil.generateResponse(FIZZ_BUZZ_OUTPUT_SUCCESSFUL, response, HttpStatus.OK);
    }


}
