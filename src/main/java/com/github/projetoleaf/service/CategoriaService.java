package com.github.projetoleaf.service;

import java.util.List;
import com.github.projetoleaf.data.Categoria;

public interface CategoriaService {
	
	List<Categoria> listar();

	Categoria buscar(Integer id);
	
	Categoria incluir(Categoria tipo);
    
    void excluir(Integer id);
}
