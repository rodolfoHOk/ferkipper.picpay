package br.com.hiokdev.picpaysimplificado.domain.services;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final UserService userService;
  private final NotificationService notificationService;
  //  private final RestTemplate restTemplate;

  @Transactional
  public Transaction create(Transaction transaction) {
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

    userService.update(sender);
    userService.update(receiver);
    Transaction transactionSaved = transactionRepository.save(newTransaction);

    notificationService.send(sender, "Transação realizada com sucesso");
    notificationService.send(receiver, "Transação recebida com sucesso");

    return transactionSaved;
  }

  private boolean authorizedTransaction(User sender, BigDecimal value) {
    String url = "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6";

//    ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(url, Map.class);
    Map<String, String> mockResponse = new HashMap<>();
    if (sender == null || value.compareTo(new BigDecimal("10000")) > 0) {
      mockResponse.put("message", "Não Autorizado");
    } else {
      mockResponse.put("message", "Autorizado");
    }
    ResponseEntity<Map<String, String>> authorizationResponse = ResponseEntity.ok(mockResponse);

    if (authorizationResponse.getStatusCode() == HttpStatus.OK && authorizationResponse.getBody() != null) {
      String message = (String) authorizationResponse.getBody().get("message");
      return message.equalsIgnoreCase("Autorizado");
    }

    return false;
  }

}
