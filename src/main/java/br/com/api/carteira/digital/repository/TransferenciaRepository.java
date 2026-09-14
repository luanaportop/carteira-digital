package br.com.api.carteira.digital.repository;

import br.com.api.carteira.digital.model.TransferenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends JpaRepository<TransferenciaEntity, Long> {
}
