package com.example.demoJPA.primary.http;

import com.example.demoJPA.core.domain.AliasesDom;
import org.mapstruct.Mapper;

@Mapper
public interface AliasesRequestMapper {

    /*
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "dob", target = "dob")
    */
    AliasesDom fromToAliasesDom(AliasesController.AliasCreateRequest aliases);

    AliasesController.AliasGetResponse fromToAliasesGetResponse(AliasesDom aliasesDom);
}
