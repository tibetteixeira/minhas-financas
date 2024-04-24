package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.repository.UsuarioRepository;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void salvar(Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuario.getDataAuditoria().setDataCriacao(new Date());

        repository.save(usuario);
    }

    @Override
    public void atualizar(Integer id, Usuario usuario) {
        Usuario usuarioDaBase = buscarPorId(id);

        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuario.setDataAuditoria(usuarioDaBase.getDataAuditoria());
        usuario.getDataAuditoria().setDataAtualizacao(new Date());

        repository.save(usuario);
    }

    @Override
    public void remover(Integer id) {
        Usuario usuario = buscarPorId(id);
        usuario.getDataAuditoria().setDataExclusao(new Date());

        repository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        Usuario usuario = repository.findById(id).orElse(null);

        if (isNull(usuario) || nonNull(usuario.getDataAuditoria().getDataExclusao()))
            throw new NotFoundException(ExceptionMessage.USUARIO_NAO_ENCONTRADO);

        return usuario;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        Usuario usuario = repository.findByEmail(email);

        if (isNull(usuario) || nonNull(usuario.getDataAuditoria().getDataExclusao()))
            throw new NotFoundException(ExceptionMessage.USUARIO_NAO_ENCONTRADO);

        return usuario;
    }

    @Override
    public Usuario buscarPorEmailESenha(String email, String senha) {
        Usuario usuario = buscarPorEmail(email);

        if (encoder.matches(senha, usuario.getSenha()))
            return usuario;

        throw new NotFoundException(ExceptionMessage.USUARIO_NAO_ENCONTRADO);
    }
}
