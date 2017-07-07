package com.github.projetoleaf.service;

import java.util.List;
import com.github.projetoleaf.data.Curso;

public interface CursoService {
	
	List<Curso> listar();

	Curso buscar(Integer id);
	
	Curso incluir(Curso curso);
    
    void excluir(Integer id);
}
