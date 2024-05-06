package br.com.fiap.brindes.service;

import br.com.fiap.brindes.dto.request.CategoriaRequest;
import br.com.fiap.brindes.dto.response.CategoriaResponse;
import br.com.fiap.brindes.entity.Categoria;
import br.com.fiap.brindes.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoriaService implements ServiceDTO<Categoria, CategoriaRequest, CategoriaResponse> {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Collection<Categoria> findAll(Example<Categoria> example) {
        return categoriaRepository.findAll(example);
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria toEntity(CategoriaRequest dto) {
        return Categoria.builder()
                .nome(dto.nome())
                .build();
    }

    @Override
    public CategoriaResponse toResponse(Categoria categoria) {
        return CategoriaResponse.builder()
                .nome(categoria.getNome())
                .id(categoria.getId())
                .build();
    }

    @Override
    public Collection<Categoria> findAll() {
        return null;
    }
}
