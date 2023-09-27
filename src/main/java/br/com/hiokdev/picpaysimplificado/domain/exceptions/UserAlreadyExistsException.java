package br.com.hiokdev.picpaysimplificado.domain.exceptions;

public class UserAlreadyExistsException extends BusinessException {

  public UserAlreadyExistsException(String message) {
    super(message);
  }

}
