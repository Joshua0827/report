package me.joshua.demo.report.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReportRequest {

	private String reportType;

	private String reportName;

	private Map<String, Object> parameters;

	private List<Map<String, Object>> detailList = new ArrayList<>();

	private List<ReportRequest> entityList;
}
