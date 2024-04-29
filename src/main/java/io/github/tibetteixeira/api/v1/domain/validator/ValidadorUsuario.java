package io.github.tibetteixeira.api.v1.domain.validator;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import io.github.tibetteixeira.api.v1.exception.ExceptionMessage;
import io.github.tibetteixeira.api.v1.exception.NotSameIdException;
import io.github.tibetteixeira.api.v1.exception.UsuarioException;
import io.github.tibetteixeira.util.UsuarioLogado;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import static io.github.tibetteixeira.api.v1.exception.ExceptionMessage.USUARIO_LOGADO_DIFERENTE_DO_USUARIO_DA_OPERACAOO;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
public class ValidadorUsuario {

    public void validar(Integer id) {
        if (isFalse(UsuarioLogado.getId().equals(id)))
            throw new UsuarioException(USUARIO_LOGADO_DIFERENTE_DO_USUARIO_DA_OPERACAOO);
    }

    public void validar(Usuario usuario) {
        if (isNull(usuario))
            throw new UsuarioException("Usuário não pode ser vazio");

        if (isEmpty(usuario.getNome()))
            throw new UsuarioException("Nome não pode ser vazio");

        if (isEmpty(usuario.getSobrenome()))
            throw new UsuarioException("Sobrenome não pode ser vazio");

        if (isEmpty(usuario.getEmail()))
            throw new UsuarioException("Email não pode ser vazio");

        if (isEmpty(usuario.getSenha()))
            throw new UsuarioException("Senha não pode ser vazia");
    }

    public void validar(Usuario usuario, Integer id) {
        if (BooleanUtils.isFalse(id.equals(usuario.getId())))
            throw new NotSameIdException(ExceptionMessage.ID_ROTA_DIFERENTE_ID_OBJETO);

        validar(id);
        validar(usuario);
    }
}
