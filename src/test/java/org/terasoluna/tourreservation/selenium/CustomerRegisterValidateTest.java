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

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRegisterValidateTest extends SeleniumTestSupport {

	@Test
	void customerRegisterRequiredValidate() {

		// go to register screen
		driver.findElement(By.id("customerRegisterBtn")).click();

		// input new customer
		driver.findElement(By.name("customerKana")).sendKeys("");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(multiMessageAssert(
				getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE).replace("{0}",
						getMessage(MessageKeys.CUSTOMERKANA)) + "\n"
						+ getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERKANA),
				driver.findElement(By.id("customerForm.errors")).getText(), "\n"))
			.isTrue();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(multiMessageAssert(
				getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE).replace("{0}",
						getMessage(MessageKeys.CUSTOMERNAME)) + "\n"
						+ getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERNAME),
				driver.findElement(By.id("customerForm.errors")).getText(), "\n"))
			.isTrue();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE).replace("{0}",
					getMessage(MessageKeys.CUSTOMERJOB)));

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(multiMessageAssert(
				getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_SIZE_MESSAGE)
					.replace("{0}", getMessage(MessageKeys.CUSTOMERTEL))
					.replace("{min}", "10")
					.replace("{max}", "13") + "\n" + getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERTEL),
				driver.findElement(By.id("customerForm.errors")).getText(), "\n"))
			.isTrue();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_PATTERN_MESSAGE)
				.replace("{0}", getMessage(MessageKeys.CUSTOMERPOST))
				.replace("{regexp}", "[0-9]{3}-[0-9]{4}"));

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE).replace("{0}",
					getMessage(MessageKeys.CUSTOMERADD)));

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(multiMessageAssert(
				getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERPASS) + "\n"
						+ getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE).replace("{0}",
								getMessage(MessageKeys.CUSTOMERPASS))
						+ "\n"
						+ getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_SIZE_MESSAGE)
							.replace("{0}", getMessage(MessageKeys.CUSTOMERPASS))
							.replace("{min}", "4")
							.replace("{max}", "20")
						+ "\n" + getMessage(MessageKeys.NOTEQUALS_CUSTOMERPASS),
				driver.findElement(By.id("customerForm.errors")).getText(), "\n"))
			.isTrue();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(multiMessageAssert(
				getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE).replace("{0}",
						getMessage(MessageKeys.CUSTOMERPASSCONFIRM)) + "\n"
						+ getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERPASSCONFIRM) + "\n"
						+ getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_SIZE_MESSAGE)
							.replace("{0}", getMessage(MessageKeys.CUSTOMERPASSCONFIRM))
							.replace("{min}", "4")
							.replace("{max}", "20")
						+ "\n" + getMessage(MessageKeys.NOTEQUALS_CUSTOMERPASS),
				driver.findElement(By.id("customerForm.errors")).getText(), "\n"))
			.isTrue();

	}

	@Test
	void customerRegisterFormatValidate() {

		// go to register screen
		driver.findElement(By.id("customerRegisterBtn")).click();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("SSSZ2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_PATTERN_MESSAGE)
				.replace("{0}", getMessage(MessageKeys.CUSTOMERPOST))
				.replace("{regexp}", "[0-9]{3}-[0-9]{4}"));

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerMail")).sendKeys("FDSAGDD");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_EMAIL_MESSAGE));
	}

	@Test
	void customerRegisterDateValidate() {

		// go to register screen
		driver.findElement(By.id("customerRegisterBtn")).click();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		new Select(driver.findElement(By.id("customerBirthYear"))).selectByValue("2000");
		new Select(driver.findElement(By.id("customerBirthDay"))).selectByValue("31");
		new Select(driver.findElement(By.id("customerBirthMonth"))).selectByValue("2");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.INCORRECTDATE_CUSTOMERBIRTH));
	}

	@Test
	void customerRegisterNumberValidate() {

		// go to register screen
		driver.findElement(By.id("customerRegisterBtn")).click();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		new Select(driver.findElement(By.id("customerBirthYear"))).selectByValue("2000");
		new Select(driver.findElement(By.id("customerBirthDay"))).selectByValue("2");
		new Select(driver.findElement(By.id("customerBirthMonth"))).selectByValue("2");

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("ter");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(multiMessageAssert(
				getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_SIZE_MESSAGE)
					.replace("{0}", getMessage(MessageKeys.CUSTOMERPASS))
					.replace("{min}", "4")
					.replace("{max}", "20") + "\n" + getMessage(MessageKeys.NOTEQUALS_CUSTOMERPASS),
				driver.findElement(By.id("customerForm.errors")).getText(), "\n"))
			.isTrue();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("ter123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("ter");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(multiMessageAssert(
				getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_SIZE_MESSAGE)
					.replace("{0}", getMessage(MessageKeys.CUSTOMERPASSCONFIRM))
					.replace("{min}", "4")
					.replace("{max}", "20") + "\n" + getMessage(MessageKeys.NOTEQUALS_CUSTOMERPASS),
				driver.findElement(By.id("customerForm.errors")).getText(), "\n"))
			.isTrue();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.JAKARTA_VALIDATION_CONSTRAINTS_SIZE_MESSAGE)
				.replace("{0}", getMessage(MessageKeys.CUSTOMERTEL))
				.replace("{min}", "10")
				.replace("{max}", "13"));
	}

	@Test
	void customerRegisterTypeValidate() {

		// go to register screen
		driver.findElement(By.id("customerRegisterBtn")).click();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("test");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERKANA));

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("test");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERNAME));

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("@@terasoluna-#");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("@@terasoluna-#");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(multiMessageAssert(
				getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERPASSCONFIRM) + "\n"
						+ getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERPASS),
				driver.findElement(By.id("customerForm.errors")).getText(), "\n"))
			.isTrue();

		// input new customer
		driver.findElement(By.id("customerKana")).clear();
		driver.findElement(By.id("customerName")).clear();
		driver.findElement(By.id("customerJob")).clear();
		driver.findElement(By.id("customerTel")).clear();
		driver.findElement(By.id("customerPost")).clear();
		driver.findElement(By.id("customerAdd")).clear();
		driver.findElement(By.id("customerPass")).clear();
		driver.findElement(By.id("customerPassConfirm")).clear();

		driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
		driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
		driver.findElement(By.name("customerJob")).sendKeys("FW");
		driver.findElement(By.name("customerTel")).sendKeys("090-999a9999");
		driver.findElement(By.name("customerPost")).sendKeys("333-2222");
		driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
		driver.findElement(By.name("customerPass")).sendKeys("tera123");
		driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

		// go to confirm screen
		driver.findElement(By.id("confirmBtn")).click();

		assertThat(driver.findElement(By.id("customerForm.errors")).getText())
			.isEqualTo(getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERTEL));
	}

	private Boolean multiMessageAssert(String expectedStr, String actualStr, String splitStr) {
		String[] expectedlist = expectedStr.split(splitStr);
		String[] actualStrlist = actualStr.split(splitStr);

		List<String> expected = Arrays.asList(expectedlist);

		for (int i = 0; i < actualStrlist.length; i++) {
			if (!expected.contains(actualStrlist[i])) {
				return false;
			}
		}

		return true;
	}

}
