package br.com.hiokdev.picpaysimplificado.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record NotificationDTO(
  @Schema(example = "Johndoe@email.com")
  String email,
  @Schema(example = "Transação realizada com sucesso")
  String message
) {
}
