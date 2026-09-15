package br.com.api.carteira.digital.service;

import br.com.api.carteira.digital.dto.CarteiraResponseDTO;
import br.com.api.carteira.digital.model.CarteiraEntity;
import br.com.api.carteira.digital.repository.CarteiraRepository;
import br.com.api.carteira.digital.util.exception.CarteiraNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarteiraService {
    @Autowired
    CarteiraRepository carteiraRepository;

    public CarteiraResponseDTO buscarMinhaCarteira(Long codUsuario){
        CarteiraEntity carteira = carteiraRepository.findByCodUsuario(codUsuario)
                .orElseThrow(CarteiraNaoEncontradaException::new);

        return new CarteiraResponseDTO(
                carteira.getCodCarteira(),
                carteira.getSaldo(),
                carteira.getStatus()
        );
    }
}
