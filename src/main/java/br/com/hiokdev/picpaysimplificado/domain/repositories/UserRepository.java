package br.com.hiokdev.picpaysimplificado.domain.repositories;

import br.com.hiokdev.picpaysimplificado.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
