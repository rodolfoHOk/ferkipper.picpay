package br.com.hiokdev.picpaysimplificado.api.dtos;

import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.models.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UserRequestDTO(
  @NotBlank
  String firstName,
  @NotBlank
  String lastName,
  @NotBlank
  String document,
  @NotBlank @Email
  String email,
  @NotBlank @Size(min = 6)
  String password,
  @NotNull
  UserType userType,
  @NotNull @PositiveOrZero
  BigDecimal balance
) {

  public static User toDomainEntity(UserRequestDTO dto) {
    User domainEntity = new User();
    domainEntity.setFirstName(dto.firstName());
    domainEntity.setLastName(dto.lastName());
    domainEntity.setDocument(dto.document());
    domainEntity.setEmail(dto.email());
    domainEntity.setPassword(dto.password());
    domainEntity.setUserType(dto.userType());
    domainEntity.setBalance(dto.balance());

    return domainEntity;
  }
}
