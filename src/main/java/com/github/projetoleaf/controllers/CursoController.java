package com.github.projetoleaf.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.github.projetoleaf.beans.Curso;
import com.github.projetoleaf.repositories.CursoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cursos")
public class CursoController {
	
	@Autowired
    private CursoRepository repository;
	
	@Autowired
    private MessageSource config;
	
	@GetMapping
	public String pesquisarCurso(Model model) {
		model.addAttribute("listagemCursos", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/cursos/pesquisar";
	}
	
	@GetMapping("/incluir")
	public String incluirCurso(Model model) {
		model.addAttribute("curso", new Curso());
		return abrirCadastroCurso(model);
	}
	
	@GetMapping("/editar/{id}")
    public String editarCurso(@PathVariable Long id, Model model) {
        Curso curso = repository.findOne(id);
        model.addAttribute("curso", curso);
        return abrirCadastroCurso(model);
    }
	
	public String abrirCadastroCurso(Model model) {
        return "/cursos/cadastro";
    }
	
	@PostMapping("/salvar")
    public String salvarCurso(Model model, @ModelAttribute("curso") @Valid Curso curso, BindingResult result) {
		try {
            if (!result.hasErrors()) {
                Curso cursoAtualizado = repository.save(curso);
                log.info(cursoAtualizado.toString() + " gravada com sucesso");
                model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o curso" }, null));
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
        
		return abrirCadastroCurso(model);

    }
	
	@GetMapping("/excluir/{id}")
    public String excluirCurso(RedirectAttributes ra, @PathVariable Long id) {		
		try {
            repository.delete(id);
            log.info("Curso #" + id + " excluído com sucesso");
            ra.addFlashAttribute("mensagemInfo", config.getMessage("excluidoSucesso", new Object[] { "o curso" }, null));
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }

        return "redirect:/cursos";
    }
	
}