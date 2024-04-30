package io.github.tibetteixeira.api.v1.domain.service.impl;

import io.github.tibetteixeira.api.v1.domain.model.DataAuditoria;
import io.github.tibetteixeira.api.v1.domain.model.Relogio;
import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.domain.repository.UsuarioRepository;
import io.github.tibetteixeira.api.v1.domain.validator.ValidadorUsuario;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.UsuarioException;
import io.github.tibetteixeira.util.UsuarioLogado;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.Month.APRIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class UsuarioServiceImplTest {

    @Mock
    private Relogio relogio;
    @Mock
    private UsuarioLogado usuarioLogado;
    @Mock
    private UsuarioRepository repository;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UsuarioServiceImpl service;

    private Usuario usuario;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        final ValidadorUsuario validador = new ValidadorUsuario(usuarioLogado);
        ReflectionTestUtils.setField(service, "validador", validador);

        when(relogio.hoje()).thenReturn(LocalDateTime.of(2024, APRIL, 29, 20, 12));
        when(usuarioLogado.getId()).thenReturn(1);
        when(encoder.encode(anyString())).thenReturn("123");

        usuario = dadoUsuario();
    }

    @Test
    public void deveriaValidarUsuarioNuloAoSalvar() {
        Exception exception = assertThrows(UsuarioException.class,
                () -> service.salvar(null)
        );

        assertThat(exception).hasMessage("Usuário não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioNomeVazioAoSalvar() {
        usuario.setNome("");

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.salvar(usuario)
        );

        assertThat(exception).hasMessage("Nome não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioSobrenomeVazioAoSalvar() {
        usuario.setSobrenome("");

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.salvar(usuario)
        );

        assertThat(exception).hasMessage("Sobrenome não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioEmailVazioAoSalvar() {
        usuario.setEmail("");

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.salvar(usuario)
        );

        assertThat(exception).hasMessage("Email não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioSenhaVazioAoSalvar() {
        usuario.setSenha("");

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.salvar(usuario)
        );

        assertThat(exception).hasMessage("Senha não pode ser vazia");
    }

    @Test
    public void deveriaValidarEmailExistenteAoSalvar() {
        when(repository.save(any(Usuario.class))).thenThrow(new RuntimeException("duplicate key value violates unique constraint \"usuario_email_key\""));


        Exception exception = assertThrows(UsuarioException.class,
                () -> service.salvar(usuario)
        );

        assertThat(exception).hasMessage("Email já cadastrado");
    }

    @Test
    public void deveriaValidarIdNuloAoAtualizar() {

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.atualizar(null, usuario)
        );

        assertThat(exception).hasMessage("Id do usuário não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioNuloAoAtualizar() {
        Exception exception = assertThrows(UsuarioException.class,
                () -> service.atualizar(1, null)
        );

        assertThat(exception).hasMessage("Usuário não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioNomeVazioAoAtualizar() {
        usuario.setNome("");

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.atualizar(1, usuario)
        );

        assertThat(exception).hasMessage("Nome não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioSobrenomeVazioAoAtualizar() {
        usuario.setSobrenome("");

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.atualizar(1, usuario)
        );

        assertThat(exception).hasMessage("Sobrenome não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioEmailVazioAoAtualizar() {
        usuario.setEmail("");

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.atualizar(1, usuario)
        );

        assertThat(exception).hasMessage("Email não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioSenhaVazioAoAtualizar() {
        usuario.setSenha("");

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.atualizar(1, usuario)
        );

        assertThat(exception).hasMessage("Senha não pode ser vazia");
    }

    @Test
    public void deveriaValidarIdIncorretoAoAtualizar() {
        usuario.setId(2);

        Exception exception = assertThrows(NotSameIdException.class,
                () -> service.atualizar(1, usuario)
        );

        assertThat(exception).hasMessage("O id da rota é diferente do id do objeto");
    }

    @Test
    public void deveriaValidarAtualizacaoDeOutroUsuario() {

        when(usuarioLogado.getId()).thenReturn(2);

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.atualizar(1, usuario)
        );

        assertThat(exception).hasMessage("Usuário logado diferente do usuário da operação");
    }

    @Test
    public void deveriaValidarEmailExistenteAoAtualzar() {

        when(repository.findById(anyInt())).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenThrow(new RuntimeException("duplicate key value violates unique constraint \"usuario_email_key\""));

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.atualizar(1, usuario)
        );

        assertThat(exception).hasMessage("Email já cadastrado");
    }

    @Test
    public void deveriaValidarIdNuloAoBuscarPorId() {
        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorId(null)
        );

        assertThat(exception).hasMessage("Id do usuário não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioInexistenteAoBuscarPorId() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Usuário não encontrado");
    }

    @Test
    public void deveriaValidarUsuarioRemovidoAoBuscarPorId() {
        usuario.getDataAuditoria().setDataExclusao(relogio.hoje());

        when(repository.findById(anyInt())).thenReturn(Optional.of(usuario));

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Usuário não encontrado");
    }

    @Test
    public void deveriaValidarBuscaDeOutroUsuario() {
        when(usuarioLogado.getId()).thenReturn(2);

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorId(1)
        );

        assertThat(exception).hasMessage("Usuário logado diferente do usuário da operação");
    }

    @Test
    public void deveriaValidarEmailNuloAoBuscarPorEmail() {
        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorEmail(null)
        );

        assertThat(exception).hasMessage("Email não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioInexistenteAoBuscarPorEmail() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorEmail("test@test.com")
        );

        assertThat(exception).hasMessage("Usuário não encontrado");
    }

    @Test
    public void deveriaValidarUsuarioRemovidoAoBuscarPorEmail() {
        usuario.getDataAuditoria().setDataExclusao(relogio.hoje());

        when(repository.findById(anyInt())).thenReturn(Optional.of(usuario));

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorEmail("test@test.com")
        );

        assertThat(exception).hasMessage("Usuário não encontrado");
    }

    @Test
    public void deveriaValidarEmailNuloAoBuscarPorEmailESenha() {
        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorEmailESenha(null, "123")
        );

        assertThat(exception).hasMessage("Email não pode ser vazio");
    }

    @Test
    public void deveriaValidarSenhaVaziaAoBuscarPorEmailESenha() {
        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorEmailESenha("test@test.com", "")
        );

        assertThat(exception).hasMessage("Senha não pode ser vazia");
    }

    @Test
    public void deveriaValidarUsuarioInexistenteAoBuscarPorEmailESenha() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorEmailESenha("test@test.com", "123")
        );

        assertThat(exception).hasMessage("Usuário não encontrado");
    }

    @Test
    public void deveriaValidarUsuarioRemovidoAoBuscarPorEmailESenha() {
        usuario.getDataAuditoria().setDataExclusao(relogio.hoje());

        when(repository.findById(anyInt())).thenReturn(Optional.of(usuario));

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorEmailESenha("test@test.com", "123")
        );

        assertThat(exception).hasMessage("Usuário não encontrado");
    }

    @Test
    public void deveriaValidarSenhaIncorretaAoBuscarPorEmailESenha() {
        when(repository.findByEmail(anyString())).thenReturn(usuario);
        when(encoder.matches(anyString(), anyString())).thenReturn(false);

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.buscarPorEmailESenha("test@test.com", "123")
        );

        assertThat(exception).hasMessage("Usuário não encontrado. Email ou senha invalidos");
    }

    @Test
    public void deveriaValidarIdNuloAoRemover() {
        Exception exception = assertThrows(UsuarioException.class,
                () -> service.remover(null)
        );

        assertThat(exception).hasMessage("Id do usuário não pode ser vazio");
    }

    @Test
    public void deveriaValidarUsuarioInexistenteAoRemover() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.remover(1)
        );

        assertThat(exception).hasMessage("Usuário não encontrado");
    }

    @Test
    public void deveriaValidarUsuarioRemovidoAoRemover() {
        usuario.getDataAuditoria().setDataExclusao(relogio.hoje());

        when(repository.findById(anyInt())).thenReturn(Optional.of(usuario));

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.remover(1)
        );

        assertThat(exception).hasMessage("Usuário não encontrado");
    }

    @Test
    public void deveriaValidarRemocaoDeOutroUsuario() {
        when(usuarioLogado.getId()).thenReturn(2);

        Exception exception = assertThrows(UsuarioException.class,
                () -> service.remover(1)
        );

        assertThat(exception).hasMessage("Usuário logado diferente do usuário da operação");
    }

    private Usuario dadoUsuario() {
        return Usuario.builder()
                .id(1)
                .nome("Nome")
                .sobrenome("Sobrenome")
                .email("email@email.com")
                .senha("123")
                .dataAuditoria(dadoDataAuditoria())
                .build();
    }

    private DataAuditoria dadoDataAuditoria() {
        return DataAuditoria.builder()
                .dataCriacao(relogio.hoje())
                .dataAtualizacao(null)
                .dataExclusao(null)
                .build();
    }
}
