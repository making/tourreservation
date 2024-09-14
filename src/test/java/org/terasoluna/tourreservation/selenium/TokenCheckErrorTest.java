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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.terasoluna.tourreservation.app.common.constants.MessageId;

import static org.assertj.core.api.Assertions.assertThat;

class TokenCheckErrorTest extends SeleniumTestSupport {

	@Test
	void customerRegisterToken() {

		// go to register screen
		driver.findElement(By.id("customerRegisterBtn")).click();

		// input new customer
		driver.findElement(By.name("customerKana")).sendKeys("テラ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		new Select(driver.findElement(By.id("customerBirthYear"))).selectByValue("2000");
		new Select(driver.findElement(By.id("customerBirthDay"))).selectByValue("1");
		new Select(driver.findElement(By.id("customerBirthMonth"))).selectByValue("12");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerMail")).sendKeys("terasoluna@nttd.co.jp");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		// falsify csrf token
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementsByName('_csrf')[0].setAttribute('type', 'text');");
		driver.findElement(By.name("_csrf")).clear();

		// register
		driver.findElement(By.id("registerBtn")).click();

		assertThat(driver.findElement(By.cssSelector("p")).getText()).isEqualTo(getMessage(MessageId.E_TR_FW_0006));
	}

	@Test
	void customerRegisterCSRFToken() {

		// go to register screen
		driver.findElement(By.id("customerRegisterBtn")).click();

		// input new customer
		driver.findElement(By.name("customerKana")).sendKeys("テラ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		new Select(driver.findElement(By.id("customerBirthYear"))).selectByValue("2000");
		new Select(driver.findElement(By.id("customerBirthDay"))).selectByValue("1");
		new Select(driver.findElement(By.id("customerBirthMonth"))).selectByValue("12");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerMail")).sendKeys("terasoluna@nttd.co.jp");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		// falsify CSRF token
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementsByName('_csrf')[0].setAttribute('type', 'text');");
		driver.findElement(By.name("_csrf")).clear();

		// register
		driver.findElement(By.id("registerBtn")).click();

		assertThat(driver.findElement(By.cssSelector("p")).getText()).isEqualTo(getMessage(MessageId.E_TR_FW_0006));

	}

	@Test
	void tourSearchRegisterToken() {

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
		LocalDate dt = LocalDate.now();
		LocalDate dtPlus = dt.plusDays(8);

		new Select(driver.findElement(By.id("depYear"))).selectByValue(Integer.toString(dtPlus.getYear()));
		new Select(driver.findElement(By.id("depMonth"))).selectByValue(Integer.toString(dtPlus.getMonthValue()));
		new Select(driver.findElement(By.id("depDay"))).selectByValue(Integer.toString(dtPlus.getDayOfMonth()));
		new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
		new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

		// search tour
		driver.findElement(By.id("searchBtn")).click();

		// paging
		driver.findElement(By.linkText("2")).click();

		// show top tour in table
		WebElement toursTable = driver.findElement(By.id("toursTable"));
		toursTable.findElements(By.tagName("a")).get(0).click();

		// input reservation contents
		driver.findElement(By.id("remarks")).sendKeys("TERASOLUNA Server Framework for Java (5.x)");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		// falsify csrf token
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementsByName('_csrf')[1].setAttribute('type', 'text');");
		driver.findElement(By.id("reserveTourForm")).findElement(By.name("_csrf")).clear();

		// reserve
		driver.findElement(By.id("reserveBtn")).click();

		assertThat(driver.findElement(By.cssSelector("p")).getText()).isEqualTo(getMessage(MessageId.E_TR_FW_0006));
	}

	@Test
	void reserveUpdateToken() {

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
		LocalDate dt = LocalDate.now();
		LocalDate dtPlus = dt.plusDays(8);

		new Select(driver.findElement(By.id("depYear"))).selectByValue(Integer.toString(dtPlus.getYear()));
		new Select(driver.findElement(By.id("depMonth"))).selectByValue(Integer.toString(dtPlus.getMonthValue()));
		new Select(driver.findElement(By.id("depDay"))).selectByValue(Integer.toString(dtPlus.getDayOfMonth()));
		new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
		new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

		// search tour
		driver.findElement(By.id("searchBtn")).click();

		// paging
		driver.findElement(By.linkText("2")).click();

		// show top tour in table
		WebElement toursTable = driver.findElement(By.id("toursTable"));
		toursTable.findElements(By.tagName("a")).get(0).click();

		// input reservation contents
		driver.findElement(By.id("remarks")).sendKeys("TERASOLUNA Server Framework for Java (5.x)");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		// reserve
		driver.findElement(By.id("reserveBtn")).click();

		// go to top screen(back to top)
		driver.findElement(By.id("goToTopLink")).click();

		// go to reserved tours list screen
		driver.findElement(By.id("reservedToursReferBtn")).click();

		// change top reservation in table
		driver.findElement(By.id("changeBtn")).click();

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		// falsify csrf token
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementsByName('_csrf')[0].setAttribute('type', 'text');");

		driver.findElement(By.id("manageReservationForm")).findElement(By.name("_csrf")).clear();

		// change reservation
		driver.findElement(By.id("changeBtn")).click();

		assertThat(driver.findElement(By.cssSelector("p")).getText()).isEqualTo(getMessage(MessageId.E_TR_FW_0006));

		// go to top screen(back to top)
		driver.findElement(By.id("goToTopLink")).click();

		// go to reserved tours list screen
		driver.findElement(By.id("reservedToursReferBtn")).click();

		// cancel top reservation in table
		driver.findElement(By.id("cancelBtn")).click();

		// cancel reservation
		driver.findElement(By.id("cancelBtn")).click();

	}

	@Test
	void reserveCancelToken() {

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
		LocalDate dt = LocalDate.now();
		LocalDate dtPlus = dt.plusDays(8);

		new Select(driver.findElement(By.id("depYear"))).selectByValue(Integer.toString(dtPlus.getYear()));
		new Select(driver.findElement(By.id("depMonth"))).selectByValue(Integer.toString(dtPlus.getMonthValue()));
		new Select(driver.findElement(By.id("depDay"))).selectByValue(Integer.toString(dtPlus.getDayOfMonth()));
		new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
		new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

		// search tour
		driver.findElement(By.id("searchBtn")).click();

		// paging
		driver.findElement(By.linkText("2")).click();

		// show top tour in table
		WebElement toursTable = driver.findElement(By.id("toursTable"));
		toursTable.findElements(By.tagName("a")).get(0).click();

		// input reservation contents
		driver.findElement(By.id("remarks")).sendKeys("TERASOLUNA Server Framework for Java (5.x)");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		// reserve
		driver.findElement(By.id("reserveBtn")).click();

		// go to top screen(back to top)
		driver.findElement(By.id("goToTopLink")).click();

		// go to reserved tours list screen
		driver.findElement(By.id("reservedToursReferBtn")).click();

		// cancel top reservation in table
		driver.findElement(By.id("cancelBtn")).click();

		// falsify csrf token
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementsByName('_csrf')[0].setAttribute('type', 'text');");
		driver.findElement(By.id("reservationCancelForm")).findElement(By.name("_csrf")).clear();

		// cancel reservation
		driver.findElement(By.id("cancelBtn")).click();

		assertThat(driver.findElement(By.cssSelector("p")).getText()).isEqualTo(getMessage(MessageId.E_TR_FW_0006));

		// go to top screen(back to top)
		driver.findElement(By.id("goToTopLink")).click();

		// go to reserved tours list screen
		driver.findElement(By.id("reservedToursReferBtn")).click();

		// cancel top reservation in table
		driver.findElement(By.id("cancelBtn")).click();

		// cancel reservation
		driver.findElement(By.id("cancelBtn")).click();

	}

}
