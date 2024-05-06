package br.com.fiap.brindes.resources;

import br.com.fiap.brindes.dto.request.LojaRequest;
import br.com.fiap.brindes.dto.request.ProdutoRequest;
import br.com.fiap.brindes.dto.response.LojaResponse;
import br.com.fiap.brindes.entity.Loja;
import br.com.fiap.brindes.service.LojaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/lojas")
public class LojaResource implements ResourceDTO<LojaRequest, LojaResponse> {

    @Autowired
    private LojaService lojaService;

    @GetMapping
    public ResponseEntity<Collection<LojaResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome
    ) {

        var loja = Loja.builder()
                .nome(nome)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Loja> example = Example.of(loja, matcher);

        Collection<Loja> all = lojaService.findAll(example);

        var response = all.stream()
                .map(lojaService::toResponse)
                .toList();

        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LojaResponse> findById(@PathVariable Long id) {
        Loja loja = lojaService.findById(id);
        if (Objects.isNull(loja)) return ResponseEntity.notFound().build();
        var response = lojaService.toResponse(loja);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    @Transactional
    public ResponseEntity<LojaResponse> save(@RequestBody @Valid LojaRequest dto) {

        var entity = lojaService.toEntity(dto);

        Loja saved = lojaService.save(entity);

        var response = lojaService.toResponse(saved);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PostMapping(value = "/{id}/produtos-comercializados")
    @Transactional
    public ResponseEntity<LojaResponse> addProdutoComercializado(@PathVariable Long id, @RequestBody @Valid ProdutoRequest produtoRequest) {
        Loja loja = lojaService.addProdutoComercializado(id, produtoRequest);
        if (Objects.isNull(loja)) return ResponseEntity.notFound().build();
        var response = lojaService.toResponse(loja);
        return ResponseEntity.ok(response);
    }
}
