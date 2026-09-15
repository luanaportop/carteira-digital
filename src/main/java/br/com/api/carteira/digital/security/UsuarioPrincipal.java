package br.com.api.carteira.digital.security;

import br.com.api.carteira.digital.model.UsuarioEntity;
import br.com.api.carteira.digital.util.enums.Role;
import br.com.api.carteira.digital.util.enums.StatusUsuario;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioPrincipal implements UserDetails {

    private final UsuarioEntity usuario;

    public UsuarioPrincipal(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public Long getCodUsuario(){
        return usuario.getCodUsuario();
    }

    public String getEmail(){
        return usuario.getEmail();
    }

    public Role getRole(){
        return usuario.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + usuario.getRole().name()
                )
        );
    }

    @Override
    public @Nullable String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return usuario.getStatus() != StatusUsuario.BLOQUEADO;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getStatus() == StatusUsuario.ATIVO;
    }
}
