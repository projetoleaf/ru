package com.github.projetoleaf.service;

import java.util.List;

import com.github.projetoleaf.beans.Usuario;

public interface UsuarioService {

	Usuario buscar(Integer id);
	
    Usuario salvar(Usuario usuario);

}
