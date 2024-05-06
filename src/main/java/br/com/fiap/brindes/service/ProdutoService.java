package br.com.fiap.brindes.service;

import br.com.fiap.brindes.dto.request.ProdutoRequest;
import br.com.fiap.brindes.dto.response.ProdutoResponse;
import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.repository.Produtorepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProdutoService implements ServiceDTO<Produto, ProdutoRequest, ProdutoResponse> {

    private final Produtorepository produtorepository;

    public ProdutoService(Produtorepository produtorepository) {
        this.produtorepository = produtorepository;
    }

    @Override
    public Collection<Produto> findAll(Example<Produto> example) {
        return produtorepository.findAll(example);
    }

    @Override
    public Produto findById(Long id) {
        return produtorepository.findById(id).orElse(null);
    }

    @Override
    public Produto save(Produto produto) {
        return produtorepository.save(produto);
    }

    @Override
    public Produto toEntity(ProdutoRequest dto) {
        return Produto.builder()
                .nome(dto.nome())
                .build();
    }

    @Override
    public ProdutoResponse toResponse(Produto produto) {
        return ProdutoResponse.builder()
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .categoria(produto.getCategoria().getId())
                .id(produto.getId())
                .build();
    }

    @Override
    public Collection<Produto> findAll() {
        return produtorepository.findAll();
    }
}
