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

@Controller
@RequestMapping("/desktop")
public class DesktopController {
	
	@GetMapping("/apresentacao")
	public String apresentacao() {
		return "apresentacao";
	}
	
	@GetMapping("/valorrefeicoes")
	public String valorrefeicoes() {
		return "valorrefeicoes";
	}
	
	@GetMapping("/cardapio")
	public String cardapio() {
		return "cardapio";
	}
	
	@GetMapping("/noticias")
	public String noticias() {
		return "noticias";
	}
	
	@GetMapping("/contato")
	public String contato() {
		return "contato";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/iniciousuario")
	public String iniciousuario() {
		return "iniciousuario";
	}
	
	@GetMapping("/conta")
	public String conta() {
		return "conta";
	}
	
	@GetMapping("/historicorefeicoes")
	public String historicorefeicoes() {
		return "historicorefeicoes";
	}
	
	@GetMapping("/reservarefeicoes")
	public String reservarefeicoes() {
		return "reservarefeicoes";
	}
	
	@GetMapping("/feriados")
	public String feriados() {
		return "feriados";
	}
	
	@GetMapping("/inicioadmin")
	public String inicioadmin() {
		return "inicioadmin";
	}
	
	@GetMapping("/sobre")
	public String sobre() {
		return "sobre";
	}
	
	@GetMapping("/comunicados")
	public String comunicados() {
		return "comunicados";
	}
	
	@GetMapping("/transferencias")
	public String transferencias() {
		return "transferencias";
	}
	
	@GetMapping("/categorias")
	public String categorias() {
		return "categorias";
	}
	
	@GetMapping("/cursos")
	public String cursos() {
		return "cursos";
	}
	
	@GetMapping("/tipos")
	public String tipos() {
		return "tipos";
	}
	
	@GetMapping("/cadastro")
	public String cadastro(Model model) {
		model.addAttribute("usuario", new UsuarioDTO());
		return "cadastro";
	}
	
	@Autowired
	private UsuarioService usuarioService;
	 
	@PostMapping(value="/salvar")
    public String salvar(@ModelAttribute("usuario") UsuarioDTO usuarioDTO, BindingResult result) {
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
        
        System.out.println(usuarioDTO.getCpf());
        System.out.println(usuarioDTO.getEmail());
        System.out.println(usuarioDTO.getSenha());
        System.out.println(usuarioDTO.getNome());
        System.out.println(usuarioDTO.getMatricula());
        System.out.println(usuarioDTO.getId_tipo());
        System.out.println(usuarioDTO.getId_curso());
        System.out.println(usuarioDTO.getData_nascimento());

        usuarioService.salvar(usuario);
        
        return "redirect:/login";

    }
}
