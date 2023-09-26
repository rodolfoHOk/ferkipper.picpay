package br.com.hiokdev.picpaysimplificado.domain.exceptions;

public class UnauthorizedTransactionException extends BusinessException {

  public UnauthorizedTransactionException(String message) {
    super(message);
  }

}
