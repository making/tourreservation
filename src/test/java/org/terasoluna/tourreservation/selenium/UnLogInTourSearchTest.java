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

class UnLogInTourSearchTest extends SeleniumTestSupport {

	@Test
	void unLogInTourSearch() {

		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(MessageId.LABEL_TR_MENU_MENUMESSAGE) + "\n"
					+ getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE));

		// go to search tour screen
		driver.findElement(By.id("searchTourBtn")).click();

		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE));

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

		WebElement toursTable = driver.findElement(By.id("toursTable"));

		String basePrice = toursTable.findElement(By.xpath(".//tr[2]/td[7]")).getText().replaceAll("[^0-9]", "");

		// show top tour in table
		toursTable.findElements(By.tagName("a")).getFirst().click();

		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE));

		assertThat(driver.findElement(By.id("screenName")).getText())
			.isEqualTo(getMessage(MessageId.LABEL_TR_SEARCHTOUR_TITLEDETAILSCREENMESSAGE));

		WebElement priceTable = driver.findElement(By.id("priceTable"));
		assertThat(priceTable.findElement(By.xpath(".//tr[2]/td[2]")).getText().replaceAll("[^0-9]", ""))
			.isEqualTo(basePrice);

		// go to login screen
		driver.findElement(By.id("loginBtn")).click();

		// input credential
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("00000001");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password");

		// login
		driver.findElement(By.id("loginBtn")).click();

		assertThat(driver.findElement(By.id("screenName")).getText())
			.isEqualTo(getMessage(MessageId.LABEL_TR_SEARCHTOUR_TITLEDETAILSCREENMESSAGE));
	}

}
