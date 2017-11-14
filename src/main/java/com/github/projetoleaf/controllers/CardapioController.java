package com.github.projetoleaf.controllers;

import java.util.Date;

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

import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.FeriadoRepository;
import com.github.projetoleaf.repositories.PeriodoRefeicaoRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@Controller
@RequestMapping("/cardapios")
public class CardapioController {	

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private FeriadoRepository feriadoRepository;

	@Autowired
	private CardapioRepository cardapioRepository;

	@Autowired
	private PeriodoRefeicaoRepository periodoRefeicaoRepository;
	
	private int count = 0;

	@GetMapping
	public String pesquisarCategoria(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		count = 0;
		model.addAttribute("listagemCardapios", cardapioRepository.findAll(new Sort(Sort.Direction.ASC, "data")));
		return "/cardapios/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirCardapio(Model model) {
		count = 0;
		model.addAttribute("cardapio", new Cardapio());
		model.addAttribute("periodoRefeicao", periodoRefeicaoRepository.findAll());
		return abrirCadastroCardapio(model);
	}

	@GetMapping("/editar/{id}")
	public String editarCardapio(@PathVariable Long id, Model model) {
		count = 1;
		Cardapio cardapio = cardapioRepository.findOne(id);
		model.addAttribute("cardapio", cardapio);
		model.addAttribute("periodoRefeicao", periodoRefeicaoRepository.findAll());
		return abrirCadastroCardapio(model);
	}

	public String abrirCadastroCardapio(Model model) {
		model.addAttribute("count", count);
		return "/cardapios/cadastro";
	}
	
	@PostMapping("/verificar")
	public @ResponseBody String verificarData(@ModelAttribute("cardapio") Cardapio cardapio, @RequestParam("count") int count) throws JSONException {
		JSONObject json = new JSONObject();
		
		boolean verifica = true;
		
		if (cardapioRepository.verificarDataEPeriodoRefeicao(cardapio.getData(), cardapio.getPeriodoRefeicao().getId()) != null) {		
			verifica = false;
			json.put("erro", "data");	
		} 
		else if (feriadoRepository.findByData(cardapio.getData()) != null) {
			verifica = false;
			json.put("erro", "feriado");	
		} 
		
		if(verifica == true || count == 1) {
			cardapioRepository.save(cardapio);
			json.put("sucesso", new Boolean(true));
		}			
		
		return json.toString();
	}

	@PostMapping("/excluir")
	public @ResponseBody String excluirCardapio(RedirectAttributes ra, @RequestParam("data") Date data, @RequestParam("periodo") String periodo) throws JSONException {
		count = 0;
		JSONObject json = new JSONObject();
		
		try {
			Long id = cardapioRepository.verificarDataEPeriodoRefeicao(data, periodoRefeicaoRepository.findByDescricao(periodo).getId());			
			cardapioRepository.delete(id);
			json.put("sucesso", new Boolean(true));
		} catch (Exception ex) {
			json.put("erro", "exclusao");
		}
		
		return json.toString();
	}
}