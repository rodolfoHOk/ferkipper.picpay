package br.com.hiokdev.picpaysimplificado.domain.services;

import br.com.hiokdev.picpaysimplificado.domain.exceptions.BusinessException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.EntityNotFoundException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.InsufficientBalanceException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.UserAlreadyExistsException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.ValidationException;
import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.models.UserType;
import br.com.hiokdev.picpaysimplificado.domain.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  public void shouldNotThrowAnExceptionWhenValidateUserTypeAndBalanceWithOkValues() {
    User user = getValidUser();
    Assertions.assertDoesNotThrow(() -> userService.validateUserTypeAndBalance(user, new BigDecimal("10")));
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateUserTypeAndBalanceWithMerchantUser() {
    User user = getValidUser();
    user.setUserType(UserType.MERCHANT);
    BusinessException exception = Assertions.assertThrows(
      BusinessException.class,
      () -> userService.validateUserTypeAndBalance(user, new BigDecimal("10"))
    );
    Assertions.assertEquals(
      exception.getMessage(),
      "Usuário do tipo lojista não está autorizado a fazer transações"
    );
  }

  @Test
  public void shouldThrowInsufficientBalanceExceptionExceptionWhenValidateUserTypeAndBalanceWithInsufficientBalance() {
    User user = getValidUser();
    InsufficientBalanceException exception = Assertions.assertThrows(
      InsufficientBalanceException.class,
      () -> userService.validateUserTypeAndBalance(user, new BigDecimal("200"))
    );
    Assertions.assertEquals(exception.getMessage(), "Saldo insuficiente");
  }

  @Test
  public void shouldReturnAUserWhenFindByIdWithValidID() {
    User expectedUser = getValidUser();
    UUID validID = expectedUser.getId();
    Mockito.when(userRepository.findById(validID)).thenReturn(Optional.of(expectedUser));
    User findedUser = userService.findById(validID);
    Assertions.assertEquals(findedUser.getId(), validID);
    Assertions.assertEquals(findedUser, expectedUser);
  }

  @Test
  public void shouldThrowEntityNotFoundExceptionWhenFindByIdWithInvalidID() {
    UUID invalidID = UUID.randomUUID();
    Mockito.when(userRepository.findById(invalidID)).thenReturn(Optional.empty());
    Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findById(invalidID));
  }

  @Test
  public void shouldReturnAListOfUserWhenGetAll() {
    User findedUser = getValidUser();
    Mockito.when(userRepository.findAll()).thenReturn(List.of(findedUser));
    Assertions.assertTrue(userService.getAll().contains(findedUser));
  }

  @Test
  public void shouldReturnAUserWhenSaveWithOkValues() {
    User userToSave = getValidUser();
    userToSave.setId(null);
    User expectedUser = getValidUser();
    Mockito.when(userRepository.save(userToSave)).thenReturn(expectedUser);
    User savedUser = userService.create(userToSave);
    Assertions.assertEquals(savedUser, expectedUser);
  }

  @Test
  public void shouldThrowValidationExceptionWhenSaveWithABlankFirstName() {
    User userToSave = getValidUser();
    userToSave.setFirstName("");
    Assertions.assertThrows(ValidationException.class, () -> userService.create(userToSave));
  }

  @Test
  public void shouldThrowUserAlreadyExistsExceptionWhenSaveWithAlreadyExistDocument() {
    User userToSave = getValidUser();
    Mockito.when(userRepository.findUserByDocument(userToSave.getDocument())).thenReturn(Optional.of(userToSave));
    UserAlreadyExistsException exception = Assertions.assertThrows(
      UserAlreadyExistsException.class,
      () -> userService.create(userToSave)
    );
    Assertions.assertEquals(exception.getMessage(), "Já existe usuário com o documento informado");
  }

  @Test
  public void shouldThrowUserAlreadyExistsExceptionWhenSaveWithAlreadyExistEmail() {
    User userToSave = getValidUser();
    Mockito.when(userRepository.findUserByEmail(userToSave.getEmail())).thenReturn(Optional.of(userToSave));
    UserAlreadyExistsException exception = Assertions.assertThrows(
      UserAlreadyExistsException.class,
      () -> userService.create(userToSave)
    );
    Assertions.assertEquals(exception.getMessage(), "Já existe usuário com o email informado");
  }

  private User getValidUser() {
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

}
