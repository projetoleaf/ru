package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCurso;

@Repository
public interface ClienteCursoRepository extends JpaRepository<ClienteCurso, Long> {

	ClienteCurso findByCliente(Cliente cliente);
}
