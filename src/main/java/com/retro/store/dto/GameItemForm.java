package com.retro.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GameItemForm {
    private Long id;

    @NotBlank(message = "Titulo e obrigatorio")
    private String title;

    @NotBlank(message = "Plataforma e obrigatoria")
    private String platform;

    @NotBlank(message = "Genero e obrigatorio")
    private String genre;

    @NotBlank(message = "Codigo e obrigatorio")
    @Pattern(regexp = "^[A-Z]{3}-\\d{4}$", message = "Formato: AAA-1234")
    private String skuCode;

    @NotBlank(message = "Descricao e obrigatoria")
    private String description;

    @NotNull(message = "Preco e obrigatorio")
    @Positive(message = "Preco deve ser positivo")
    private Double price;

    private String imageUrl;
}
