package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Integer> {

    @Query(value = """
            SELECT g FROM Gasto g\s
            INNER JOIN Usuario u ON u.id = g.usuario.id\s
            WHERE u.id = :usuarioId AND u.dataAuditoria.dataExclusao IS NULL AND g.id = :id
            """)
    Optional<Gasto> buscarPorId(Integer id, Integer usuarioId);

    @Query(value = """
            SELECT g FROM Gasto g\s
            INNER JOIN Usuario u ON u.id = g.usuario.id\s
            WHERE u.id = :usuarioId AND u.dataAuditoria.dataExclusao IS NULL AND g.categoria.id = :categoriaId AND g.fatura IS NULL\s
            ORDER BY dataGasto DESC, g.id DESC
            """)
    List<Gasto> buscarPorCategoria(Integer categoriaId, Integer usuarioId);

    @Query(value = """
            SELECT g FROM Gasto g\s
            INNER JOIN Usuario u ON u.id = g.usuario.id\s
            WHERE YEAR(g.dataGasto) = :ano AND MONTH(g.dataGasto) = :mes AND g.fatura IS NULL\s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao IS NULL\s
            ORDER BY dataGasto DESC, g.id DESC
            """)
    List<Gasto> buscarPorData(Integer ano, Integer mes, Integer usuarioId);

    @Query(value = """
            SELECT g FROM Gasto g\s
            INNER JOIN Usuario u ON u.id = g.usuario.id
            WHERE u.id = :usuarioId AND u.dataAuditoria.dataExclusao IS NULL AND g.fatura IS NULL\s
            ORDER BY dataGasto DESC, g.id DESC
            """)
    List<Gasto> buscarTodos(Integer usuarioId);
}
