/*
 * Copyright(c) 2013 NTT DATA Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.tourreservation.selenium;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThat;

class TourSearchValidateTest extends SeleniumTestSupport {

	@Test
	void requiredValidate() {
		// go to login screen
		driver.findElement(By.id("loginBtn")).click();

		// input credential
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("00000001");

		// login
		driver.findElement(By.id("loginBtn")).click();

		// go to search tour screen
		driver.findElement(By.id("searchTourBtn")).click();

		// input search criteria
		new Select(driver.findElement(By.id("depCode"))).selectByValue("");
		new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

		// search tour
		driver.findElement(By.id("searchBtn")).click();

		assertThat(driver.findElement(By.id("searchTourForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE).replace("{0}",
					getMessage(MessageKeys.DEPCODE)));

		// input credential
		new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
		new Select(driver.findElement(By.id("arrCode"))).selectByValue("");

		// search tour
		driver.findElement(By.id("searchBtn")).click();

		assertThat(driver.findElement(By.id("searchTourForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE).replace("{0}",
					getMessage(MessageKeys.ARRCODE)));
	}

	@Test
	void dateValidate() {
		// go to search tour screen
		driver.findElement(By.id("searchTourBtn")).click();

		// input credential
		new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
		new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

		int currentYear = LocalDate.now().getYear();

		new Select(driver.findElement(By.id("depYear"))).selectByValue(String.valueOf(currentYear));
		new Select(driver.findElement(By.id("depMonth"))).selectByValue("2");
		new Select(driver.findElement(By.id("depDay"))).selectByValue("30");

		// search tour
		driver.findElement(By.id("searchBtn")).click();

		assertThat(driver.findElement(By.id("searchTourForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.INCORRECTDATE_INPUTDATE));
	}

}
