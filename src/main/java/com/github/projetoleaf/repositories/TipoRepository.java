package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.Tipo;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Integer> {

}
