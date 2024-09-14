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
package org.terasoluna.tourreservation.app.managereservation;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Output of Price Calculation.<br>
 */
public class DownloadPDFOutput implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6982754828396123236L;

	private String referenceName;

	private String referenceEmail;

	private String referenceTel;

	private String paymentMethod;

	private String paymentCompanyName;

	private String paymentAccount;

	private Integer childCount;

	private String tourName;

	private String accomName;

	private String customerKana;

	private String customerTel;

	private Integer adultUnitPrice;

	private Date reservedDay;

	private String conductor;

	private String tourAbs;

	private String customerAdd;

	private String customerJob;

	private String tourDays;

	private Date depDay;

	private String customerName;

	private Integer childUnitPrice;

	private String depName;

	private Date customerBirth;

	private String arrName;

	private String customerMail;

	private Integer adultCount;

	private String customerCode;

	private String reserveNo;

	private String remarks;

	private String accomTel;

	private String customerPost;

	private Date printDay;

	private Integer adultPrice;

	private Integer childPrice;

	private Integer sumPrice;

	private String paymentTimeLimit;

	public DownloadPDFOutput(String referenceName, String referenceEmail, String referenceTel, String paymentMethod,
			String paymentCompanyName, String paymentAccount, Integer childCount, String tourName, String accomName,
			String customerKana, String customerTel, Integer adultUnitPrice, Date reservedDay, String conductor,
			String tourAbs, String customerAdd, String customerJob, String tourDays, Date depDay, String customerName,
			Integer childUnitPrice, String depName, Date customerBirth, String arrName, String customerMail,
			Integer adultCount, String customerCode, String reserveNo, String remarks, String accomTel,
			String customerPost, Date printDay, Integer adultPrice, Integer childPrice, Integer sumPrice,
			String paymentTimeLimit) {
		this.referenceName = referenceName;
		this.referenceEmail = referenceEmail;
		this.referenceTel = referenceTel;
		this.paymentMethod = paymentMethod;
		this.paymentCompanyName = paymentCompanyName;
		this.paymentAccount = paymentAccount;
		this.childCount = childCount;
		this.tourName = tourName;
		this.accomName = accomName;
		this.customerKana = customerKana;
		this.customerTel = customerTel;
		this.adultUnitPrice = adultUnitPrice;
		this.reservedDay = reservedDay;
		this.conductor = conductor;
		this.tourAbs = tourAbs;
		this.customerAdd = customerAdd;
		this.customerJob = customerJob;
		this.tourDays = tourDays;
		this.depDay = depDay;
		this.customerName = customerName;
		this.childUnitPrice = childUnitPrice;
		this.depName = depName;
		this.customerBirth = customerBirth;
		this.arrName = arrName;
		this.customerMail = customerMail;
		this.adultCount = adultCount;
		this.customerCode = customerCode;
		this.reserveNo = reserveNo;
		this.remarks = remarks;
		this.accomTel = accomTel;
		this.customerPost = customerPost;
		this.printDay = printDay;
		this.adultPrice = adultPrice;
		this.childPrice = childPrice;
		this.sumPrice = sumPrice;
		this.paymentTimeLimit = paymentTimeLimit;
	}

	public DownloadPDFOutput() {
	}

	public String getReferenceName() {
		return this.referenceName;
	}

	public String getReferenceEmail() {
		return this.referenceEmail;
	}

	public String getReferenceTel() {
		return this.referenceTel;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public String getPaymentCompanyName() {
		return this.paymentCompanyName;
	}

	public String getPaymentAccount() {
		return this.paymentAccount;
	}

	public Integer getChildCount() {
		return this.childCount;
	}

	public String getTourName() {
		return this.tourName;
	}

	public String getAccomName() {
		return this.accomName;
	}

	public String getCustomerKana() {
		return this.customerKana;
	}

	public String getCustomerTel() {
		return this.customerTel;
	}

	public Integer getAdultUnitPrice() {
		return this.adultUnitPrice;
	}

	public Date getReservedDay() {
		return this.reservedDay;
	}

	public String getConductor() {
		return this.conductor;
	}

	public String getTourAbs() {
		return this.tourAbs;
	}

	public String getCustomerAdd() {
		return this.customerAdd;
	}

	public String getCustomerJob() {
		return this.customerJob;
	}

	public String getTourDays() {
		return this.tourDays;
	}

	public Date getDepDay() {
		return this.depDay;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public Integer getChildUnitPrice() {
		return this.childUnitPrice;
	}

	public String getDepName() {
		return this.depName;
	}

	public Date getCustomerBirth() {
		return this.customerBirth;
	}

	public String getArrName() {
		return this.arrName;
	}

	public String getCustomerMail() {
		return this.customerMail;
	}

	public Integer getAdultCount() {
		return this.adultCount;
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public String getReserveNo() {
		return this.reserveNo;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public String getAccomTel() {
		return this.accomTel;
	}

	public String getCustomerPost() {
		return this.customerPost;
	}

	public Date getPrintDay() {
		return this.printDay;
	}

	public Integer getAdultPrice() {
		return this.adultPrice;
	}

	public Integer getChildPrice() {
		return this.childPrice;
	}

	public Integer getSumPrice() {
		return this.sumPrice;
	}

	public String getPaymentTimeLimit() {
		return this.paymentTimeLimit;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public void setReferenceEmail(String referenceEmail) {
		this.referenceEmail = referenceEmail;
	}

	public void setReferenceTel(String referenceTel) {
		this.referenceTel = referenceTel;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setPaymentCompanyName(String paymentCompanyName) {
		this.paymentCompanyName = paymentCompanyName;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public void setTourName(String tourName) {
		this.tourName = tourName;
	}

	public void setAccomName(String accomName) {
		this.accomName = accomName;
	}

	public void setCustomerKana(String customerKana) {
		this.customerKana = customerKana;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	public void setAdultUnitPrice(Integer adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	public void setReservedDay(Date reservedDay) {
		this.reservedDay = reservedDay;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	public void setTourAbs(String tourAbs) {
		this.tourAbs = tourAbs;
	}

	public void setCustomerAdd(String customerAdd) {
		this.customerAdd = customerAdd;
	}

	public void setCustomerJob(String customerJob) {
		this.customerJob = customerJob;
	}

	public void setTourDays(String tourDays) {
		this.tourDays = tourDays;
	}

	public void setDepDay(Date depDay) {
		this.depDay = depDay;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setChildUnitPrice(Integer childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public void setCustomerBirth(Date customerBirth) {
		this.customerBirth = customerBirth;
	}

	public void setArrName(String arrName) {
		this.arrName = arrName;
	}

	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}

	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public void setReserveNo(String reserveNo) {
		this.reserveNo = reserveNo;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setAccomTel(String accomTel) {
		this.accomTel = accomTel;
	}

	public void setCustomerPost(String customerPost) {
		this.customerPost = customerPost;
	}

	public void setPrintDay(Date printDay) {
		this.printDay = printDay;
	}

	public void setAdultPrice(Integer adultPrice) {
		this.adultPrice = adultPrice;
	}

	public void setChildPrice(Integer childPrice) {
		this.childPrice = childPrice;
	}

	public void setSumPrice(Integer sumPrice) {
		this.sumPrice = sumPrice;
	}

	public void setPaymentTimeLimit(String paymentTimeLimit) {
		this.paymentTimeLimit = paymentTimeLimit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DownloadPDFOutput that))
			return false;
		return Objects.equals(getReferenceName(), that.getReferenceName())
				&& Objects.equals(getReferenceEmail(), that.getReferenceEmail())
				&& Objects.equals(getReferenceTel(), that.getReferenceTel())
				&& Objects.equals(getPaymentMethod(), that.getPaymentMethod())
				&& Objects.equals(getPaymentCompanyName(), that.getPaymentCompanyName())
				&& Objects.equals(getPaymentAccount(), that.getPaymentAccount())
				&& Objects.equals(getChildCount(), that.getChildCount())
				&& Objects.equals(getTourName(), that.getTourName())
				&& Objects.equals(getAccomName(), that.getAccomName())
				&& Objects.equals(getCustomerKana(), that.getCustomerKana())
				&& Objects.equals(getCustomerTel(), that.getCustomerTel())
				&& Objects.equals(getAdultUnitPrice(), that.getAdultUnitPrice())
				&& Objects.equals(getReservedDay(), that.getReservedDay())
				&& Objects.equals(getConductor(), that.getConductor())
				&& Objects.equals(getTourAbs(), that.getTourAbs())
				&& Objects.equals(getCustomerAdd(), that.getCustomerAdd())
				&& Objects.equals(getCustomerJob(), that.getCustomerJob())
				&& Objects.equals(getTourDays(), that.getTourDays()) && Objects.equals(getDepDay(), that.getDepDay())
				&& Objects.equals(getCustomerName(), that.getCustomerName())
				&& Objects.equals(getChildUnitPrice(), that.getChildUnitPrice())
				&& Objects.equals(getDepName(), that.getDepName())
				&& Objects.equals(getCustomerBirth(), that.getCustomerBirth())
				&& Objects.equals(getArrName(), that.getArrName())
				&& Objects.equals(getCustomerMail(), that.getCustomerMail())
				&& Objects.equals(getAdultCount(), that.getAdultCount())
				&& Objects.equals(getCustomerCode(), that.getCustomerCode())
				&& Objects.equals(getReserveNo(), that.getReserveNo())
				&& Objects.equals(getRemarks(), that.getRemarks()) && Objects.equals(getAccomTel(), that.getAccomTel())
				&& Objects.equals(getCustomerPost(), that.getCustomerPost())
				&& Objects.equals(getPrintDay(), that.getPrintDay())
				&& Objects.equals(getAdultPrice(), that.getAdultPrice())
				&& Objects.equals(getChildPrice(), that.getChildPrice())
				&& Objects.equals(getSumPrice(), that.getSumPrice())
				&& Objects.equals(getPaymentTimeLimit(), that.getPaymentTimeLimit());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getReferenceName(), getReferenceEmail(), getReferenceTel(), getPaymentMethod(),
				getPaymentCompanyName(), getPaymentAccount(), getChildCount(), getTourName(), getAccomName(),
				getCustomerKana(), getCustomerTel(), getAdultUnitPrice(), getReservedDay(), getConductor(),
				getTourAbs(), getCustomerAdd(), getCustomerJob(), getTourDays(), getDepDay(), getCustomerName(),
				getChildUnitPrice(), getDepName(), getCustomerBirth(), getArrName(), getCustomerMail(), getAdultCount(),
				getCustomerCode(), getReserveNo(), getRemarks(), getAccomTel(), getCustomerPost(), getPrintDay(),
				getAdultPrice(), getChildPrice(), getSumPrice(), getPaymentTimeLimit());
	}

	@Override
	public String toString() {
		return "DownloadPDFOutput{" + "referenceName='" + referenceName + '\'' + ", referenceEmail='" + referenceEmail
				+ '\'' + ", referenceTel='" + referenceTel + '\'' + ", paymentMethod='" + paymentMethod + '\''
				+ ", paymentCompanyName='" + paymentCompanyName + '\'' + ", paymentAccount='" + paymentAccount + '\''
				+ ", childCount=" + childCount + ", tourName='" + tourName + '\'' + ", accomName='" + accomName + '\''
				+ ", customerKana='" + customerKana + '\'' + ", customerTel='" + customerTel + '\''
				+ ", adultUnitPrice=" + adultUnitPrice + ", reservedDay=" + reservedDay + ", conductor='" + conductor
				+ '\'' + ", tourAbs='" + tourAbs + '\'' + ", customerAdd='" + customerAdd + '\'' + ", customerJob='"
				+ customerJob + '\'' + ", tourDays='" + tourDays + '\'' + ", depDay=" + depDay + ", customerName='"
				+ customerName + '\'' + ", childUnitPrice=" + childUnitPrice + ", depName='" + depName + '\''
				+ ", customerBirth=" + customerBirth + ", arrName='" + arrName + '\'' + ", customerMail='"
				+ customerMail + '\'' + ", adultCount=" + adultCount + ", customerCode='" + customerCode + '\''
				+ ", reserveNo='" + reserveNo + '\'' + ", remarks='" + remarks + '\'' + ", accomTel='" + accomTel + '\''
				+ ", customerPost='" + customerPost + '\'' + ", printDay=" + printDay + ", adultPrice=" + adultPrice
				+ ", childPrice=" + childPrice + ", sumPrice=" + sumPrice + ", paymentTimeLimit='" + paymentTimeLimit
				+ '\'' + '}';
	}

}
