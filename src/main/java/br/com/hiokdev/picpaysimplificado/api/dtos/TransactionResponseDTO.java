package br.com.hiokdev.picpaysimplificado.api.dtos;

import br.com.hiokdev.picpaysimplificado.domain.models.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponseDTO(
  @Schema(example = "bc4bf699-f25a-4a6a-9161-0328d4c5c3f6")
  UUID id,
  @Schema(example = "100.34")
  BigDecimal value,
  @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
  UUID payer,
  @Schema(example = "2a91b48e-7f3d-442d-a953-ceb146e39fcb")
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
