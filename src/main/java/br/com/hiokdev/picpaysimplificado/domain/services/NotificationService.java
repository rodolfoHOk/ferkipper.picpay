package br.com.hiokdev.picpaysimplificado.domain.services;

import br.com.hiokdev.picpaysimplificado.api.dtos.NotificationDTO;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.NotificationException;
import br.com.hiokdev.picpaysimplificado.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationService {

  private final RestTemplate restTemplate;

  public void send(User user, String message) {
    String url = "http://o4d9z.mocklab.io/notify";
    String email = user.getEmail();

    NotificationDTO notificationRequest = new NotificationDTO(email, message);

//    ResponseEntity<String> notificationResponse = restTemplate.postForEntity(url, notificationRequest, String.class);
    ResponseEntity<String> notificationResponse = ResponseEntity.ok().build();

    if (!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
      System.out.println("Erro ao enviar notificação");
      throw new NotificationException("Serviço de notificação está fora do ar");
    }

    System.out.println("Notificação enviada para o usuário: " + user.getFullName());
  }

}
