package com.github.projetoleaf.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.projetoleaf.beans.Curso;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.CursoRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@RequestMapping("/cursos")
public class CursoController {

	@Autowired
	private CursoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping
	public String pesquisarCurso(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
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

	@PostMapping("/verificar")
	public @ResponseBody String verificarDescricaoCurso(@ModelAttribute("curso") Curso curso) throws JSONException {
		JSONObject json = new JSONObject();
		
		if (repository.findByDescricao(curso.getDescricao()) != null) {		
			json.put("erro", "descricao");	
		} else {
			repository.save(curso);
			json.put("sucesso", new Boolean(true));
		}			
		
		return json.toString();
	}

	@PostMapping("/excluir")
	public @ResponseBody String excluirCurso(RedirectAttributes ra, @RequestParam("descricao") String descricao) throws JSONException {
		JSONObject json = new JSONObject();
		
		try {
			Long id = repository.findByDescricao(descricao).getId();	
			repository.delete(id);
			json.put("sucesso", new Boolean(true));
		} catch (Exception ex) {
			json.put("erro", "exclusao");
		}
		
		return json.toString();
	}
}