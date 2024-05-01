package io.github.tibetteixeira.api.configuration.security.service;

import io.github.tibetteixeira.api.configuration.security.repository.TokenRepository;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.configuration.security.model.TipoToken;
import io.github.tibetteixeira.api.configuration.security.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository repository;

    public void salvar(String chave, Usuario usuario) {
        Token token = Token.builder()
                .chave(chave)
                .expirado(false)
                .revogado(false)
                .tipo(TipoToken.BEARER)
                .usuario(usuario)
                .build();

        repository.save(token);
    }

    public Token obterToken(String chave) {
        return repository.findByChave(chave).orElse(null);
    }

    public boolean tokenValido(String chave) {
        Token tokenDaBase = repository.findByChave(chave).orElse(null);

        return nonNull(tokenDaBase) && tokenDaBase.valido();
    }

    public void inativar(String chave) {
        Token token = obterToken(chave);
        inativar(token);
    }

    public void inativar(Token token) {
        if (isNull(token))
            return;

        token.setExpirado(Boolean.TRUE);
        token.setRevogado(Boolean.TRUE);

        repository.save(token);
    }

    public void inativarTodosOsTokenDoUsuario(Usuario usuario) {
        repository.inativarTodosOsTokenDoUsuario(usuario.getId());
    }

}
