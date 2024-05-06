package br.com.fiap.brindes.service;

import br.com.fiap.brindes.dto.request.LojaRequest;
import br.com.fiap.brindes.dto.request.ProdutoRequest;
import br.com.fiap.brindes.dto.response.LojaResponse;
import br.com.fiap.brindes.entity.Loja;
import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LojaService implements ServiceDTO<Loja, LojaRequest, LojaResponse> {

    @Autowired
    private LojaRepository lojaRepository;
    @Autowired
    private ProdutoService produtoService;

    public Loja addProdutoComercializado(Long id, ProdutoRequest produtoRequest) {
        Loja loja = findById(id);
        if (loja != null) {
            Produto produto = produtoService.toEntity(produtoRequest);
            produto = produtoService.save(produto); // Salva o produto antes de adicioná-lo à loja
            loja.getProdutosComercializados().add(produto);
            save(loja);
        }
        return loja;
    }

    @Override
    public Collection<Loja> findAll(Example<Loja> example) {
        return lojaRepository.findAll(example);
    }

    @Override
    public Loja findById(Long id) {
        return lojaRepository.findById(id).orElse(null);
    }

    @Override
    public Loja save(Loja loja) {
        return lojaRepository.save(loja);
    }

    @Override
    public Loja toEntity(LojaRequest dto) {
        return Loja.builder()
                .nome(dto.nome())
                .build();
    }

    @Override
    public LojaResponse toResponse(Loja loja) {
        return LojaResponse.builder()
                .nome(loja.getNome())
                .id(loja.getId())
                .build();
    }

    @Override
    public Collection<Loja> findAll() {
        return lojaRepository.findAll();
    }
}
