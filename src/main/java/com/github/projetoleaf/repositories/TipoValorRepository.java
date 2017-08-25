package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.TipoValor;

@Repository
public interface TipoValorRepository extends JpaRepository<TipoValor, Long> {

}
