package com.ecowork.security;

import com.ecowork.exception.NotFoundException;
import com.ecowork.models.Usuario;
import com.ecowork.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final UsuarioRepository usuarioRepository;

    public Usuario getUsuarioLogado() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new NotFoundException("Nenhum usuário autenticado.");
        }

        String email = auth.getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }
}