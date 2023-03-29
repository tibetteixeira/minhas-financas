package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    List<Pagamento> findByFatura(Fatura fatura);

    @Query(
            value = "SELECT P FROM Pagamento P WHERE YEAR(P.dataPagamento) = :ano AND MONTH(P.dataPagamento) = :mes AND P.fatura is null"
    )
    List<Pagamento> findByAnoAndMesAndFaturaIsNull(Integer ano, Integer mes);
}
