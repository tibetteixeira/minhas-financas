package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaGasto;
import io.github.tibetteixeira.api.v1.domain.model.Fatura;
import io.github.tibetteixeira.api.v1.domain.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Integer> {

    List<Gasto> findByCategoria(CategoriaGasto categoriaGasto);

    List<Gasto> findByFatura(Fatura fatura);

    @Query(
            value = "SELECT G FROM Gasto G WHERE YEAR(G.dataGasto) = :ano AND MONTH(G.dataGasto) = :mes AND G.fatura is null"
    )
    List<Gasto> findByAnoAndMesAndFaturaIsNull(Integer ano, Integer mes);
}
