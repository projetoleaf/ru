package com.github.projetoleaf.service;

import java.util.List;

import com.github.projetoleaf.beans.Tipo;

public interface TipoService {
	
	List<Tipo> listar();

	Tipo buscar(Integer id);
	
    Tipo incluir(Tipo tipo);
    
    void excluir(Integer id);
}
