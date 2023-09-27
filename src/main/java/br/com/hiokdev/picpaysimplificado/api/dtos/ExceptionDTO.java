package br.com.hiokdev.picpaysimplificado.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record ExceptionDTO(
  @Schema(example = "400")
  Integer statusCode,
  @Schema(example = "Bad Request")
  String status,
  @Schema(example = "[lastName: não deve estar em branco]")
  String message
) {
}
