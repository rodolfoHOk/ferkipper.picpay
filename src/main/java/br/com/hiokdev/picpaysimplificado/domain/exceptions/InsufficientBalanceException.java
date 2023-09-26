package br.com.hiokdev.picpaysimplificado.domain.exceptions;

public class InsufficientBalanceException extends BusinessException {

  public InsufficientBalanceException(String message) {
    super(message);
  }

}
