package com.github.projetoleaf.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.ReservasAdmin;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.FeriadoRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.StatusRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;
import com.github.projetoleaf.repositories.TipoValorRepository;

@Controller
@RequestMapping("/remanescentes")
public class RemanescentesAdminController {

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@Autowired
	private FeriadoRepository feriadoRepository;

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private CardapioRepository cardapioRepository;

	@Autowired
	private TipoValorRepository tipoValorRepository;

	@Autowired
	private ReservaItemRepository reservaItemRepository;

	@Autowired
	private TipoRefeicaoRepository tipoRefeicaoRepository;

	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;

	@GetMapping
	public String pesquisarRemanescentes(Model model) throws ParseException {

		int count = 0;

		int countSegunda = 0;
		int countTerca = 0;
		int countQuarta = 0;
		int countQuinta = 0;
		int countSexta = 0;
		int countDisponivelSegunda = 0;
		int countDisponivelTerca = 0;
		int countDisponivelQuarta = 0;
		int countDisponivelQuinta = 0;
		int countDisponivelSexta = 0;
		int countExpiradoSegunda = 0;
		int countExpiradoTerca = 0;
		int countExpiradoQuarta = 0;
		int countExpiradoQuinta = 0;
		int countExpiradoSexta = 0;

		Date segunda = new Date();
		Date terca = new Date();
		Date quarta = new Date();
		Date quinta = new Date();
		Date sexta = new Date();

		Calendar dataHoje = Calendar.getInstance();
		Calendar proximaSeg = Calendar.getInstance();
		Calendar ultimoDiaUtil = Calendar.getInstance();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

		List<ReservasAdmin> todasAsReservas = new ArrayList<ReservasAdmin>();

		List<Cliente> todosOsClientesDoBD = clienteRepository.findAll();
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();

		proximaSeg = verificarData(proximaSeg);
		
		ultimoDiaUtil.setTime(proximaSeg.getTime());
		ultimoDiaUtil.add(Calendar.DATE, 4); // Com base no valor da dataAtual, define a sexta-feira da próxima
												// semana

		List<Date> todasAsDatasDaProximaSemana = cardapioRepository.todasAsDatasDaSemana(proximaSeg.getTime(),
				ultimoDiaUtil.getTime());
		ultimoDiaUtil.setTime(todasAsDatasDaProximaSemana.get(todasAsDatasDaProximaSemana.size() - 1)); // Define
																										// o
																										// último
																										// dia
																										// util,
																										// conforme
																										// o
																										// range
																										// de
																										// datas
																										// do BD

		if (!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				&& !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& feriadoRepository.findByData(dataHoje.getTime()) == null
				&& dataHoje.get(Calendar.DAY_OF_WEEK) != ultimoDiaUtil.get(Calendar.DAY_OF_WEEK)) {

			for (int z = 0; z < todosOsClientesDoBD.size(); z++) {
				
				BigDecimal saldo = new BigDecimal(0.00);
				
				Calendar dataAtual = Calendar.getInstance();
				dataAtual = verificarData(dataAtual);

				List<Extrato> todosOsExtratos = extratoRepository
						.buscarTodasTransacoesDoCliente(todosOsClientesDoBD.get(z).getId());

				if (!todosOsExtratos.isEmpty()) {
					saldo = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
				}

				ReservasAdmin reservasAdmin = new ReservasAdmin();
				reservasAdmin.setId(Long.valueOf(z));
				reservasAdmin.setNome(todosOsClientesDoBD.get(z).getNome());
				reservasAdmin.setCreditos(nf.format(saldo));

				for (int i = 0; i < todasAsDatasDaProximaSemana.size(); i++) {

					List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());

					for (Object[] linhaDoBanco : dataDoBanco) {

						String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
						Date dataVar = formatoDesejado.parse(dataFormatada);
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(dataVar);											
						
						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
							countDisponivelSegunda = reservaItemRepository
									.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
							countExpiradoSegunda = reservaItemRepository
									.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
							countDisponivelTerca = reservaItemRepository
									.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
							countExpiradoTerca = reservaItemRepository
									.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
							countDisponivelQuarta = reservaItemRepository
									.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
							countExpiradoQuarta = reservaItemRepository
									.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
							countDisponivelQuinta = reservaItemRepository
									.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
							countExpiradoQuinta = reservaItemRepository
									.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
							countDisponivelSexta = reservaItemRepository
									.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
							countExpiradoSexta = reservaItemRepository
									.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
						}
						
						// Qtde de reservas já feitas no último dia útil, por data da semana que vem
						for (int y = 0; y < reservasItensDoBD.size(); y++) {

							if (reservasItensDoBD.get(y).getReserva().getDataReserva()
									.equals(dataHoje.getTime())) {

								if (reservasItensDoBD.get(y).getCardapio().getData().equals(linhaDoBanco[1])) {

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
										countSegunda++;
									}

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
										countTerca++;
									}

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
										countQuarta++;
									}

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
										countQuinta++;
									}

									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
										countSexta++;
									}
								}
							}
						}

						// Verifica se o count disponível é menor que 360 refeições e se o count das
						// reservas do último dia útil é maior que 0
						if (countDisponivelSegunda < 360 || countDisponivelTerca < 360
								|| countDisponivelQuarta < 360 || countDisponivelQuinta < 360
								|| countDisponivelSexta < 360 || countExpiradoSegunda > 0 || countExpiradoTerca > 0
								|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {

							// Verifica se a qtde de reservas do último dia útil é menor ou igual a qtde de
							// reservas expiradas (que agora estão disponíveis)
							if (countSegunda <= countExpiradoSegunda || countTerca <= countExpiradoTerca
									|| countQuarta <= countExpiradoQuarta || countQuinta <= countExpiradoQuinta
									|| countSexta <= countExpiradoSexta) {
								
								for (int x = 0; x < reservasItensDoBD.size(); x++) {

									if (todosOsClientesDoBD.get(z).getNome() == reservasItensDoBD.get(x).getReserva()
											.getCliente().getNome()) {

										if (reservaItemRepository.verificarSeReservaExiste(todosOsClientesDoBD.get(z).getId(), (Date) linhaDoBanco[1]) != null) {	

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
												reservasAdmin
														.setSegundaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
											}
				
											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
												reservasAdmin
														.setTercaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
											}
				
											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
												reservasAdmin
														.setQuartaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
											}
				
											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
												reservasAdmin
														.setQuintaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
											}
				
											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
												reservasAdmin
														.setSextaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
											}			
										}
									}
								}
							}
						}						
					}

					if (!dataDoBanco.isEmpty()) {

						if (reservasAdmin.getSegundaStatus() == null) {
							reservasAdmin.setSegundaStatus("Não reservado");
						}

						if (reservasAdmin.getTercaStatus() == null) {
							reservasAdmin.setTercaStatus("Não reservado");
						}

						if (reservasAdmin.getQuartaStatus() == null) {
							reservasAdmin.setQuartaStatus("Não reservado");
						}

						if (reservasAdmin.getQuintaStatus() == null) {
							reservasAdmin.setQuintaStatus("Não reservado");
						}

						if (reservasAdmin.getSextaStatus() == null) {
							reservasAdmin.setSextaStatus("Não reservado");
						}
					} else {

						Calendar cal = Calendar.getInstance();
						cal.setTime(dataAtual.getTime());

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
							reservasAdmin.setSegundaStatus("Dia indisponível");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
							reservasAdmin.setTercaStatus("Dia indisponível");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
							reservasAdmin.setQuartaStatus("Dia indisponível");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
							reservasAdmin.setQuintaStatus("Dia indisponível");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
							reservasAdmin.setSextaStatus("Dia indisponível");
						}
					}

					switch (i) {
						case 0:
							segunda = dataAtual.getTime();
						case 1:
							terca = dataAtual.getTime();
						case 2:
							quarta = dataAtual.getTime();
						case 3:
							quinta = dataAtual.getTime();
						case 4:
							sexta = dataAtual.getTime();
					}

					dataAtual.add(Calendar.DAY_OF_MONTH, 1);
				}

				if (!(reservasAdmin.getSegundaStatus() == "Não reservado"
						&& reservasAdmin.getSegundaStatus() == "Dia indisponível"
						&& reservasAdmin.getTercaStatus() == "Não reservado"
						&& reservasAdmin.getTercaStatus() == "Dia indisponível"
						&& reservasAdmin.getQuartaStatus() == "Não reservado"
						&& reservasAdmin.getQuartaStatus() == "Dia indisponível"
						&& reservasAdmin.getQuintaStatus() == "Não reservado"
						&& reservasAdmin.getQuintaStatus() == "Dia indisponível"
						&& reservasAdmin.getSextaStatus() == "Não reservado"
						&& reservasAdmin.getSextaStatus() == "Dia indisponível")) {
					todasAsReservas.add(reservasAdmin);
				}
			}

		} else {
			count = 1;
		}

		model.addAttribute("listagemRemanescentes", todasAsReservas);
		model.addAttribute("segunda", formatoDesejado.format(segunda));
		model.addAttribute("terca", formatoDesejado.format(terca));
		model.addAttribute("quarta", formatoDesejado.format(quarta));
		model.addAttribute("quinta", formatoDesejado.format(quinta));
		model.addAttribute("sexta", formatoDesejado.format(sexta));
		model.addAttribute("holiday", count);

		return "/remanescentes/pesquisar";
	}

	@GetMapping("/pagamento/{nome}")
	public String efetivarPagamentoRemanescentes(@PathVariable String nome, Model model)
			throws ParseException, JsonGenerationException, JsonMappingException, IOException {

		BigDecimal creditos = new BigDecimal(0.00);
		
		int countSegunda = 0;
		int countTerca = 0;
		int countQuarta = 0;
		int countQuinta = 0;
		int countSexta = 0;
		int countDisponivelSegunda = 0;
		int countDisponivelTerca = 0;
		int countDisponivelQuarta = 0;
		int countDisponivelQuinta = 0;
		int countDisponivelSexta = 0;
		int countExpiradoSegunda = 0;
		int countExpiradoTerca = 0;
		int countExpiradoQuarta = 0;
		int countExpiradoQuinta = 0;
		int countExpiradoSexta = 0;		
		
		Calendar dataHoje = Calendar.getInstance();
		Calendar dataAtual = Calendar.getInstance();
		Calendar ultimoDiaUtil = Calendar.getInstance();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		
		ReservasAdmin reservasAdmin = new ReservasAdmin();
		List<Cardapio> cardapio = new ArrayList<Cardapio>();

		Cliente cliente = clienteRepository.findByNome(nome);
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();

		dataAtual = verificarData(dataAtual);
		
		ultimoDiaUtil.setTime(dataAtual.getTime());
		ultimoDiaUtil.add(Calendar.DATE, 4); // Com base no valor da dataAtual, define a sexta-feira da próxima
												// semana

		List<Date> todasAsDatasDaProximaSemana = cardapioRepository.todasAsDatasDaSemana(dataAtual.getTime(),
				ultimoDiaUtil.getTime());
		ultimoDiaUtil.setTime(todasAsDatasDaProximaSemana.get(todasAsDatasDaProximaSemana.size() - 1)); // Define
																										// o
																										// último
																										// dia
																										// util,
																										// conforme
																										// o
																										// range
																										// de
																										// datas
																										// do BD
		
		List<Extrato> todosOsExtratos = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

		if (!todosOsExtratos.isEmpty()) {
			creditos = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
		}

		reservasAdmin.setNome(cliente.getNome());
		reservasAdmin.setCreditos(nf.format(creditos));

		if (!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				&& !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& feriadoRepository.findByData(dataHoje.getTime()) == null
				&& dataHoje.get(Calendar.DAY_OF_WEEK) != ultimoDiaUtil.get(Calendar.DAY_OF_WEEK)) {

			for (int i = 0; i < todasAsDatasDaProximaSemana.size(); i++) {	
				
				List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());
	
				for (Object[] linhaDoBanco : dataDoBanco) {
	
					Calendar cal = Calendar.getInstance();
					cal.setTime((Date) linhaDoBanco[1]);

					String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
					Date dataVar = formatoDesejado.parse(dataFormatada);	
					
					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
						countDisponivelSegunda = reservaItemRepository
								.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
						countExpiradoSegunda = reservaItemRepository
								.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
					}

					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
						countDisponivelTerca = reservaItemRepository
								.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
						countExpiradoTerca = reservaItemRepository
								.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
					}

					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
						countDisponivelQuarta = reservaItemRepository
								.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
						countExpiradoQuarta = reservaItemRepository
								.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
					}

					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
						countDisponivelQuinta = reservaItemRepository
								.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
						countExpiradoQuinta = reservaItemRepository
								.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
					}

					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
						countDisponivelSexta = reservaItemRepository
								.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
						countExpiradoSexta = reservaItemRepository
								.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
					}
					
					// Qtde de reservas já feitas no último dia útil, por data da semana que vem
					for (int y = 0; y < reservasItensDoBD.size(); y++) {

						if (reservasItensDoBD.get(y).getReserva().getDataReserva()
								.equals(dataHoje.getTime())) {

							if (reservasItensDoBD.get(y).getCardapio().getData().equals(linhaDoBanco[1])) {
								
								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
									countSegunda++;
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
									countTerca++;
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
									countQuarta++;
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
									countQuinta++;
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
									countSexta++;
								}
							}
						}
					}

					// Verifica se o count disponível é menor que 360 refeições e se o count das
					// reservas do último dia útil é maior que 0
					if (countDisponivelSegunda < 360 || countDisponivelTerca < 360
							|| countDisponivelQuarta < 360 || countDisponivelQuinta < 360
							|| countDisponivelSexta < 360 || countExpiradoSegunda > 0 || countExpiradoTerca > 0
							|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {

						// Verifica se a qtde de reservas do último dia útil é menor ou igual a qtde de
						// reservas expiradas (que agora estão disponíveis)
						if (countSegunda <= countExpiradoSegunda || countTerca <= countExpiradoTerca
								|| countQuarta <= countExpiradoQuarta || countQuinta <= countExpiradoQuinta
								|| countSexta <= countExpiradoSexta) {
							
							if(reservaItemRepository.verificarSeReservaExiste(cliente.getId(), (Date) linhaDoBanco[1]) == null) {
								Cardapio c = new Cardapio();
								
								c.setId((Long) linhaDoBanco[0]);
								c.setData(dataVar);
								
								cardapio.add(c);
							}
						}
					}
				}
	
				dataAtual.add(Calendar.DAY_OF_MONTH, 1);
			}		
		}

		model.addAttribute(reservasAdmin);
		model.addAttribute("datasReservas", cardapio);
		model.addAttribute("remanescentes", new ReservasAdmin());
		model.addAttribute("valorRefeicao",
				clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorComSubsidio());

		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
		String objectJSON = mapper.writeValueAsString(cardapio);// json string
		model.addAttribute("objectJSON", objectJSON);

		return "/remanescentes/pagamento";
	}

	@PostMapping("/salvar")
	public String salvarRemanescentes(@RequestParam("nome") String nome, @RequestParam("data") String[] idsCardapios,
			@RequestParam("tipoRefeicao") Integer[] idsTipoRefeicao,
			@RequestParam(value = "valor", required = false) String valor,
			@RequestParam(value = "recargas", required = false) String recargas,
			@RequestParam(value = "utilizarCreditos", required = false) Boolean creditos) {

		Cliente cliente = clienteRepository.findByNome(nome);

		List<Integer> tiposRefeicoesSelecionados = new ArrayList<Integer>();

		if (creditos == null) {

			if (recargas == null) {

				Reserva reserva = new Reserva();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

				reserva.setCliente(cliente);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada"));
				reserva.setDataReserva(timestamp);

				reservaRepository.save(reserva);

				List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
				Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

				for (int z = 0; z < idsTipoRefeicao.length; z++) {

					if (idsTipoRefeicao[z] != null) {
						tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
					}
				}

				for (int z = 0; z < idsCardapios.length; z++) {

					Extrato extrato = null;
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					ReservaItem reservaItem = new ReservaItem();

					r.setId(id);
					c.setId(Long.parseLong((idsCardapios[z])));

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(z) % 2 == 0
							? tipoRefeicaoRepository.findByDescricao("Vegetariano")
							: tipoRefeicaoRepository.findByDescricao("Tradicional"));
					reservaItem.setExtrato(extrato);

					reservaItemRepository.save(reservaItem);
				}
			} else {
				Reserva reserva = new Reserva();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

				reserva.setCliente(cliente);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada"));
				reserva.setDataReserva(timestamp);

				reservaRepository.save(reserva);

				List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
				Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

				List<Extrato> todosOsExtratos = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

				BigDecimal saldo = new BigDecimal(0.00);

				if (!todosOsExtratos.isEmpty()) {
					saldo = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
				}

				Extrato extrato = new Extrato();
				extrato.setCliente(cliente);
				extrato.setTransacao(new BigDecimal(recargas.replaceAll(",", ".")));
				extrato.setDataTransacao(timestamp);
				extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",", "."))));

				extratoRepository.save(extrato);

				for (int z = 0; z < idsTipoRefeicao.length; z++) {

					if (idsTipoRefeicao[z] != null) {
						tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
					}
				}

				for (int z = 0; z < idsCardapios.length; z++) {

					Extrato e = null;
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					ReservaItem reservaItem = new ReservaItem();

					r.setId(id);
					c.setId(Long.parseLong((idsCardapios[z])));

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(z) % 2 == 0
							? tipoRefeicaoRepository.findByDescricao("Vegetariano")
							: tipoRefeicaoRepository.findByDescricao("Tradicional"));
					reservaItem.setExtrato(e);

					reservaItemRepository.save(reservaItem);
				}
			}
		} else {

			List<Extrato> todosOsExtratos = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

			Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

			BigDecimal saldo = new BigDecimal(0.00);

			if (!todosOsExtratos.isEmpty()) {
				saldo = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
			}

			Extrato extrato = new Extrato();
			extrato.setCliente(cliente);
			extrato.setTransacao(new BigDecimal(recargas.replaceAll(",", ".")));
			extrato.setDataTransacao(timestamp);
			extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",", "."))));

			extratoRepository.save(extrato);

			ClienteCategoria clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);

			List<Extrato> ultimoSaldoAtualizado = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

			BigDecimal saldoAtualizado = new BigDecimal(0.00);

			if (!ultimoSaldoAtualizado.isEmpty()) {
				saldoAtualizado = ultimoSaldoAtualizado.get(ultimoSaldoAtualizado.size() - 1).getSaldo();
			}

			Extrato debitoNoSaldo = new Extrato();
			debitoNoSaldo.setCliente(cliente);
			debitoNoSaldo.setDataTransacao(timestamp);

			BigDecimal transacao = BigDecimal.valueOf(idsCardapios.length)
					.multiply(clienteCategoria.getCategoria().getValorComSubsidio());

			debitoNoSaldo.setSaldo(saldoAtualizado.subtract(transacao));
			debitoNoSaldo.setTransacao(transacao.negate());

			extratoRepository.save(debitoNoSaldo);

			Reserva reserva = new Reserva();
			reserva.setCliente(cliente);
			reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada"));
			reserva.setDataReserva(timestamp);

			reservaRepository.save(reserva);

			List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
			Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

			for (int z = 0; z < idsTipoRefeicao.length; z++) {

				if (idsTipoRefeicao[z] != null) {
					tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
				}
			}

			for (int z = 0; z < idsCardapios.length; z++) {

				Reserva r = new Reserva();
				Cardapio c = new Cardapio();
				ReservaItem reservaItem = new ReservaItem();

				r.setId(id);
				c.setId(Long.parseLong((idsCardapios[z])));

				reservaItem.setReserva(r);
				reservaItem.setCardapio(c);
				reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
				reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(z) % 2 == 0
						? tipoRefeicaoRepository.findByDescricao("Vegetariano")
						: tipoRefeicaoRepository.findByDescricao("Tradicional"));
				reservaItem.setExtrato(debitoNoSaldo);

				reservaItemRepository.save(reservaItem);
			}
		}

		return "redirect:/remanescentes";
	}

	public static Calendar verificarData(Calendar data) {
		// se for segunda
		if (data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			data.add(Calendar.DATE, 7);
		}
		// se for terça
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