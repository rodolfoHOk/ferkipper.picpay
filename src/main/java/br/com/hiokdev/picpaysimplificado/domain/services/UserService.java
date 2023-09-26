package br.com.hiokdev.picpaysimplificado.domain.services;

import br.com.hiokdev.picpaysimplificado.domain.exceptions.BusinessException;
import br.com.hiokdev.picpaysimplificado.domain.exceptions.EntityNotFoundException;
import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

  private final UserRepository userRepository;

  public void validateUserTypeAndBalance(User sender, BigDecimal amount) {
    if (sender.isMerchant()) {
      throw new BusinessException("Usuário do tipo lojista não está autorizado a fazer transações");
    }

    sender.validateBalance(amount);
  }

  public User findById(UUID id) {
    return userRepository.findById(id).orElseThrow(() ->
      new EntityNotFoundException("Usuário não encontrado com o id informado"));
  }

  public void save(User user) {
    user.validate();
    userRepository.save(user);
  }

}
