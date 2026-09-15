package br.com.api.carteira.digital.repository;

import br.com.api.carteira.digital.model.CarteiraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteiraRepository extends JpaRepository<CarteiraEntity, Long> {
    Optional<CarteiraEntity> findByCodUsuario(Long codUsuario);
}
