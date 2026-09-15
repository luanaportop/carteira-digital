package br.com.api.carteira.digital.service;

import br.com.api.carteira.digital.dto.UsuarioCadastroDTO;
import br.com.api.carteira.digital.dto.UsuarioResponseDTO;
import br.com.api.carteira.digital.model.CarteiraEntity;
import br.com.api.carteira.digital.model.UsuarioEntity;
import br.com.api.carteira.digital.repository.CarteiraRepository;
import br.com.api.carteira.digital.repository.UsuarioRepository;
import br.com.api.carteira.digital.util.enums.Role;
import br.com.api.carteira.digital.util.enums.StatusCarteira;
import br.com.api.carteira.digital.util.enums.StatusUsuario;
import br.com.api.carteira.digital.util.exception.CpfJaCadastradoException;
import br.com.api.carteira.digital.util.exception.EmailJaCadastradoException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CarteiraRepository carteiraRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO cadastrar (UsuarioCadastroDTO cadastro) {
        if(usuarioRepository.existsByEmail(cadastro.email())){
            throw new EmailJaCadastradoException();
        }
        if (usuarioRepository.existsByCpf(cadastro.cpf())){
            throw new CpfJaCadastradoException();
        }
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome(cadastro.nome())
                .email(cadastro.email())
                .cpf(cadastro.cpf())
                .senha(passwordEncoder.encode(cadastro.senha()))
                .status(StatusUsuario.ATIVO)
                .role(Role.USER)
                .dataCriacao(LocalDateTime.now())
                .build();
        UsuarioEntity usuarioNovo = usuarioRepository.save(usuario);

        CarteiraEntity carteira = CarteiraEntity.builder()
                .codUsuario(usuarioNovo.getCodUsuario())
                .saldo(BigDecimal.ZERO)
                .status(StatusCarteira.ATIVA)
                .dataCriacao(LocalDateTime.now())
                .build();
        CarteiraEntity carteiraSalva = carteiraRepository.save(carteira);

        UsuarioResponseDTO resposta = new UsuarioResponseDTO(
                usuarioNovo.getCodUsuario(),
                usuarioNovo.getNome(),
                usuarioNovo.getEmail(),
                carteiraSalva.getCodCarteira()
        );

        return resposta;
    }

}
