package com.getCurrencies.handleRequests;

import com.getCurrencies.handleRequests.Service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.testng.AssertJUnit.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class HandleRequestsApplicationTests {

	@InjectMocks
	private CurrencyService currencyService;

	@Test
	public void testGetCurrentCurrencyValue() {
		double result = currencyService.getCurrencyValue("EUR");
		System.out.println(result);
		assertNotNull(result);
	}

}
