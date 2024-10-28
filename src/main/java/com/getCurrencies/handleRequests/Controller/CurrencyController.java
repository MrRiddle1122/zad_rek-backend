package com.getCurrencies.handleRequests.Controller;

import com.getCurrencies.handleRequests.Request.CurrencyRequest;
import com.getCurrencies.handleRequests.Response.CurrencyResponse;
import com.getCurrencies.handleRequests.Service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/currencies")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/get-current-currency-value-command")
    public ResponseEntity<?> getCurrentCurrencyValue(@RequestBody CurrencyRequest request) {
        logger.info("Received request for currency: {}, name: {}", request.getCurrency(), request.getName());

        String currencyCode = request.getCurrency();
        String name = request.getName();

        if (currencyCode == null || currencyCode.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid currency code.");
        }
        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid name.");
        }

        try {
            double value = currencyService.getCurrencyValue(currencyCode);

            currencyService.saveCurrencyRequest(currencyCode, name, value);

            return ResponseEntity.ok(new CurrencyResponse(value));
        } catch (Exception e) {
            logger.error("Error occurred while fetching currency value", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid currency code");
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<List<CurrencyRequest>> getAllRequests() {
        return ResponseEntity.ok(currencyService.getAllRequests());
    }
}
