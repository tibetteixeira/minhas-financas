package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO;
import io.github.tibetteixeira.api.v1.domain.model.enums.StatusPagamentoFatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Integer> {

    @Query("""
            SELECT f FROM Fatura f\s
            INNER JOIN Usuario u on u.id = f.usuario.id\s
            WHERE f.id = :id\s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null
            """)
    Optional<Fatura> buscarPorId(Integer id, Integer usuarioId);

    @Query("""
            SELECT f FROM Fatura f\s
            INNER JOIN Usuario u on u.id = f.usuario.id\s
            WHERE f.cartao.id = :cartaoId\s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null
            """)
    List<Fatura> buscarPorCartao(Integer cartaoId, Integer usuarioId);

    @Query("""
            SELECT f FROM Fatura f\s
            INNER JOIN Usuario u on u.id = f.usuario.id\s
            WHERE u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null
            """)
    List<Fatura> buscarTodas(Integer usuarioId);

    @Query("""
            SELECT f FROM Fatura f\s
            INNER JOIN Usuario u on u.id = f.usuario.id\s
            WHERE f.cartao.id = :cartaoId AND MONTH(dataVencimento) = :mes AND YEAR(dataVencimento) = :ano\s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null
            """)
    Optional<Fatura> buscarPorCartaoMesAno(Integer cartaoId, Integer mes, Integer ano, Integer usuarioId);

    @Query("""
            SELECT new io.github.tibetteixeira.api.v1.domain.model.dto.FaturaDTO(f.id, f.valorPago, SUM(g.valor))\s
            FROM Fatura f\s
            LEFT JOIN Gasto g on g.fatura.id = f.id\s
            WHERE f.id = :id\s
            GROUP BY f.id, f.valorPago
            """)
    FaturaDTO buscarEstadoAtual(Integer id);


    @Query("SELECT f FROM Fatura f WHERE f.status in :status")
    List<Fatura> buscarPorStatus(StatusPagamentoFatura... status);
}
