package com.github.projetoleaf.controllers;

import java.util.ArrayList;
import java.util.List;
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
import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.TipoRefeicao;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cardapios")
public class CardapioController {
	
	@Autowired
	private CardapioRepository cardapioRepository;
	
	@Autowired
	private TipoRefeicaoRepository tipoRefeicaoRepository;
	
	@Autowired
    private MessageSource config;
	
	@GetMapping
	public String pesquisarCategoria(Model model) {
		model.addAttribute("listagemCardapios", cardapioRepository.findAll(new Sort(Sort.Direction.ASC, "data")));
		return "/cardapios/pesquisar";
	}
	
	@GetMapping("/incluir")
	public String incluirCardapio(Model model) {
		TipoRefeicao trad = tipoRefeicaoRepository.findByDescricao("Tradicional");
		TipoRefeicao veget = tipoRefeicaoRepository.findByDescricao("Vegetariano");
		List<TipoRefeicao> tipoRefeicao = new ArrayList<TipoRefeicao>();
    	
    	tipoRefeicao.add(trad);
    	tipoRefeicao.add(veget);
    	
    	model.addAttribute("cardapio", new Cardapio());
		model.addAttribute("tipoRefeicao", tipoRefeicao);
		
		return abrirCadastroCardapio(model);
	}
	
	@GetMapping("/editar/{id}")
    public String editarCardapio(@PathVariable Long id, Model model) {
Cardapio cardapio = cardapioRepository.findOne(id);
        
        TipoRefeicao trad = tipoRefeicaoRepository.findByDescricao("Tradicional");
		TipoRefeicao veget = tipoRefeicaoRepository.findByDescricao("Vegetariano");
		List<TipoRefeicao> tipoRefeicao = new ArrayList<TipoRefeicao>();
    	
    	tipoRefeicao.add(trad);
    	tipoRefeicao.add(veget);
    	
        model.addAttribute("cardapio", cardapio);
        model.addAttribute("tipoRefeicao", tipoRefeicao);
        
        return abrirCadastroCardapio(model);
    }
	
	public String abrirCadastroCardapio(Model model) {
        return "/cardapios/cadastro";
    }
	
	@PostMapping("/salvar")
    public String salvarCardapio(Model model, @ModelAttribute("cardapio") @Valid Cardapio cardapio, BindingResult result) {
		try {
            if (!result.hasErrors()) {
                Cardapio cardapioAtualizado = cardapioRepository.save(cardapio);
                log.info(cardapioAtualizado.toString() + " gravado com sucesso");
                model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o cardápio" }, null));
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
		
		return "redirect:/cardapios";
    }
	
	@GetMapping("/excluir/{id}")
    public String excluirCardapio(RedirectAttributes ra, @PathVariable Long id) {		
		try {
			cardapioRepository.delete(id);
            log.info("Cardápio #" + id + " excluído com sucesso");
            ra.addFlashAttribute("mensagemInfo", config.getMessage("excluidoSucesso", new Object[] { "o cardápio" }, null));
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
	
		return "redirect:/cardapios";
    }
	
}