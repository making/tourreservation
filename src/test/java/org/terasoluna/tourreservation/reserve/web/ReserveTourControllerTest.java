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
package org.terasoluna.tourreservation.reserve.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.auth.ReservationUserDetails;
import org.terasoluna.tourreservation.common.BusinessException;
import org.terasoluna.tourreservation.common.ResultMessages;
import org.terasoluna.tourreservation.customer.Customer;
import org.terasoluna.tourreservation.reserve.ReserveTourOutputBuilder;

import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.terasoluna.tourreservation.reserve.web.TourDetailOutputBuilder.tourDetailOutput;

class ReserveTourControllerTest {

	ReservationUserDetails userDetails;

	MockMvc mockMvc;

	ReserveTourHelper reserveTourHelper;

	ReserveTourController reserveTourController;

	@BeforeEach
	void setupForm() {

		// other members instantiation and assignment
		userDetails = new ReservationUserDetails(new Customer("xxxx"));
		reserveTourHelper = mock(ReserveTourHelper.class);

		// instantiate the controller to test
		reserveTourController = new ReserveTourController(reserveTourHelper);
		// build
		mockMvc = MockMvcBuilders.standaloneSetup(reserveTourController)
			.setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
			.addFilters(new SecurityContextPersistenceFilter())
			.build();
	}

	@Test
	void reserveFormSuccess() throws Exception {

		// Prepare get request
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/tours/123/reserve")
			.param("form", "")
			.with(user(userDetails));

		// Set mock behavior for helper method
		when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class), eq("123"), any(ReserveTourForm.class)))
			.thenReturn(tourDetailOutput().tourInfo(null).priceCalculateOutput(null).build());

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("reservetour/reserveForm"));
		results.andExpect(model().attribute("output", notNullValue()));

	}

	@Test
	void readFormSuccess() throws Exception {

		// Prepare get request
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/tours/123")
			.param("form", "")
			.with(user(userDetails));

		// Set mock behavior for helper method
		when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class), eq("123"), any(ReserveTourForm.class)))
			.thenReturn(tourDetailOutput().tourInfo(null).priceCalculateOutput(null).build());

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("reservetour/reserveForm"));
		results.andExpect(model().attribute("output", notNullValue()));

	}

	@Test
	void reserveTourConfirmSuccess() throws Exception {

		// Prepare POST request
		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/tours/123/reserve")
			.param("confirm", "")
			.with(user(userDetails));

		// Set mock behavior for helper method
		when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class), eq("123"), any(ReserveTourForm.class)))
			.thenReturn(tourDetailOutput().tourInfo(null).priceCalculateOutput(null).build());

		// Set form data
		postRequest.param("adultCount", "2");
		postRequest.param("childCount", "2");

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(model().hasNoErrors());
		results.andExpect(model().attribute("output", notNullValue()));
		results.andExpect(view().name("reservetour/reserveConfirm"));

	}

	@Test
	void reserveTourConfirmFail() throws Exception {
		// Prepare POST request
		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/tours/123/reserve")
			.param("confirm", "")
			.with(user(userDetails));

		// Set mock behavior for helper method
		when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class), eq("123"), any(ReserveTourForm.class)))
			.thenReturn(tourDetailOutput().tourInfo(null).priceCalculateOutput(null).build());

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("reservetour/reserveForm"));
		results.andExpect(model().hasErrors());
		results.andExpect(model().attributeErrorCount("reserveTourForm", 2));
		results.andExpect(model().attribute("output", notNullValue()));

	}

	@Test
	void reserveTourReserveSuccess() throws Exception {
		// Prepare POST request
		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/tours/123/reserve")
			.with(user(userDetails));

		// Set mock behavior for helper method
		when(reserveTourHelper.reserve(any(ReservationUserDetails.class), eq("123"), any(ReserveTourForm.class)))
			.thenReturn(ReserveTourOutputBuilder.reserveTourOutput()
				.priceCalculateOutput(null)
				.reserve(null)
				.customer(null)
				.tourInfo(null)
				.paymentTimeLimit(null)
				.build());

		postRequest.param("adultCount", "2");
		postRequest.param("childCount", "2");

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isFound());
		results.andExpect(model().hasNoErrors());
		results.andExpect(flash().attribute("output", notNullValue()));
		results.andExpect(view().name("redirect:/tours/{tourCode}/reserve?complete"));

	}

	@Test
	void reserveTourReserveFailByBusinessException() throws Exception {
		// Prepare POST request
		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/tours/123/reserve")
			.with(user(userDetails));

		// Set mock behavior for helper method
		when(reserveTourHelper.reserve(any(ReservationUserDetails.class), eq("123"), any(ReserveTourForm.class)))
			.thenThrow(new BusinessException(ResultMessages.error()));
		when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class), eq("123"), any(ReserveTourForm.class)))
			.thenReturn(tourDetailOutput().tourInfo(null).priceCalculateOutput(null).build());

		// Set form data
		postRequest.param("adultCount", "2");
		postRequest.param("childCount", "2");

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(model().hasNoErrors());
		results.andExpect(model().attribute("output", notNullValue()));
		results.andExpect(model().attribute("resultMessages", notNullValue()));
		results.andExpect(view().name("reservetour/reserveForm"));

	}

	@Test
	void reserveComplete() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/tours/123/reserve")
			.param("complete", "");

		// No Logic testing here
		// this will just test the request mapping part

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("reservetour/reserveComplete"));

	}

	@Test
	void reserveRedo() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/tours/123/reserve").param("redo", "");

		// No Logic testing here
		// this will just test the request mapping part

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("reservetour/reserveForm"));

	}

}
