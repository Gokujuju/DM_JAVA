package com.julien.dmJava;


import org.springframework.web.client.RestTemplate;


public class API_Exchange {

    public static Currency getAPI() {
        RestTemplate restTemplate = new RestTemplate();
        String apiURL = "https://api.exchangeratesapi.io/latest";
        return restTemplate.getForObject(apiURL, Currency.class);
    }
}

