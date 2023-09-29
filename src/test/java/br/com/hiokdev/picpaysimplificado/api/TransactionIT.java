package br.com.hiokdev.picpaysimplificado.api;

import br.com.hiokdev.picpaysimplificado.api.dtos.TransactionDTO;
import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.models.UserType;
import br.com.hiokdev.picpaysimplificado.domain.repositories.TransactionRepository;
import br.com.hiokdev.picpaysimplificado.domain.repositories.UserRepository;
import br.com.hiokdev.picpaysimplificado.utils.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TransactionIT {

  @LocalServerPort
  private int port;

  @Autowired
  private DatabaseCleaner databaseCleaner;

  @Autowired
  private UserRepository userRepository;

  private UUID payerId;
  private UUID payeeId;

  @BeforeEach
  public void setup() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = this.port;
    RestAssured.basePath = "/api/transactions";

    databaseCleaner.clearTables();
    populateDatabase();
  }

  @Test
  public void shouldReturnATransactionWhenCreateWithOkValues() {
    TransactionDTO transaction = new TransactionDTO(
      new BigDecimal("100"),
      payerId,
      payeeId
    );

    RestAssured
      .given().accept(ContentType.JSON).contentType(ContentType.JSON).body(transaction)
      .when().post()
      .then().statusCode(200)
        .body("value", Matchers.is(100))
        .body("payer", Matchers.is(payerId.toString()))
        .body("payee", Matchers.is(payeeId.toString()))
        .body("id", Matchers.notNullValue());
  }

  @Test
  public void shouldReturnStatus404WhenCreateWithInvalidPayerId() {
    UUID invalidPayerId = UUID.randomUUID();
    TransactionDTO transaction = new TransactionDTO(
      new BigDecimal("100"),
      invalidPayerId,
      payeeId
    );

    RestAssured
      .given().accept(ContentType.JSON).contentType(ContentType.JSON).body(transaction)
      .when().post()
      .then().statusCode(404);
  }

  @Test
  public void shouldReturnStatus404WhenCreateWithInvalidPayeeId() {
    UUID invalidPayeeId = UUID.randomUUID();
    TransactionDTO transaction = new TransactionDTO(
      new BigDecimal("100"),
      payerId,
      invalidPayeeId
    );

    RestAssured
      .given().accept(ContentType.JSON).contentType(ContentType.JSON).body(transaction)
      .when().post()
      .then().statusCode(404);
  }

  @Test
  public void shouldReturnStatus400WhenCreateWithoutPayerId() {
    TransactionDTO transaction = new TransactionDTO(
      new BigDecimal("100"),
      null,
      payeeId
    );

    RestAssured
      .given().accept(ContentType.JSON).contentType(ContentType.JSON).body(transaction)
      .when().post()
      .then().statusCode(400);
  }

  @Test
  public void shouldReturnStatus400WhenCreateWithoutPayeeId() {
    TransactionDTO transaction = new TransactionDTO(
      new BigDecimal("100"),
      payerId,
      null
    );

    RestAssured
      .given().accept(ContentType.JSON).contentType(ContentType.JSON).body(transaction)
      .when().post()
      .then().statusCode(400);
  }

  @Test
  public void shouldReturnStatus400WhenCreateWithoutValue() {
    TransactionDTO transaction = new TransactionDTO(
      null,
      payerId,
      payeeId
    );

    RestAssured
      .given().accept(ContentType.JSON).contentType(ContentType.JSON).body(transaction)
      .when().post()
      .then().statusCode(400);
  }

  @Test
  public void shouldReturnStatus400WhenCreateWithNegativeValue() {
    TransactionDTO transaction = new TransactionDTO(
      new BigDecimal("-100"),
      payerId,
      payeeId
    );

    RestAssured
      .given().accept(ContentType.JSON).contentType(ContentType.JSON).body(transaction)
      .when().post()
      .then().statusCode(400);
  }

  @Test
  public void shouldReturnStatus401WhenCreateInvalidValue() {
    TransactionDTO transaction = new TransactionDTO(
      new BigDecimal("10000.01"),
      payerId,
      payeeId
    );

    RestAssured
      .given().accept(ContentType.JSON).contentType(ContentType.JSON).body(transaction)
      .when().post()
      .then().statusCode(401);
  }

  @Test
  public void shouldReturnStatus400WhenCreateWithPayerIsMerchant() {
    TransactionDTO transaction = new TransactionDTO(
      new BigDecimal("-100"),
      payeeId,
      payerId
    );

    RestAssured
      .given().accept(ContentType.JSON).contentType(ContentType.JSON).body(transaction)
      .when().post()
      .then().statusCode(400);
  }

  private void populateDatabase() {
    User user1 = new User(
      null,
      "User 1",
      "Test",
      "123456789-01",
      "user1@emailtest.com",
      "123456",
      UserType.COMMON,
      new BigDecimal("15000")
    );

    User user2 = new User(
      null,
      "User 2",
      "Test",
      "123456789-02",
      "user2@emailtest.com",
      "123456",
      UserType.MERCHANT,
      new BigDecimal("5000")
    );

    payerId = userRepository.save(user1).getId();
    payeeId = userRepository.save(user2).getId();
  }
}
