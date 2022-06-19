package com.lightson.findpropapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lightson.findpropapi.model.RentPriceResponse;
import com.lightson.findpropapi.service.PriceService;

@RestController
public class PropertyStatsController {
        private static final Logger log = LoggerFactory.getLogger(PropertyStatsController.class);
        private PriceService priceService;

        public PropertyStatsController(PriceService priceService) {
                this.priceService = priceService;
        }

        @GetMapping("findprop/api/v1/rent/price")
        public RentPriceResponse getRentPrices(
                        @RequestParam(value = "longitude") double longitude,
                        @RequestParam(value = "latitude") double latitude,
                        @RequestParam(value = "maxRange") double maxRange,
                        @RequestParam(value = "propertyType", defaultValue = "flat") String propertyType,
                        @RequestParam(value = "bedrooms", defaultValue = "1") int bedrooms) {

                // TODO: validate input params

                RentPriceResponse response = this.priceService.getRentPrice(longitude, latitude, maxRange,
                                propertyType, bedrooms);

                return response;
        }
}
