package com.kk.microservices.currencyconversionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kk.microservices.currencyconversionservice.model.CurrencyConversion;

@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {

	
	@GetMapping("/currencyExchange/from/{from}/to/{to}")
	public CurrencyConversion getCurrenctExchange(
			@PathVariable ("from") String from,
			@PathVariable ("to") String to
			);
	
}
