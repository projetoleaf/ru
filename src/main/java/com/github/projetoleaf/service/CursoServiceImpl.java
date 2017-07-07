package com.github.projetoleaf.service;

import com.github.projetoleaf.data.Curso;
import com.github.projetoleaf.repository.CursoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    CursoRepository cursoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Curso buscar(Integer id) {
        return cursoRepository.findOne(id);
    }
    
    @Override
    @Transactional
    public Curso incluir(Curso curso) {
        return cursoRepository.save(curso);
    }
    
    @Override
    @Transactional
    public void excluir(Integer id) {
    	cursoRepository.delete(id);
    }
}