package br.com.hiokdev.picpaysimplificado.api.controllers;

import br.com.hiokdev.picpaysimplificado.api.dtos.TransactionDTO;
import br.com.hiokdev.picpaysimplificado.api.dtos.TransactionResponseDTO;
import br.com.hiokdev.picpaysimplificado.domain.models.Transaction;
import br.com.hiokdev.picpaysimplificado.domain.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping
  public ResponseEntity<TransactionResponseDTO> create(@RequestBody @Valid TransactionDTO inputDTO) {
    Transaction newTransaction = TransactionDTO.toDomainEntity(inputDTO);
    Transaction transactionCreated = transactionService.create(newTransaction);
    return ResponseEntity.ok(TransactionResponseDTO.toRepresentationModel(transactionCreated));
  }

}
