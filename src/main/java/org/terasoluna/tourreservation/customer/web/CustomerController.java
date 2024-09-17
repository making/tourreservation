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

import java.time.LocalDate;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.tourreservation.common.LegacyDate;
import org.terasoluna.tourreservation.customer.Customer;
import org.terasoluna.tourreservation.customer.CustomerBuilder;
import org.terasoluna.tourreservation.customer.CustomerService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handle request of customer register.
 */
@Controller
@RequestMapping(value = "customers")
@Builder(style = BuilderStyle.STAGED)
public class CustomerController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	private final CustomerService customerService;

	private final CustomerPassEqualsValidator passwordEqualsValidator;

	private final CustomerBirthdayValidator dateValidator;

	private final Integer initialBirthYear;

	private final Integer initialBirthMonth;

	private final Integer initialBirthDay;

	public CustomerController(CustomerService customerService, CustomerPassEqualsValidator passwordEqualsValidator,
			CustomerBirthdayValidator dateValidator,
			@Value("${customer.initialBirthYear:2024}") Integer initialBirthYear,
			@Value("${customer.initialBirthMonth:1}") Integer initialBirthMonth,
			@Value("${customer.initialBirthDay:1}") Integer initialBirthDay) {
		this.customerService = customerService;
		this.passwordEqualsValidator = passwordEqualsValidator;
		this.dateValidator = dateValidator;
		this.initialBirthYear = initialBirthYear;
		this.initialBirthMonth = initialBirthMonth;
		this.initialBirthDay = initialBirthDay;
	}

	@InitBinder("customerForm")
	public void initBinder(WebDataBinder webDataBinder) {
		// add custom validators to default bean validations
		webDataBinder.addValidators(passwordEqualsValidator, dateValidator);
	}

	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@ModelAttribute
	public CustomerForm setUpCustomerForm() {
		CustomerForm form = new CustomerForm();
		form.setCustomerBirthYear(initialBirthYear);
		form.setCustomerBirthMonth(initialBirthMonth);
		form.setCustomerBirthDay(initialBirthDay);
		log.debug("populate form {}", form);
		return form;
	}

	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@GetMapping(value = "create", params = "form")
	public String createForm() {
		return "managecustomer/createForm";
	}

	/**
	 * Return to main input screen
	 * @return
	 */
	@PostMapping(value = "create", params = "redo")
	public String createRedo(CustomerForm form) {
		return "managecustomer/createForm";
	}

	/**
	 * shows the confirmation screen before registering a new customer
	 * @param form
	 * @param result
	 * @return
	 */
	@PostMapping(value = "create", params = "confirm")
	public String createConfirm(@Validated CustomerForm form, BindingResult result) {
		if (result.hasErrors()) {
			return createRedo(form);
		}
		return "managecustomer/createConfirm";
	}

	/**
	 * registers a new customer
	 * @param form
	 * @param result
	 * @param redirectAttr
	 * @return
	 */
	@PostMapping("create")
	public String create(@Validated CustomerForm form, BindingResult result, RedirectAttributes redirectAttr) {
		if (result.hasErrors()) {
			return createRedo(form);
		}

		Customer customer = CustomerBuilder.customer()
			.customerName(form.getCustomerName())
			.customerKana(form.getCustomerKana())
			.customerPass(form.getCustomerPass())
			.customerBirth(LegacyDate.fromLocalDate(LocalDate.of(form.getCustomerBirthYear(),
					form.getCustomerBirthMonth(), form.getCustomerBirthDay())))
			.customerJob(form.getCustomerJob())
			.customerMail(form.getCustomerMail())
			.customerTel(form.getCustomerTel())
			.customerPost(form.getCustomerPost())
			.customerAdd(form.getCustomerAdd())
			.build();

		Customer registeredCustomer = customerService.register(customer, form.getCustomerPass());
		redirectAttr.addFlashAttribute(registeredCustomer);
		return "redirect:/customers/create?complete";
	}

	/**
	 * Redirected to the result page after registering a customer
	 * @return
	 */
	@GetMapping(value = "create", params = "complete")
	public String createComplete() {
		return "managecustomer/createComplete";
	}

}
