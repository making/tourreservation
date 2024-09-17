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
package org.terasoluna.tourreservation.customer.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.customer.Customer;
import org.terasoluna.tourreservation.customer.CustomerService;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.terasoluna.tourreservation.customer.web.CustomerControllerBuilder.customerController;

class CustomerControllerTest {

	MockMvc mockMvc;

	CustomerService customerService;

	@BeforeEach
	void setUp() {

		// other members instantiation and assignment
		customerService = mock(CustomerService.class);
		CustomerPassEqualsValidator cpev = new CustomerPassEqualsValidator();
		CustomerBirthdayValidator cbv = new CustomerBirthdayValidator();

		// instantiate the controller to test
		CustomerController customerController = customerController().customerService(customerService)
			.passwordEqualsValidator(cpev)
			.dateValidator(cbv)
			.initialBirthYear(2000)
			.initialBirthMonth(1)
			.initialBirthDay(1)
			.build();

		// build
		mockMvc = standaloneSetup(customerController).build();
	}

	/**
	 * Dispatches the Create form request to MockServlet. The difference between this test
	 * and the Mockito only version is that you’re not directly testing the result of the
	 * method call to your test instance; you’re testing the HttpServletResponse object
	 * that the call to your method generates.<br>
	 */
	@Test
	void createForm() throws Exception {

		MockHttpServletRequestBuilder getRequest = get("/customers/create").param("form", "");

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managecustomer/createForm"));

		// Also check the default data set in setupForm method
		// this will test the @ModelAttribute annotation

		results.andExpect(model().attribute("customerForm", hasProperty("customerBirthYear", is(2000))));
		results.andExpect(model().attribute("customerForm", hasProperty("customerBirthMonth", is(1))));
		results.andExpect(model().attribute("customerForm", hasProperty("customerBirthDay", is(1))));
	}

	@Test
	void createConfirmSuccess() throws Exception {

		// Prepare request
		MockHttpServletRequestBuilder postRequest = post("/customers/create").param("confirm", "");

		// Prepare form and set to POST request
		CustomerForm form = prepareNewForm();
		setFormForPost(form, postRequest);

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managecustomer/createConfirm"));

	}

	@Test
	void createConfirmFail() throws Exception {

		// Prepare request
		MockHttpServletRequestBuilder postRequest = post("/customers/create").param("confirm", "");

		// Prepare form
		CustomerForm form = prepareNewForm();
		// overwrite some values so that validation errors can occur
		// This will test @InitBinder and @Validated annotations

		// entered password and confirm password are different
		// passwordEqualsValidator will report error
		form.setCustomerPassConfirm("aaaaaaaa");

		// Set invalid date
		// dateValidator will report error
		form.setCustomerBirthMonth(2);
		form.setCustomerBirthDay(30);

		// Set invalid email
		// This will confirm working of @Validated annotation
		form.setCustomerMail("a.com");

		// set form to POST request
		setFormForPost(form, postRequest);
		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managecustomer/createForm"));
		results.andExpect(model().attributeErrorCount("customerForm", 3));
		results.andExpect(model().attributeHasFieldErrors("customerForm", "customerBirthYear"));
		results.andExpect(model().attributeHasFieldErrors("customerForm", "customerPass"));
		results.andExpect(model().attributeHasFieldErrors("customerForm", "customerMail"));

	}

	/**
	 * Test back button request<br>
	 */
	@Test
	void createRedo() throws Exception {
		MockHttpServletRequestBuilder postRequest = post("/customers/create").param("redo", "");

		// Set form to the request to simulate correct back button behaviour
		// Since the control will be on confirmation screen, form values may be different
		// fromLocalDate the default
		// Prepare form and set to POST request
		CustomerForm form = prepareNewForm();
		setFormForPost(form, postRequest);

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managecustomer/createForm"));
		results.andExpect(model().attribute("customerForm", form));

		CustomerForm backForm = (CustomerForm) results.andReturn().getRequest().getAttribute("customerForm");

		assertThat(backForm.toString()).isEqualTo(form.toString());

	}

	/**
	 * Test Customer create request<br>
	 */
	@Test
	void createSuccess() throws Exception {

		// Prepare request
		MockHttpServletRequestBuilder postRequest = post("/customers/create");

		when(customerService.register(any(Customer.class), eq("12345"))).thenReturn(new Customer("12345678"));

		CustomerForm form = prepareNewForm();
		setFormForPost(form, postRequest);

		ResultActions results = mockMvc.perform(postRequest);
		// check redirect http status : 302
		results.andExpect(status().isFound());
		results.andExpect(view().name("redirect:/customers/create?complete"));
		results.andExpect(flash().attribute("customer", notNullValue()));

	}

	/**
	 * Test Customer create request - Error case<br>
	 */
	@Test
	void createFail() throws Exception {
		// Prepare request
		MockHttpServletRequestBuilder postRequest = post("/customers/create");

		when(customerService.register(any(Customer.class), eq("12345"))).thenReturn(new Customer("12345678"));

		CustomerForm form = prepareNewForm();

		// Set some field such that it will throw validation error
		form.setCustomerName(null);

		setFormForPost(form, postRequest);

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(model().attributeErrorCount("customerForm", 1));
		results.andExpect(view().name("managecustomer/createForm"));

	}

	@Test
	void createComplete() throws Exception {
		MockHttpServletRequestBuilder getRequest = get("/customers/create").param("complete", "");
		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managecustomer/createComplete"));
	}

	private CustomerForm prepareNewForm() {
		CustomerForm form = new CustomerForm();
		form.setCustomerName("名前");
		form.setCustomerKana("カナ");
		form.setCustomerAdd("address");
		form.setCustomerPass("12345");
		form.setCustomerPassConfirm("12345");
		form.setCustomerBirthDay(2);
		form.setCustomerBirthMonth(2);
		form.setCustomerBirthYear(2002);
		form.setCustomerJob("会社員");
		form.setCustomerPost("123-3344");
		form.setCustomerTel("12-121212333");
		form.setCustomerMail("a@a.com");
		return form;
	}

	private void setFormForPost(CustomerForm form, MockHttpServletRequestBuilder postRequest) {
		postRequest.formField("customerName", form.getCustomerName());
		postRequest.formField("customerKana", form.getCustomerKana());
		postRequest.formField("customerAdd", form.getCustomerAdd());
		postRequest.formField("customerPass", form.getCustomerPass());
		postRequest.formField("customerPassConfirm", form.getCustomerPassConfirm());
		postRequest.formField("customerBirthDay", String.valueOf(form.getCustomerBirthDay()));
		postRequest.formField("customerBirthMonth", String.valueOf(form.getCustomerBirthMonth()));
		postRequest.formField("customerBirthYear", String.valueOf(form.getCustomerBirthYear()));
		postRequest.formField("customerJob", form.getCustomerJob());
		postRequest.formField("customerPost", form.getCustomerPost());
		postRequest.formField("customerTel", form.getCustomerTel());
		postRequest.formField("customerMail", form.getCustomerMail());
	}

}
