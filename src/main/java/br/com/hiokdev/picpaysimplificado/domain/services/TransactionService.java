package br.com.hiokdev.picpaysimplificado.domain.services;

import br.com.hiokdev.picpaysimplificado.domain.exceptions.BusinessException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.UnauthorizedTransactionException;
import br.com.hiokdev.picpaysimplificado.domain.models.Transaction;
import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final UserService userService;
  private final RestTemplate restTemplate;

  @Transactional
  public void create(Transaction transaction) {
    User sender = this.userService.findById(transaction.getSender().getId());
    User receiver = this.userService.findById(transaction.getReceiver().getId());

    userService.validateUserTypeAndBalance(sender, transaction.getAmount());

    boolean isAuthorized = authorizedTransaction(sender, transaction.getAmount());
    if (!isAuthorized) {
      throw new UnauthorizedTransactionException("Transação não autorizada");
    }

    Transaction newTransaction = new Transaction();
    newTransaction.setAmount(transaction.getAmount());
    newTransaction.setSender(sender);
    newTransaction.setReceiver(receiver);
    newTransaction.setTimestamp(LocalDateTime.now());

    sender.setBalance(sender.getBalance().subtract(newTransaction.getAmount()));
    receiver.setBalance(receiver.getBalance().add(newTransaction.getAmount()));

    transactionRepository.save(newTransaction);
    userService.save(sender);
    userService.save(receiver);
  }

  private boolean authorizedTransaction(User sender, BigDecimal value) {
    String url = "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6";

    ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(url, Map.class);

    if (authorizationResponse.getStatusCode() == HttpStatus.OK && authorizationResponse.getBody() != null) {
      String message = (String) authorizationResponse.getBody().get("message");
      return message.equalsIgnoreCase("Autorizado");
    }

    return false;
  }

}
