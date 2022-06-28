package com.lightson.findpropapi.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lightson.findpropapi.entity.LocalAuthority;
import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;
import com.lightson.findpropapi.entity.LocalAuthorityUtilityPrice;
import com.lightson.findpropapi.entity.Postcode;
import com.lightson.findpropapi.entity.PostcodeAreaRentPrice;
import com.lightson.findpropapi.entity.PostcodeRentPrice;
import com.lightson.findpropapi.entity.Region;
import com.lightson.findpropapi.entity.RegionRentPrice;
import com.lightson.findpropapi.model.RentPriceResponse;
import com.lightson.findpropapi.model.UpfrontPriceLocalAuthorityDetails;
import com.lightson.findpropapi.model.UtilityPriceLocalAuthorityDetails;
import com.lightson.findpropapi.model.RentPriceLocalAuthorityDetails;
import com.lightson.findpropapi.model.RentPricePostcodeAreaDetails;
import com.lightson.findpropapi.model.RentPricePostcodeDetails;
import com.lightson.findpropapi.model.RentPricePropertyTypeEnum;
import com.lightson.findpropapi.model.RentPriceRegionDetails;
import com.lightson.findpropapi.repository.LocalAuthorityRentPriceRepository;
import com.lightson.findpropapi.repository.LocalAuthorityUtilityPriceRepository;
import com.lightson.findpropapi.repository.PostcodeAreaRentPriceRepository;
import com.lightson.findpropapi.repository.PostcodeRentPriceRepository;
import com.lightson.findpropapi.repository.PostcodeRepository;
import com.lightson.findpropapi.repository.RegionRentPriceRepository;

@Service
public class PriceService {

        @Autowired
        private PostcodeRepository postcodeRepository;

        @Autowired
        private RegionRentPriceRepository regionRentPriceRepository;

        @Autowired
        private LocalAuthorityRentPriceRepository localAuthorityRentPriceRepository;

        @Autowired
        private LocalAuthorityUtilityPriceRepository localAuthorityUtilityPriceRepository;

        @Autowired
        private PostcodeAreaRentPriceRepository postcodeAreaRentPriceRepository;

        @Autowired
        private PostcodeRentPriceRepository postcodeRentPriceRepository;

        private final Logger log = LoggerFactory.getLogger(PriceService.class);

        public RentPriceResponse getRentPrice(double longitude, double latitude, double maxRange, String propertyType,
                        int bedrooms) {

                // init response and take start of processing time
                Instant processingStart = Instant.now();
                RentPriceResponse response = new RentPriceResponse();

                // return back input params
                updateParams(response, longitude, latitude, maxRange, propertyType, bedrooms);

                // find nearest postcodes
                List<Postcode> postcodes = this.postcodeRepository.findByDistance(longitude, latitude, maxRange);
                if (postcodes == null || postcodes.size() == 0) {
                        log.error(String.format(
                                        "Cannot find nearest postcode for longitude %f, latitude %f, maxRange %f",
                                        longitude, latitude, maxRange));

                        response.setDuration(Duration.between(processingStart, Instant.now()).toMillis());
                        response.setStatus(RentPriceResponse.StatusEnum.FAILURE);
                        response.setCode(500);
                        return response;
                }

                // get nearest postcode
                Postcode postcode = postcodes.get(0);

                // get local authority
                LocalAuthority localAuthority = postcode.getLocalAuthority();
                if (localAuthority == null) {
                        log.error(String.format(
                                        "Cannot find local authority for postcode %s",
                                        postcode.getCode()));
                        response.setDuration(Duration.between(processingStart, Instant.now()).toMillis());
                        response.setStatus(RentPriceResponse.StatusEnum.FAILURE);
                        response.setCode(500);
                        return response;
                }

                // get region
                Region region = localAuthority.getRegion();
                if (region == null) {
                        log.error(String.format(
                                        "Cannot find region for local authority %s",
                                        localAuthority.getName()));
                        response.setDuration(Duration.between(processingStart, Instant.now()).toMillis());
                        response.setStatus(RentPriceResponse.StatusEnum.FAILURE);
                        response.setCode(500);
                        return response;
                }

                // set rent price of region
                setRegionDetails(response, region, propertyType, bedrooms);

                // set rent price of local authority
                setLocalAuthorityDetails(response, localAuthority, propertyType, bedrooms);

                // set rent price of postcode area
                setPostcodeAreaDetails(response, postcode, propertyType, bedrooms);

                // set rent price of postcode
                setPostcodeDetails(response, postcode, propertyType, bedrooms);

                // set rent price of near-by postcodes
                setRelatedByPostcodeDetails(response, postcodes, propertyType, bedrooms);

                // set rent prices of related local authorities
                setRelatedLocalAuthorityDetails(response, localAuthority, propertyType, bedrooms);

                // set rent prices of similar local authorities
                setSimilarLocalAuthorityDetails(response, localAuthority, propertyType, bedrooms);

                // set utility prices of local authority
                setLocalAuthorityUtilityDetails(response, localAuthority, propertyType, bedrooms);

                // set status and processing time
                response.setDuration(Duration.between(processingStart, Instant.now()).toMillis());
                response.setStatus(RentPriceResponse.StatusEnum.OK);
                response.setCode(200);

                return response;
        }

