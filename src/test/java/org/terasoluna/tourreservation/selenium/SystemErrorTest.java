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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.terasoluna.tourreservation.app.common.constants.MessageId;

class SystemErrorTest extends SeleniumTestSupport {

	@Test
	void systemError() {
		// go to login screen
		driver.findElement(By.id("loginBtn")).click();

		// input credential
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("00000001");

		// login
		driver.findElement(By.id("loginBtn")).click();

		// go to reserved tours list screen
		driver.findElement(By.id("reservedToursReferBtn")).click();

		driver.get(baseUrl() + "/reservations/aaaaaa");

		assertThat(driver.findElement(By.cssSelector("p")).getText()).isEqualTo(getMessage(MessageId.E_TR_FW_0003));
	}

}
