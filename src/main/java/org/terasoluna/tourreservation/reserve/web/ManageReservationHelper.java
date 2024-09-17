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

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.terasoluna.tourreservation.auth.ReservationUserDetails;
import org.terasoluna.tourreservation.common.LegacyDate;
import org.terasoluna.tourreservation.customer.Customer;
import org.terasoluna.tourreservation.customer.CustomerService;
import org.terasoluna.tourreservation.reserve.Reserve;
import org.terasoluna.tourreservation.reserve.ReserveService;
import org.terasoluna.tourreservation.tour.PriceCalculateOutput;
import org.terasoluna.tourreservation.tour.PriceCalculateSharedService;
import org.terasoluna.tourreservation.tour.TourInfo;
import org.terasoluna.tourreservation.tour.TourInfoSharedService;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_COMMON_BANKTRANSFER;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_COMMON_COMPANYEMAIL;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_COMMON_COMPANYNAME;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_COMMON_COMPANYTEL;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_COMMON_DATEPATTERN;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_COMMON_PAYMENTCOMPANYNAME;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_COMMON_SAVINGSACCOUNT;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_MANAGERESERVATION_DAY;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_MANAGERESERVATION_DAYTRIP;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_MANAGERESERVATION_DONE;
import static org.terasoluna.tourreservation.message.ScreenMessageId.LABEL_TR_MANAGERESERVATION_NIGHT;

@Component
@Builder(style = BuilderStyle.STAGED)
public class ManageReservationHelper {

	private final TourInfoSharedService tourInfoSharedService;

	private final PriceCalculateSharedService priceCalculateService;

	private final ReserveService reserveService;

	private final CustomerService customerService;

	private final MessageSource messageSource;

	private final Clock clock;

	public ManageReservationHelper(TourInfoSharedService tourInfoSharedService,
			PriceCalculateSharedService priceCalculateService, ReserveService reserveService,
			CustomerService customerService, MessageSource messageSource, Clock clock) {
		this.tourInfoSharedService = tourInfoSharedService;
		this.priceCalculateService = priceCalculateService;
		this.reserveService = reserveService;
		this.customerService = customerService;
		this.messageSource = messageSource;
		this.clock = clock;
	}

	protected String convertNightDays(int days, Locale locale) {
		if (days == 1) {
			return this.messageSource.getMessage(LABEL_TR_MANAGERESERVATION_DAYTRIP, null, locale);
		}
		String builder = (days - 1) + this.messageSource.getMessage(LABEL_TR_MANAGERESERVATION_NIGHT, null, locale)
				+ days + this.messageSource.getMessage(LABEL_TR_MANAGERESERVATION_DAY, null, locale);
		return builder;
	}

	public List<ReserveRowOutput> list(ReservationUserDetails userDetails) {
		// must be logged in
		String customerCode = userDetails.getUsername();

		List<Reserve> reserves = reserveService.findAllWithTourInfoByCustomer(customerCode);

		List<ReserveRowOutput> rows = new ArrayList<>();
		for (Reserve reservation : reserves) {
			ReserveRowOutput reservationRowOutput = new ReserveRowOutput();
			TourInfo tourInfo = reservation.getTourInfo();

			reservationRowOutput.setLimitExceeding(tourInfoSharedService.isOverPaymentLimit(tourInfo));
			reservationRowOutput.setTourDays(String.valueOf(tourInfo.getTourDays()));
			reservationRowOutput.setReserve(reservation);
			rows.add(reservationRowOutput);
		}
		return rows;
	}

	/**
	 * Fetches detailed information of a particular reservation The pricing information is
	 * calculated on the basis of adult count and child count provided at the time of
	 * making the reservation. It uses existing values of adult count and child count
	 * @param reserveNo
	 * @return
	 */
	public ReservationDetailOutput findDetail(String reserveNo) {
		Reserve reserve = reserveService.findOneWithTourInfo(reserveNo);
		Customer customer = customerService.findOne(reserve.getCustomer().getCustomerCode());

		TourInfo info = reserve.getTourInfo();
		int adultCount = reserve.getAdultCount();
		int childCount = reserve.getChildCount();

		ReservationDetailOutput output = new ReservationDetailOutput();

		PriceCalculateOutput price = priceCalculateService.calculatePrice(info.getBasePrice(), adultCount, childCount);
		output.setPriceCalculateOutput(price);

		// reserve related
		output.setReserve(reserve);
		output.setCustomer(customer);

		// payment related
		output.setPaymentTimeLimit(LegacyDate.fromLocalDate(info.getPaymentLimit()));
		output.setLimitExceeding(tourInfoSharedService.isOverPaymentLimit(info));

		return output;

	}

