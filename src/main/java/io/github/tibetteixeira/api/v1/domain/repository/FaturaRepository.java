package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Integer> {

    @Query("""
            SELECT f FROM Fatura f\s
            INNER JOIN Cartao c on c.id = f.cartao.id\s
            INNER JOIN Usuario u on u.id = c.usuario.id\s
            WHERE f.id = :id\s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null
            """)
    Optional<Fatura> buscarPorId(Integer id, Integer usuarioId);

    @Query("""
            SELECT f FROM Fatura f\s
            INNER JOIN Cartao c on c.id = f.cartao.id\s
            INNER JOIN Usuario u on u.id = c.usuario.id\s
            WHERE c.id = :cartaoId\s
            AND u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null
            """)
    List<Fatura> buscarPorCartao(Integer cartaoId, Integer usuarioId);

    @Query("""
            SELECT f FROM Fatura f\s
            INNER JOIN Cartao c on c.id = f.cartao.id\s
            INNER JOIN Usuario u on u.id = c.usuario.id\s
            WHERE u.id = :usuarioId AND u.dataAuditoria.dataExclusao is null
            """)
    List<Fatura> buscarTodas(Integer usuarioId);

    @Query("""
            SELECT f FROM Fatura f\s
            INNER JOIN Cartao c on c.id = f.cartao.id\s
            WHERE c.id = :cartaoId AND MONTH(dataVencimento) = :mes AND YEAR(dataVencimento) = :ano\s
            """)
    Optional<Fatura> buscarPorCartaoMesAno(Integer cartaoId, Integer mes, Integer ano);
}
