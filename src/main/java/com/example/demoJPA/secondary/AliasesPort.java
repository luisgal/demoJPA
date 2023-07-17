package com.example.demoJPA.secondary;

import com.example.demoJPA.core.domain.AliasesDom;

import java.util.Optional;

public interface AliasesPort {
    String safeAliases(AliasesDom aliasesDom);

    AliasesDom findAliases(String id);

    Optional<AliasesDom> findOptionalAliases(String id);
}
