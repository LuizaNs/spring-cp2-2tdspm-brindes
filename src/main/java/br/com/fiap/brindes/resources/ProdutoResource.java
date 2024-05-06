package br.com.fiap.brindes.resources;

import br.com.fiap.brindes.dto.request.ProdutoRequest;
import br.com.fiap.brindes.dto.response.ProdutoResponse;
import br.com.fiap.brindes.entity.Categoria;
import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.service.CategoriaService;
import br.com.fiap.brindes.service.ProdutoService;
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
@RequestMapping(value = "/produtos")
public class ProdutoResource implements ResourceDTO<ProdutoRequest, ProdutoResponse> {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Collection<ProdutoResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome
    ) {

        var produto = Produto.builder()
                .nome(nome)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Produto> example = Example.of(produto, matcher);

        Collection<Produto> all = produtoService.findAll(example);

        var response = all.stream()
                .map(produtoService::toResponse)
                .toList();

        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        Produto produto = produtoService.findById(id);
        if (Objects.isNull(produto)) return ResponseEntity.notFound().build();
        var response = produtoService.toResponse(produto);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid ProdutoRequest r) {
        var entity = produtoService.toEntity(r);

        Long categoriaId = r.categoriaId();
        Categoria categoria = categoriaService.findById(categoriaId);
        if (categoria == null) {
            return ResponseEntity.badRequest().build();
        }

        entity.setCategoria(categoria);
        entity.setPreco(r.preco());

        Produto saved = produtoService.save(entity);

        var response = produtoService.toResponse(saved);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
