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

import java.io.Serializable;
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
import static org.terasoluna.tourreservation.reserve.web.DownloadPDFOutputBuilder.downloadPDFOutput;
import static org.terasoluna.tourreservation.reserve.web.ReservationDetailOutputBuilder.reservationDetailOutput;
import static org.terasoluna.tourreservation.reserve.web.ReserveRowOutputBuilder.reserveRowOutput;

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
			TourInfo tourInfo = reservation.getTourInfo();
			ReserveRowOutput reservationRowOutput = reserveRowOutput().reserve(reservation)
				.limitExceeding(tourInfoSharedService.isOverPaymentLimit(tourInfo))
				.tourDays(String.valueOf(tourInfo.getTourDays()))
				.build();
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
		PriceCalculateOutput price = priceCalculateService.calculatePrice(info.getBasePrice(), adultCount, childCount);
		return reservationDetailOutput().priceCalculateOutput(price)
			.reserve(reserve)
			.customer(customer)
			.paymentTimeLimit(LegacyDate.fromLocalDate(info.getPaymentLimit()))
			.limitExceeding(tourInfoSharedService.isOverPaymentLimit(info))
			.build();

	}

	/**
	 * Fetches detailed information of a particular reservation. This pricing information
	 * of a reservation is by using adult count and child count provided as parameters.
	 * Existing adult count and child count information is not used.
	 */
	public ReservationDetailOutput findDetail(String reserveNo, ManageReservationForm form) {
		ReservationDetailOutput output = findDetail(reserveNo);
		// re-calculate
		TourInfo info = output.reserve().getTourInfo();
		PriceCalculateOutput price = priceCalculateService.calculatePrice(info.getBasePrice(), form.getAdultCount(),
				form.getChildCount());
		return ReservationDetailOutputBuilder.from(output).priceCalculateOutput(price).build();
	}

	public DownloadPDFOutput createPDF(String reserveNo, Locale locale) {
		ReservationDetailOutput reserveDetailOutput = findDetail(reserveNo);
		Reserve reserve = reserveDetailOutput.reserve();
		TourInfo tourInfo = reserve.getTourInfo();

		String paymentTimeLimit;
		if ("1".equals(reserve.getTransfer())) {
			paymentTimeLimit = this.messageSource.getMessage(LABEL_TR_MANAGERESERVATION_DONE, null, locale);
		}
		else {
			SimpleDateFormat sdf = new SimpleDateFormat(
					this.messageSource.getMessage(LABEL_TR_COMMON_DATEPATTERN, null, locale));
			paymentTimeLimit = sdf.format(reserveDetailOutput.paymentTimeLimit());
		}

		// calculate price
		PriceCalculateOutput priceCalcResult = this.priceCalculateService.calculatePrice(tourInfo.getBasePrice(),
				reserve.getAdultCount(), reserve.getChildCount());

		// set customer information
		Customer customer = reserveDetailOutput.customer();

		return downloadPDFOutput()
			.referenceName(this.messageSource.getMessage(LABEL_TR_COMMON_COMPANYNAME, null, locale))
			.referenceEmail(this.messageSource.getMessage(LABEL_TR_COMMON_COMPANYEMAIL, null, locale))
			.referenceTel(this.messageSource.getMessage(LABEL_TR_COMMON_COMPANYTEL, null, locale))
			.paymentMethod(this.messageSource.getMessage(LABEL_TR_COMMON_BANKTRANSFER, null, locale))
			.paymentCompanyName(this.messageSource.getMessage(LABEL_TR_COMMON_PAYMENTCOMPANYNAME, null, locale))
			.paymentAccount(this.messageSource.getMessage(LABEL_TR_COMMON_SAVINGSACCOUNT, null, locale))
			.childCount(reserve.getChildCount())
			.tourName(tourInfo.getTourName())
			.accomName(tourInfo.getAccommodation().getAccomName())
			.customerKana(customer.getCustomerKana())
			.customerTel(customer.getCustomerTel())
			.adultUnitPrice(priceCalcResult.getAdultUnitPrice())
			.reservedDay(reserve.getReservedDay())
			.conductor(tourInfo.getConductor())
			.tourAbs(tourInfo.getTourAbs())
			.customerAdd(customer.getCustomerAdd())
			.customerJob(customer.getCustomerJob())
			.tourDays(String.valueOf(tourInfo.getTourDays()))
			.depDay(tourInfo.getDepDay())
			.customerName(customer.getCustomerName())
			.childUnitPrice(priceCalcResult.getChildUnitPrice())
			.depName(tourInfo.getDeparture().getDepName())
			.customerBirth(customer.getCustomerBirth())
			.arrName(tourInfo.getArrival().getArrName())
			.customerMail(customer.getCustomerMail())
			.adultCount(reserve.getAdultCount())
			.customerCode(customer.getCustomerCode())
			.reserveNo(reserveNo)
			.remarks(reserve.getRemarks())
			.accomTel(tourInfo.getAccommodation().getAccomTel())
			.customerPost(customer.getCustomerPost())
			.printDay(Date.from(Instant.now(this.clock)))
			.adultPrice(priceCalcResult.getAdultPrice())
			.childPrice(priceCalcResult.getChildPrice())
			.sumPrice(priceCalcResult.getSumPrice())
			.paymentTimeLimit(paymentTimeLimit)
			.build();
	}

	@Builder(style = BuilderStyle.STAGED, toBuilder = "from")
	public record ReservationDetailOutput(PriceCalculateOutput priceCalculateOutput, Reserve reserve, Customer customer,
			Date paymentTimeLimit, Boolean limitExceeding) {

	}

	@Builder(style = BuilderStyle.STAGED)
	public record ReserveRowOutput(Reserve reserve, Boolean limitExceeding, String tourDays) {
	}

	/**
	 * Output of Price Calculation.<br>
	 */
	@Builder(style = BuilderStyle.STAGED)
	public record DownloadPDFOutput(String referenceName, String referenceEmail, String referenceTel,
			String paymentMethod, String paymentCompanyName, String paymentAccount, Integer childCount, String tourName,
			String accomName, String customerKana, String customerTel, Integer adultUnitPrice, Date reservedDay,
			String conductor, String tourAbs, String customerAdd, String customerJob, String tourDays, Date depDay,
			String customerName, Integer childUnitPrice, String depName, Date customerBirth, String arrName,
			String customerMail, Integer adultCount, String customerCode, String reserveNo, String remarks,
			String accomTel, String customerPost, Date printDay, Integer adultPrice, Integer childPrice,
			Integer sumPrice, String paymentTimeLimit) implements Serializable {
	}

}
