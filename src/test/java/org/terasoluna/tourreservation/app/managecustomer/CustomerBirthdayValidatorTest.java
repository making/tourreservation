package org.terasoluna.tourreservation.app.managecustomer;

import org.junit.jupiter.api.Test;

import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class CustomerBirthdayValidatorTest {

	/**
	 * check validate normal return
	 */
	@Test
	void validate01() {
		CustomerBirthdayValidator validator = new CustomerBirthdayValidator();

		CustomerForm customer = new CustomerForm();
		customer.setCustomerBirthYear(2011);
		customer.setCustomerBirthMonth(2);
		customer.setCustomerBirthDay(28);

		BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

		// run
		validator.validate(customer, result);

		// assert
		assertThat(result.hasErrors()).isEqualTo(false);
	}

	/**
	 * Date parse Error
	 */
	@Test
	void validate02() {
		CustomerBirthdayValidator validator = new CustomerBirthdayValidator();

		CustomerForm customer = new CustomerForm();
		customer.setCustomerBirthYear(2011);
		customer.setCustomerBirthMonth(2);
		customer.setCustomerBirthDay(29);

		BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

		// run
		validator.validate(customer, result);

		// assert
		assertThat(result.hasErrors()).isEqualTo(true);

		FieldError error = result.getFieldError("customerBirthYear");

		if (error != null) {
			assertThat(error.getCode()).isEqualTo("IncorrectDate.customerBirth");
			assertThat(error.getDefaultMessage()).isEqualTo("Incorrect date was entered.");

		}
		else {
			fail("error");
		}
	}

	/**
	 * check leap year
	 */
	@Test
	void validate03() {
		CustomerBirthdayValidator validator = new CustomerBirthdayValidator();

		CustomerForm customer = new CustomerForm();
		customer.setCustomerBirthYear(2012);
		customer.setCustomerBirthMonth(2);
		customer.setCustomerBirthDay(29);

		BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

		// run
		validator.validate(customer, result);

		// assert
		assertThat(result.hasErrors()).isEqualTo(false);
	}

}