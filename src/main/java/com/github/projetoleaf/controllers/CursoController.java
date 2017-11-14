package com.github.projetoleaf.controllers;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import lombok.extern.slf4j.Slf4j;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@Slf4j
@Controller
@RequestMapping("/cursos")
public class CursoController {

	@Autowired
	private MessageSource config;

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

	@PostMapping("/salvar")
	public String salvarCurso(Model model, @ModelAttribute("curso") @Valid Curso curso, BindingResult result) {
		try {
			if (!result.hasErrors()) {
				Curso cursoAtualizado = repository.save(curso);
				log.info(cursoAtualizado.toString() + " gravada com sucesso");
				model.addAttribute("mensagemInfo",
						config.getMessage("gravadoSucesso", new Object[] { "o curso" }, null));
			}
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/cursos";
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