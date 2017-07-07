package com.github.projetoleaf.service;

import com.github.projetoleaf.beans.Tipo;
import com.github.projetoleaf.beans.Usuario;
import com.github.projetoleaf.repositories.UsuarioRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    
  

    @Override
    @Transactional(readOnly = true)
    public Usuario buscar(Integer id) {
        return usuarioRepository.findOne(id);
    }
    
    @Override
    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
