package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.ReservasAdmin;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.FeriadoRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.StatusRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STF')")
@Controller
@RequestMapping("/reservas")
public class ReservasController {

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@Autowired
	private FeriadoRepository feriadoRepository;

	@Autowired
	private CardapioRepository cardapioRepository;

	@Autowired
	private ReservaItemRepository reservaItemRepository;

	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;

	@GetMapping
	public String pesquisarReserva(Model model) throws ParseException {

		Date segunda = new Date();
		Date terca = new Date();
		Date quarta = new Date();
		Date quinta = new Date();
		Date sexta = new Date();

		Calendar dataHoje = Calendar.getInstance();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

		List<ReservasAdmin> todasAsReservas = new ArrayList<ReservasAdmin>();

		List<Cliente> todosOsClientesDoBD = clienteRepository.findAll();
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();

		if (
//				!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
//				&& !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
//				&& 
				feriadoRepository.findByData(dataHoje.getTime()) == null) {

			for (Cliente cliente : todosOsClientesDoBD) {

				Calendar datasDaProxSemana = Calendar.getInstance();
				datasDaProxSemana = verificarData(datasDaProxSemana);

				Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
				BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

				ReservasAdmin reservasAdmin = new ReservasAdmin();
				reservasAdmin.setId(cliente.getId());
				reservasAdmin.setNome(cliente.getNome());
				reservasAdmin.setCreditos(nf.format(saldo));

				for (int i = 0; i < 5; i++) {

					Cardapio dataDoBanco = cardapioRepository.findByData(datasDaProxSemana.getTime());

					if (dataDoBanco != null) {

						Calendar cal = Calendar.getInstance();
						cal.setTime(dataDoBanco.getData());

						for (ReservaItem reserva : reservasItensDoBD) {// Mudar para query?
							if (cliente.getNome() == reserva.getReserva().getCliente().getNome()) {
								if (formatoDesejado.format(cal.getTime())
										.equals(formatoDesejado.format(reserva.getCardapio().getData()))) {

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
										reservasAdmin.setSegundaStatus(reserva.getStatus().getDescricao());
									}

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
										reservasAdmin.setTercaStatus(reserva.getStatus().getDescricao());
									}

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
										reservasAdmin.setQuartaStatus(reserva.getStatus().getDescricao());
									}

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
										reservasAdmin.setQuintaStatus(reserva.getStatus().getDescricao());
									}

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
										reservasAdmin.setSextaStatus(reserva.getStatus().getDescricao());
									}
								}
							}
						}
						// Verifica se o status estÃ¡ nulo, caso sim, define status
						if (reservasAdmin.getSegundaStatus() == null) {
							reservasAdmin.setSegundaStatus("NÃ£o reservado");
						}

						if (reservasAdmin.getTercaStatus() == null) {
							reservasAdmin.setTercaStatus("NÃ£o reservado");
						}

						if (reservasAdmin.getQuartaStatus() == null) {
							reservasAdmin.setQuartaStatus("NÃ£o reservado");
						}

						if (reservasAdmin.getQuintaStatus() == null) {
							reservasAdmin.setQuintaStatus("NÃ£o reservado");
						}

