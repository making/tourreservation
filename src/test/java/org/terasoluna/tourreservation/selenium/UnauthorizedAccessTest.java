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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.terasoluna.tourreservation.app.common.constants.MessageId;

import static org.assertj.core.api.Assertions.assertThat;

class UnauthorizedAccessTest extends SeleniumTestSupport {

	@Test
	void accessToSecuredResourceInUnauthorized() {

		// go to login screen
		driver.findElement(By.id("loginBtn")).click();

		// input credential
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("00000002");

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
		toursTable.findElements(By.tagName("a")).getFirst().click();

		// input reservation contents
		driver.findElement(By.id("remarks")).sendKeys("test");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		// reserve
		driver.findElement(By.id("reserveBtn")).click();

		// go to top screen(back to top)
		driver.findElement(By.id("goToTopLink")).click();

		// go to reserved tours list screen
		driver.findElement(By.id("reservedToursReferBtn")).click();

		WebElement reservationsTable = driver.findElement(By.id("reservationsTable"));
		String reserveNumber = reservationsTable.findElement(By.xpath(".//tr[2]/td[1]")).getText();

		// logout
		driver.findElement(By.id("logoutBtn")).click();

		driver.get(baseUrl() + "/reservations/" + reserveNumber);

		assertThat(driver.findElement(By.cssSelector("p.box")).getText())
			.isEqualTo(getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE));

		// login
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("00000002");
		driver.findElement(By.id("loginBtn")).click();

		assertThat(driver.findElement(By.id("screenName")).getText())
			.isEqualTo(getMessage(MessageId.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONSHOWSCREENTITLEMESSAGE));

		WebElement reserveTable = driver.findElement(By.id("reserveTable"));
		assertThat(reserveNumber).isEqualTo(reserveTable.findElement(By.xpath(".//tr[1]/td[2]")).getText());
	}

	@Test
	void accessToOtherOwnerResource() {

		// go to login screen
		driver.findElement(By.id("loginBtn")).click();

		// input credential
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("00000002");

		// login
		driver.findElement(By.id("loginBtn")).click();

		// go to reserved tours list screen
		driver.findElement(By.id("reservedToursReferBtn")).click();

		WebElement reservationsTable = driver.findElement(By.id("reservationsTable"));
		String reserveNumber = reservationsTable.findElement(By.xpath(".//tr[2]/td[1]")).getText();

		// logout
		driver.findElement(By.id("logoutBtn")).click();

		// go to login screen
		driver.findElement(By.id("loginBtn")).click();

		// input credential
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("00000001");

		// login
		driver.findElement(By.id("loginBtn")).click();

		// show other user's reservation
		driver.get(baseUrl() + "/reservations/" + reserveNumber);

		assertThat(driver.findElement(By.cssSelector("p")).getText()).isEqualTo(getMessage(MessageId.E_TR_FW_0006));
	}

}
