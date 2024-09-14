package org.terasoluna.tourreservation.selenium;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.terasoluna.tourreservation.TestcontainersConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import({ TestcontainersConfiguration.class, SeleniumConfig.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SeleniumTestSupport {

	@Autowired
	WebDriver driver;

	@Autowired
	MessageSource messageSource;

	@LocalServerPort
	int port;

	@BeforeEach
	protected void setUp() {
		driver.get(baseUrl());
	}

	@AfterEach
	void tearDown() {
		driver.quit();
	}

	protected String baseUrl() {
		return "http://localhost:" + port;
	}

	protected String getMessage(String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}

	protected void assertTableContents(WebElement table, int rowOffset, int cellIndex, ValueEditor valueEditor,
			String... expectedContents) {
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		assertThat(tableRows).hasSize(expectedContents.length + rowOffset);
		for (int i = rowOffset; i < (tableRows.size() - rowOffset); i++) {
			WebElement row = tableRows.get(i);
			WebElement contentCell = row.findElements(By.tagName("td")).get(cellIndex);
			String text = contentCell.getText();
			if (valueEditor != null) {
				text = valueEditor.edit(text);
			}
			assertThat(text).isEqualTo(expectedContents[i - rowOffset]);
		}
	}

	protected interface ValueEditor {

		String edit(String text);

	}

}
