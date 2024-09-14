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
package org.terasoluna.tourreservation.app.managecustomer;

import org.junit.jupiter.api.Test;

import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class CustomerPassEqualsValidatorTest {

	/**
	 * check validate normal return
	 */
	@Test
	void validate01() {
		CustomerPassEqualsValidator validator = new CustomerPassEqualsValidator();
		CustomerForm customer = new CustomerForm();
		customer.setCustomerPass("12345");
		customer.setCustomerPassConfirm("12345");

		BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

		// run
		validator.validate(customer, result);

		// assert
		assertThat(result.hasErrors()).isEqualTo(false);
		assertThat(result.getErrorCount()).isEqualTo(0);
	}

	/**
	 * password check error
	 */
	@Test
	void validate02() {
		CustomerPassEqualsValidator validator = new CustomerPassEqualsValidator();
		CustomerForm customer = new CustomerForm();
		customer.setCustomerPass("12345");
		customer.setCustomerPassConfirm("1234");

		BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

		// run
		validator.validate(customer, result);

		// assert
		assertThat(result.hasErrors()).isEqualTo(true);

		FieldError error = result.getFieldError("customerPass");

		if (error != null) {
			assertThat(error.getCode()).isEqualTo("NotEquals.customerPass");
			assertThat(error.getDefaultMessage()).isEqualTo("Password and password confirm is not same.");
		}
		else {
			fail("error");
		}

	}

	/**
	 * check validate return nothing passConfirm is null
	 */
	@Test
	void validate03() {
		CustomerPassEqualsValidator validator = new CustomerPassEqualsValidator();
		CustomerForm customer = new CustomerForm();
		customer.setCustomerPass("12345");

		BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

		// run
		validator.validate(customer, result);

		// assert
		assertThat(result.hasErrors()).isEqualTo(false);
		assertThat(result.getErrorCount()).isEqualTo(0);
	}

	/**
	 * check validate return nothing password is null
	 */
	@Test
	void validate04() {
		CustomerPassEqualsValidator validator = new CustomerPassEqualsValidator();
		CustomerForm customer = new CustomerForm();
		customer.setCustomerPassConfirm("12345");

		BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

		// run
		validator.validate(customer, result);

		// assert
		assertThat(result.hasErrors()).isEqualTo(false);
		assertThat(result.getErrorCount()).isEqualTo(0);

	}

}
