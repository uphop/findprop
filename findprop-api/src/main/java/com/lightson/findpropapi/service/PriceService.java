package com.lightson.findpropapi.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lightson.findpropapi.api.FindPropApiException;
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
import com.lightson.findpropapi.model.UpfrontPricePostcodeAreaDetails;
import com.lightson.findpropapi.model.UpfrontPricePostcodeDetails;
import com.lightson.findpropapi.model.UpfrontPriceRelatedPostcodeDetails;
import com.lightson.findpropapi.model.UtilityPriceLocalAuthorityDetails;
import com.lightson.findpropapi.model.RentPriceLocalAuthorityDetails;
import com.lightson.findpropapi.model.RentPricePostcodeAreaDetails;
import com.lightson.findpropapi.model.RentPricePostcodeDetails;
import com.lightson.findpropapi.model.RentPriceRegionDetails;
import com.lightson.findpropapi.repository.LocalAuthorityRentPriceRepository;
import com.lightson.findpropapi.repository.LocalAuthorityUtilityPriceRepository;
import com.lightson.findpropapi.repository.PostcodeAreaRentPriceRepository;
import com.lightson.findpropapi.repository.PostcodeRentPriceRepository;
import com.lightson.findpropapi.repository.PostcodeRepository;
import com.lightson.findpropapi.repository.RegionRentPriceRepository;

@Service
public class PriceService {

        private final int DEFAULT_FIND_POSTCODE_MAX_ATTEMPTS = 3;
        private final double DEFAULT_FIND_POSTCODE_RANGE_INCREASE_FACTOR = 2.0;

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

        public RentPriceResponse getRentPrice(RentPriceResponse response, double longitude, double latitude, double maxRange, String propertyType,
                        int bedrooms) throws FindPropApiException {

                // find nearest postcodes
                List<Postcode> postcodes = getNearestPostcodes(longitude, latitude, maxRange);
                if (postcodes == null || postcodes.size() == 0) {
                        throw new FindPropApiException(String.format(
                                "Cannot find nearest postcode for longitude %f, latitude %f, maxRange %f",
                                longitude, latitude, maxRange));
                }

                // get nearest postcode
                Postcode postcode = postcodes.get(0);

                // get local authority
                LocalAuthority localAuthority = postcode.getLocalAuthority();
                if (localAuthority == null) {
                        throw new FindPropApiException(String.format(
                                        "Cannot find local authority for postcode %s",
                                        postcode.getCode()));
                }

                // get region
                Region region = localAuthority.getRegion();
                if (region == null) {
                        throw new FindPropApiException(String.format(
                                        "Cannot find region for local authority %s",
                                        localAuthority.getName()));
                }

                // set rent price of region
                setRegionDetails(response, region, propertyType, bedrooms);

                // set rent price of local authority
                LocalAuthorityRentPrice localAuthorityRentPrice = setLocalAuthorityDetails(response, localAuthority,
                                propertyType, bedrooms);

                // set rent prices of related local authorities
                setRelatedLocalAuthorityDetails(response, localAuthority, propertyType, bedrooms);

                // set rent prices of similar local authorities
                setSimilarLocalAuthorityDetails(response, localAuthority, propertyType, bedrooms);

                // set utility prices of local authority
                setLocalAuthorityUtilityDetails(response, localAuthority, propertyType, bedrooms);

                // set rent price of postcode area
                PostcodeAreaRentPrice postcodeAreaRentPrice = setPostcodeAreaDetails(response, postcode, propertyType,
                                bedrooms);

                // set rent price of postcode
                PostcodeRentPrice postcodeRentPrice = setPostcodeDetails(response, postcode, propertyType, bedrooms);

                // set rent price of near-by postcodes
                setRelatedPostcodeDetails(response, postcodes, propertyType, bedrooms);

                // set rent upfront costs
                setUpfrontCostDetails(response, localAuthorityRentPrice, postcodeAreaRentPrice, postcodeRentPrice);

                return response;
        }

        private void setRegionDetails(RentPriceResponse response, Region region, String propertyType,
                        int bedrooms) {
                // get region rent prices
                List<RegionRentPrice> regionRentPrices = regionRentPriceRepository
                                .findRegionRentPrices(region.getId(), propertyType, bedrooms);

                // take top price
                RegionRentPrice price = (regionRentPrices != null && regionRentPrices.size() > 0)
                                ? regionRentPrices.get(0)
                                : null;

                response.setRegionDetails(
                                new RentPriceRegionDetails(region.getName(),
                                                price));
        }

        private LocalAuthorityRentPrice setLocalAuthorityDetails(RentPriceResponse response,
                        LocalAuthority localAuthority,
                        String propertyType,
                        int bedrooms) {

                // get local authority rent prices
                List<LocalAuthorityRentPrice> localAuthorityRentPrices = localAuthorityRentPriceRepository
                                .findLocalAuthorityRentPrices(localAuthority.getId(), propertyType,
                                                bedrooms);

                // take top price
                LocalAuthorityRentPrice price = (localAuthorityRentPrices != null
                                && localAuthorityRentPrices.size() > 0) ? localAuthorityRentPrices.get(0) : null;

                response.setLocalAuthorityDetails(
                                new RentPriceLocalAuthorityDetails(localAuthority.getName(),
                                                price));

                return price;
        }

