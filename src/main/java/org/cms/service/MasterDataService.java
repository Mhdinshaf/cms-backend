package org.cms.service;

import org.cms.entity.City;
import org.cms.entity.Country;
import java.util.List;

public interface MasterDataService {
    List<Country> getAllCountries();
    List<City> getCitiesByCountryName(String countryName);
}