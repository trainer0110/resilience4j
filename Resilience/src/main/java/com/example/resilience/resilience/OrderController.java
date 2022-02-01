package com.example.resilience.resilience;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class OrderController {

	@Autowired
	private RestTemplate template;

	@GetMapping("/order")
	@Retry(name = "inventory", fallbackMethod = "inventoryDefaultResponse")
	public String getItemFromInventory() {
		// 1. Call inventory service to get data in order service
		System.out.println("Calling inventory service...........");
		String inventoryItems = template.getForObject("http://localhost:9991/inventory", String.class);
		return inventoryItems;
	}

	public String inventoryDefaultResponse(Exception e) {
		return "Out of stock...........";
	}

}
