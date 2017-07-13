package com.github.projetoleaf.controllers;

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
import com.github.projetoleaf.repositories.CardapioRepository;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("/cardapios")
public class CardapioController {
	
	@Autowired
	private CardapioRepository repository;
	
	@Autowired
    private MessageSource config;
	
	Logger log = Logger.getLogger(CardapioController.class);
	
	@GetMapping
	public String pesquisarCategoria(Model model) {
		model.addAttribute("listagemCardapios", repository.findAll(new Sort(Sort.Direction.ASC, "data")));
		return "/cardapios/pesquisar";
	}
	
	@GetMapping("/incluir")
	public String incluirCardapio(Model model) {
		model.addAttribute("cardapio", new Cardapio());
		return abrirCadastroCardapio(model);
	}
	
	@GetMapping("/editar/{id}")
    public String editarCardapio(@PathVariable Integer id, Model model) {
        Cardapio cardapio = repository.findOne(id);
        model.addAttribute("cardapio", cardapio);
        return abrirCadastroCardapio(model);
    }
	
	public String abrirCadastroCardapio(Model model) {
        return "/cardapios/cadastro";
    }
	
	@PostMapping("/salvar")
    public String salvarCardapio(Model model, @ModelAttribute("cardapio") @Valid Cardapio cardapio, BindingResult result) {
		try {
            if (!result.hasErrors()) {
                Cardapio cardapioAtualizado = repository.save(cardapio);
                log.info(cardapioAtualizado.toString() + " gravado com sucesso");
                model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o cardápio" }, null));
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
		
        return abrirCadastroCardapio(model);
    }
	
	@GetMapping("/excluir/{id}")
    public String excluirCardapio(RedirectAttributes ra, @PathVariable Integer id) {		
		try {
            repository.delete(id);
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