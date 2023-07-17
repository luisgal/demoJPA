package com.example.demoJPA.secondary;

import com.example.demoJPA.core.domain.AliasesDom;
import com.example.demoJPA.core.enums.Gender;

import java.util.Optional;

public interface GenderPort {
    Optional<Gender> findGender(AliasesDom aliasesDom);
}
