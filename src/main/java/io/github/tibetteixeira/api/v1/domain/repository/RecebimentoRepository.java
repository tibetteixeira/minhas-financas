package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Integer> {

    @Query("""
            SELECT r FROM Recebimento r\s
            INNER JOIN Usuario u on u.id = r.usuario.id\s
            WHERE r.id = :id\s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null
            """)
    Optional<Recebimento> buscarPorId(Integer id, Integer usuarioId);

    @Query("""
            SELECT r FROM Recebimento r\s
            INNER JOIN Usuario u on u.id = r.usuario.id\s
            WHERE r.descricao ilike %:descricao% \s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null\s
            ORDER BY r.descricao asc, r.dataRecebimento desc, r.id desc
            """)
    List<Recebimento> buscarPorDescricao(String descricao, Integer usuarioId);

    @Query("""
            SELECT r FROM Recebimento r\s
            INNER JOIN Usuario u on u.id = r.usuario.id\s
            WHERE u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null\s
            ORDER BY r.dataRecebimento desc, r.id desc
            """)
    List<Recebimento> buscarTodos(Integer usuarioId);
}
