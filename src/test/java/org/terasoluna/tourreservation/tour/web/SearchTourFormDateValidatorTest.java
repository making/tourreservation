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
package org.terasoluna.tourreservation.tour.web;

import org.junit.jupiter.api.Test;

import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.assertThat;

class SearchTourFormDateValidatorTest {

	/**
	 * check validate normal return
	 */
	@Test
	void validate01() {
		SearchTourFormDateValidator validator = new SearchTourFormDateValidator();
		SearchTourForm searchTourForm = new SearchTourForm();
		BindingResult result = new DirectFieldBindingResult(searchTourForm, "SearchTourcriteria");
		searchTourForm.setDepDay(1);
		searchTourForm.setDepMonth(1);
		searchTourForm.setDepYear(2000);

		// run
		validator.validate(searchTourForm, result);

		// assert
		assertThat(result.hasErrors()).isEqualTo(false);
	}

	/**
	 * Date parse Error
	 */
	@Test
	void validate02() {
		SearchTourFormDateValidator validator = new SearchTourFormDateValidator();
		SearchTourForm criteria = new SearchTourForm();
		BindingResult result = new DirectFieldBindingResult(criteria, "SearchTourcriteria");
		criteria.setDepDay(31);
		criteria.setDepMonth(2);
		criteria.setDepYear(2000);

		// run
		validator.validate(criteria, result);

		// assert
		assertThat(result.hasErrors()).isEqualTo(true);
		FieldError error = result.getFieldError("depYear");
		assertThat(error).isNotNull();
		assertThat(error.getCode()).isEqualTo("IncorrectDate.inputdate");
		assertThat(error.getDefaultMessage()).isEqualTo("Incorrect date was entered.");
	}

}
