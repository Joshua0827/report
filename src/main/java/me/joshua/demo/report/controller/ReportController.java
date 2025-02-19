package me.joshua.demo.report.controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import me.joshua.demo.report.common.Const;
import me.joshua.demo.report.entity.ReportRequest;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@RestController
public class ReportController {

	@PostMapping("/createPdf")
	@CrossOrigin
	public void createPdf(HttpServletResponse response, @RequestBody ReportRequest entity) throws Exception {
		String filePath = Path.of(Const.JRXMLS_PATH, entity.getReportType() + ".jrxml").toString();
		Map<String, Object> parameters = entity.getParameters();
		List<Map<String, Object>> detailList = entity.getDetailList();
		JRDataSource dataSource = new JRBeanCollectionDataSource(detailList);

		JasperDesign design = JRXmlLoader.load(filePath);
		JasperReport report = JasperCompileManager.compileReport(design);
		JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

		String reportName = Optional.ofNullable(entity.getReportName()).orElse("unknown");
		StringBuilder contentDisposition = new StringBuilder();
		contentDisposition.append("attachment; ").append("filename=\"").append(reportName).append(".pdf\"");
		response.setContentType(Const.MIME_PDF);
		response.setHeader("Content-Disposition", contentDisposition.toString());
		JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
	}

	@PostMapping("/createWord")
	@CrossOrigin
	public void createWord(HttpServletResponse response, @RequestBody ReportRequest entity) throws Exception {
		String filePath = Path.of(Const.JRXMLS_PATH, entity.getReportType() + ".jrxml").toString();
		Map<String, Object> parameters = entity.getParameters();
		List<Map<String, Object>> detailList = entity.getDetailList();
		JRDataSource dataSource = new JRBeanCollectionDataSource(detailList);

		JasperDesign design = JRXmlLoader.load(filePath);
		JasperReport report = JasperCompileManager.compileReport(design);
		JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

		String reportName = Optional.ofNullable(entity.getReportName()).orElse("unknown");
		StringBuilder contentDisposition = new StringBuilder();
		contentDisposition.append("attachment; ").append("filename=\"").append(reportName).append(".docx\"");
		response.setContentType(Const.MIME_DOCX);
		response.setHeader("Content-Disposition", contentDisposition.toString());
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setExporterInput(new SimpleExporterInput(print));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

	@PostMapping("/createPdfLoop")
	@CrossOrigin
	public void createPdfLoop(HttpServletResponse response, @RequestBody ReportRequest entity) throws Exception {
		List<JasperPrint> printList = new ArrayList<>();
		String filePath = Path.of(Const.JRXMLS_PATH, entity.getReportType() + ".jrxml").toString();
		JasperDesign design = JRXmlLoader.load(filePath);
		JasperReport report = JasperCompileManager.compileReport(design);

		entity.getEntityList().forEach(data -> {
			Map<String, Object> parameters = data.getParameters();
			List<Map<String, Object>> detailList = data.getDetailList();
			JRDataSource dataSource = new JRBeanCollectionDataSource(detailList);

			try {
				printList.add(JasperFillManager.fillReport(report, parameters, dataSource));
			} catch (JRException e) {
				throw new RuntimeException(e);
			}
		});

		String reportName = Optional.ofNullable(entity.getReportName()).orElse("unknown");
		StringBuilder contentDisposition = new StringBuilder();
		contentDisposition.append("attachment; ").append("filename=\"").append(reportName).append(".pdf\"");
		response.setContentType(Const.MIME_PDF);
		response.setHeader("Content-Disposition", contentDisposition.toString());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(SimpleExporterInput.getInstance(printList));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

	@PostMapping("/createWordLoop")
	@CrossOrigin
	public void createWordLoop(HttpServletResponse response, @RequestBody ReportRequest entity) throws Exception {
		List<JasperPrint> printList = new ArrayList<>();
		String filePath = Path.of(Const.JRXMLS_PATH, entity.getReportType() + ".jrxml").toString();
		JasperDesign design = JRXmlLoader.load(filePath);
		JasperReport report = JasperCompileManager.compileReport(design);

		entity.getEntityList().forEach(data -> {
			Map<String, Object> parameters = data.getParameters();
			List<Map<String, Object>> detailList = data.getDetailList();
			JRDataSource dataSource = new JRBeanCollectionDataSource(detailList);

			try {
				printList.add(JasperFillManager.fillReport(report, parameters, dataSource));
			} catch (JRException e) {
				throw new RuntimeException(e);
			}
		});

		String reportName = Optional.ofNullable(entity.getReportName()).orElse("unknown");
		StringBuilder contentDisposition = new StringBuilder();
		contentDisposition.append("attachment; ").append("filename=\"").append(reportName).append(".docx\"");
		response.setContentType(Const.MIME_DOCX);
		response.setHeader("Content-Disposition", contentDisposition.toString());
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setExporterInput(SimpleExporterInput.getInstance(printList));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

}
