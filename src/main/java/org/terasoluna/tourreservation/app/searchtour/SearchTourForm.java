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
package org.terasoluna.tourreservation.app.searchtour;

import java.io.Serializable;
import java.util.Objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SearchTourForm implements Serializable {

	@NotNull
	private Integer depYear;

	@NotNull
	@Min(1)
	@Max(12)
	private Integer depMonth;

	@NotNull
	@Min(1)
	@Max(31)
	private Integer depDay;

	@NotNull
	private Integer tourDays;

	@NotEmpty
	private String depCode;

	@NotEmpty
	private String arrCode;

	@NotNull
	@Min(0)
	private Integer adultCount;

	@NotNull
	@Min(0)
	private Integer childCount;

	@NotNull
	@Min(0)
	private Integer basePrice;

	public SearchTourForm() {
	}

	public @NotNull Integer getDepYear() {
		return this.depYear;
	}

	public @NotNull @Min(1) @Max(12) Integer getDepMonth() {
		return this.depMonth;
	}

	public @NotNull @Min(1) @Max(31) Integer getDepDay() {
		return this.depDay;
	}

	public @NotNull Integer getTourDays() {
		return this.tourDays;
	}

	public @NotEmpty String getDepCode() {
		return this.depCode;
	}

	public @NotEmpty String getArrCode() {
		return this.arrCode;
	}

	public @NotNull @Min(0) Integer getAdultCount() {
		return this.adultCount;
	}

	public @NotNull @Min(0) Integer getChildCount() {
		return this.childCount;
	}

	public @NotNull @Min(0) Integer getBasePrice() {
		return this.basePrice;
	}

	public void setDepYear(@NotNull Integer depYear) {
		this.depYear = depYear;
	}

	public void setDepMonth(@NotNull @Min(1) @Max(12) Integer depMonth) {
		this.depMonth = depMonth;
	}

	public void setDepDay(@NotNull @Min(1) @Max(31) Integer depDay) {
		this.depDay = depDay;
	}

	public void setTourDays(@NotNull Integer tourDays) {
		this.tourDays = tourDays;
	}

	public void setDepCode(@NotEmpty String depCode) {
		this.depCode = depCode;
	}

	public void setArrCode(@NotEmpty String arrCode) {
		this.arrCode = arrCode;
	}

	public void setAdultCount(@NotNull @Min(0) Integer adultCount) {
		this.adultCount = adultCount;
	}

	public void setChildCount(@NotNull @Min(0) Integer childCount) {
		this.childCount = childCount;
	}

	public void setBasePrice(@NotNull @Min(0) Integer basePrice) {
		this.basePrice = basePrice;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SearchTourForm that))
			return false;
		return Objects.equals(getDepYear(), that.getDepYear()) && Objects.equals(getDepMonth(), that.getDepMonth())
				&& Objects.equals(getDepDay(), that.getDepDay()) && Objects.equals(getTourDays(), that.getTourDays())
				&& Objects.equals(getDepCode(), that.getDepCode()) && Objects.equals(getArrCode(), that.getArrCode())
				&& Objects.equals(getAdultCount(), that.getAdultCount())
				&& Objects.equals(getChildCount(), that.getChildCount())
				&& Objects.equals(getBasePrice(), that.getBasePrice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getDepYear(), getDepMonth(), getDepDay(), getTourDays(), getDepCode(), getArrCode(),
				getAdultCount(), getChildCount(), getBasePrice());
	}

	@Override
	public String toString() {
		return "SearchTourForm{" + "depYear=" + depYear + ", depMonth=" + depMonth + ", depDay=" + depDay
				+ ", tourDays=" + tourDays + ", depCode='" + depCode + '\'' + ", arrCode='" + arrCode + '\''
				+ ", adultCount=" + adultCount + ", childCount=" + childCount + ", basePrice=" + basePrice + '}';
	}

}
