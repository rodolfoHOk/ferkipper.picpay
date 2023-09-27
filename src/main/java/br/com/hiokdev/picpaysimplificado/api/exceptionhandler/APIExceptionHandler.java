package br.com.hiokdev.picpaysimplificado.api.exceptionhandler;

import br.com.hiokdev.picpaysimplificado.api.dtos.ExceptionDTO;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.BusinessException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.EntityNotFoundException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.InsufficientBalanceException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.NotificationException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.UnauthorizedTransactionException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ExceptionDTO> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(
      HttpStatus.BAD_REQUEST.value(),
      HttpStatus.BAD_REQUEST.name(),
      exception.getMessage()
    );
    return ResponseEntity.badRequest().body(exceptionDTO);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ExceptionDTO> handleEntityNotFoundException(EntityNotFoundException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(
      HttpStatus.NOT_FOUND.value(),
      HttpStatus.NOT_FOUND.name(),
      exception.getMessage()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
  }

  @ExceptionHandler(InsufficientBalanceException.class)
  public ResponseEntity<ExceptionDTO> handleInsufficientBalanceException(InsufficientBalanceException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(
      HttpStatus.BAD_REQUEST.value(),
      HttpStatus.BAD_REQUEST.name(),
      exception.getMessage()
    );
    return ResponseEntity.badRequest().body(exceptionDTO);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ExceptionDTO> handleBusinessException(BusinessException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(
      HttpStatus.BAD_REQUEST.value(),
      HttpStatus.BAD_REQUEST.name(),
      exception.getMessage()
    );
    return ResponseEntity.badRequest().body(exceptionDTO);
  }

  @ExceptionHandler(UnauthorizedTransactionException.class)
  public ResponseEntity<ExceptionDTO> handleUnauthorizedTransactionException(UnauthorizedTransactionException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(
      HttpStatus.UNAUTHORIZED.value(),
      HttpStatus.UNAUTHORIZED.name(),
      exception.getMessage()
    );
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionDTO);
  }

  @ExceptionHandler(NotificationException.class)
  public ResponseEntity<ExceptionDTO> handleNotificationException(NotificationException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(
      HttpStatus.SERVICE_UNAVAILABLE.value(),
      HttpStatus.SERVICE_UNAVAILABLE.name(),
      exception.getMessage()
    );
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exceptionDTO);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDTO> handleOtherException(Exception exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      HttpStatus.INTERNAL_SERVER_ERROR.name(),
      exception.getMessage()
    );
    return ResponseEntity.internalServerError().body(exceptionDTO);
  }

}
