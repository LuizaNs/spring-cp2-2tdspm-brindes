package br.com.fiap.brindes.dto.response;

import java.util.Collection;

public record LojaResponse(

        Long id,
        String nome,
        Collection<ProdutoResponse> produtosComercializados
) {
}
