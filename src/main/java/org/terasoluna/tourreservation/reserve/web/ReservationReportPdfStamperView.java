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

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfStamper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfStamperView;

@Component
public class ReservationReportPdfStamperView extends AbstractPdfStamperView {

	private static final String DOWNLOAD_PDF_EXTENSION = ".pdf";

	private static final int REFERENCE_NAME_MAXIMUM_VALUE_WITH_NORMAL_FONTSIZE = 30;

	private static final float REFERENCE_NAME_VARIABLE_FONTSIZE = 8.0F;

	private final String reservationReportPdfUrl;

	public ReservationReportPdfStamperView(
			@Value("${reservation.reportPdfUrl:classpath:reports/reservationReport.pdf}") String reservationReportPdfUrl) {
		this.reservationReportPdfUrl = reservationReportPdfUrl;
	}

	@Override
	public String getUrl() {
		return reservationReportPdfUrl;
	}

	@Override
	protected void mergePdfDocument(Map<String, Object> model, PdfStamper stamper, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManageReservationHelper.DownloadPDFOutput downloadPDFOutput = (ManageReservationHelper.DownloadPDFOutput) model
			.get("downloadPDFOutput");
		String downloadPDFName = (String) model.get("downloadPDFName");

		AcroFields form = stamper.getAcroFields();
		String referenceName = downloadPDFOutput.referenceName();
		if (referenceName
			.getBytes(StandardCharsets.UTF_8).length >= REFERENCE_NAME_MAXIMUM_VALUE_WITH_NORMAL_FONTSIZE) {
			form.setFieldProperty("referenceName", "textsize", REFERENCE_NAME_VARIABLE_FONTSIZE, null);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		form.setField("referenceName", referenceName);
		form.setField("referenceEmail", downloadPDFOutput.referenceEmail());
		form.setField("referenceTel", downloadPDFOutput.referenceTel());
		form.setField("paymentMethod", downloadPDFOutput.paymentMethod());
		form.setField("paymentCompanyName", downloadPDFOutput.paymentCompanyName());
		form.setField("paymentAccount", downloadPDFOutput.paymentAccount());
		form.setField("childCount", String.valueOf(downloadPDFOutput.childCount()));
		form.setField("tourName", downloadPDFOutput.tourName());
		form.setField("accomName", downloadPDFOutput.accomName());
		form.setField("customerKana", downloadPDFOutput.customerKana());
		form.setField("customerTel", downloadPDFOutput.customerTel());
		form.setField("adultUnitPrice", String.valueOf(downloadPDFOutput.adultUnitPrice()));
		form.setField("reservedDay", dateFormat.format(downloadPDFOutput.reservedDay()));
		form.setField("conductor", downloadPDFOutput.conductor());
		form.setField("tourAbs", downloadPDFOutput.tourAbs());
		form.setField("customerAdd", downloadPDFOutput.customerAdd());
		form.setField("customerJob", downloadPDFOutput.customerJob());
		form.setField("tourDays", downloadPDFOutput.tourDays());
		form.setField("depDay", dateFormat.format(downloadPDFOutput.depDay()));
		form.setField("customerName", downloadPDFOutput.customerName());
		form.setField("childUnitPrice", String.valueOf(downloadPDFOutput.childUnitPrice()));
		form.setField("depName", downloadPDFOutput.depName());
		form.setField("customerBirth", dateFormat.format(downloadPDFOutput.customerBirth()));
		form.setField("arrName", downloadPDFOutput.arrName());
		form.setField("customerMail", downloadPDFOutput.customerMail());
		form.setField("adultCount", String.valueOf(downloadPDFOutput.adultCount()));
		form.setField("customerCode", downloadPDFOutput.customerCode());
		form.setField("reserveNo", downloadPDFOutput.reserveNo());
		form.setField("remarks", downloadPDFOutput.remarks());
		form.setField("accomTel", downloadPDFOutput.accomTel());
		form.setField("customerPost", downloadPDFOutput.customerPost());
		form.setField("printDay", dateFormat.format(downloadPDFOutput.printDay()));
		form.setField("adultPrice", String.valueOf(downloadPDFOutput.adultPrice()));
		form.setField("childPrice", String.valueOf(downloadPDFOutput.childPrice()));
		form.setField("sumPrice", String.valueOf(downloadPDFOutput.sumPrice()));
		form.setField("paymentTimeLimit", downloadPDFOutput.paymentTimeLimit());
		stamper.setFormFlattening(true);
		stamper.setFreeTextFlattening(true);

		response.setHeader("Content-Disposition", "attachment; filename=" + downloadPDFName + DOWNLOAD_PDF_EXTENSION);
	}

	@Override
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		super.prepareResponse(request, response);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
	}

}
