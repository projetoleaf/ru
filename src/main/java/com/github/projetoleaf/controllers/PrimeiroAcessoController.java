package com.github.projetoleaf.controllers;

import java.sql.Timestamp;
import java.text.ParseException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.ClienteCurso;
import com.github.projetoleaf.beans.UsuarioDetails;
import com.github.projetoleaf.repositories.CategoriaRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteCursoRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.CursoRepository;

@Controller
public class PrimeiroAcessoController {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ClienteCursoRepository clienteCursoRepository;

	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;

	private String retorno;

	@GetMapping("/boasvindas")
	public String boasVindas(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			if (clienteRepository.findByIdentificacao(authentication.getName()) != null) {
				retorno = "boasvindas";
			} else {
				model.addAttribute("categorias", categoriaRepository.findAll());
				model.addAttribute("cursos", cursoRepository.findAll());
				retorno = abrirPrimeiroAcesso(model);
			}
		}
		
		return retorno;
	}

	public String abrirPrimeiroAcesso(Model model) {
		return "primeiroacesso";
	}
	
	@PostMapping("/primeiroacesso/salvar")
	public @ResponseBody void salvarPrimeiroLogin(@RequestParam("categoria") String categoria, 
		   @RequestParam("curso") String curso,
		   @RequestParam("raMatricula") int raMatricula) throws JSONException, ParseException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioDetails usuarioAtual = (UsuarioDetails) authentication.getDetails();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			String nome = usuarioAtual.getNome();
			String cpf = usuarioAtual.getCpf();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

			Cliente clienteAtualizado = new Cliente();
			clienteAtualizado.setIdentificacao(authentication.getName());
			clienteAtualizado.setCpf(clienteAtualizado.imprimeCPF(cpf));
			clienteAtualizado.setNome(nome);
			clienteAtualizado.setBiometria("N");
			clienteAtualizado.setDataCriado(timestamp);

			clienteRepository.save(clienteAtualizado);
			
			if(curso != "") {
				ClienteCurso clienteCurso = new ClienteCurso();
				clienteCurso.setCliente(clienteAtualizado);
				clienteCurso.setCurso(cursoRepository.findByDescricao(curso));
				clienteCurso.setDataInicio(timestamp);
				clienteCurso.setDataFim(null);
				
				clienteCursoRepository.save(clienteCurso);
			}

			ClienteCategoria clienteCategoria = new ClienteCategoria();
			clienteCategoria.setCliente(clienteAtualizado);
			clienteCategoria.setCategoria(categoriaRepository.findByDescricao(categoria));
			clienteCategoria.setRaMatricula(raMatricula);
			clienteCategoria.setDataInicio(timestamp);
			clienteCategoria.setDataFim(null);

			clienteCategoriaRepository.save(clienteCategoria);
		}
	}
}