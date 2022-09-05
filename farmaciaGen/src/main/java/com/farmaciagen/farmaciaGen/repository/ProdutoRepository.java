package com.farmaciagen.farmaciaGen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmaciagen.farmaciaGen.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	public List<Produto> findAllByMedicamentoContainingIgnoreCase (String medicamento);


}
