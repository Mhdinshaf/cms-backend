package org.cms.controller;

import lombok.RequiredArgsConstructor;
import org.cms.entity.City;
import org.cms.entity.Country;
import org.cms.service.MasterDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/master") // පොදු path එක
@RequiredArgsConstructor
@CrossOrigin
public class MasterDataController {

    private final MasterDataService masterDataService;

    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return masterDataService.getAllCountries();
    }


    @GetMapping("/cities/{countryName}")
    public List<City> getCitiesByCountryName(@PathVariable String countryName) {
        return masterDataService.getCitiesByCountryName(countryName);
    }
}