        private void updateParams(RentPriceResponse response, double longitude, double latitude, double maxRange,
                        String propertyType,
                        int bedrooms) {
                response.setTimestamp(new Date());
                response.setBedrooms(bedrooms);
                response.setLatitude(latitude);
                response.setLongitude(longitude);
                response.setMaxRange(maxRange);
                response.setPropertyType(RentPricePropertyTypeEnum.valueOf(propertyType));
        }

        private void setRegionDetails(RentPriceResponse response, Region region, String propertyType,
                        int bedrooms) {
                // get region rent prices
                List<RegionRentPrice> regionRentPrices = regionRentPriceRepository
                                .findByRegionAndPropertyTypeAndBedrooms(region, propertyType, bedrooms);

                // take top price
                RegionRentPrice price = (regionRentPrices != null && regionRentPrices.size() > 0)
                                ? regionRentPrices.get(0)
                                : null;

                response.setRegionDetails(
                                new RentPriceRegionDetails(region.getName(),
                                                price));
        }

        private void setLocalAuthorityDetails(RentPriceResponse response, LocalAuthority localAuthority,
                        String propertyType,
                        int bedrooms) {

                // get local authority rent prices
                List<LocalAuthorityRentPrice> localAuthorityRentPrices = localAuthorityRentPriceRepository
                                .findByLocalAuthorityAndPropertyTypeAndBedrooms(localAuthority, propertyType,
                                                bedrooms);

                // take top price
                LocalAuthorityRentPrice price = (localAuthorityRentPrices != null
                                && localAuthorityRentPrices.size() > 0) ? localAuthorityRentPrices.get(0) : null;

                response.setLocalAuthorityDetails(
                                new RentPriceLocalAuthorityDetails(localAuthority.getName(),
                                                price));

                // set upfront prices of postcode area (if available), or local authority (if
                // postcode area prices are not available)
                response.setUpfrontDetails(
                                new UpfrontPriceLocalAuthorityDetails(price));
        }

        private void setPostcodeAreaDetails(RentPriceResponse response, Postcode postcode, String propertyType,
                        int bedrooms) {
                // get postcode area
                String postcodeArea = postcode.getCode().split(" ")[0];

                // get postcode area rent prices
                List<PostcodeAreaRentPrice> postcodeAreaRentPrices = postcodeAreaRentPriceRepository
                                .findByPostcodeAreaAndPropertyTypeAndBedrooms(postcodeArea, propertyType, bedrooms);

                // take top price
                PostcodeAreaRentPrice price = (postcodeAreaRentPrices != null && postcodeAreaRentPrices.size() > 0)
                                ? postcodeAreaRentPrices.get(0)
                                : null;

                response.setPostcodeAreaDetails(
                                new RentPricePostcodeAreaDetails(postcodeArea, price));

                // set upfront prices of postcode area (if available), or local authority (if
                // postcode area prices are not available)
                response.setUpfrontDetails(
                                new UpfrontPriceLocalAuthorityDetails(price));
        }

