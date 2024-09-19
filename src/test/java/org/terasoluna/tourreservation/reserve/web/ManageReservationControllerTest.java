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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.auth.ReservationUserDetails;
import org.terasoluna.tourreservation.common.BusinessException;
import org.terasoluna.tourreservation.common.ResultMessages;
import org.terasoluna.tourreservation.reserve.Reserve;
import org.terasoluna.tourreservation.reserve.ReserveService;

import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.terasoluna.tourreservation.reserve.ReservationUpdateOutputBuilder.reservationUpdateOutput;
import static org.terasoluna.tourreservation.reserve.web.DownloadPDFOutputBuilder.downloadPDFOutput;
import static org.terasoluna.tourreservation.reserve.web.ManageReservationControllerBuilder.manageReservationController;
import static org.terasoluna.tourreservation.reserve.web.ReservationDetailOutputBuilder.reservationDetailOutput;

class ManageReservationControllerTest {

	MockMvc mockMvc;

	ManageReservationController manageReservationController;

	ManageReservationHelper manageReservationHelper;

	ReserveService reserveService;

	ReservationUserDetails userDetails;

	@BeforeEach
	void setupForm() {

		// other members instantiation and assignment
		manageReservationHelper = mock(ManageReservationHelper.class);
		reserveService = mock(ReserveService.class);
		userDetails = mock(ReservationUserDetails.class);

		// instantiate the controller to test
		manageReservationController = manageReservationController().manageReservationHelper(manageReservationHelper)
			.reserveService(reserveService)
			.reservationReportPdfName("foo")
			.build();

		// build
		mockMvc = MockMvcBuilders.standaloneSetup(manageReservationController)
			.setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
			.build();
	}

	@Test
	void manageReservationListSuccess() throws Exception {

		// Prepare get request
		MockHttpServletRequestBuilder getRequest = get("/reservations/me");

		// Set mock behavior for helper method
		when(manageReservationHelper.list(userDetails)).thenReturn(new ArrayList<>());
		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/list"));
		results.andExpect(model().attribute("rows", notNullValue()));
	}

	@Test
	void manageReservationDetailSuccess() throws Exception {

		// Prepare get request
		MockHttpServletRequestBuilder getRequest = get("/reservations/123");

		// Set mock behavior for helper method
		when(manageReservationHelper.findDetail("123")).thenReturn(reservationDetailOutput().priceCalculateOutput(null)
			.reserve(null)
			.customer(null)
			.paymentTimeLimit(null)
			.limitExceeding(null)
			.build());

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/detailForm"));
		results.andExpect(model().attribute("output", notNullValue()));

	}

	@Test
	void manageReservationUpdateFormSuccess() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder getRequest = get("/reservations/123/update").param("form", "");

