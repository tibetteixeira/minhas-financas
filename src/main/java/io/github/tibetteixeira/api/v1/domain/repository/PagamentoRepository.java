package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    @Query("""
            SELECT p FROM Pagamento p\s
            INNER JOIN Fatura f on f.id = p.fatura.id\s
            INNER JOIN Cartao c on c.id = f.cartao.id\s
            INNER JOIN Usuario u on u.id = c.usuario.id\s
            WHERE p.id = :id\s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null
            """)
    Optional<Pagamento> buscarPorId(Integer id, Integer usuarioId);

    @Query("""
            SELECT p FROM Pagamento p\s
            INNER JOIN Fatura f on f.id = p.fatura.id\s
            INNER JOIN Cartao c on c.id = f.cartao.id\s
            INNER JOIN Usuario u on u.id = c.usuario.id\s
            WHERE f.id = :faturaId\s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null\s
            ORDER BY p.fatura.id desc, p.dataPagamento desc, p.id desc
            """)
    List<Pagamento> buscarPorFatura(Integer faturaId, Integer usuarioId);

    @Query("""
            SELECT p FROM Pagamento p\s
            INNER JOIN Fatura f on f.id = p.fatura.id\s
            INNER JOIN Cartao c on c.id = f.cartao.id\s
            INNER JOIN Usuario u on u.id = c.usuario.id\s
            WHERE u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null\s
            ORDER BY p.fatura.id desc, p.dataPagamento desc, p.id desc
            """)
    List<Pagamento> buscarTodos(Integer usuarioId);
}
