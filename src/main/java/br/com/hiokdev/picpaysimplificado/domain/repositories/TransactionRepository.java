package br.com.hiokdev.picpaysimplificado.domain.repositories;

import br.com.hiokdev.picpaysimplificado.domain.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
