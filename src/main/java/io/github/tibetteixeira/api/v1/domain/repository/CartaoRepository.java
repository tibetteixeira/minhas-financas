package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
    @Query(value = """
      select c from Cartao c\s
      inner join Usuario u on c.usuario.id = u.id\s
      where c.id = :id and u.id = :usuarioId and u.dataAuditoria.dataExclusao is null
      """)
    Optional<Cartao> findByIdAndUsuario(Integer id, Integer usuarioId);

    @Query(value = """
      select c from Cartao c\s
      inner join Usuario u on c.usuario.id = u.id\s
      where u.id = :usuarioId and u.dataAuditoria.dataExclusao is null\s
      order by c.nome, c.id
      """)
    List<Cartao> findByUsuario(Integer usuarioId);

    @Query(value = """
      select c from Cartao c\s
      inner join Usuario u on c.usuario.id = u.id\s
      where c.nome ilike %:nomeCartao% and u.id = :usuarioId and u.dataAuditoria.dataExclusao is null\s
      order by c.nome, c.id
      """)
    List<Cartao> findByUsuarioAndNome(Integer usuarioId, String nomeCartao);

}
