package org.cms.service.impl;

import lombok.RequiredArgsConstructor;
import org.cms.entity.City;
import org.cms.entity.Country;
import org.cms.repository.CityRepository;
import org.cms.repository.CountryRepository;
import org.cms.service.MasterDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterServiceDataImpl implements MasterDataService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public List<City> getCitiesByCountryName(String countryName) {
        return cityRepository.findByCountryName(countryName);
    }
}
