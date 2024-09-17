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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_COMMON_LOGOUT;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_MENU_MENUMESSAGE;

class LogInLogOutTest extends SeleniumTestSupport {

	@Test
	void loginLogoff() {
		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(LABEL_TR_MENU_MENUMESSAGE) + "\n" + getMessage(LABEL_TR_COMMON_NOTLOGINMESSAGE));

		// go to login screen
		driver.findElement(By.id("loginBtn")).click();

		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(LABEL_TR_COMMON_NOTLOGINMESSAGE));

		// input credential
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("00000001");

		// login
		driver.findElement(By.id("loginBtn")).click();

		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(LABEL_TR_MENU_MENUMESSAGE) + "\n" + getMessage(LABEL_TR_COMMON_LOGOUT));

		// go to search tour screen
		driver.findElement(By.id("searchTourBtn")).click();

		// logout
		driver.findElement(By.id("logoutBtn")).click();

		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(LABEL_TR_MENU_MENUMESSAGE) + "\n" + getMessage(LABEL_TR_COMMON_NOTLOGINMESSAGE));
	}

	@Test
	void loginFailure() {
		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(LABEL_TR_MENU_MENUMESSAGE) + "\n" + getMessage(LABEL_TR_COMMON_NOTLOGINMESSAGE));

		// go to login screen
		driver.findElement(By.id("loginBtn")).click();

		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(LABEL_TR_COMMON_NOTLOGINMESSAGE));

		// input credential
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password1234");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("00000001");

		// login
		driver.findElement(By.id("loginBtn")).click();

		// login failure default message
		assertThat(driver.findElement(By.id("loginError")).getText()).isEqualTo("Bad credentials");

		// go to login screen
		driver.get(baseUrl() + "/login");

		assertThat(driver.findElement(By.id("messagesArea")).getText())
			.isEqualTo(getMessage(LABEL_TR_COMMON_NOTLOGINMESSAGE));
	}

}
