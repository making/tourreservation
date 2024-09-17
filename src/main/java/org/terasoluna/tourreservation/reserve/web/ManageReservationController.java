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

import java.util.List;
import java.util.Locale;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.terasoluna.tourreservation.common.BusinessException;
import org.terasoluna.tourreservation.reserve.ReservationUpdateInputBuilder;
import org.terasoluna.tourreservation.reserve.Reserve;
import org.terasoluna.tourreservation.reserve.ReservationUpdateInput;
import org.terasoluna.tourreservation.reserve.ReservationUpdateOutput;
import org.terasoluna.tourreservation.reserve.ReserveService;
import org.terasoluna.tourreservation.auth.ReservationUserDetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "reservations")
@Builder(style = BuilderStyle.STAGED)
public class ManageReservationController {

	private final ManageReservationHelper manageReservationHelper;

	private final ReserveService reserveService;

	private final String reservationReportPdfName;

	public ManageReservationController(ManageReservationHelper manageReservationHelper, ReserveService reserveService,
			@Value("${reservation.reportPdfName:reservationReport}") String reservationReportPdfName) {
		this.manageReservationHelper = manageReservationHelper;
		this.reserveService = reserveService;
		this.reservationReportPdfName = reservationReportPdfName;
	}

	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@ModelAttribute
	public ManageReservationForm setUpManageReservationForm() {
		ManageReservationForm form = new ManageReservationForm();
		return form;
	}

	/**
	 * Shows the list of reservations for a particular user
	 * @param model
	 * @return
	 */
	@GetMapping("me")
	public String list(@AuthenticationPrincipal ReservationUserDetails userDetails, Model model) {
		List<ReserveRowOutput> rows = manageReservationHelper.list(userDetails);

		model.addAttribute("rows", rows);
		return "managereservation/list";
	}

	/**
	 * Shows the detailed information of a reservation
	 * @param reserveNo
	 * @param model
	 * @return
	 */
	@GetMapping("{reserveNo}")
	public String detailForm(@PathVariable("reserveNo") String reserveNo, Model model) {
		ReservationDetailOutput output = manageReservationHelper.findDetail(reserveNo);
		model.addAttribute("output", output);
		return "managereservation/detailForm";
	}

	/**
	 * Shows the edit screen for user to make changes to the reservation. Uses reserveNo
	 * fromLocalDate the path to fetch the reservation info
	 * @param reserveNo
	 * @param form
	 * @param model
	 * @return
	 */
	@GetMapping(value = "{reserveNo}/update", params = "form")
	public String updateForm(@PathVariable("reserveNo") String reserveNo, ManageReservationForm form, Model model) {

		Reserve reserve = reserveService.findOneWithTourInfo(reserveNo);

		// Map Model to form
		// This is needed to copy the current values of the reservation into the form
		form.setChildCount(reserve.getChildCount());
		form.setAdultCount(reserve.getAdultCount());

		model.addAttribute(reserve);
		return "managereservation/updateForm";
	}

	/**
	 * Goes back to the edit screen for user to make changes to the reservation.
	 * @param reserveNo
	 * @param model
	 * @return
	 */
	@PostMapping(value = "{reserveNo}/update", params = "redo")
	public String updateRedo(@PathVariable("reserveNo") String reserveNo, ManageReservationForm form, Model model) {
		Reserve reserve = reserveService.findOneWithTourInfo(reserveNo);
		model.addAttribute(reserve);
		return "managereservation/updateForm";
	}

	/**
	 * Shows the confirmation page after user changes edits the reservation info on the
	 * edit reservation page
	 * @param model
	 * @return
	 */
	@PostMapping(value = "{reserveNo}/update", params = "confirm")
	public String updateConfirm(@PathVariable("reserveNo") String reserveNo, @Validated ManageReservationForm form,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return updateRedo(reserveNo, form, model);
		}
		ReservationDetailOutput output = manageReservationHelper.findDetail(reserveNo, form);
		model.addAttribute("output", output);
		return "managereservation/updateConfirm";
	}

	/**
	 * Updates the reservation after user changes edits the reservation info on the edit
	 * reservation page
	 * @param form
	 * @return
	 */
	@PostMapping("{reserveNo}/update")
	public String update(@PathVariable("reserveNo") String reserveNo, @Validated ManageReservationForm form,
			BindingResult result, Model model, RedirectAttributes redirectAttr) {
		if (result.hasErrors()) {
			return updateRedo(reserveNo, form, model);
		}
		ReservationUpdateInput input = ReservationUpdateInputBuilder.reservationUpdateInput()
			.reserveNo(reserveNo)
			.adultCount(form.getAdultCount())
			.childCount(form.getChildCount())
			.build();
		ReservationUpdateOutput output = reserveService.update(input);
		redirectAttr.addFlashAttribute("output", output);
		return "redirect:/reservations/{reserveNo}/update?complete";
	}

	/**
	 * redirects to the update completion page
	 * @return
	 */
	@GetMapping(value = "{reserveNo}/update", params = "complete")
	public String updateComplete() {
		return "managereservation/updateComplete";
	}

	@PostMapping(value = "{reserveNo}/update", params = "backTolist")
	public String updateBackList() {
		return "redirect:/reservations/me";
	}

	@GetMapping("{reserveNo}/cancel")
	public String cancelConfirm(@PathVariable("reserveNo") String reserveNo, Model model) {
		ReservationDetailOutput output = manageReservationHelper.findDetail(reserveNo);
		model.addAttribute("output", output);
		return "managereservation/cancelConfirm";
	}

	@PostMapping("{reserveNo}/cancel")
	public String cancel(@PathVariable("reserveNo") String reserveNo, Model model, RedirectAttributes redirectAttr) {
		try {
			reserveService.cancel(reserveNo);
		}
		catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return cancelConfirm(reserveNo, model);
		}
		return "redirect:/reservations/{reserveNo}/cancel?complete";
	}

	/**
	 * redirects to the update completion page
	 * @return
	 */
	@GetMapping(value = "{reserveNo}/cancel", params = "complete")
	public String cancelComplete(@PathVariable("reserveNo") String reserveNo, Model model) {
		model.addAttribute("reserveNo", reserveNo);
		return "managereservation/cancelComplete";
	}

	@PostMapping(value = "{reserveNo}/cancel", params = "backTolist")
	public String backList() {
		return "redirect:/reservations/me";
	}

	@GetMapping(path = "{reserveNo}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public String downloadPDF(@PathVariable("reserveNo") String reserveNo, Model model, Locale locale) {
		DownloadPDFOutput downloadPDFOutput = manageReservationHelper.createPDF(reserveNo, locale);
		model.addAttribute("downloadPDFOutput", downloadPDFOutput);
		model.addAttribute("downloadPDFName", reservationReportPdfName);
		return "reservationReportPdfStamperView";
	}

}