	/**
	 * Fetches detailed information of a particular reservation. This pricing information
	 * of a reservation is by using adult count and child count provided as parameters.
	 * Existing adult count and child count information is not used.
	 */
	public ReservationDetailOutput findDetail(String reserveNo, ManageReservationForm form) {
		ReservationDetailOutput output = findDetail(reserveNo);
		// re-calculate
		TourInfo info = output.getReserve().getTourInfo();
		PriceCalculateOutput price = priceCalculateService.calculatePrice(info.getBasePrice(), form.getAdultCount(),
				form.getChildCount());
		output.setPriceCalculateOutput(price);
		return output;
	}

	public DownloadPDFOutput createPDF(String reserveNo, Locale locale) {
		ReservationDetailOutput reserveDetailOutput = findDetail(reserveNo);
		Reserve reserve = reserveDetailOutput.getReserve();
		TourInfo tourInfo = reserve.getTourInfo();

		String paymentTimeLimit;
		if ("1".equals(reserve.getTransfer())) {
			paymentTimeLimit = this.messageSource.getMessage(LABEL_TR_MANAGERESERVATION_DONE, null, locale);
		}
		else {
			SimpleDateFormat sdf = new SimpleDateFormat(
					this.messageSource.getMessage(LABEL_TR_COMMON_DATEPATTERN, null, locale));
			paymentTimeLimit = sdf.format(reserveDetailOutput.getPaymentTimeLimit());
		}

		DownloadPDFOutput downloadPDFOutput = new DownloadPDFOutput();
		downloadPDFOutput.setReserveNo(reserveNo);
		downloadPDFOutput.setTourName(tourInfo.getTourName());
		downloadPDFOutput.setReservedDay(reserve.getReservedDay());
		downloadPDFOutput.setDepDay(tourInfo.getDepDay());

		downloadPDFOutput.setTourDays(String.valueOf(tourInfo.getTourDays()));
		downloadPDFOutput.setDepName(tourInfo.getDeparture().getDepName());
		downloadPDFOutput.setArrName(tourInfo.getArrival().getArrName());
		downloadPDFOutput.setConductor(tourInfo.getConductor());
		downloadPDFOutput.setAccomName(tourInfo.getAccommodation().getAccomName());
		downloadPDFOutput.setAccomTel(tourInfo.getAccommodation().getAccomTel());
		downloadPDFOutput.setTourAbs(tourInfo.getTourAbs());
		downloadPDFOutput.setAdultCount(reserve.getAdultCount());
		downloadPDFOutput.setChildCount(reserve.getChildCount());
		downloadPDFOutput.setRemarks(reserve.getRemarks());
		downloadPDFOutput.setPaymentMethod(this.messageSource.getMessage(LABEL_TR_COMMON_BANKTRANSFER, null, locale));
		downloadPDFOutput
			.setPaymentCompanyName(this.messageSource.getMessage(LABEL_TR_COMMON_PAYMENTCOMPANYNAME, null, locale));
		downloadPDFOutput
			.setPaymentAccount(this.messageSource.getMessage(LABEL_TR_COMMON_SAVINGSACCOUNT, null, locale));
		downloadPDFOutput.setPaymentTimeLimit(paymentTimeLimit);

		// calculate price
		PriceCalculateOutput priceCalcResult = this.priceCalculateService.calculatePrice(tourInfo.getBasePrice(),
				reserve.getAdultCount(), reserve.getChildCount());

		// set price information
		downloadPDFOutput.setAdultUnitPrice(priceCalcResult.getAdultUnitPrice());
		downloadPDFOutput.setChildUnitPrice(priceCalcResult.getChildUnitPrice());
		downloadPDFOutput.setAdultPrice(priceCalcResult.getAdultPrice());
		downloadPDFOutput.setChildPrice(priceCalcResult.getChildPrice());
		downloadPDFOutput.setSumPrice(priceCalcResult.getSumPrice());

		// set customer information
		Customer customer = reserveDetailOutput.getCustomer();
		downloadPDFOutput.setCustomerCode(customer.getCustomerCode());
		downloadPDFOutput.setCustomerKana(customer.getCustomerKana());
		downloadPDFOutput.setCustomerName(customer.getCustomerName());
		downloadPDFOutput.setCustomerBirth(customer.getCustomerBirth());
		downloadPDFOutput.setCustomerJob(customer.getCustomerJob());
		downloadPDFOutput.setCustomerMail(customer.getCustomerMail());
		downloadPDFOutput.setCustomerTel(customer.getCustomerTel());
		downloadPDFOutput.setCustomerPost(customer.getCustomerPost());
		downloadPDFOutput.setCustomerAdd(customer.getCustomerAdd());

		// set reference information
		downloadPDFOutput.setReferenceName(this.messageSource.getMessage(LABEL_TR_COMMON_COMPANYNAME, null, locale));
		downloadPDFOutput.setReferenceEmail(this.messageSource.getMessage(LABEL_TR_COMMON_COMPANYEMAIL, null, locale));
		downloadPDFOutput.setReferenceTel(this.messageSource.getMessage(LABEL_TR_COMMON_COMPANYTEL, null, locale));

		// set print date
		downloadPDFOutput.setPrintDay(Date.from(Instant.now(this.clock)));

		return downloadPDFOutput;
	}

}
