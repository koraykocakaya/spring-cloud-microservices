package com.kk.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kk.microservices.currencyconversionservice.model.CurrencyConversion;
import com.kk.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	CurrencyExchangeProxy currencyExchangeProxy;

	@GetMapping ("/currencyConversionWOProxy/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getCurrencyConversionWithoutProxy(
			@PathVariable("from") String from,
			@PathVariable("to") String to,
			@PathVariable("quantity") BigDecimal quantity
			) {
		
		Map<String, String> varMap = new HashMap<>();
		varMap.put("from", from);
		varMap.put("to", to);
		
		ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity("http://localhost:8000/currencyExchange/from/{from}/to/{to}", CurrencyConversion.class, varMap);
		CurrencyConversion currencyConversion =  response.getBody();
		
		currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(quantity));
		currencyConversion.setQuantity(quantity);
		return currencyConversion;
	}
	
	@GetMapping ("/currencyConversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getCurrencyConversionFeign(
			@PathVariable("from") String from,
			@PathVariable("to") String to,
			@PathVariable("quantity") BigDecimal quantity
			) {
		
		CurrencyConversion currencyConversion =  currencyExchangeProxy.getCurrenctExchange(from, to);
		
		currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(quantity));
		currencyConversion.setQuantity(quantity);
		currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " feign");
		return currencyConversion;
	}
}
