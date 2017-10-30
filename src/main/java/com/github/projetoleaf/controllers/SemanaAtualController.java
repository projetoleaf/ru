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
@RequestMapping("/semanaAtual")
public class SemanaAtualController {

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
	public String pesquisarSemanaAtual(Model model) throws ParseException {

		int count = 0;

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
		int countNãoSubsidiadaSegunda = 0;
		int countNãoSubsidiadaTerca = 0;
		int countNãoSubsidiadaQuarta = 0;
		int countNãoSubsidiadaQuinta = 0;
		int countNãoSubsidiadaSexta = 0;

		Date segunda = new Date();
		Date terca = new Date();
		Date quarta = new Date();
		Date quinta = new Date();
		Date sexta = new Date();

		Calendar dataHoje = Calendar.getInstance();
		Calendar dataAtual = Calendar.getInstance();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		
		List<ReservasAdmin> todasAsReservas = new ArrayList<ReservasAdmin>();

		List<Cliente> todosOsClientesDoBD = clienteRepository.findAll();
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();		

		if (!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				&& !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& feriadoRepository.findByData(dataHoje.getTime()) == null) {

			for (int z = 0; z < todosOsClientesDoBD.size(); z++) {

				dataAtual.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// Define o dia segunda-feira desta semana
				
				BigDecimal saldo = new BigDecimal(0.00);

				List<Extrato> todosOsExtratos = extratoRepository
						.buscarTodasTransacoesDoCliente(todosOsClientesDoBD.get(z).getId());

				if (!todosOsExtratos.isEmpty()) {
					saldo = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
				}

				ReservasAdmin reservasAdmin = new ReservasAdmin();
				reservasAdmin.setId(Long.valueOf(z));
				reservasAdmin.setNome(todosOsClientesDoBD.get(z).getNome());
				reservasAdmin.setCreditos(nf.format(saldo));

				for (int i = 0; i < 5; i++) {

					List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());

					for (Object[] linhaDoBanco : dataDoBanco) {

						Calendar cal = Calendar.getInstance();
						cal.setTime((Date) linhaDoBanco[1]);

						if (dataHoje.get(Calendar.DAY_OF_WEEK) <= cal.get(Calendar.DAY_OF_WEEK)) {

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
								countDisponivelSegunda = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoSegunda = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaSegunda = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
								countDisponivelTerca = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoTerca = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaTerca = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
								countDisponivelQuarta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoQuarta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaQuarta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
								countDisponivelQuinta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoQuinta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaQuinta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
								countDisponivelSexta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoSexta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaSexta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							// Verifica se o count disponível é menor que 360 refeições
							if (countDisponivelSegunda < 360 || countDisponivelTerca < 360
									|| countDisponivelQuarta < 360 || countDisponivelQuinta < 360
									|| countDisponivelSexta < 360 || countExpiradoSegunda > 0 || countExpiradoTerca > 0
									|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {

								for (int x = 0; x < reservasItensDoBD.size(); x++) {

									if (todosOsClientesDoBD.get(z).getNome() == reservasItensDoBD.get(x).getReserva()
											.getCliente().getNome()) {

										if (reservaItemRepository.verificarSeReservaExiste(
												todosOsClientesDoBD.get(z).getId(), (Date) linhaDoBanco[1]) != null) {

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
												reservasAdmin.setSegundaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
												reservasAdmin.setTercaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
												reservasAdmin.setQuartaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
												reservasAdmin.setQuintaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
												reservasAdmin.setSextaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
											}
										}
									}
								}
							}

							if (countNãoSubsidiadaSegunda < 140 || countNãoSubsidiadaTerca < 140
									|| countNãoSubsidiadaQuarta < 140 || countNãoSubsidiadaQuinta < 140
									|| countNãoSubsidiadaSexta < 140) {

								for (int x = 0; x < reservasItensDoBD.size(); x++) {

									if (todosOsClientesDoBD.get(z).getNome() == reservasItensDoBD.get(x)
											.getReserva().getCliente().getNome()) {

										if (reservaItemRepository.verificarSeReservaExiste(
												todosOsClientesDoBD.get(z).getId(),
												(Date) linhaDoBanco[1]) != null) {

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
												reservasAdmin.setSegundaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
												reservasAdmin.setTercaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
												reservasAdmin.setQuartaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
												reservasAdmin.setQuintaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
												reservasAdmin.setSextaStatus(
														reservasItensDoBD.get(x).getStatus().getDescricao());
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

						Calendar call = Calendar.getInstance();
						call.setTime(dataAtual.getTime());

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
							reservasAdmin.setSegundaStatus("Dia indisponível");
						}

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
							reservasAdmin.setTercaStatus("Dia indisponível");
						}

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
							reservasAdmin.setQuartaStatus("Dia indisponível");
						}

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
							reservasAdmin.setQuintaStatus("Dia indisponível");
						}

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
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

		model.addAttribute("listagemSemanaAtual", todasAsReservas);
		model.addAttribute("segunda", formatoDesejado.format(segunda));
		model.addAttribute("terca", formatoDesejado.format(terca));
		model.addAttribute("quarta", formatoDesejado.format(quarta));
		model.addAttribute("quinta", formatoDesejado.format(quinta));
		model.addAttribute("sexta", formatoDesejado.format(sexta));
		model.addAttribute("holiday", count);

		return "/semanaAtual/pesquisar";
	}

	@GetMapping("/pagamento/{nome}")
	public String efetivarPagamentoSemanaAtual(@PathVariable String nome, Model model)
			throws ParseException, JsonGenerationException, JsonMappingException, IOException {

		int count = 0;

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
		int countNãoSubsidiadaSegunda = 0;
		int countNãoSubsidiadaTerca = 0;
		int countNãoSubsidiadaQuarta = 0;
		int countNãoSubsidiadaQuinta = 0;
		int countNãoSubsidiadaSexta = 0;
		
		BigDecimal saldo = new BigDecimal(0.00);

		Calendar dataHoje = Calendar.getInstance();
		Calendar dataAtual = Calendar.getInstance();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

		Cliente cliente = clienteRepository.findByNome(nome);
		ClienteCategoria clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);
		
		List<Integer> contadores = new ArrayList<Integer>();
		List<Cardapio> cardapio = new ArrayList<Cardapio>();
		
		if (!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				&& !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& feriadoRepository.findByData(dataHoje.getTime()) == null) {
			
				dataAtual.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// Define o dia segunda-feira desta semana

				List<Extrato> todosOsExtratos = extratoRepository
						.buscarTodasTransacoesDoCliente(cliente.getId());

				if (!todosOsExtratos.isEmpty()) {
					saldo = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
				}

				for (int i = 0; i < 5; i++) {

					Cardapio c = new Cardapio();
					List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());

					for (Object[] linhaDoBanco : dataDoBanco) {
						
						String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
						Date dataVar = formatoDesejado.parse(dataFormatada);

						Calendar cal = Calendar.getInstance();
						cal.setTime((Date) linhaDoBanco[1]);

						if (dataHoje.get(Calendar.DAY_OF_WEEK) <= cal.get(Calendar.DAY_OF_WEEK)) {

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
								countDisponivelSegunda = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoSegunda = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaSegunda = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
								countDisponivelTerca = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoTerca = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaTerca = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
								countDisponivelQuarta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoQuarta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaQuarta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
								countDisponivelQuinta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoQuinta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaQuinta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
								countDisponivelSexta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoSexta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaSexta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							// Verifica se o count disponível é menor que 360 refeições
							if (countDisponivelSegunda < 360 || countDisponivelTerca < 360
									|| countDisponivelQuarta < 360 || countDisponivelQuinta < 360
									|| countDisponivelSexta < 360 || countExpiradoSegunda > 0 || countExpiradoTerca > 0
									|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {

								c.setId((Long) linhaDoBanco[0]);
								c.setData(dataVar);

								if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
										(Date) linhaDoBanco[1]) == null) {
									cardapio.add(c);
									
									count = 1;
								}					
							}

							if (countNãoSubsidiadaSegunda < 140 || countNãoSubsidiadaTerca < 140
									|| countNãoSubsidiadaQuarta < 140 || countNãoSubsidiadaQuinta < 140
									|| countNãoSubsidiadaSexta < 140) {

								if (count == 0) {

									c.setId((Long) linhaDoBanco[0]);
									c.setData(dataVar);

									if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
											(Date) linhaDoBanco[1]) == null) {
										
										int caiu = 0;
										
										for(Cardapio daLista : cardapio) {
											
											if(daLista.getData().equals(dataVar)) {
												caiu = 1;
											}
										}
											
										if(caiu == 0) {
											cardapio.add(c);
										}										
										
										count = 2;						
									}		

								} else {

									c.setId((Long) linhaDoBanco[0]);
									c.setData(dataVar);

									if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
											(Date) linhaDoBanco[1]) == null) {
										
										int caiu = 0;
										
										for(Cardapio daLista : cardapio) {
											
											if(daLista.getData().equals(dataVar)) {
												caiu = 1;
											}
										}
											
										if(caiu == 0) {
											cardapio.add(c);
										}	
										
										count = 3;
									}	
								}
							}

							contadores.add(count);
						}					
					}	

