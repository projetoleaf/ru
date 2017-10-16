package com.github.projetoleaf.controllers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.ClienteCurso;
import com.github.projetoleaf.beans.PrimeiroAcesso;
import com.github.projetoleaf.beans.UsuarioDetails;
import com.github.projetoleaf.repositories.CategoriaRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteCursoRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.CursoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PrimeiroAcessoController {

	@Autowired
	private MessageSource config;

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

	@GetMapping("/boasVindas")
	public String boasVindas(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			String identificacao = authentication.getName();
			Cliente cliente = clienteRepository.buscarCliente(identificacao);

			if (cliente != null) {
				retorno = "boasVindas";
			} else {
				model.addAttribute("primeiroAcesso", new PrimeiroAcesso());
				model.addAttribute("categoria", categoriaRepository.findAll());
				model.addAttribute("curso", cursoRepository.findAll());
				retorno = abrirPrimeiroAcesso(model);
			}
		}

		return retorno;
	}

	public String abrirPrimeiroAcesso(Model model) {
		return "primeiroAcesso";
	}

	@PostMapping("/primeiroAcesso/salvar")
	public String salvarPrimeiroLogin(Model model, @ModelAttribute("primeiroAcesso") PrimeiroAcesso primeiroAcesso,
			BindingResult result) {
		try {
			if (!result.hasErrors()) {

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				UsuarioDetails usuarioAtual = (UsuarioDetails) authentication.getDetails();

				if (!(authentication instanceof AnonymousAuthenticationToken)) {

					String nome = usuarioAtual.getNome();
					String cpf = usuarioAtual.getCpf();
					String identificacao = authentication.getName();
					Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

					Cliente clienteAtualizado = new Cliente();
					clienteAtualizado.setIdentificacao(identificacao);
					clienteAtualizado.setCpf(clienteAtualizado.imprimeCPF(cpf));
					clienteAtualizado.setNome(nome);
					clienteAtualizado.setBiometria("N");
					clienteAtualizado.setDataCriado(timestamp);

					clienteRepository.save(clienteAtualizado);

					ClienteCurso clienteCurso = new ClienteCurso();
					clienteCurso.setCliente(clienteAtualizado);
					clienteCurso.setCurso(primeiroAcesso.getCurso());
					clienteCurso.setDataInicio(timestamp);
					clienteCurso.setDataFim(null);

					clienteCursoRepository.save(clienteCurso);

					ClienteCategoria clienteCategoria = new ClienteCategoria();
					clienteCategoria.setCliente(clienteAtualizado);
					clienteCategoria.setCategoria(primeiroAcesso.getCategoria());
					clienteCategoria.setRaMatricula(primeiroAcesso.getRaMatricula());
					clienteCategoria.setDataInicio(timestamp);
					clienteCategoria.setDataFim(null);

					clienteCategoriaRepository.save(clienteCategoria);

					log.info(clienteAtualizado.toString() + " gravado com sucesso");
					model.addAttribute("mensagemInfo",
							config.getMessage("gravadoSucesso", new Object[] { "o primeiro acesso" }, null));
				}
			}
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/boasVindas";
	}
}