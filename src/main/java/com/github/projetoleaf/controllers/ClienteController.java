package com.github.projetoleaf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.projetoleaf.beans.Categoria;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.repositories.CategoriaRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
    private MessageSource config;
	
	@GetMapping
	public String pesquisarCliente(Model model) {
		model.addAttribute("listagemClientes", clienteCategoriaRepository.findAll());
		return "/clientes/pesquisar";
	}
	
	@GetMapping("/editarCategoria/{id}")
	public String editarCategoriaCliente(@PathVariable Long id, Model model) {
		
		ClienteCategoria clienteCategoria = clienteCategoriaRepository.findOne(id);				
		
	    model.addAttribute("cliente", clienteCategoria);
	    model.addAttribute("categorias", categoriaRepository.findAll());
	    
	    return "/clientes/categoria";
	}
	
	@GetMapping("/definirBiometria/{id}")
	public String definirBiometriaCliente(@PathVariable Long id, Model model) {
		
		ClienteCategoria c = clienteCategoriaRepository.findOne(id);		
		Cliente cliente = c.getCliente();
		
	    model.addAttribute("cliente", cliente);
	    
	    return "/clientes/biometria";
	}
	
	@PostMapping("/salvarBiometria")
	public String salvarClienteBiometria(Model model, @RequestParam("biometria") String biometria, @RequestParam("id") Long id) {
		
		try{
			Cliente cliente = clienteRepository.findOne(id);
			
			cliente.setBiometria(biometria);
			
			clienteRepository.save(cliente);
		}
	    catch (Exception ex) {
	       log.error("Erro de processamento", ex);
	       model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
	    }
		
		return "redirect:/clientes";
	}
	
	@PostMapping("/salvarCategoria")
	public String salvarClienteCategoria(Model model, @RequestParam("categoria") Long idCategoriaSelecionada, @RequestParam("id") Long id) {
		
		try{
			ClienteCategoria clienteCategoria = clienteCategoriaRepository.findOne(id);
			Categoria categoria = categoriaRepository.findOne(idCategoriaSelecionada);
			
			clienteCategoria.setCategoria(categoria);
			
			clienteCategoriaRepository.save(clienteCategoria);
		}
	    catch (Exception ex) {
	       log.error("Erro de processamento", ex);
	       model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
	    }
		
		return "redirect:/clientes";
	}
}