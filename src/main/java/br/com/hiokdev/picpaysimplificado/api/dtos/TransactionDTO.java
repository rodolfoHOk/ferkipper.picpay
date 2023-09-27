package br.com.hiokdev.picpaysimplificado.api.dtos;

import br.com.hiokdev.picpaysimplificado.domain.models.Transaction;
import br.com.hiokdev.picpaysimplificado.domain.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(
  @Schema(example = "100.34")
  @NotNull @PositiveOrZero
  BigDecimal value,
  @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
  @NotNull
  UUID payer,
  @Schema(example = "2a91b48e-7f3d-442d-a953-ceb146e39fcb")
  @NotNull
  UUID payee
) {

  public static Transaction toDomainEntity(TransactionDTO dto) {
    Transaction domainEntity = new Transaction();
    domainEntity.setAmount(dto.value());
    User payer = new User();
    payer.setId(dto.payer());
    domainEntity.setSender(payer);
    User payee = new User();
    payee.setId(dto.payee());
    domainEntity.setReceiver(payee);

    return domainEntity;
  }

}
