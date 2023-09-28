package br.com.hiokdev.picpaysimplificado.domain.models;

import br.com.hiokdev.picpaysimplificado.domain.exceptions.BusinessException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class UserTest {

  @Test
  public void shouldNotThrowAnExceptionWhenUserValidate() {
    User user = getValidUser();
    Assertions.assertDoesNotThrow(user::validate);
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithFirstNameIsNull() {
    User user = getValidUser();
    user.setFirstName(null);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Primeiro nome não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithFirstNameIsBlank() {
    User user = getValidUser();
    user.setFirstName("");
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Primeiro nome não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithLastNameIsNull() {
    User user = getValidUser();
    user.setLastName(null);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Último nome não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithLastNameIsBlank() {
    User user = getValidUser();
    user.setLastName("");
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Último nome não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithDocumentIsNull() {
    User user = getValidUser();
    user.setDocument(null);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Documento não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithDocumentIsBlank() {
    User user = getValidUser();
    user.setDocument("");
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Documento não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithEmailIsNull() {
    User user = getValidUser();
    user.setEmail(null);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Email não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithEmailIsBlank() {
    User user = getValidUser();
    user.setEmail("");
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Email não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithPasswordIsNull() {
    User user = getValidUser();
    user.setPassword(null);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Senha não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithPasswordIsBlank() {
    User user = getValidUser();
    user.setPassword("");
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Senha não pode ser nulo ou vazio");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithPasswordLessThen6Char() {
    User user = getValidUser();
    user.setPassword("12345");
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Senha deve ter ao menos 6 caracteres");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithUserTypeIsNull() {
    User user = getValidUser();
    user.setUserType(null);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Tipo de usuário não pode ser nulo");
  }

  @Test
  public void shouldThrowBusinessExceptionWhenValidateWithInvalidUserType() {
    User user = getValidUser();

    user.setUserType(UserType.INVALID);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, user::validate);
    Assertions.assertEquals(exception.getMessage(), "Tipo de usuário inválido");
  }

  @Test
  public void shouldReturnTrueWhenUserTypeIsMerchant() {
    User user = getValidUser();
    user.setUserType(UserType.MERCHANT);
    Assertions.assertTrue(user::isMerchant);
  }

  @Test
  public void shouldReturnFalseWhenUserTypeNotIsMerchant() {
    User user = getValidUser();
    user.setUserType(UserType.COMMON);
    Assertions.assertFalse(user::isMerchant);
  }

  @Test
  public void shouldNotThrowAnExceptionWhenValidateBalanceWithSufficientAmount() {
    User user = getValidUser();
    user.setBalance(new BigDecimal("100"));
    Assertions.assertDoesNotThrow(() -> user.validateBalance(new BigDecimal("10")));
  }

  @Test
  public void shouldThrowInsufficientBalanceExceptionWhenValidateBalanceWithInSufficientAmount() {
    User user = getValidUser();
    user.setBalance(new BigDecimal("100"));
    Assertions.assertThrows(InsufficientBalanceException.class, () -> user.validateBalance(new BigDecimal("200")));
  }

  @Test
  public void shouldReturnCompleteNameWhenCallGetFullName() {
    User user = getValidUser();
    Assertions.assertEquals(user.getFullName(), "John Doe");
  }

  private User getValidUser() {
    User user = new User();
    user.setId(UUID.randomUUID());
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setDocument("123456789-01");
    user.setEmail("johndoe@email.com");
    user.setPassword("123456");
    user.setBalance(new BigDecimal("1200.34"));
    user.setUserType(UserType.COMMON);
    return user;
  }

}
