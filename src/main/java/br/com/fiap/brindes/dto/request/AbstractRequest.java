package br.com.fiap.brindes.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbstractRequest(

        @Positive(message = "O id é inválido")
        @NotNull(message = "O id é obrigatório")
        Long id
) {
}
