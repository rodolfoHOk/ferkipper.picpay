package br.com.hiokdev.picpaysimplificado.api.dtos;

import br.com.hiokdev.picpaysimplificado.domain.models.Transaction;
import br.com.hiokdev.picpaysimplificado.domain.models.User;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(
  BigDecimal value,
  UUID payer,
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
