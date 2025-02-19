package me.joshua.demo.report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.joshua.demo.report.common.Const;

@SpringBootApplication
@Slf4j
public class ReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}

	@Component
	public static class DirectoryInitializer implements ApplicationRunner {

		@Override
		public void run(ApplicationArguments args) throws Exception {
			checkAndCreateDirectory(Const.JRXMLS_PATH, Const.PUBLIC_PATH);
		}

		private void checkAndCreateDirectory(String... pathStrings) {
			for (String pathString : pathStrings) {
				Path path = Path.of(pathString);
				if (!Files.exists(path)) {
					try {
						Files.createDirectories(path);
					} catch (IOException e) {
						log.error("Failed to create directory: {}", path);
						System.exit(1);
					}
				}
			}
		}
	}

}
