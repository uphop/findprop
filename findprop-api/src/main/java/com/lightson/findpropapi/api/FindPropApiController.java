package com.lightson.findpropapi.api;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lightson.findpropapi.model.RentPricePropertyTypeEnum;
import com.lightson.findpropapi.model.RentPriceResponse;
import com.lightson.findpropapi.service.PriceService;

@RestController
public class FindPropApiController {
        private PriceService priceService;
        
        @Value("${api.key}")
        private String expectedApiKey;

        private final Logger log = LoggerFactory.getLogger(FindPropApiController.class);

        public FindPropApiController(PriceService priceService) {
                this.priceService = priceService;
        }

        @GetMapping("findprop/api/v1/rent/price")
        public RentPriceResponse getRentPrices(
                        @RequestParam(value = "longitude") double longitude,
                        @RequestParam(value = "latitude") double latitude,
                        @RequestParam(value = "maxRange") double maxRange,
                        @RequestParam(value = "propertyType", defaultValue = "flat") String propertyType,
                        @RequestParam(value = "bedrooms", defaultValue = "1") int bedrooms,
                        @RequestParam(value = "apiKey", defaultValue = "") String apiKey) {

                // init response and take start of processing time
                RentPriceResponse response = new RentPriceResponse();
                Instant processingStart = Instant.now();

                try {
                        // validate API key
                        if (apiKey == null || apiKey.length() == 0 || apiKey.compareTo(expectedApiKey) != 0) {
                                throw new FindPropApiException("Invalid key - " + ((apiKey == null || apiKey.length() == 0) ? "<empty>" : apiKey));
                        }

                        // get prices
                        response = this.priceService.getRentPrice(response, longitude, latitude, maxRange,
                                        propertyType, bedrooms);

                        // set status
                        response.setStatus(RentPriceResponse.StatusEnum.OK);
                        response.setCode(200);

                } catch (FindPropApiException priceEx) {
                        // set status ane message
                        response.setStatus(RentPriceResponse.StatusEnum.FAILURE);
                        response.setCode(500);
                        response.setMessage(priceEx.getMessage());

                        log.error(priceEx.getMessage());
                } 
                finally {
                        // set processing time
                        response.setDuration(Duration.between(processingStart, Instant.now()).toMillis());

                        // return back input params
                        response.setTimestamp(new Date());
                        response.setBedrooms(bedrooms);
                        response.setLatitude(latitude);
                        response.setLongitude(longitude);
                        response.setMaxRange(maxRange);
                        response.setPropertyType(RentPricePropertyTypeEnum.valueOf(propertyType));
                }

                return response;
        }
}
