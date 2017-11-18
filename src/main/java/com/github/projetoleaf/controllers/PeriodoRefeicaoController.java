package com.github.projetoleaf.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.projetoleaf.beans.PeriodoRefeicao;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.PeriodoRefeicaoRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@Controller
@RequestMapping("/periodosRefeicoes")
public class PeriodoRefeicaoController {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PeriodoRefeicaoRepository repository;
	
	@GetMapping
	public String pesquisarPeriodoRefeicao(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		model.addAttribute("listagemPeriodos", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/periodosRefeicoes/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirPeriodoRefeicao(Model model) {
		model.addAttribute("periodoRefeicao", new PeriodoRefeicao());
		return abrirCadastroPeriodoRefeicao(model);
	}

	@GetMapping("/editar/{id}")
	public String editarPeriodoRefeicao(@PathVariable Long id, Model model) {
		PeriodoRefeicao periodoRefeicao = repository.findOne(id);
		model.addAttribute("periodoRefeicao", periodoRefeicao);
		return abrirCadastroPeriodoRefeicao(model);
	}

	public String abrirCadastroPeriodoRefeicao(Model model) {
		return "/periodosRefeicoes/cadastro";
	}

	@PostMapping("/verificar")
	public @ResponseBody String verificarDescricao(@ModelAttribute("periodoRefeicao") PeriodoRefeicao periodoRefeicao) throws JSONException {
		JSONObject json = new JSONObject();
		
		if (repository.findByDescricao(periodoRefeicao.getDescricao()) != null) {		
			json.put("erro", "descricao");	
		} else {
			repository.save(periodoRefeicao);
			json.put("sucesso", new Boolean(true));
		}			
		
		return json.toString();
	}

	@PostMapping("/excluir")
	public @ResponseBody String excluirPeriodoRefeicao(RedirectAttributes ra, @RequestParam("descricao") String descricao) throws JSONException {
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