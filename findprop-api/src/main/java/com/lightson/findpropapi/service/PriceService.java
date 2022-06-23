package com.lightson.findpropapi.service;

import java.util.Date;
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

        private final Logger log = LoggerFactory.getLogger(PriceService.class);

        public RentPriceResponse getRentPrice(double longitude, double latitude, double maxRange, String propertyType,
                        int bedrooms) {

                RentPriceResponse response = new RentPriceResponse();
                response.setTimestamp(new Date());

                // return back input params
                response.setBedrooms(bedrooms);
                response.setLatitude(latitude);
                response.setLongitude(longitude);
                response.setMaxRange(maxRange);
                response.setPropertyType(RentPricePropertyTypeEnum.valueOf(propertyType));

                // find nearest postcode
                Postcode postcode = this.postcodeRepository.findByDistance(longitude, latitude, maxRange);
                if (postcode == null) {
                        log.error(String.format(
                                        "Cannot find nearest postcode for longitude %f, latitude %f, maxRange %f",
                                        longitude, latitude, maxRange));

                        response.setStatus(RentPriceResponse.StatusEnum.FAILURE);
                        response.setCode(500);
                        return response;
                }
                // set geos
                response.setPostcodeDetails(new RentPricePostcodeDetails(postcode.getCode(), postcode.getLongitude(),
                                postcode.getLatitude()));

                String postcodeArea = postcode.getCode().split(" ")[0];
                LocalAuthority localAuthority = postcode.getLocalAuthority();
                Region region = localAuthority.getRegion();

                // set rent price of region
                RegionRentPrice regionRentPrice = regionRentPriceRepository
                                .findByRegionAndPropertyTypeAndBedrooms(region, propertyType, bedrooms);
                response.setRegionDetails(new RentPriceRegionDetails(region.getName(), regionRentPrice));

                // set rent price of local authority
                LocalAuthorityRentPrice localAuthorityRentPrice = localAuthorityRentPriceRepository
                                .findByLocalAuthorityAndPropertyTypeAndBedrooms(localAuthority, propertyType, bedrooms);
                response.setLocalAuthorityDetails(
                                new RentPriceLocalAuthorityDetails(localAuthority.getName(), localAuthorityRentPrice));

                // set rent price of postcode area
                PostcodeAreaRentPrice postcodeAreaRentPrice = postcodeAreaRentPriceRepository
                                .findByPostcodeAreaAndPropertyTypeAndBedrooms(postcodeArea, propertyType, bedrooms);
                response.setPostcodeAreaDetails(new RentPricePostcodeAreaDetails(postcodeArea, postcodeAreaRentPrice));

                // set rent prices of related local authorities
                for (LocalAuthority relatedLocalAuthority : localAuthority.getRelatedAuthorities()) {
                        LocalAuthorityRentPrice relatedLocalAuthorityRentPrice = localAuthorityRentPriceRepository
                                        .findByLocalAuthorityAndPropertyTypeAndBedrooms(relatedLocalAuthority,
                                                        propertyType, bedrooms);

                        response.addRelatedLocalAuthorityDetails(relatedLocalAuthority.getName(),
                                        relatedLocalAuthorityRentPrice);
                }

                // set rent prices of similar local authorities
                Set<LocalAuthorityRentPrice> similarLocalAuthorityRentPrices = localAuthorityRentPriceRepository
                                .findSimilarLocalAuthorityRentPrices(localAuthority.getId(),
                                                propertyType, bedrooms);
                for (LocalAuthorityRentPrice similarLocalAuthorityRentPrice : similarLocalAuthorityRentPrices) {
                        response.addSimilarLocalAuthorityDetails(
                                        similarLocalAuthorityRentPrice.getLocalAuthority().getName(),
                                        similarLocalAuthorityRentPrice);
                }

                // set utility prices of local authority
                Set<LocalAuthorityUtilityPrice> localAuthorityUtilityPrices = localAuthorityUtilityPriceRepository
                                .findByLocalAuthorityAndPropertyTypeAndBedrooms(localAuthority, propertyType, bedrooms);
                response.setUtilityDetails(
                                new UtilityPriceLocalAuthorityDetails(localAuthority.getName(),
                                                localAuthorityUtilityPrices));

                // set upfront prices of postcode area (if available), or local authority (if
                // postcode area prices are not available)
                if (postcodeAreaRentPrice != null) {
                        response.setUpfrontDetails(new UpfrontPriceLocalAuthorityDetails(postcodeAreaRentPrice));
                } else if (localAuthorityRentPrice != null) {
                        response.setUpfrontDetails(new UpfrontPriceLocalAuthorityDetails(localAuthorityRentPrice));
                }

                // set status
                response.setStatus(RentPriceResponse.StatusEnum.OK);
                response.setCode(200);

                return response;
        }
}
