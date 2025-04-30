package com.techelevator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class OSMService {

    private static final String OSM_API_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";

    public Map<String, Double> getCoordinates(String address) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String requestUrl = OSM_API_URL + address.replace(" ", "+");


            OSMResponse[] response = restTemplate.getForObject(requestUrl, OSMResponse[].class);
            if (response != null && response.length > 0) {
                Map<String, Double> coordinates = new HashMap<>();
                coordinates.put("latitude", Double.parseDouble(response[0].getLat()));
                coordinates.put("longitude", Double.parseDouble(response[0].getLon()));
                return coordinates;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class OSMResponse {
        private String lat;
        private String lon;

        public String getLat() { return lat; }
        public String getLon() { return lon; }
    }
}

