package br.com.fiap.brindes.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(

        @Size(min = 7, max = 255, message = "Nome inválido!")
        @NotNull(message = "Nome não pode ser nulo!")
        String nome
) {
}