					dataAtual.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
		
		model.addAttribute("nome", nome);
		model.addAttribute("contadores", contadores);
		model.addAttribute("saldo", nf.format(saldo));
		model.addAttribute("todasAsDatas", cardapio);
		model.addAttribute("semanaAtual", new ReservasAdmin());
		model.addAttribute("valorRefeicaoS", clienteCategoria.getCategoria().getValorComSubsidio());
		model.addAttribute("valorRefeicaoC", clienteCategoria.getCategoria().getValorSemSubsidio());

		
		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
		String objectJSON = mapper.writeValueAsString(cardapio);// json string
		model.addAttribute("objectJSON", objectJSON);

		return "/semanaAtual/pagamento";
	}

	@PostMapping("/salvar")
	public String salvarSemanaAtual(@RequestParam("nome") String nome, @RequestParam("data") Integer[] idsCardapios,
			@RequestParam("tipoRefeicao") Integer[] idsTipoRefeicao,
			@RequestParam("tipoValor") Integer[] idsTipoValor,
			@RequestParam(value = "valor", required = false) String valor,
			@RequestParam(value = "recargas", required = false) String recargas,
			@RequestParam(value = "utilizarCreditos", required = false) Boolean creditos) {

		Cliente cliente = clienteRepository.findByNome(nome);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual
		
		List<Integer> tiposValoresCusto = new ArrayList<Integer>();
		List<Integer> tiposRefeicoesCusto = new ArrayList<Integer>();
		List<Integer> datasSelecionadasCusto = new ArrayList<Integer>();
		List<Integer> tiposValoresSubsidiada = new ArrayList<Integer>();
		List<Integer> tiposRefeicoesSubsidiada = new ArrayList<Integer>();
		List<Integer> tiposRefeicoesSelecionados = new ArrayList<Integer>();		
		List<Integer> datasSelecionadasSubsidiada = new ArrayList<Integer>();	
		
		for (int z = 0; z < idsTipoRefeicao.length; z++) {

			if (idsTipoRefeicao[z] != null) {
				tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
			}
		}

		for (int z = 0; z < idsTipoValor.length; z++) {

			if (idsTipoValor[z] != null) {

				if (idsTipoValor[z] % 2 == 0) {
					tiposValoresSubsidiada.add(idsTipoValor[z]);
					datasSelecionadasSubsidiada.add(idsCardapios[z]);
					tiposRefeicoesSubsidiada.add(tiposRefeicoesSelecionados.get(z));
				} else {
					tiposValoresCusto.add(idsTipoValor[z]);
					datasSelecionadasCusto.add(idsCardapios[z]);
					tiposRefeicoesCusto.add(tiposRefeicoesSelecionados.get(z));
				}
			}
		}

		if (creditos == null) {

			if (recargas == null) {
				
				if (!tiposValoresSubsidiada.isEmpty()) {
					
					Reserva reserva = new Reserva();
					reserva.setCliente(cliente);
					reserva.setDataReserva(timestamp);
					reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada")); 

					reservaRepository.save(reserva);

					List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
					Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

					for (int x = 0; x <= datasSelecionadasSubsidiada.size() - 1; x++) {

						Extrato extrato = null;							
						Reserva r = new Reserva();
						Cardapio c = new Cardapio();
						ReservaItem reservaItem = new ReservaItem();	

						r.setId(id);
						c.setId(datasSelecionadasSubsidiada.get(x).longValue());

						reservaItem.setReserva(r);
						reservaItem.setCardapio(c);
						reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
						reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
								? tipoRefeicaoRepository.findByDescricao("Vegetariano")
										: tipoRefeicaoRepository.findByDescricao("Tradicional"));
						reservaItem.setExtrato(extrato);

						reservaItemRepository.save(reservaItem);
					}
				}
				
				if (!tiposValoresCusto.isEmpty()) {
					
					Reserva reserva = new Reserva();
					reserva.setCliente(cliente);
					reserva.setDataReserva(timestamp);
					reserva.setTipoValor(tipoValorRepository.findByDescricao("Custo"));

					reservaRepository.save(reserva);

					List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
					Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

					for (int x = 0; x <= datasSelecionadasCusto.size() - 1; x++) {

						Extrato extrato = null;			
						Reserva r = new Reserva();
						Cardapio c = new Cardapio();
						ReservaItem reservaItem = new ReservaItem();

						r.setId(id);
						c.setId(datasSelecionadasCusto.get(x).longValue());

						reservaItem.setReserva(r);
						reservaItem.setCardapio(c);
						reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
						reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
								? tipoRefeicaoRepository.findByDescricao("Vegetariano")
										: tipoRefeicaoRepository.findByDescricao("Tradicional"));
						reservaItem.setExtrato(extrato);

						reservaItemRepository.save(reservaItem);
					}
				}
				
			} else {
				
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

				if (!tiposValoresSubsidiada.isEmpty()) {
					
					Reserva reserva = new Reserva();
					reserva.setCliente(cliente);
					reserva.setDataReserva(timestamp);
					reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada")); 

					reservaRepository.save(reserva);

					List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
					Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

					for (int x = 0; x <= datasSelecionadasSubsidiada.size() - 1; x++) {

						Extrato e = null;							
						Reserva r = new Reserva();
						Cardapio c = new Cardapio();
						ReservaItem reservaItem = new ReservaItem();	

						r.setId(id);
						c.setId(datasSelecionadasSubsidiada.get(x).longValue());

						reservaItem.setReserva(r);
						reservaItem.setCardapio(c);
						reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
						reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
								? tipoRefeicaoRepository.findByDescricao("Vegetariano")
										: tipoRefeicaoRepository.findByDescricao("Tradicional"));
						reservaItem.setExtrato(e);

						reservaItemRepository.save(reservaItem);
					}
				}
				
				if (!tiposValoresCusto.isEmpty()) {
					
					Reserva reserva = new Reserva();
					reserva.setCliente(cliente);
					reserva.setDataReserva(timestamp);
					reserva.setTipoValor(tipoValorRepository.findByDescricao("Custo"));

					reservaRepository.save(reserva);

					List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
					Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

					for (int x = 0; x <= datasSelecionadasCusto.size() - 1; x++) {

						Extrato e = null;			
						Reserva r = new Reserva();
						Cardapio c = new Cardapio();
						ReservaItem reservaItem = new ReservaItem();

						r.setId(id);
						c.setId(datasSelecionadasCusto.get(x).longValue());

						reservaItem.setReserva(r);
						reservaItem.setCardapio(c);
						reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
						reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
								? tipoRefeicaoRepository.findByDescricao("Vegetariano")
										: tipoRefeicaoRepository.findByDescricao("Tradicional"));
						reservaItem.setExtrato(e);

						reservaItemRepository.save(reservaItem);
					}
				}
			}
		} else {
			
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

			ClienteCategoria clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);
			
			List<Extrato> ultimoSaldoAtualizado = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

			BigDecimal saldoAtualizado = new BigDecimal(0.00);

			if (!ultimoSaldoAtualizado.isEmpty()) {
				saldoAtualizado = ultimoSaldoAtualizado.get(ultimoSaldoAtualizado.size() - 1).getSaldo();
			}
			
			if (!tiposValoresSubsidiada.isEmpty()) {
				
				Extrato eSubsidiada = new Extrato();
				eSubsidiada.setCliente(cliente);
				eSubsidiada.setDataTransacao(timestamp);

				BigDecimal transacao = BigDecimal.valueOf(idsCardapios.length)
						.multiply(clienteCategoria.getCategoria().getValorComSubsidio());

				eSubsidiada.setSaldo(saldoAtualizado.subtract(transacao));
				eSubsidiada.setTransacao(transacao.negate());

				extratoRepository.save(eSubsidiada);
				
				Reserva reserva = new Reserva();
				reserva.setCliente(cliente);
				reserva.setDataReserva(timestamp);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada")); 

				reservaRepository.save(reserva);

				List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
				Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

				for (int x = 0; x <= datasSelecionadasSubsidiada.size() - 1; x++) {
					
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					ReservaItem reservaItem = new ReservaItem();	

					r.setId(id);
					c.setId(datasSelecionadasSubsidiada.get(x).longValue());

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
							? tipoRefeicaoRepository.findByDescricao("Vegetariano")
									: tipoRefeicaoRepository.findByDescricao("Tradicional"));
					reservaItem.setExtrato(eSubsidiada);

					reservaItemRepository.save(reservaItem);
				}
			}
			
			if (!tiposValoresCusto.isEmpty()) {
				
				Extrato eCusto = new Extrato();
				eCusto.setCliente(cliente);
				eCusto.setDataTransacao(timestamp);

				BigDecimal transacao = BigDecimal.valueOf(idsCardapios.length)
						.multiply(clienteCategoria.getCategoria().getValorSemSubsidio());

				eCusto.setSaldo(saldoAtualizado.subtract(transacao));
				eCusto.setTransacao(transacao.negate());

				extratoRepository.save(eCusto);
				
				Reserva reserva = new Reserva();
				reserva.setCliente(cliente);
				reserva.setDataReserva(timestamp);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Custo"));

				reservaRepository.save(reserva);

				List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
				Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

				for (int x = 0; x <= datasSelecionadasCusto.size() - 1; x++) {
	
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					ReservaItem reservaItem = new ReservaItem();

					r.setId(id);
					c.setId(datasSelecionadasCusto.get(x).longValue());

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
							? tipoRefeicaoRepository.findByDescricao("Vegetariano")
									: tipoRefeicaoRepository.findByDescricao("Tradicional"));
					reservaItem.setExtrato(eCusto);

					reservaItemRepository.save(reservaItem);
				}
			}			
		}

		return "redirect:/semanaAtual";
	}
}