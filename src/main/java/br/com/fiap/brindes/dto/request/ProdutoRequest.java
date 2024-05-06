package br.com.fiap.brindes.dto.request;

public record ProdutoRequest(

        String nome,
        double preco,
        Long categoriaId

) {
}
