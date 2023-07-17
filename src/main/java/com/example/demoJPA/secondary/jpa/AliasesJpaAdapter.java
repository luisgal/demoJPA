package com.example.demoJPA.secondary.jpa;

import com.example.demoJPA.core.domain.AliasesDom;
import com.example.demoJPA.core.enums.Gender;
import com.example.demoJPA.secondary.AliasesPort;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AliasesJpaAdapter implements AliasesPort {

    private final AliasesRepository aliasesRepository;
    private final AliasesEntityMapper aliasesEntityMapper;

    @Override
    public String safeAliases(AliasesDom aliasesDom) {
        return aliasesRepository.save(aliasesEntityMapper.fromToAliasesEntity(aliasesDom)).getId();
    }

    @Override
    public AliasesDom findAliases(String id) {
        return aliasesRepository
                .findById(id)
                //.map(aliasesEntityMapper::fromToAliasesDom)
                .map(aliasesEntityMapper::fromToAliasesDom)
                .orElseGet(AliasesDom::new);
    }

    @Override
    public Optional<AliasesDom> findOptionalAliases(String id) {
        return aliasesRepository
                .findById(id)
                .map(aliasesEntityMapper::fromToAliasesDom);
    }

    @Entity
    @Table(name = "tx_aliases")
    @Getter
    @Setter
    public static class AliasesEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;
        private String firstname;
        private String lastname;
        private LocalDate dob;
        @Enumerated(EnumType.STRING)
        private Gender gender;
    }

    public interface AliasesRepository extends JpaRepository<AliasesEntity, String> {

    }

    @Mapper
    public interface AliasesEntityMapper {
        AliasesEntity fromToAliasesEntity(AliasesDom aliasesDom);
        AliasesDom fromToAliasesDom(AliasesEntity aliasesEntity);
    }
}
