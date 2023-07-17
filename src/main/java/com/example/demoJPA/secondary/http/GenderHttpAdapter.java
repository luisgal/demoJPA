package com.example.demoJPA.secondary.http;

import com.example.demoJPA.core.domain.AliasesDom;
import com.example.demoJPA.core.enums.Gender;
import com.example.demoJPA.secondary.GenderPort;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Component
public class GenderHttpAdapter implements GenderPort {

    private RestTemplate restTemplate;

    record GenderResponse(String name, String gender, double probability, long count){}

    public GenderHttpAdapter(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<Gender> findGender(AliasesDom aliasesDom) {
        Map<String, String> parameters = Map.of("name", aliasesDom.getFirstname());
        GenderResponse genderResponse = restTemplate.getForObject("https://api.genderize.io?name={name}", GenderResponse.class, parameters);
        if(EnumUtils.isValidEnumIgnoreCase(Gender.class, genderResponse.gender)){
            Gender gender = EnumUtils.getEnumIgnoreCase(Gender.class, genderResponse.gender);
            return Optional.of(gender);
        }
        return Optional.empty();
    }
}
