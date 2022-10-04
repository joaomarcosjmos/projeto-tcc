package com.uva.moneyapi.repository;

import com.uva.moneyapi.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value = "select * from lancamento l " +
                        "join pessoa p on p.codigo = l.codigo_pessoa " +
                        "join categoria c on c.codigo = l.codigo_categoria " +
                    "where l.codigo = :id", nativeQuery = true)
    Lancamento findFullLancamentoByID(Long id);

}
