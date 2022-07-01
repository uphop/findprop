package com.lightson.findpropapi.crawler.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PropertyDataAdapter {
    private final Logger log = LoggerFactory.getLogger(PropertyDataAdapter.class);

    @Autowired
    private PropertyDataProperties config;

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        // configure HTTP client
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory
                .setConnectTimeout(config.getConnect_timeout());
        return clientHttpRequestFactory;
    }

    public PropertyDataRentResponse getRentPrices(String postcodeArea, String propertyType, Integer bedrooms) {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

        // prepare full URL
        String resourceUrl = config.getBase_url() + "/rents";

        // prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // prepare query params
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceUrl)
                .queryParam("key", "{key}")
                .queryParam("postcode", "{postcode}")
                .queryParam("bedrooms", "{bedrooms}")
                .queryParam("type", "{type}")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put("key", config.getKey());
        params.put("postcode", postcodeArea);
        params.put("bedrooms", String.valueOf(bedrooms));
        params.put("type", propertyType);

        try {
            // make a delay not to hir rate limits
            TimeUnit.MILLISECONDS.sleep(config.getWith_delay());

            // call Property Data API
            ResponseEntity<String> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class,
                    params);

            // check returned status code first
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Failed to get rent prices for postcode area: " + postcodeArea + ", propertyType: "
                        + propertyType + ", bedrooms: " + String.valueOf(bedrooms)
                        + "status code: " + response.getStatusCode() + ", response: "
                        + response.getBody());
                return null;
            }
            log.info("Got rent prices for postcode area: " + postcodeArea + ", propertyType: " + propertyType
                    + ", bedrooms: " + String.valueOf(bedrooms));

            // map response to DTO
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode longLetNode = root.path("data").get("long_let");
            PropertyDataRentResponse rentResponse = mapper.treeToValue(longLetNode, PropertyDataRentResponse.class);
            return rentResponse;
        } catch (HttpClientErrorException e) {
            log.error("Failed to get rent prices for postcode area: " + postcodeArea + ", propertyType: "
                    + propertyType + ", bedrooms: " + String.valueOf(bedrooms)
                    + ", error: " + e.toString());
            return null;
        } catch (JsonMappingException e) {
            log.error("JSON mapping Property Data API response failed: " + postcodeArea + ", propertyType: "
                    + propertyType + ", bedrooms: " + String.valueOf(bedrooms)
                    + ", error: " + e.toString());
            return null;
        } catch (JsonProcessingException e) {
            log.error("JSON processing Property Data API response failed: " + postcodeArea + ", propertyType: "
                    + propertyType + ", bedrooms: " + String.valueOf(bedrooms)
                    + ", error: " + e.toString());
            return null;
        } catch (InterruptedException e) {
            log.error("Call Property Data API response failed: " + postcodeArea + ", propertyType: "
                    + propertyType + ", bedrooms: " + String.valueOf(bedrooms)
                    + ", error: " + e.toString());
            return null;
        } catch (ResourceAccessException e) {
            log.error("Call Property Data API response failed: " + postcodeArea + ", propertyType: "
                    + propertyType + ", bedrooms: " + String.valueOf(bedrooms)
                    + ", error: " + e.toString());
            return null;
        }

    }
}
