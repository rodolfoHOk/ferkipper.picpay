package br.com.hiokdev.picpaysimplificado.api;

import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.models.UserType;
import br.com.hiokdev.picpaysimplificado.domain.repositories.UserRepository;
import br.com.hiokdev.picpaysimplificado.utils.DatabaseCleaner;
import br.com.hiokdev.picpaysimplificado.utils.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class UserIT {

  @LocalServerPort
  private int port;

  @Autowired
  private DatabaseCleaner databaseCleaner;

  @Autowired
  private UserRepository userRepository;

  private long quantity;

  @BeforeEach
  public void setup() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = this.port;
    RestAssured.basePath = "/api/users";

    databaseCleaner.clearTables();
    populateDatabase();
  }

  @Test
  public void shouldReturnStatus200AndCorrectQuantityWhenListUsers() {
    RestAssured
      .given().accept(ContentType.JSON)
      .when().get()
      .then().statusCode(HttpStatus.OK.value())
        .body("", Matchers.hasSize((int) quantity));
  }

  @Test
  public void shouldReturnStatus201WhenCreateUser() {
    String userJson = ResourceUtils.getContentFromResource("/json/user.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.CREATED.value())
        .body("firstName", Matchers.is("User 3"))
        .body("lastName", Matchers.is("Test"))
        .body("document", Matchers.is("123456789-03"))
        .body("email", Matchers.is("user3@email.com"))
        .body("userType", Matchers.is("COMMON"))
        .body("balance", Matchers.is(2000.0F))
        .body("id", Matchers.notNullValue())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithoutFirstName() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-without-firstname.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithBlankFirstName() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-blank-firstname.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithBlankLastName() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-blank-lastname.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithBlankDocument() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-blank-document.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithBlankEmail() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-blank-email.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithIncorrectEmail() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-incorrect-email.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithBlankPassword() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-blank-password.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithNotMinSizePassword() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-size-password.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithoutUserType() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-without-usertype.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithInvalidUserType() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-invalid-usertype.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithoutBalance() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-without-balance.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
  }

  @Test
  public void shouldReturnStatus400WhenCreateUserWithNegativeBalance() {
    String userJson = ResourceUtils.getContentFromResource("/json/user-negative-balance.json");
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(userJson)
      .when().post()
      .then().statusCode(HttpStatus.BAD_REQUEST.value())
    ;
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
      new BigDecimal("1000")
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

    userRepository.save(user1);
    userRepository.save(user2);

    this.quantity = userRepository.count();
  }

}
