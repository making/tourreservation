package org.terasoluna.tourreservation.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@TestConfiguration(proxyBeanMethods = false)
public class SeleniumConfig {

	@Bean
	@Scope("prototype")
	public WebDriver webDriver() {
		return new HtmlUnitDriver(true);
	}

}
