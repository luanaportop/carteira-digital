package br.com.api.carteira.digital.repository;

import br.com.api.carteira.digital.model.CarteiraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<CarteiraEntity, Long> {
}
