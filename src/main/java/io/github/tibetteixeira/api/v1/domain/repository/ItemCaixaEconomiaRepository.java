package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.ItemCaixaEconomia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCaixaEconomiaRepository extends JpaRepository<ItemCaixaEconomia, Integer> {


    @Query(value = """
            select i from ItemCaixaEconomia i\s
            inner join CaixaEconomia c on c.id = i.caixa.id\s
            inner join Usuario u on c.usuario.id = u.id\s
            where u.id = :usuarioId and u.dataAuditoria.dataExclusao is null and i.id = :id
            """)
    Optional<ItemCaixaEconomia> buscarPorId(Integer id, Integer usuarioId);

    @Query(value = """
            select i from ItemCaixaEconomia i\s
            inner join CaixaEconomia c on c.id = i.caixa.id\s
            inner join Usuario u on c.usuario.id = u.id\s
            where u.id = :usuarioId and u.dataAuditoria.dataExclusao is null and c.id = :caixaId\s
            order by c.nome, c.id
            """)
    List<ItemCaixaEconomia> buscarPorCaixaEconomia(Integer caixaId, Integer usuarioId);
}
