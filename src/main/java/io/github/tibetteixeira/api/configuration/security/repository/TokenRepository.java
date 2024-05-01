package io.github.tibetteixeira.api.configuration.security.repository;

import io.github.tibetteixeira.api.configuration.security.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    @Query(value = """
      select t from Token t inner join Usuario u\s
      on t.usuario.id = u.id\s
      where u.id = :usuarioId and (t.expirado = false and t.revogado = false)
      """)
    List<Token> findAllValidTokenByUser(Integer usuarioId);

    Optional<Token> findByChave(String chave);

    @Modifying
    @Query(value = """
      update Token t set t.expirado = true, t.revogado = true\s
      where t.usuario.id = :usuarioId
      """)
    void inativarTodosOsTokenDoUsuario(@Param("usuarioId") Integer usuarioId);
}
