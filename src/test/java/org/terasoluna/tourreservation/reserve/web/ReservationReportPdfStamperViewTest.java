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

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.TextField;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.reserve.web.ManageReservationHelper.DownloadPDFOutput;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationReportPdfStamperViewTest {

	private static final int OUTPUT_BYTE_ARRAY_INITIAL_SIZE = 4096;

	private static final String RESERVATION_REPORT_PDF_PLACE = "src/main/resources/reports/reservationReport.pdf";

	private static final float REFERENCE_NAME_VARIABLE_FONTSIZE = 8.0F;

	private static final float REFERENCE_NAME_DEFAULT_FONTSIZE = 10.5F;

	ReservationReportPdfStamperView reservationReportPdfStamperView;

	@BeforeEach
	void setUp() throws Exception {
		reservationReportPdfStamperView = new ReservationReportPdfStamperView(
				"classpath:reports/reservationReport.pdf");
	}

	@Test
	void getUrl() {
		String url = reservationReportPdfStamperView.getUrl();
		assertThat(url).isEqualTo("classpath:reports/reservationReport.pdf");
	}

	@Test
	void mergePdfDocument() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		DownloadPDFOutput downloadPDFOutput = DownloadPDFOutputBuilder.downloadPDFOutput()
			.referenceName("TERASOLUNA TRAVEL CUSTOMER CENTER")
			.referenceEmail("customer@example.com")
			.referenceTel("01-2345-6789")
			.paymentMethod("Bank Transfer")
			.paymentCompanyName(null)
			.paymentAccount("Current Account：12345678")
			.childCount(3)
			.tourName("【おすすめ】Terasolunaツアー(6泊7日)")
			.accomName("TERASOLUNAホテル第一荘")
			.customerKana("キムラ　タロウ")
			.customerTel("111-1111-1111")
			.adultUnitPrice(75000)
			.reservedDay(sdf.parse("2019/02/21"))
			.conductor("Yes")
			.tourAbs("そこは別天地、静寂と湯けむりの待つ宿へ… 詳しい情報はお取り合わせをお願い致します。")
			.customerAdd("千葉県八千代市上高野")
			.customerJob("プログラマ")
			.tourDays("7")
			.depDay(sdf.parse("2019/03/31"))
			.customerName("木村　太郎")
			.childUnitPrice(37500)
			.depName("北海道")
			.customerBirth(sdf.parse("1975/01/05"))
			.arrName("北海道")
			.customerMail("tarou@example.com")
			.adultCount(5)
			.customerCode("00000001")
			.reserveNo("00000001")
			.remarks("特になし")
			.accomTel("018-123-4567")
			.customerPost("276-0022")
			.printDay(sdf.parse("2019/03/06"))
			.adultPrice(375000)
			.childPrice(112500)
			.sumPrice(487500)
			.paymentTimeLimit("2019/03/24")
			.build();

		Map<String, Object> model = new HashMap<>();
		model.put("downloadPDFOutput", downloadPDFOutput);
		model.put("downloadPDFName", "reservationReport");

		PdfStamper stamper = new PdfStamper(new PdfReader(RESERVATION_REPORT_PDF_PLACE),
				new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE));

		HttpServletRequest request = new MockHttpServletRequest();

		HttpServletResponse response = new MockHttpServletResponse();

		reservationReportPdfStamperView.mergePdfDocument(model, stamper, request, response);

		AcroFields form = stamper.getAcroFields();
		assertThat(form.getAllFields()).hasSize(35);
		assertThat(form.getField("referenceName")).isEqualTo("TERASOLUNA TRAVEL CUSTOMER CENTER");
		// Can't get field properties directly fromLocalDate AcroFields
		AcroFields.Item referenceNameItem = form.getFieldItem("referenceName");
		PdfDictionary referenceNameMerged = referenceNameItem.getMerged(0);
		TextField referenceNameTextField = new TextField(null, null, null);
		form.decodeGenericDictionary(referenceNameMerged, referenceNameTextField);
		assertThat(referenceNameTextField.getFontSize()).isEqualTo(REFERENCE_NAME_VARIABLE_FONTSIZE);
		assertThat(form.getField("referenceEmail")).isEqualTo("customer@example.com");
		assertThat(form.getField("referenceTel")).isEqualTo("01-2345-6789");
		assertThat(form.getField("paymentMethod")).isEqualTo("Bank Transfer");
		assertThat(form.getField("paymentAccount")).isEqualTo("Current Account：12345678");
		assertThat(form.getField("childCount")).isEqualTo("3");
		assertThat(form.getField("tourName")).isEqualTo("【おすすめ】Terasolunaツアー(6泊7日)");
		assertThat(form.getField("accomName")).isEqualTo("TERASOLUNAホテル第一荘");
		assertThat(form.getField("customerKana")).isEqualTo("キムラ　タロウ");
		assertThat(form.getField("customerTel")).isEqualTo("111-1111-1111");
		assertThat(form.getField("adultUnitPrice")).isEqualTo("75000");
		assertThat(form.getField("reservedDay")).isEqualTo("2019/02/21");
		assertThat(form.getField("conductor")).isEqualTo("Yes");
		assertThat(form.getField("tourAbs")).isEqualTo("そこは別天地、静寂と湯けむりの待つ宿へ… 詳しい情報はお取り合わせをお願い致します。");
		assertThat(form.getField("customerAdd")).isEqualTo("千葉県八千代市上高野");
		assertThat(form.getField("customerJob")).isEqualTo("プログラマ");
		assertThat(form.getField("tourDays")).isEqualTo("7");
		assertThat(form.getField("depDay")).isEqualTo("2019/03/31");
		assertThat(form.getField("customerName")).isEqualTo("木村　太郎");
		assertThat(form.getField("childUnitPrice")).isEqualTo("37500");
		assertThat(form.getField("depName")).isEqualTo("北海道");
		assertThat(form.getField("customerBirth")).isEqualTo("1975/01/05");
		assertThat(form.getField("arrName")).isEqualTo("北海道");
		assertThat(form.getField("customerMail")).isEqualTo("tarou@example.com");
		assertThat(form.getField("adultCount")).isEqualTo("5");
		assertThat(form.getField("customerCode")).isEqualTo("00000001");
		assertThat(form.getField("reserveNo")).isEqualTo("00000001");
		assertThat(form.getField("remarks")).isEqualTo("特になし");
		assertThat(form.getField("accomTel")).isEqualTo("018-123-4567");
		assertThat(form.getField("customerPost")).isEqualTo("276-0022");
		assertThat(form.getField("printDay")).isEqualTo("2019/03/06");
		assertThat(form.getField("adultPrice")).isEqualTo("375000");
		assertThat(form.getField("childPrice")).isEqualTo("112500");
		assertThat(form.getField("sumPrice")).isEqualTo("487500");
		assertThat(form.getField("paymentTimeLimit")).isEqualTo("2019/03/24");

		Object pdfStamperImp = ReflectionTestUtils.getField(stamper, "stamper");
		assertThat((boolean) ReflectionTestUtils.getField(pdfStamperImp, "flat")).isEqualTo(true);
		assertThat((boolean) ReflectionTestUtils.getField(pdfStamperImp, "flatFreeText")).isEqualTo(true);

		assertThat(response.getHeader("Content-Disposition")).isEqualTo("attachment; filename=reservationReport.pdf");
	}

	@Test
	void mergePdfDocumentDefaultFontsize() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		DownloadPDFOutput downloadPDFOutput = DownloadPDFOutputBuilder.downloadPDFOutput()
			.referenceName("TERASOLUNA TRAVEL")
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
			.reservedDay(sdf.parse("2019/02/21"))
			.conductor(null)
			.tourAbs(null)
			.customerAdd(null)
			.customerJob(null)
			.tourDays(null)
			.depDay(sdf.parse("2019/03/31"))
			.customerName(null)
			.childUnitPrice(null)
			.depName(null)
			.customerBirth(sdf.parse("1975/01/05"))
			.arrName(null)
			.customerMail(null)
			.adultCount(null)
			.customerCode(null)
			.reserveNo(null)
			.remarks(null)
			.accomTel(null)
			.customerPost(null)
			.printDay(sdf.parse("2019/03/06"))
			.adultPrice(null)
			.childPrice(null)
			.sumPrice(null)
			.paymentTimeLimit(null)
			.build();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("downloadPDFOutput", downloadPDFOutput);
		model.put("downloadPDFName", "reservationReport");

		PdfStamper stamper = new PdfStamper(new PdfReader(RESERVATION_REPORT_PDF_PLACE),
				new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE));

		HttpServletRequest request = new MockHttpServletRequest();

		HttpServletResponse response = new MockHttpServletResponse();

		reservationReportPdfStamperView.mergePdfDocument(model, stamper, request, response);

		AcroFields form = stamper.getAcroFields();
		assertThat(form.getField("referenceName")).isEqualTo("TERASOLUNA TRAVEL");
		// Can't get field properties directly fromLocalDate AcroFields
		AcroFields.Item referenceNameItem = form.getFieldItem("referenceName");
		PdfDictionary referenceNameMerged = referenceNameItem.getMerged(0);
		TextField referenceNameTextField = new TextField(null, null, null);
		form.decodeGenericDictionary(referenceNameMerged, referenceNameTextField);
		assertThat(referenceNameTextField.getFontSize()).isEqualTo(REFERENCE_NAME_DEFAULT_FONTSIZE);
	}

	@Test
	void prepareResponse() {

		HttpServletRequest request = new MockHttpServletRequest();

		HttpServletResponse response = new MockHttpServletResponse();

		reservationReportPdfStamperView.prepareResponse(request, response);

		// Pragma and Cache-Control are responsibility of super class
		assertThat(response.getHeader("Pragma")).isEqualTo("private");
		assertThat(response.getHeader("Cache-Control")).isEqualTo("private, must-revalidate");

		// CharacterEncoding is responsibility of ReservationReportPdfStamperView class
		assertThat(response.getCharacterEncoding()).isEqualTo(StandardCharsets.UTF_8.name());
	}

}
