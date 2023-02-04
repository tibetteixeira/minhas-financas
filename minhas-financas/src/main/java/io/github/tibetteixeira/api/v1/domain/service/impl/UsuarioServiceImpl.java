package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.exception.UsuarioException;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.repository.UsuarioRepository;
import io.github.tibetteixeira.api.v1.domain.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;
    private PasswordEncoder encoder;

    public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void salvar(Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuario.getDataAuditoria().setDataCriacao(new Date());

        repository.save(usuario);
    }

    @Override
    public void atualizar(Integer id, Usuario usuario) {
        if (isFalse(id.equals(usuario.getId())))
            throw new UsuarioException("Id do usuário diferente do id da Url!");

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
            throw new UsuarioException(String.format("Usuário com id %d não foi encontrado.", id));

        return usuario;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        Usuario usuario = repository.findByEmail(email);

        if (isNull(usuario) || nonNull(usuario.getDataAuditoria().getDataExclusao()))
            throw new UsuarioException("Usuário não foi encontrado.");

        return usuario;
    }

    @Override
    public Usuario buscarPorEmailESenha(String email, String senha) {

        Usuario usuario = buscarPorEmail(email);

        if (encoder.matches(senha, usuario.getSenha()))
            return usuario;

        throw new UsuarioException("Usuário não foi encontrado.");
    }
}
