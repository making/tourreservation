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

import java.util.Objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ManageReservationForm {

	@NotNull
	@Min(0)
	@Max(5)
	private Integer adultCount;

	@NotNull
	@Min(0)
	@Max(5)
	private Integer childCount;

	public ManageReservationForm() {
	}

	ManageReservationForm(Integer adultCount, Integer childCount) {
		this.adultCount = adultCount;
		this.childCount = childCount;
	}

	public @NotNull @Min(0) @Max(5) Integer getAdultCount() {
		return this.adultCount;
	}

	public @NotNull @Min(0) @Max(5) Integer getChildCount() {
		return this.childCount;
	}

	public void setAdultCount(@NotNull @Min(0) @Max(5) Integer adultCount) {
		this.adultCount = adultCount;
	}

	public void setChildCount(@NotNull @Min(0) @Max(5) Integer childCount) {
		this.childCount = childCount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ManageReservationForm that))
			return false;
		return Objects.equals(getAdultCount(), that.getAdultCount())
				&& Objects.equals(getChildCount(), that.getChildCount());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAdultCount(), getChildCount());
	}

	@Override
	public String toString() {
		return "ManageReservationForm{" + "adultCount=" + adultCount + ", childCount=" + childCount + '}';
	}

}
