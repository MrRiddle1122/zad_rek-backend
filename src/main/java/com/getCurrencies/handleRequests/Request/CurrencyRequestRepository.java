package com.getCurrencies.handleRequests.Request;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRequestRepository extends JpaRepository<CurrencyRequest, Long> {
}
