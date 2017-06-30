package com.github.projetoleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.projetoleaf.data.Usuario;
import com.github.projetoleaf.dto.UsuarioDTO;
import com.github.projetoleaf.service.UsuarioService;
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
	
	@GetMapping("/cadastro")
	public String cadastro(Model model) {
		model.addAttribute("usuario", new UsuarioDTO());
		return "cadastro";
	}
	
	@Autowired
	private UsuarioService usuarioService;
	 
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
        usuario.setId_tipo(usuarioDTO.getId_tipo());
        usuario.setId_curso(usuarioDTO.getId_curso());
        usuario.setData_nascimento(usuarioDTO.getData_nascimento());
        usuario.setExcluido("n");

        usuarioService.salvar(usuario);
        
        return "redirect:/desktop/login";

    }
	
}
