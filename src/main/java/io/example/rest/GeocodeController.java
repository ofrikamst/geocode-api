package io.example.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;


@RestController
public class GeocodeController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AddressRepository addressRepository;


    @RequestMapping(path="/geocode", method=RequestMethod.POST, consumes={"application/json"})
    public String getGeocode(@RequestBody Address address) throws IOException {
        List<AddressItem> similarAddresses = addressRepository.findAllByAddress(address.getState(), address.getCity(),
                address.getStreet(), address.getNumber());
        if (similarAddresses.size() > 0) {
            return similarAddresses.get(0).getPostal();
        }
        String fullAddress = address.getFullAddress();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("x-rapidapi-host", "google-maps-geocoding.p.rapidapi.com");
        headers.add("x-rapidapi-key", "4b0990766bmshee07ea2b79c5389p18e3abjsn79ca2e3354b2");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en&address="+fullAddress,
                HttpMethod.GET, entity, String.class);
        AddressItem addressItem = new AddressItem(null, address.getState(),
                address.getCity(), address.getStreet(), address.getNumber(), getPostalCode(response));
        addressRepository.save(addressItem);
        return addressItem.getPostal();
    }

    private String getPostalCode(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return jsonNode.get("results").get(0).get("address_components").get(7).get("long_name").asText();
    }


}
