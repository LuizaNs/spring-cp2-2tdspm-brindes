package br.com.fiap.brindes.resources;

import br.com.fiap.brindes.dto.request.CategoriaRequest;
import br.com.fiap.brindes.dto.response.CategoriaResponse;
import br.com.fiap.brindes.entity.Categoria;
import br.com.fiap.brindes.service.CategoriaService;
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
@RequestMapping(value = "/categorias")
public class CategoriaResource implements ResourceDTO<CategoriaRequest, CategoriaResponse> {

        @Autowired
        private CategoriaService categoriaService;

        @GetMapping
        public ResponseEntity<Collection<CategoriaResponse>> findAll(
                @RequestParam(name = "nome", required = false) String nome
        ) {

            var categoria = Categoria.builder()
                    .nome(nome)
                    .build();

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withIgnoreCase()
                    .withIgnoreNullValues();

            Example<Categoria> example = Example.of(categoria, matcher);

            Collection<Categoria> all = categoriaService.findAll(example);

            var response = all.stream()
                    .map(categoriaService::toResponse)
                    .toList();

            return ResponseEntity.ok(response);

        }

        @GetMapping(value = "/{id}")
        public ResponseEntity<CategoriaResponse> findById(@PathVariable Long id) {
            Categoria categoria = categoriaService.findById(id);
            if (Objects.isNull(categoria)) return ResponseEntity.notFound().build();
            var response = categoriaService.toResponse(categoria);
            return ResponseEntity.ok(response);
        }

        @Override
        @PostMapping
        @Transactional
        public ResponseEntity<CategoriaResponse> save(@RequestBody @Valid CategoriaRequest dto) {

            var entity = categoriaService.toEntity(dto);

            Categoria saved = categoriaService.save(entity);

            var response = categoriaService.toResponse(saved);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(response);

        }
    }
