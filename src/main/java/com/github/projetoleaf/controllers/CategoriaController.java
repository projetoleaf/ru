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

import com.github.projetoleaf.beans.Categoria;
import com.github.projetoleaf.repositories.CategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@Controller
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping
	public String pesquisarCategoria(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		model.addAttribute("listagemCategorias", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/categorias/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return abrirCadastroCategoria(model);
	}

	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable Long id, Model model) {
		Categoria categoria = repository.findOne(id);

		model.addAttribute("categoria", categoria);

		return abrirCadastroCategoria(model);
	}

	public String abrirCadastroCategoria(Model model) {
		return "/categorias/cadastro";
	}
	
	@PostMapping("/verificar")
	public @ResponseBody String verificarDescricaoCategoria(@ModelAttribute("categoria") Categoria categoria) throws JSONException {
		JSONObject json = new JSONObject();
		
		if (repository.findByDescricao(categoria.getDescricao()) != null) {		
			json.put("erro", "descricao");	
		} else {
			repository.save(categoria);
			json.put("sucesso", new Boolean(true));
		}			
		
		return json.toString();
	}

	@PostMapping("/excluir")
	public @ResponseBody String excluirCategoria(RedirectAttributes ra, @RequestParam("descricao") String descricao) throws JSONException {		
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