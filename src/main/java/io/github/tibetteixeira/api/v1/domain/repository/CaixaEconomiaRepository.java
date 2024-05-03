package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaixaEconomiaRepository extends JpaRepository<CaixaEconomia, Integer> {

    @Query(value = """
            select c from CaixaEconomia c\s
            inner join Usuario u on c.usuario.id = u.id\s
            where u.id = :usuarioId and u.dataAuditoria.dataExclusao is null and c.id = :id
            """)
    Optional<CaixaEconomia> buscarPorIdEUsuario(Integer id, Integer usuarioId);

    @Query(value = """
            select c from CaixaEconomia c\s
            inner join Usuario u on c.usuario.id = u.id\s
            where u.id = :usuarioId and u.dataAuditoria.dataExclusao is null and c.nome ilike %:nome%\s
            order by c.nome, c.id
            """)
    List<CaixaEconomia> buscarPorNomeEUsuario(String nome, Integer usuarioId);

    @Query(value = """
            select c from CaixaEconomia c\s
            inner join Usuario u on c.usuario.id = u.id\s
            where u.id = :usuarioId and u.dataAuditoria.dataExclusao is null\s
            order by c.nome, c.id
            """)
    List<CaixaEconomia> buscarPorUsuario(Integer usuarioId);


}
