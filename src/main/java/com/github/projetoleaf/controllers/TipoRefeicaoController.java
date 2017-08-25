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
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;
import lombok.extern.slf4j.Slf4j;
import com.github.projetoleaf.beans.TipoRefeicao;

@Slf4j
@Controller
@RequestMapping("/tipos")
public class TipoRefeicaoController {
	
	@Autowired
    private TipoRefeicaoRepository repository;
	
	@Autowired
    private MessageSource config;
	
	@GetMapping
	public String pesquisarTipo(Model model) {
		model.addAttribute("listagemTipos", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/tipos/pesquisar";
	}
	
	@GetMapping("/incluir")
	public String incluirTipo(Model model) {
		model.addAttribute("tipo", new TipoRefeicao());
		return abrirCadastroTipo(model);
	}
	
	@GetMapping("/editar/{id}")
    public String editarTipo(@PathVariable Long id, Model model) {
        TipoRefeicao tipoRefeicao = repository.findOne(id);
        model.addAttribute("tipo", tipoRefeicao);
        return abrirCadastroTipo(model);
    }
	
	public String abrirCadastroTipo(Model model) {
        return "/tipos/cadastro";
    }
	 
	@PostMapping("/salvar")
    public String salvarTipo(Model model, @ModelAttribute("tipo") @Valid TipoRefeicao tipoRefeicao, BindingResult result) {
		try {
            if (!result.hasErrors()) {
                TipoRefeicao tipoAtualizado = repository.save(tipoRefeicao);
                log.info(tipoAtualizado.toString() + " gravado com sucesso");
                model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o tipo" }, null));
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
		
        return abrirCadastroTipo(model);

    }
	
	@GetMapping("/excluir/{id}")
    public String excluirTipo(RedirectAttributes ra, @PathVariable Long id) {		
		try {
            repository.delete(id);
            log.info("TipoRefeicao #" + id + " excluído com sucesso");
            ra.addFlashAttribute("mensagemInfo", config.getMessage("excluidoSucesso", new Object[] { "o tipo" }, null));
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }

        return "redirect:/tipos";
    }
	
}