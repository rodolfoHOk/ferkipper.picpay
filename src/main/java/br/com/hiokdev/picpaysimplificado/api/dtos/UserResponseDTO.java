package br.com.hiokdev.picpaysimplificado.api.dtos;

import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.models.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record UserResponseDTO(
  @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
  UUID id,
  @Schema(example = "John")
  String firstName,
  @Schema(example = "Doe")
  String lastName,
  @Schema(example = "123456789-01")
  String document,
  @Schema(example = "johndoe@email.com")
  String email,
  @Schema(example = "MERCHANT")
  UserType userType,
  @Schema(example = "3000.67")
  BigDecimal balance
) {

  public static UserResponseDTO toRepresentationModel(User entity) {
    return new UserResponseDTO(
      entity.getId(),
      entity.getFirstName(),
      entity.getLastName(),
      entity.getDocument(),
      entity.getEmail(),
      entity.getUserType(),
      entity.getBalance()
    );
  }

}