        private PostcodeAreaRentPrice setPostcodeAreaDetails(RentPriceResponse response, Postcode postcode,
                        String propertyType,
                        int bedrooms) {
                // get postcode area
                String postcodeArea = postcode.getCode().split(" ")[0];

                // get postcode area rent prices
                List<PostcodeAreaRentPrice> postcodeAreaRentPrices = postcodeAreaRentPriceRepository
                                .findPostcodeAreaRentPrices(postcodeArea, propertyType, bedrooms);

                // take top price
                PostcodeAreaRentPrice price = (postcodeAreaRentPrices != null && postcodeAreaRentPrices.size() > 0)
                                ? postcodeAreaRentPrices.get(0)
                                : null;

                response.setPostcodeAreaDetails(
                                new RentPricePostcodeAreaDetails(postcodeArea, price));

                return price;
        }

        private PostcodeRentPrice setPostcodeDetails(RentPriceResponse response, Postcode postcode, String propertyType,
                        int bedrooms) {
                // get postcode rent prices
                List<PostcodeRentPrice> postcodeRentPrices = postcodeRentPriceRepository
                                .findPostcodeRentPrices(postcode.getId(), propertyType, bedrooms);
                // take top price
                PostcodeRentPrice price = (postcodeRentPrices != null && postcodeRentPrices.size() > 0)
                                ? postcodeRentPrices.get(0)
                                : null;

                response.setPostcodeDetails(
                                new RentPricePostcodeDetails(postcode.getCode(), postcode.getLongitude(),
                                                postcode.getLatitude(), price));

                return price;
        }

        private void setRelatedPostcodeDetails(RentPriceResponse response, List<Postcode> postcodes,
                        String propertyType,
                        int bedrooms) {

                // check if we have more postcodes near-by than the anchor
                if (postcodes.size() > 1) {
                        for (int i = 1; i < postcodes.size(); i++) {
                                // get next nearest postcode - this will become our near-by postcode
                                Postcode nearbyPostcode = postcodes.get(i);

                                // get rent prices of nearby postcode
                                List<PostcodeRentPrice> nearbyPostcodeRentPrices = postcodeRentPriceRepository
                                                .findPostcodeRentPrices(nearbyPostcode.getId(), propertyType,
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
                                                .findLocalAuthorityRentPrices(
                                                                relatedLocalAuthority.getId(),
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
                                .findLocalAuthorityUtilityPrices(localAuthority.getId(), propertyType,
                                                bedrooms);

                // take top price
                if (localAuthorityUtilityPrices != null && localAuthorityUtilityPrices.size() > 0) {
                        response.setUtilityDetails(
                                        new UtilityPriceLocalAuthorityDetails(localAuthority.getName(),
                                                        localAuthorityUtilityPrices));
                }
        }

        private void setUpfrontCostDetails(RentPriceResponse response, LocalAuthorityRentPrice localAuthorityRentPrice,
                        PostcodeAreaRentPrice postcodeAreaRentPrice, PostcodeRentPrice postcodeRentPrice) {
                // set upfront costs based on the most granular rent price available
                if (postcodeRentPrice != null) {
                        response.setUpfrontDetails(
                                        new UpfrontPricePostcodeDetails(postcodeRentPrice));
                } else if (response.getRelatedPostcodeDetails() != null
                                && response.getRelatedPostcodeDetails().size() > 0) {
                        response.setUpfrontDetails(
                                        new UpfrontPriceRelatedPostcodeDetails(response.getRelatedPostcodeDetails()));
                } else if (postcodeAreaRentPrice != null) {
                        response.setUpfrontDetails(
                                        new UpfrontPricePostcodeAreaDetails(postcodeAreaRentPrice));
                } else if (localAuthorityRentPrice != null) {
                        response.setUpfrontDetails(
                                        new UpfrontPriceLocalAuthorityDetails(localAuthorityRentPrice));
                }
        }

        private List<Postcode> getNearestPostcodes(double longitude, double latitude, double startingMaxRange) {
                // make up to DEFAULT_FIND_POSTCODE_MAX_ATTEMPTS attempts to find nearest
                // postcodes, gradually increasing range
                for (int attempt = 0; attempt < DEFAULT_FIND_POSTCODE_MAX_ATTEMPTS; attempt++) {
                        // calculate max range for the next attempt
                        double currentMaxRange = startingMaxRange
                                        * Math.pow(DEFAULT_FIND_POSTCODE_RANGE_INCREASE_FACTOR, attempt);

                        // try to find postcodes with the current range
                        List<Postcode> result = this.postcodeRepository.findByDistance(longitude, latitude,
                                        currentMaxRange);

                        // if found some postcodes, let's stop here
                        if (result != null && result.size() > 0)
                                return result;
                }

                // nothing is found
                return null;
        }
}