		// Set mock behavior for service method
		when(reserveService.findOneWithTourInfo("123")).thenReturn(new Reserve());

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/updateForm"));
		results.andExpect(model().attribute("reserve", notNullValue()));
	}

	@Test
	void manageReservationRedoSuccess() throws Exception {
		// Prepare post request
		MockHttpServletRequestBuilder postRequest = post("/reservations/123/update").param("redo", "");

		// Set mock behavior for service method
		when(reserveService.findOneWithTourInfo("123")).thenReturn(new Reserve());

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/updateForm"));
		results.andExpect(model().attribute("reserve", notNullValue()));

	}

	@Test
	void manageReservationUpdateConfirmSuccess() throws Exception {
		// Prepare post request
		MockHttpServletRequestBuilder postRequest = post("/reservations/123/update").param("confirm", "");

		// Set mock behavior for helper method
		when(manageReservationHelper.findDetail(eq("123"), any(ManageReservationForm.class)))
			.thenReturn(reservationDetailOutput().priceCalculateOutput(null)
				.reserve(null)
				.customer(null)
				.paymentTimeLimit(null)
				.limitExceeding(null)
				.build());

		// Set form data to pass @Validated check
		postRequest.formField("reserveNo", "123");
		postRequest.formField("adultCount", "1");
		postRequest.formField("childCount", "2");

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/updateConfirm"));
		results.andExpect(model().attribute("output", notNullValue()));
	}

	@Test
	void manageReservationUpdateConfirmFail() throws Exception {
		// Prepare post request
		MockHttpServletRequestBuilder postRequest = post("/reservations/123/update").param("confirm", "");

		// Set mock behavior for helper method
		when(reserveService.findOneWithTourInfo("123")).thenReturn(new Reserve());

		// Set form data to fail the @Validated check
		postRequest.formField("reserveNo", "123");
		postRequest.formField("adultCount", "10"); // Error
		postRequest.formField("childCount", "10"); // Error

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/updateForm"));
		results.andExpect(model().attribute("reserve", notNullValue()));
		results.andExpect(model().hasErrors());
		results.andExpect(model().attributeErrorCount("manageReservationForm", 2));
	}

	@Test
	void manageReservationUpdateSuccess() throws Exception {
		// Prepare post request
		MockHttpServletRequestBuilder postRequest = post("/reservations/123/update");

		// Set mock behavior for helper method
		when(reserveService.update(any(ReserveService.ReservationUpdateInput.class))).thenReturn(
				reservationUpdateOutput().priceCalculateOutput(null).reserve(null).paymentTimeLimit(null).build());

		// Set form data to pass @Validated check
		postRequest.formField("reserveNo", "123");
		postRequest.formField("adultCount", "1");
		postRequest.formField("childCount", "2");

		ResultActions results = mockMvc.perform(postRequest);
		// check redirect success
		results.andExpect(status().isFound());
		results.andExpect(view().name("redirect:/reservations/{reserveNo}/update?complete"));
		results.andExpect(flash().attribute("output", notNullValue()));
	}

	@Test
	void manageReservationUpdateFail() throws Exception {
		// Prepare post request
		MockHttpServletRequestBuilder postRequest = post("/reservations/123/update");

		// Set mock behavior for helper method
		// Set mock behavior for service method
		when(reserveService.findOneWithTourInfo("123")).thenReturn(new Reserve());

		// Set form data to pass @Validated check
		postRequest.formField("reserveNo", "123");
		postRequest.formField("adultCount", "10");
		postRequest.formField("childCount", "20");

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/updateForm"));
		results.andExpect(model().attribute("reserve", notNullValue()));
		results.andExpect(model().hasErrors());
		results.andExpect(model().attributeErrorCount("manageReservationForm", 2));
	}

	@Test
	void manageReservationComplete() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder getRequest = get("/reservations/123/update").param("complete", "");

		// No Logic testing here
		// this will just test the request mapping part

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/updateComplete"));
	}

	@Test
	void manageReservationDownloadPDF() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder getRequest = get("/reservations/123/pdf");

		// TODO
		Map<String, String> clExistenceMap = new LinkedHashMap<>();
		clExistenceMap.put("0", "No");
		clExistenceMap.put("1", "Yes");
		when(manageReservationHelper.createPDF("123", Locale.ENGLISH))
			.thenReturn(downloadPDFOutput().referenceName(null)
				.referenceEmail(null)
				.referenceTel(null)
				.paymentMethod(null)
				.paymentCompanyName(null)
				.paymentAccount(null)
				.childCount(null)
				.tourName(null)
				.accomName(null)
				.customerKana(null)
				.customerTel(null)
				.adultUnitPrice(null)
				.reservedDay(null)
				.conductor(null)
				.tourAbs(null)
				.customerAdd(null)
				.customerJob(null)
				.tourDays(null)
				.depDay(null)
				.customerName(null)
				.childUnitPrice(null)
				.depName(null)
				.customerBirth(null)
				.arrName(null)
				.customerMail(null)
				.adultCount(null)
				.customerCode(null)
				.reserveNo(null)
				.remarks(null)
				.accomTel(null)
				.customerPost(null)
				.printDay(null)
				.adultPrice(null)
				.childPrice(null)
				.sumPrice(null)
				.paymentTimeLimit(null)
				.build());

		// No Logic testing here
		// this will just test the request mapping part

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("reservationReportPdfStamperView"));
		results.andExpect(model().attribute("downloadPDFOutput", Matchers.notNullValue()));
	}

	@Test
	void manageReservationCancelComplete() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder getRequest = get("/reservations/123/cancel").param("complete", "");

		// No Logic testing here
		// this will just test the request mapping part

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/cancelComplete"));
	}

	@Test
	void manageReservationBackList() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder getRequest = post("/reservations/123/cancel").param("backTolist", "");

		// No Logic testing here
		// this will just test the request mapping part

		ResultActions results = mockMvc.perform(getRequest);
		// Test Redirect
		results.andExpect(status().isFound());
		results.andExpect(view().name("redirect:/reservations/me"));

	}

	@Test
	void manageReservationUpdateBackList() throws Exception {
		// Prepare post request
		MockHttpServletRequestBuilder postRequest = post("/reservations/123/update").param("backTolist", "");

		// No Logic testing here
		// this will just test the request mapping part

		ResultActions results = mockMvc.perform(postRequest);
		// Test Redirect
		results.andExpect(status().isFound());
		results.andExpect(view().name("redirect:/reservations/me"));

	}

	@Test
	void manageReservationCancelConfirm() throws Exception {
		// Prepare post request
		MockHttpServletRequestBuilder postRequest = get("/reservations/123/cancel").param("confirm", "");

		// Set mock behavior for helper method
		when(manageReservationHelper.findDetail("123")).thenReturn(reservationDetailOutput().priceCalculateOutput(null)
			.reserve(null)
			.customer(null)
			.paymentTimeLimit(null)
			.limitExceeding(null)
			.build());

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/cancelConfirm"));
		results.andExpect(model().attribute("output", notNullValue()));
	}

	@Test
	void manageReservationCancelSuccess() throws Exception {
		// Prepare post request
		MockHttpServletRequestBuilder postRequest = post("/reservations/123/cancel");
		// Set mock behavior for service method
		doNothing().when(reserveService).cancel("123");

		ResultActions results = mockMvc.perform(postRequest);
		// Test Redirect
		results.andExpect(status().isFound());
		results.andExpect(view().name("redirect:/reservations/{reserveNo}/cancel?complete"));
	}

	@Test
	void manageReservationCancelFailByBusinessException() throws Exception {
		// Prepare post request
		MockHttpServletRequestBuilder postRequest = post("/reservations/123/cancel");
		// Set mock behavior for service and helper method
		doThrow(new BusinessException(ResultMessages.error())).when(reserveService).cancel("123");
		when(manageReservationHelper.findDetail("123")).thenReturn(reservationDetailOutput().priceCalculateOutput(null)
			.reserve(null)
			.customer(null)
			.paymentTimeLimit(null)
			.limitExceeding(null)
			.build());

		ResultActions results = mockMvc.perform(postRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("managereservation/cancelConfirm"));
		results.andExpect(model().attribute("output", notNullValue()));
		// Exception message existence check
		results.andExpect(model().attribute("resultMessages", notNullValue()));
	}

}
