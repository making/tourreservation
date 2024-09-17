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
package org.terasoluna.tourreservation.customer.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Customer form object.
 */
public class CustomerForm implements Serializable {

	@NotEmpty
	@Size(max = 20)
	@Pattern(regexp = "^[ァ-ヶ]+$")
	private String customerKana;

	@NotEmpty
	@Size(max = 20)
	@Pattern(regexp = "[^ -~｡-ﾟ]*")
	private String customerName;

	@NotNull
	@Min(1900)
	private Integer customerBirthYear;

	@NotNull
	@Min(1)
	@Max(12)
	private Integer customerBirthMonth;

	@NotNull
	@Min(1)
	@Max(31)
	private Integer customerBirthDay;

	@NotEmpty
	@Size(max = 40)
	private String customerJob;

	@Email
	@Size(max = 30)
	private String customerMail;

	@NotEmpty
	@Pattern(regexp = "[0-9a-zA-Z]+")
	@Size(min = 4, max = 20)
	private String customerPass;

	@NotEmpty
	@Pattern(regexp = "[0-9a-zA-Z]+")
	@Size(min = 4, max = 20)
	private String customerPassConfirm;

	@Pattern(regexp = "[0-9-]+")
	@Size(min = 10, max = 13)
	private String customerTel;

	@Pattern(regexp = "[0-9]{3}-[0-9]{4}")
	private String customerPost;

	@NotEmpty
	private String customerAdd;

	public CustomerForm() {
	}

	public @NotEmpty @Size(max = 20) @Pattern(regexp = "^[ァ-ヶ]+$") String getCustomerKana() {
		return this.customerKana;
	}

	public @NotEmpty @Size(max = 20) @Pattern(regexp = "[^ -~｡-ﾟ]*") String getCustomerName() {
		return this.customerName;
	}

	public @NotNull @Min(1900) Integer getCustomerBirthYear() {
		return this.customerBirthYear;
	}

	public @NotNull @Min(1) @Max(12) Integer getCustomerBirthMonth() {
		return this.customerBirthMonth;
	}

	public @NotNull @Min(1) @Max(31) Integer getCustomerBirthDay() {
		return this.customerBirthDay;
	}

	public @NotEmpty @Size(max = 40) String getCustomerJob() {
		return this.customerJob;
	}

	public @Email @Size(max = 30) String getCustomerMail() {
		return this.customerMail;
	}

	public @NotEmpty @Pattern(regexp = "[0-9a-zA-Z]+") @Size(min = 4, max = 20) String getCustomerPass() {
		return this.customerPass;
	}

	public @NotEmpty @Pattern(regexp = "[0-9a-zA-Z]+") @Size(min = 4, max = 20) String getCustomerPassConfirm() {
		return this.customerPassConfirm;
	}

	public @Pattern(regexp = "[0-9-]+") @Size(min = 10, max = 13) String getCustomerTel() {
		return this.customerTel;
	}

	public @Pattern(regexp = "[0-9]{3}-[0-9]{4}") String getCustomerPost() {
		return this.customerPost;
	}

	public @NotEmpty String getCustomerAdd() {
		return this.customerAdd;
	}

	public LocalDate getBirthDay() {
		return LocalDate.of(this.customerBirthYear, this.customerBirthMonth, this.customerBirthDay);
	}

	public void setCustomerKana(@NotEmpty @Size(max = 20) @Pattern(regexp = "^[ァ-ヶ]+$") String customerKana) {
		this.customerKana = customerKana;
	}

	public void setCustomerName(@NotEmpty @Size(max = 20) @Pattern(regexp = "[^ -~｡-ﾟ]*") String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerBirthYear(@NotNull @Min(1900) Integer customerBirthYear) {
		this.customerBirthYear = customerBirthYear;
	}

	public void setCustomerBirthMonth(@NotNull @Min(1) @Max(12) Integer customerBirthMonth) {
		this.customerBirthMonth = customerBirthMonth;
	}

	public void setCustomerBirthDay(@NotNull @Min(1) @Max(31) Integer customerBirthDay) {
		this.customerBirthDay = customerBirthDay;
	}

	public void setCustomerJob(@NotEmpty @Size(max = 40) String customerJob) {
		this.customerJob = customerJob;
	}

	public void setCustomerMail(@Email @Size(max = 30) String customerMail) {
		this.customerMail = customerMail;
	}

	public void setCustomerPass(
			@NotEmpty @Pattern(regexp = "[0-9a-zA-Z]+") @Size(min = 4, max = 20) String customerPass) {
		this.customerPass = customerPass;
	}

	public void setCustomerPassConfirm(
			@NotEmpty @Pattern(regexp = "[0-9a-zA-Z]+") @Size(min = 4, max = 20) String customerPassConfirm) {
		this.customerPassConfirm = customerPassConfirm;
	}

	public void setCustomerTel(@Pattern(regexp = "[0-9-]+") @Size(min = 10, max = 13) String customerTel) {
		this.customerTel = customerTel;
	}

	public void setCustomerPost(@Pattern(regexp = "[0-9]{3}-[0-9]{4}") String customerPost) {
		this.customerPost = customerPost;
	}

	public void setCustomerAdd(@NotEmpty String customerAdd) {
		this.customerAdd = customerAdd;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomerForm that))
			return false;
		return Objects.equals(getCustomerKana(), that.getCustomerKana())
				&& Objects.equals(getCustomerName(), that.getCustomerName())
				&& Objects.equals(getCustomerBirthYear(), that.getCustomerBirthYear())
				&& Objects.equals(getCustomerBirthMonth(), that.getCustomerBirthMonth())
				&& Objects.equals(getCustomerBirthDay(), that.getCustomerBirthDay())
				&& Objects.equals(getCustomerJob(), that.getCustomerJob())
				&& Objects.equals(getCustomerMail(), that.getCustomerMail())
				&& Objects.equals(getCustomerPass(), that.getCustomerPass())
				&& Objects.equals(getCustomerPassConfirm(), that.getCustomerPassConfirm())
				&& Objects.equals(getCustomerTel(), that.getCustomerTel())
				&& Objects.equals(getCustomerPost(), that.getCustomerPost())
				&& Objects.equals(getCustomerAdd(), that.getCustomerAdd());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCustomerKana(), getCustomerName(), getCustomerBirthYear(), getCustomerBirthMonth(),
				getCustomerBirthDay(), getCustomerJob(), getCustomerMail(), getCustomerPass(), getCustomerPassConfirm(),
				getCustomerTel(), getCustomerPost(), getCustomerAdd());
	}

	@Override
	public String toString() {
		return "CustomerForm{" + "customerKana='" + customerKana + '\'' + ", customerName='" + customerName + '\''
				+ ", customerBirthYear=" + customerBirthYear + ", customerBirthMonth=" + customerBirthMonth
				+ ", customerBirthDay=" + customerBirthDay + ", customerJob='" + customerJob + '\'' + ", customerMail='"
				+ customerMail + '\'' + ", customerTel='" + customerTel + '\'' + ", customerPost='" + customerPost
				+ '\'' + ", customerAdd='" + customerAdd + '\'' + '}';
	}

}