						if (reservasAdmin.getSextaStatus() == null) {
							reservasAdmin.setSextaStatus("NÃ£o reservado");
						}
					} else {
						// Se a data nÃ£o existir, define o status como indisponÃ­vel
						Calendar cal = Calendar.getInstance();
						cal.setTime(datasDaProxSemana.getTime());

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
							reservasAdmin.setSegundaStatus("Dia indisponÃ­vel");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
							reservasAdmin.setTercaStatus("Dia indisponÃ­vel");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
							reservasAdmin.setQuartaStatus("Dia indisponÃ­vel");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
							reservasAdmin.setQuintaStatus("Dia indisponÃ­vel");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
							reservasAdmin.setSextaStatus("Dia indisponÃ­vel");
						}
					}

					switch (i) {// Com base no indice do for, define as datas da prÃ³xima semana para aparecer
								// nas colunas dos dias no datatable
						case 0:
							segunda = datasDaProxSemana.getTime();
						case 1:
							terca = datasDaProxSemana.getTime();
						case 2:
							quarta = datasDaProxSemana.getTime();
						case 3:
							quinta = datasDaProxSemana.getTime();
						case 4:
							sexta = datasDaProxSemana.getTime();
					}
					datasDaProxSemana.add(Calendar.DAY_OF_MONTH, 1);
				}
				// SÃ³ serÃ¡ adicionado o objeto reservasAdmin, quando o status for diferente dos
				// ifs
				if (!(reservasAdmin.getSegundaStatus() == "NÃ£o reservado"
						&& reservasAdmin.getSegundaStatus() == "Dia indisponÃ­vel"
						&& reservasAdmin.getTercaStatus() == "NÃ£o reservado"
						&& reservasAdmin.getTercaStatus() == "Dia indisponÃ­vel"
						&& reservasAdmin.getQuartaStatus() == "NÃ£o reservado"
						&& reservasAdmin.getQuartaStatus() == "Dia indisponÃ­vel"
						&& reservasAdmin.getQuintaStatus() == "NÃ£o reservado"
						&& reservasAdmin.getQuintaStatus() == "Dia indisponÃ­vel"
						&& reservasAdmin.getSextaStatus() == "NÃ£o reservado"
						&& reservasAdmin.getSextaStatus() == "Dia indisponÃ­vel")) {
					todasAsReservas.add(reservasAdmin);
				}
			}
		}

		model.addAttribute("listagemReservas", todasAsReservas);
		model.addAttribute("segunda", formatoDesejado.format(segunda));
		model.addAttribute("terca", formatoDesejado.format(terca));
		model.addAttribute("quarta", formatoDesejado.format(quarta));
		model.addAttribute("quinta", formatoDesejado.format(quinta));
		model.addAttribute("sexta", formatoDesejado.format(sexta));

		return "/reservas/pesquisar";
	}

	@GetMapping("/pagamento/{nome}")
	public String efetivarPagamento(@PathVariable String nome, Model model) throws ParseException {

		Date segunda = new Date();
		Date terca = new Date();
		Date quarta = new Date();
		Date quinta = new Date();
		Date sexta = new Date();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

		ReservasAdmin reservasAdmin = new ReservasAdmin();

		List<String> datasDasReservas = new ArrayList<String>();

		Cliente cliente = clienteRepository.findByNome(nome);
		Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);

		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();

		reservasAdmin.setCreditos(nf.format(ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00)));

		Calendar datasDaProxSemana = Calendar.getInstance();
		datasDaProxSemana = verificarData(datasDaProxSemana);

		for (int i = 0; i < 5; i++) {

			Cardapio dataDoBanco = cardapioRepository.findByData(datasDaProxSemana.getTime());

			if (dataDoBanco != null) {

				Calendar cal = Calendar.getInstance();
				cal.setTime(dataDoBanco.getData());

				for (ReservaItem reserva : reservasItensDoBD) {// Converter para query?
					if (cliente.getNome() == reserva.getReserva().getCliente().getNome()) {
						if (formatoDesejado.format(cal.getTime())
								.equals(formatoDesejado.format(reserva.getCardapio().getData()))) {
							if (reserva.getStatus().getDescricao().equals("Solicitado")) {

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
									reservasAdmin.setSegundaStatus(reserva.getStatus().getDescricao());
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
									reservasAdmin.setTercaStatus(reserva.getStatus().getDescricao());
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
									reservasAdmin.setQuartaStatus(reserva.getStatus().getDescricao());
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
									reservasAdmin.setQuintaStatus(reserva.getStatus().getDescricao());
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
									reservasAdmin.setSextaStatus(reserva.getStatus().getDescricao());
								}
							}
						}
					}
				}
			}

			switch (i) {
			case 0:
				segunda = datasDaProxSemana.getTime();
			case 1:
				terca = datasDaProxSemana.getTime();
			case 2:
				quarta = datasDaProxSemana.getTime();
			case 3:
				quinta = datasDaProxSemana.getTime();
			case 4:
				sexta = datasDaProxSemana.getTime();
			}

			datasDaProxSemana.add(Calendar.DAY_OF_MONTH, 1);
		}
		// Formata as datas, se os status das reservas nÃ£o forem nulas
		if (!(reservasAdmin.getSegundaStatus() == null)) {
			datasDasReservas.add(formatoDesejado.format(segunda));
		}

		if (!(reservasAdmin.getTercaStatus() == null)) {
			datasDasReservas.add(formatoDesejado.format(terca));
		}

		if (!(reservasAdmin.getQuartaStatus() == null)) {
			datasDasReservas.add(formatoDesejado.format(quarta));
		}

		if (!(reservasAdmin.getQuintaStatus() == null)) {
			datasDasReservas.add(formatoDesejado.format(quinta));
		}

		if (!(reservasAdmin.getSextaStatus() == null)) {
			datasDasReservas.add(formatoDesejado.format(sexta));
		}

		model.addAttribute(cliente);
		model.addAttribute(reservasAdmin);
		model.addAttribute("reserva", new ReservasAdmin());
		model.addAttribute("datasReservas", datasDasReservas);
		model.addAttribute("valorRefeicao",
				clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorComSubsidio());

		return "/reservas/pagamento";
	}

	@PostMapping("/salvar")
	public String salvarReservas(@RequestParam("nome") String nome, @RequestParam("data") String[] datasSelecionadas,
			@RequestParam(value = "valor", required = false) String valor,
			@RequestParam(value = "recargas", required = false) String recargas,
			@RequestParam(value = "utilizarCreditos", required = false) Boolean creditos) {

		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

		Cliente cliente = clienteRepository.findByNome(nome);
		List<ReservaItem> reservasDoCliente = reservaItemRepository.todasAsReservasDoCliente(cliente.getId());

		if (creditos == null) {

			if (recargas == null) {

				for (int z = 0; z < datasSelecionadas.length; z++) {

					for (ReservaItem reserva : reservasDoCliente) {

						if (formatoDesejado.format(reserva.getCardapio().getData()).equals(datasSelecionadas[z])) {

							reserva.setStatus(statusRepository.findByDescricao("Pago"));
							reservaItemRepository.save(reserva);
						}
					}
				}
			} else {

				Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
				BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

				Extrato extrato = new Extrato();
				extrato.setCliente(cliente);
				extrato.setTransacao(new BigDecimal(recargas.replaceAll(",", ".")));
				extrato.setDataTransacao(timestamp);
				extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",", "."))));

				extratoRepository.save(extrato);

				for (int z = 0; z < datasSelecionadas.length; z++) {

					for (ReservaItem reserva : reservasDoCliente) {

						if (formatoDesejado.format(reserva.getCardapio().getData()).equals(datasSelecionadas[z])) {

							reserva.setStatus(statusRepository.findByDescricao("Pago"));
							reservaItemRepository.save(reserva);
						}
					}
				}
			}
		} else {

			Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
			BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

			Extrato extrato = new Extrato();
			extrato.setCliente(cliente);
			extrato.setTransacao(new BigDecimal(recargas.replaceAll(",", ".")));
			extrato.setDataTransacao(timestamp);
			extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",", "."))));

			extratoRepository.save(extrato);

			BigDecimal saldoAtualizado = extratoRepository.findFirstByClienteOrderByIdDesc(cliente).getSaldo();

			Extrato debitoNoSaldo = new Extrato();
			debitoNoSaldo.setCliente(cliente);
			debitoNoSaldo.setDataTransacao(timestamp);

			BigDecimal transacao = BigDecimal.valueOf(datasSelecionadas.length)
					.multiply(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorComSubsidio());

			debitoNoSaldo.setSaldo(saldoAtualizado.subtract(transacao));
			debitoNoSaldo.setTransacao(transacao.negate());

			extratoRepository.save(debitoNoSaldo);

			for (int z = 0; z < datasSelecionadas.length; z++) {

				for (ReservaItem reserva : reservasDoCliente) {

					if (formatoDesejado.format(reserva.getCardapio().getData()).equals(datasSelecionadas[z])) {

						reserva.setStatus(statusRepository.findByDescricao("Pago"));
						reservaItemRepository.save(reserva);
					}
				}
			}
		}

		return "redirect:/reservas";
	}

	public static Calendar verificarData(Calendar data) {
		// se for segunda
		if (data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			data.add(Calendar.DATE, 7);
		}
		// se for terÃ§a
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			data.add(Calendar.DATE, 6);
		}
		// se for quarta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			data.add(Calendar.DATE, 5);
		}
		// se for quinta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			data.add(Calendar.DATE, 4);
		}
		// se for sexta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			data.add(Calendar.DATE, 3);
		}
		// se for sabado
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			data.add(Calendar.DATE, 2);
		}
		// se for domingo
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			data.add(Calendar.DATE, 1);
		}
		return data;
	}
}