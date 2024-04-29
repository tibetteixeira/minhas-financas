package io.github.tibetteixeira.configuration.security.repository;

import io.github.tibetteixeira.configuration.security.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
