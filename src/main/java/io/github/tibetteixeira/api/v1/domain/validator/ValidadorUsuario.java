package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.UsuarioException;
import io.github.tibetteixeira.util.UsuarioLogado;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class ValidadorUsuario {

    private final UsuarioLogado usuarioLogado;

    public void validar(Integer id) {
        if (isNull(id))
            throw new UsuarioException(ID_DO_USUARIO_NAO_PODE_SER_VAZIO);

        if (isFalse(usuarioLogado.getId().equals(id)))
            throw new UsuarioException(USUARIO_LOGADO_DIFERENTE_DO_USUARIO_DA_OPERACAOO);
    }

    public void validar(Usuario usuario) {
        if (isNull(usuario))
            throw new UsuarioException(USUARIO_NAO_PODE_SER_VAZIO);

        if (isEmpty(usuario.getNome()))
            throw new UsuarioException(NOME_NAO_PODE_SER_VAZIO);

        if (isEmpty(usuario.getSobrenome()))
            throw new UsuarioException(SOBRENOME_NAO_PODE_SER_VAZIO);

        validarEmail(usuario.getEmail());
        validarSenha(usuario.getSenha());
    }

    public void validar(Usuario usuario, Integer id) {
        validar(id);
        validar(usuario);

        if (BooleanUtils.isFalse(id.equals(usuario.getId())))
            throw new NotSameIdException(ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);
    }

    public void validarEmail(String email) {
        if (isEmpty(email))
            throw new UsuarioException(EMAIL_NAO_PODE_SER_VAZIO);
    }

    public void validarSenha(String senha) {
        if (isEmpty(senha))
            throw new UsuarioException(SENHA_NAO_PODE_SER_VAZIA);
    }

    public void validarEmailSenha(String email, String senha) {
        validarEmail(email);
        validarSenha(senha);
    }
}
