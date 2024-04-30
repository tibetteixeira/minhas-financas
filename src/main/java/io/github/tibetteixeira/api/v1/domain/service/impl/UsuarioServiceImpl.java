package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.repository.UsuarioRepository;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorUsuario;
import io.github.tibetteixeira.api.v1.exception.UsuarioException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.EMAIL_JA_CADASTRADO;
import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.USUARIO_NAO_ENCONTRADO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final ValidadorUsuario validador;
    private final Relogio relogio;

    private static final String USUARIO_EMAIL_KEY = "usuario_email_key";

    @Override
    public void salvar(Usuario usuario) {
        validador.validar(usuario);
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuario.getDataAuditoria().setDataCriacao(relogio.hoje());

        try {
            repository.save(usuario);
        } catch (Exception e) {
            if (e.getMessage().contains(USUARIO_EMAIL_KEY))
                throw new UsuarioException(EMAIL_JA_CADASTRADO);
        }
    }

    @Override
    public void atualizar(Integer id, Usuario usuario) {
        validador.validar(usuario, id);
        Usuario usuarioDaBase = buscarPorId(id);

        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuario.setDataAuditoria(usuarioDaBase.getDataAuditoria());
        usuario.getDataAuditoria().setDataAtualizacao(relogio.hoje());

        try {
            repository.save(usuario);
        } catch (Exception e) {
            if (e.getMessage().contains(USUARIO_EMAIL_KEY))
                throw new UsuarioException(EMAIL_JA_CADASTRADO);
        }
    }

    @Override
    public void remover(Integer id) {
        validador.validar(id);
        Usuario usuario = buscarPorId(id);
        usuario.setEmail("");
        usuario.setSenha("");
        usuario.getDataAuditoria().setDataExclusao(relogio.hoje());

        repository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        validador.validar(id);
        Usuario usuario = repository.findById(id).orElse(null);

        if (isNull(usuario) || nonNull(usuario.getDataAuditoria().getDataExclusao()))
            throw new UsuarioException(USUARIO_NAO_ENCONTRADO);

        return usuario;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        validador.validarEmail(email);

        Usuario usuario = repository.findByEmail(email);

        if (isNull(usuario) || nonNull(usuario.getDataAuditoria().getDataExclusao()))
            throw new UsuarioException(USUARIO_NAO_ENCONTRADO);

        return usuario;
    }

    @Override
    public Usuario buscarPorEmailESenha(String email, String senha) {
        validador.validarEmailSenha(email, senha);
        Usuario usuario = buscarPorEmail(email);

        if (encoder.matches(senha, usuario.getSenha()))
            return usuario;

        throw new UsuarioException(USUARIO_NAO_ENCONTRADO);
    }
}
