package com.github.projetoleaf.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.github.projetoleaf.repositories.TipoRepository;
import com.github.projetoleaf.repositories.CursoRepository;
import com.github.projetoleaf.repositories.UsuarioRepository;
import com.github.projetoleaf.beans.Usuario;
import javax.validation.Valid;
import org.springframework.context.MessageSource;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TipoRepository tipoRepository;
	
	@Autowired
    private MessageSource config;
	
	Logger log = Logger.getLogger(UsuarioController.class);
	
	@GetMapping("/cadastro")
	public String cadastro(Model model) {
		model.addAttribute("cursos", cursoRepository.findAll());
		model.addAttribute("tipos", tipoRepository.findAll());
		model.addAttribute("usuario", new Usuario());		
		return "cadastro";
	}	
	 
	@PostMapping("/salvar")
    public String salvarUsuario(Model model, @ModelAttribute @Valid Usuario usuario, BindingResult result) {
		try {
            if (!result.hasErrors()) {
                Usuario usuarioAtualizado = usuarioRepository.save(usuario);
                log.info(usuarioAtualizado.toString() + " gravado com sucesso");
                model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o usuário" }, null));
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
        
        return "redirect:/cadastro";

    }
	
}