package br.com.hiokdev.picpaysimplificado.domain.services;

import br.com.hiokdev.picpaysimplificado.domain.exceptions.UnauthorizedTransactionException;
import br.com.hiokdev.picpaysimplificado.domain.models.Transaction;
import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.models.UserType;
import br.com.hiokdev.picpaysimplificado.domain.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

  @Mock
  private UserService userService;

  @Mock
  private NotificationService notificationService;

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionService transactionService;

  @Test
  public void shouldReturnATransactionWhenCreateWithOkValues() {
    User sender = getValidCommonUser();
    User receiver = getValidMerchantUser();
    Transaction transaction = getTransaction(sender, receiver, new BigDecimal("10000"));

    Transaction savedTransaction = getTransaction(sender, receiver, new BigDecimal("10000"));
    savedTransaction.setId(UUID.randomUUID());

    Mockito.when(userService.findById(sender.getId())).thenReturn(sender);
    Mockito.when(userService.findById(receiver.getId())).thenReturn(receiver);
    Mockito.doNothing().when(userService).validateUserTypeAndBalance(sender, transaction.getAmount());
    Mockito.when(userService.save(sender)).thenReturn(sender);
    Mockito.when(userService.save(receiver)).thenReturn(receiver);
    Mockito.when(transactionRepository.save(transaction)).thenReturn(savedTransaction);

    Transaction result = transactionService.create(transaction);

    Mockito
      .verify(notificationService, Mockito.times(1))
      .send(sender, "Transação realizada com sucesso");
    Mockito
      .verify(notificationService, Mockito.times(1))
      .send(receiver, "Transação recebida com sucesso");

    Assertions.assertEquals(result, savedTransaction);
  }

  @Test
  public void shouldThrowUnauthorizedTransactionExceptionWhenCreateWithUnauthorizedValue() {
    User sender = getValidCommonUser();
    User receiver = getValidMerchantUser();
    Transaction transaction = getTransaction(sender, receiver, new BigDecimal("10000.01"));

    Mockito.when(userService.findById(sender.getId())).thenReturn(sender);
    Mockito.when(userService.findById(receiver.getId())).thenReturn(receiver);
    Mockito.doNothing().when(userService).validateUserTypeAndBalance(sender, transaction.getAmount());

    UnauthorizedTransactionException exception = Assertions
      .assertThrows(UnauthorizedTransactionException.class, () -> transactionService.create(transaction));

    Assertions.assertEquals(exception.getMessage(), "Transação não autorizada");
  }

  private Transaction getTransaction(User sender, User receiver, BigDecimal amount) {
    Transaction transaction = new Transaction();
    transaction.setSender(sender);
    transaction.setReceiver(receiver);
    transaction.setAmount(amount);
    return transaction;
  }

  private User getValidCommonUser() {
    User user = new User();
    user.setId(UUID.randomUUID());
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setDocument("123456789-01");
    user.setEmail("johndoe@email.com");
    user.setPassword("123456");
    user.setBalance(new BigDecimal("100"));
    user.setUserType(UserType.COMMON);
    return user;
  }

  private User getValidMerchantUser() {
    User user = new User();
    user.setId(UUID.randomUUID());
    user.setFirstName("Maria");
    user.setLastName("Doe");
    user.setDocument("123456789-02");
    user.setEmail("mariadoe@email.com");
    user.setPassword("123456");
    user.setBalance(new BigDecimal("1000"));
    user.setUserType(UserType.MERCHANT);
    return user;
  }
  
}
