package me.joshua.demo.report.common;

import java.nio.file.Path;

public class Const {

	public static final String APP_PATH = System.getProperty("user.dir");

	public static final String JRXMLS_PATH = Path.of(APP_PATH, "jrxmls").toString();

	public static final String PUBLIC_PATH = Path.of(APP_PATH, "public").toString();

	public static final String MIME_OCT_STREAM = "application/octet-stream";

	public static final String MIME_PDF = "application/pdf";

	public static final String MIME_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
}
