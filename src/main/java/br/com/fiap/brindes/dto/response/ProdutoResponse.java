package br.com.fiap.brindes.dto.response;

import br.com.fiap.brindes.entity.Produto;

public record ProdutoResponse(

        Long id,
        String nome,
        Double preco,
        Long categoria
) {

    public ProdutoResponse toResponse(Produto produto) {
        CategoriaResponse categoriaResponse = null;
        if (produto.getCategoria() != null) {
            categoriaResponse = new CategoriaResponse(produto.getCategoria().getId(), produto.getCategoria().getNome());
        }
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria().getId());
    }
}
