package br.com.hiokdev.picpaysimplificado.api.dtos;

import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.models.UserType;

import java.math.BigDecimal;
import java.util.UUID;

public record UserResponseDTO(
  UUID id,
  String firstName,
  String lastName,
  String document,
  String email,
  UserType userType,
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
