package com.github.projetoleaf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.projetoleaf.beans.Usuario;
import com.github.projetoleaf.dto.UsuarioDTO;
import com.github.projetoleaf.service.UsuarioService;
import com.github.projetoleaf.service.CursoService;
import com.github.projetoleaf.service.TipoService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
@RequestMapping("/desktop")
public class CadastroController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private TipoService tipoService;
	
	@GetMapping("/cadastro")
	public String cadastro(Model model) {
		model.addAttribute("cursos", cursoService.listar());
		model.addAttribute("tipos", tipoService.listar());
		model.addAttribute("usuario", new UsuarioDTO());
		return "cadastro";
	}
	
	
	 
	@PostMapping(value="/salvar")
    public String salvar(@ModelAttribute UsuarioDTO usuarioDTO, BindingResult result) {
        Usuario usuario;

        if (usuarioDTO.getId() != null) {
            usuario = usuarioService.buscar(usuarioDTO.getId());
        } else {
            usuario = new Usuario();
        }
        
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setMatricula(usuarioDTO.getMatricula());
        usuario.setTipo(usuarioDTO.getTipo());
        usuario.setCurso(usuarioDTO.getCurso());
        usuario.setData_nascimento(usuarioDTO.getData_nascimento());
        usuario.setExcluido("n");

        usuarioService.salvar(usuario);
        
        return "redirect:/desktop/login";

    }
	
}
