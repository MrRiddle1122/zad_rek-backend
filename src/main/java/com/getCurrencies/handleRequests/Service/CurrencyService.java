package com.getCurrencies.handleRequests.Service;

import com.getCurrencies.handleRequests.Request.CurrencyRequest;
import com.getCurrencies.handleRequests.Request.CurrencyRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {

    @Autowired
    private final CurrencyRequestRepository currencyRequestRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public void saveCurrencyRequest(String currencyCode, String name, double value) {
        CurrencyRequest currencyRequest = new CurrencyRequest();
        currencyRequest.setCurrency(currencyCode);
        currencyRequest.setName(name);
        currencyRequest.setValue(value);
        currencyRequest.setDate(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        currencyRequestRepository.save(currencyRequest);
    }


    @Autowired
    public CurrencyService(CurrencyRequestRepository currencyRequestRepository) {
        this.currencyRequestRepository = currencyRequestRepository;
    }

    public double getCurrencyValue(String currencyCode) {
        String url = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";
        Map<String, Object>[] response = restTemplate.getForObject(url, Map[].class);
        for (Map<String, Object> table : response) {
            var rates = (List<Map<String, Object>>) table.get("rates");
            for (Map<String, Object> rate : rates) {
                if (rate.get("code").equals(currencyCode)) {
                    return (double) rate.get("mid");
                }
            }
        }
        throw new IllegalArgumentException("Invalid currency code: " + currencyCode);
    }

    public List<CurrencyRequest> getAllRequests() {
        return currencyRequestRepository.findAll();
    }
}
