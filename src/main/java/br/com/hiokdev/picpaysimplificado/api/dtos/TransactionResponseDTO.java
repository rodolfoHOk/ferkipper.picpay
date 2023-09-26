package br.com.hiokdev.picpaysimplificado.api.dtos;

import br.com.hiokdev.picpaysimplificado.domain.models.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponseDTO(
  UUID id,
  BigDecimal value,
  UUID payer,
  UUID payee
) {

  public static TransactionResponseDTO toRepresentationModel(Transaction entity) {
    return new TransactionResponseDTO(
      entity.getId(),
      entity.getAmount(),
      entity.getSender().getId(),
      entity.getReceiver().getId()
    );
  }

}
