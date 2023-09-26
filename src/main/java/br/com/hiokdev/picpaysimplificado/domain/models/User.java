package br.com.hiokdev.picpaysimplificado.domain.models;

import br.com.hiokdev.picpaysimplificado.domain.exceptions.BusinessException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.InsufficientBalanceException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String document;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_type", nullable = false)
  private UserType userType;

  @Column(nullable = false)
  @ColumnDefault(value = "0.00")
  private BigDecimal balance;

  public void validate() {
    if (this.getFirstName() == null || this.getFirstName().isEmpty()) {
      throw new BusinessException("Primeiro nome não pode ser nulo ou vazio");
    }

    if (this.getLastName() == null || this.getLastName().isEmpty()) {
      throw new BusinessException("Último nome não pode ser nulo ou vazio");
    }

    if (this.getDocument() == null || this.getDocument().isEmpty()) {
      throw new BusinessException("Documento não pode ser nulo ou vazio");
    }

    if (this.getEmail() == null || this.getEmail().isEmpty()) {
      throw new BusinessException("Email não pode ser nulo ou vazio");
    }

    if (this.getPassword() == null || this.getPassword().isEmpty()) {
      throw new BusinessException("Senha não pode ser nulo ou vazio");
    }

    if (this.getPassword().length() < 6) {
      throw new BusinessException("Senha deve ter ao menos 6 caracteres");
    }

    if (this.getUserType() == null) {
      throw new BusinessException("Tipo de usuário não pode ser nulo");
    }

    if (!this.getUserType().name().equals(UserType.COMMON.name()) && !this.getUserType().name().equals(UserType.MERCHANT.name())) {
      throw new BusinessException("Tipo de usuário inválido");
    }
  }

  public boolean isMerchant() {
    return this.getUserType() == UserType.MERCHANT;
  }

  public void validateBalance(BigDecimal amount) {
    if (this.getBalance().compareTo(amount) < 0) {
      throw new InsufficientBalanceException("Saldo insuficiente");
    }
  }

  public String getFullName() {
    return this.getFirstName() + " " + this.getLastName();
  }

}
