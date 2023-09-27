package br.com.hiokdev.picpaysimplificado.api.dtos;

public record ExceptionDTO(
  Integer statusCode,
  String status,
  String message
) {
}
