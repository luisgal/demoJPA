package com.example.demoJPA.core.business;

import com.example.demoJPA.core.domain.AliasesDom;
import com.example.demoJPA.core.exception.ResourceNotFound;

public interface AliasesService {
    String crearAliases(AliasesDom aliasesdom);
    AliasesDom findAliases(String id) throws ResourceNotFound;
}