        private void setPostcodeDetails(RentPriceResponse response, Postcode postcode, String propertyType,
                        int bedrooms) {
                // get postcode rent prices
                List<PostcodeRentPrice> postcodeRentPrices = postcodeRentPriceRepository
                                .findByPostcodeAndPropertyTypeAndBedrooms(postcode, propertyType, bedrooms);
                // take top price
                PostcodeRentPrice price = (postcodeRentPrices != null && postcodeRentPrices.size() > 0)
                                ? postcodeRentPrices.get(0)
                                : null;

                response.setPostcodeDetails(
                                new RentPricePostcodeDetails(postcode.getCode(), postcode.getLongitude(),
                                                postcode.getLatitude(), price));
        }

        private void setRelatedByPostcodeDetails(RentPriceResponse response, List<Postcode> postcodes,
                        String propertyType,
                        int bedrooms) {

                // check if we have more postcodes near-by than the anchor
                if (postcodes.size() > 1) {
                        for (int i = 1; i < postcodes.size(); i++) {
                                // get next nearest postcode - this will become our near-by postcode
                                Postcode nearbyPostcode = postcodes.get(i);

                                // get rent prices of nearby postcode
                                List<PostcodeRentPrice> nearbyPostcodeRentPrices = postcodeRentPriceRepository
                                                .findByPostcodeAndPropertyTypeAndBedrooms(nearbyPostcode, propertyType,
                                                                bedrooms);

                                // take top price
                                if (nearbyPostcodeRentPrices != null && nearbyPostcodeRentPrices.size() > 0) {
                                        response.addRelatedPostcodeDetails(nearbyPostcode.getCode(),
                                                        nearbyPostcode.getLongitude(),
                                                        nearbyPostcode.getLatitude(), nearbyPostcodeRentPrices.get(0));
                                }
                        }
                }
        }

        private void setRelatedLocalAuthorityDetails(RentPriceResponse response, LocalAuthority localAuthority,
                        String propertyType,
                        int bedrooms) {

                // get related local authorities
                Set<LocalAuthority> relatedLocalAuthorities = localAuthority.getRelatedAuthorities();
                if (relatedLocalAuthorities != null) {
                        for (LocalAuthority relatedLocalAuthority : relatedLocalAuthorities) {
                                // get rent prices of related local authority
                                List<LocalAuthorityRentPrice> relatedLocalAuthorityRentPrices = localAuthorityRentPriceRepository
                                                .findByLocalAuthorityAndPropertyTypeAndBedrooms(
                                                                relatedLocalAuthority,
                                                                propertyType, bedrooms);
                                // take top price
                                if (relatedLocalAuthorityRentPrices != null
                                                && relatedLocalAuthorityRentPrices.size() > 0) {
                                        response.addRelatedLocalAuthorityDetails(
                                                        relatedLocalAuthority.getName(),
                                                        relatedLocalAuthorityRentPrices.get(0));
                                }
                        }
                }

        }

        private void setSimilarLocalAuthorityDetails(RentPriceResponse response, LocalAuthority localAuthority,
                        String propertyType,
                        int bedrooms) {

                // get rent prices of similar local authority
                List<LocalAuthorityRentPrice> similarLocalAuthorityRentPrices = localAuthorityRentPriceRepository
                                .findSimilarLocalAuthorityRentPrices(localAuthority.getId(),
                                                propertyType, bedrooms);

                // add to the result
                for (LocalAuthorityRentPrice similarLocalAuthorityRentPrice : similarLocalAuthorityRentPrices) {
                        response.addSimilarLocalAuthorityDetails(
                                        similarLocalAuthorityRentPrice.getLocalAuthority().getName(),
                                        similarLocalAuthorityRentPrice);
                }
        }

        private void setLocalAuthorityUtilityDetails(RentPriceResponse response, LocalAuthority localAuthority,
                        String propertyType,
                        int bedrooms) {

                // get utility prices
                List<LocalAuthorityUtilityPrice> localAuthorityUtilityPrices = localAuthorityUtilityPriceRepository
                                .findByLocalAuthorityAndPropertyTypeAndBedrooms(localAuthority, propertyType,
                                                bedrooms);

                // take top price
                if (localAuthorityUtilityPrices != null && localAuthorityUtilityPrices.size() > 0) {
                        response.setUtilityDetails(
                                        new UtilityPriceLocalAuthorityDetails(localAuthority.getName(),
                                                        localAuthorityUtilityPrices));
                }
        }
}
