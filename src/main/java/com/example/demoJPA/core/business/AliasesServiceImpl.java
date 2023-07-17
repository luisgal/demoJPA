package com.example.demoJPA.core.business;

import com.example.demoJPA.core.domain.AliasesDom;
import com.example.demoJPA.core.enums.Gender;
import com.example.demoJPA.core.exception.ResourceNotFound;
import com.example.demoJPA.secondary.AliasesPort;
import com.example.demoJPA.secondary.GenderPort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AliasesServiceImpl implements AliasesService {

    private final AliasesPort aliasesPort;
    private final GenderPort genderPort;

    @Override
    public String crearAliases(AliasesDom aliasesdom) {
        Gender gender = genderPort.findGender(aliasesdom).orElseGet(() -> Gender.UNKNOWN);
        aliasesdom.setGender(gender);
        return aliasesPort.safeAliases(aliasesdom);
    }

    public AliasesDom findAliases(String id) throws ResourceNotFound {
        //AliasesDom aliasesDom = aliasesPort.findAliases(id);
        //if(StringUtils.isBlank(aliasesDom.getId())){
        //    throw new ResourceNotFound("Aliases", id);
        //}
        //return aliasesDom;

        return aliasesPort.findOptionalAliases(id).orElseThrow(() -> new ResourceNotFound("Aliases", id));
    }
}
