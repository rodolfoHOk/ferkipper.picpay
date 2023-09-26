package br.com.hiokdev.picpaysimplificado.domain.exceptions;

public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }

}
