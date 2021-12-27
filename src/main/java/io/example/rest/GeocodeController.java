package io.example.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@RestController
public class GeocodeController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path="/geocode", method=RequestMethod.GET)
    public String getGeocode(@RequestBody Address address) throws IOException {
        String fullAdress =address.getNumber() + address.getStreet();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("x-rapidapi-host", "google-maps-geocoding.p.rapidapi.com");
        headers.add("x-rapidapi-key", "4b0990766bmshee07ea2b79c5389p18e3abjsn79ca2e3354b2");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        return restTemplate.exchange("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en&address="+fullAdress,
                HttpMethod.GET, entity, String.class).getBody();


        //JsonNode

    }


}
