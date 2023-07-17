package com.example.demoJPA.primary.http;

import com.example.demoJPA.core.business.AliasesService;
import com.example.demoJPA.core.exception.ResourceNotFound;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/aliases")
@RestController
@Validated
//@RequiredArgsConstructor
public class AliasesController {

    private final AliasesService aliasesService;
    private final AliasesRequestMapper mapper;

    public AliasesController(AliasesService aliasesService, AliasesRequestMapper aliasesRequestMapper) {
        this.aliasesService = aliasesService;
        this.mapper = aliasesRequestMapper;
    }

    record AliasCreateRequest(@NotBlank @Size(max = 20) String firstname, @Size(max = 20) String lastname,
                              @Past LocalDate dob) {
    }

    record AliasCreateResponse(String id) {
    }

    record AliasGetResponse(String id, String firstname, String lastname, LocalDate dob, String gender) {
    }

    @PostMapping
    public AliasCreateResponse createAlias(@RequestBody @Valid AliasCreateRequest request) {
        String id = aliasesService.crearAliases(mapper.fromToAliasesDom(request));
        return new AliasCreateResponse(id);
    }

    @GetMapping("/{id}")
    public AliasGetResponse findAlias(@PathVariable("id") @Size(min = 36, max = 36) String id) throws ResourceNotFound {
        return mapper.fromToAliasesGetResponse(aliasesService.findAliases(id));
    }



}
