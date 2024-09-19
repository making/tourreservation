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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.auth.ReservationUserDetails;
import org.terasoluna.tourreservation.customer.Customer;
import org.terasoluna.tourreservation.reserve.ReserveService;
import org.terasoluna.tourreservation.tour.Accommodation;
import org.terasoluna.tourreservation.tour.Arrival;
import org.terasoluna.tourreservation.tour.Departure;
import org.terasoluna.tourreservation.tour.PriceCalculateOutput;
import org.terasoluna.tourreservation.tour.PriceCalculateSharedService;
import org.terasoluna.tourreservation.tour.TourInfo;
import org.terasoluna.tourreservation.tour.TourInfoSharedService;

import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.terasoluna.tourreservation.reserve.ReserveTourOutputBuilder.reserveTourOutput;
import static org.terasoluna.tourreservation.reserve.web.ReserveTourHelperBuilder.reserveTourHelper;

class ReserveTourHelperTest {

	ReserveTourHelper reserveHelper;

	TourInfoSharedService tourInfoSharedService;

	ReserveService reserveService;

	PriceCalculateSharedService priceCalculateSharedService;

	TourInfo tourInfo;

	Customer customer;

	PriceCalculateOutput priceCalculateOutput;

	ReservationUserDetails userDetails;

	@BeforeEach
	void setUp() throws Exception {
		SecurityContextHolder.clearContext();

		tourInfoSharedService = mock(TourInfoSharedService.class);
		reserveService = mock(ReserveService.class);
		priceCalculateSharedService = mock(PriceCalculateSharedService.class);

		reserveHelper = reserveTourHelper().priceCalculateService(priceCalculateSharedService)
			.reserveService(reserveService)
			.tourInfoSharedService(tourInfoSharedService)
			.build();

		// setup mock behavior
		String tourCode = "xxxxx";

		tourInfo = new TourInfo();
		tourInfo.setTourCode(tourCode);
		tourInfo.setBasePrice(10000);
		Arrival a = new Arrival();
		a.setArrCode("1234");
		tourInfo.setArrival(a);

		Departure departure = new Departure();
		departure.setDepCode("5678");
		tourInfo.setDeparture(departure);

		Accommodation accommodation = new Accommodation();
		accommodation.setAccomCode("9012");
		tourInfo.setAccommodation(accommodation);

		when(tourInfoSharedService.findOneWithDetails(tourCode)).thenReturn(tourInfo);

		priceCalculateOutput = new PriceCalculateOutput();
		priceCalculateOutput.setSumPrice(100000);
		priceCalculateOutput.setAdultCount(1);
		priceCalculateOutput.setChildCount(2);
		when(priceCalculateSharedService.calculatePrice(10000, 1, 2)).thenReturn(priceCalculateOutput);

		customer = new Customer("12345678");

		userDetails = new ReservationUserDetails(customer);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void findTourDetail01() {
		// test when principal is not null

		String tourCode = "xxxxx";

		ReserveTourForm form = new ReserveTourForm();
		form.setAdultCount(1);
		form.setChildCount(2);

		// run
		ReserveTourHelper.TourDetailOutput resultOutput = reserveHelper.findTourDetail(userDetails, tourCode, form);

		// assert
		assertThat(resultOutput.customer()).isEqualTo(customer);
		assertThat(resultOutput.priceCalculateOutput()).isEqualTo(priceCalculateOutput);
		assertThat(resultOutput.tourInfo()).isEqualTo(tourInfo);
	}

	@Test
	void findTourDetail02() {
		// test when principal is null

		String tourCode = "xxxxx";

		ReserveTourForm form = new ReserveTourForm();
		form.setAdultCount(1);
		form.setChildCount(2);

		// run
		ReserveTourHelper.TourDetailOutput resultOutput = reserveHelper.findTourDetail(null, tourCode, form);

		// assert
		assertThat(resultOutput.customer()).isNull();
		assertThat(resultOutput.priceCalculateOutput()).isEqualTo(priceCalculateOutput);
		assertThat(resultOutput.tourInfo()).isEqualTo(tourInfo);
	}

	@Test
	void reserve01() {

		ReserveTourForm form = new ReserveTourForm();
		ReserveService.ReserveTourOutput output = reserveTourOutput().priceCalculateOutput(null)
			.reserve(null)
			.customer(null)
			.tourInfo(null)
			.paymentTimeLimit(null)
			.build();
		when(reserveService.reserve(any(ReserveService.ReserveTourInput.class))).thenReturn(output);

		// run
		ReserveService.ReserveTourOutput result = reserveHelper.reserve(userDetails, "123", form);

		// assert
		assertThat(result).isEqualTo(output);
	}

}
