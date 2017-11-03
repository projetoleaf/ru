package com.github.projetoleaf.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Relatorios;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller
@RequestMapping("/planilhas")
public class PlanilhasController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@GetMapping
	public String planilhas(Model model) {

		List<String> planilhas = new ArrayList<String>();
		planilhas.add("Cursos");
		planilhas.add("Período");
		planilhas.add("Faculdade");
		planilhas.add("Refeição");
		planilhas.add("Idade");
		planilhas.add("Sexo");
		planilhas.add("Venda por categoria");

		model.addAttribute("planilhas", planilhas);
		model.addAttribute("tiposPlanilhas", new Relatorios());

		return "planilhas";
	}

	@GetMapping("/gerarXSLX")
	public void gerarXSLX(@RequestParam(value = "planilha", required = false) String[] planilhas,
			HttpServletResponse response) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Teste");
		XSSFRow row;

		Map<String, Object[]> dados = new TreeMap<String, Object[]>();
		List<Cliente> todosOsClientes = clienteRepository.findAll();

		int numero = 1;
		dados.put(String.valueOf(numero), new Object[] { "Nome", "Saldo" });

		for (Cliente cliente : todosOsClientes) {
			numero++;
			List<Extrato> todosOsExtratosDoCliente = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());
			BigDecimal saldo = todosOsExtratosDoCliente.isEmpty() ? new BigDecimal(0.00)
					: todosOsExtratosDoCliente.get(todosOsExtratosDoCliente.size() - 1).getSaldo();
			dados.put(String.valueOf(numero), new Object[] { cliente.getNome(), saldo.toString() });
		}

		Set<String> keyid = dados.keySet();
		int rowid = 0;

		for (String key : keyid) {
			row = sheet.createRow(rowid++);
			Object[] objectArr = dados.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=Teste.xlsx");

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		workbook.write(byteArrayOutputStream);
		response.setHeader("Content-Length", "" + byteArrayOutputStream.size());
		workbook.write(response.getOutputStream());
		workbook.close();
	}

	@GetMapping("/gerarPDF")
	public void gerarPDF(@RequestParam(value = "planilha", required = false) String[] planilhas,
			HttpServletResponse response) throws DocumentException, IOException {

		List<Cliente> todosOsClientes = clienteRepository.findAll();

		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100); // Width 100%
		table.setSpacingBefore(10f); // Space before table
		table.setSpacingAfter(10f); // Space after table

		// Set Column width
		float[] columnWidths = { 1f, 1f };
		table.setWidths(columnWidths);

		for (Cliente cliente : todosOsClientes) {

			List<Extrato> todosOsExtratosDoCliente = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());
			BigDecimal saldo = todosOsExtratosDoCliente.isEmpty() ? new BigDecimal(0.00)
					: todosOsExtratosDoCliente.get(todosOsExtratosDoCliente.size() - 1).getSaldo();

			PdfPCell cell1 = new PdfPCell(new Paragraph(cliente.getNome()));
			cell1.setBorderColor(BaseColor.BLUE);
			cell1.setPaddingLeft(10);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell cell2 = new PdfPCell(new Paragraph(saldo.toString()));
			cell2.setBorderColor(BaseColor.GREEN);
			cell2.setPaddingLeft(10);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

			table.addCell(cell1);
			table.addCell(cell2);
		}

		document.add(table);

		document.close();
		writer.close();

		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");

		response.setContentType("application/pdf");
		response.addHeader("content-disposition", "inline; filename=Teste.pdf");
		response.setContentLength(baos.size());
		ServletOutputStream out = response.getOutputStream();
		baos.writeTo(out);
		out.flush();
	}
}