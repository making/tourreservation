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

import java.time.Clock;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.auth.ReservationUserDetails;
import org.terasoluna.tourreservation.customer.Customer;
import org.terasoluna.tourreservation.customer.CustomerService;
import org.terasoluna.tourreservation.reserve.Reserve;
import org.terasoluna.tourreservation.reserve.ReserveService;
import org.terasoluna.tourreservation.tour.PriceCalculateSharedService;
import org.terasoluna.tourreservation.tour.TourInfo;
import org.terasoluna.tourreservation.tour.TourInfoSharedService;

import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.terasoluna.tourreservation.reserve.web.ManageReservationHelperBuilder.manageReservationHelper;

class ManageReservationHelperTest {

	Authentication authentication;

	ReservationUserDetails userDetails;

	ManageReservationHelper manageReservationFacade;

	ReserveService reserveService;

	TourInfoSharedService tourInfoSharedService;

	PriceCalculateSharedService priceCalculateSharedService;

	CustomerService customerService;

	SecurityContext securityContext;

	MessageSource messageSource;

	Clock clock;

	@BeforeEach
	void setUp() throws Exception {

		securityContext = mock(SecurityContext.class);
		authentication = mock(Authentication.class);
		userDetails = new ReservationUserDetails(new Customer("xxxx"));

		reserveService = mock(ReserveService.class);
		tourInfoSharedService = mock(TourInfoSharedService.class);
		priceCalculateSharedService = mock(PriceCalculateSharedService.class);
		customerService = mock(CustomerService.class);
		messageSource = mock(MessageSource.class);
		clock = Clock.systemUTC();

		manageReservationFacade = manageReservationHelper().tourInfoSharedService(tourInfoSharedService)
			.priceCalculateService(priceCalculateSharedService)
			.reserveService(reserveService)
			.customerService(customerService)
			.messageSource(messageSource)
			.clock(clock)
			.build();

	}

	@Test
	void list01() {
		TourInfo tour1 = new TourInfo("01");
		TourInfo tour2 = new TourInfo("02");
		Reserve reserve1 = new Reserve("001");
		Reserve reserve2 = new Reserve("002");
		reserve1.setTourInfo(tour1);
		reserve2.setTourInfo(tour2);
		tour1.setTourDays(2);
		tour2.setTourDays(4);
		List<Reserve> reserves = Arrays.asList(reserve1, reserve2);

		when(reserveService.findAllWithTourInfoByCustomer("xxxx")).thenReturn(reserves);
		when(tourInfoSharedService.isOverPaymentLimit(tour1)).thenReturn(false);
		when(tourInfoSharedService.isOverPaymentLimit(tour2)).thenReturn(true);

		List<ReserveRowOutput> result = manageReservationFacade.list(userDetails);
		assertThat(result).isNotNull().hasSize(2);
		ReserveRowOutput o1 = result.getFirst();
		assertThat(o1.getLimitExceeding()).isFalse();
		assertThat(o1.getReserve()).isEqualTo(reserve1);
		assertThat(o1.getTourDays()).isEqualTo("2");
		ReserveRowOutput o2 = result.get(1);
		assertThat(o2.getLimitExceeding()).isTrue();
		assertThat(o2.getReserve()).isEqualTo(reserve2);
		assertThat(o2.getTourDays()).isEqualTo("4");

	}

}
