package com.kk.microservices.currencyexchangeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kk.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.kk.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;
	
	@GetMapping("/currencyExchange/from/{from}/to/{to}")
	public CurrencyExchange getCurrenctExchange(
			@PathVariable ("from") String from,
			@PathVariable ("to") String to
			) {
		
		String env = environment.getProperty("local.server.port");
		CurrencyExchange ce = currencyExchangeRepository.findByFromAndTo(from, to);
		ce.setEnvironment(env);
		return ce;
	}
}
