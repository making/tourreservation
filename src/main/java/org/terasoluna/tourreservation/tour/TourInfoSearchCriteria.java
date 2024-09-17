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
package org.terasoluna.tourreservation.tour;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

@Builder(style = BuilderStyle.STAGED)
public class TourInfoSearchCriteria implements Serializable {

	/**
	 * serialVersion.
	 */
	private static final long serialVersionUID = -4668739317291588721L;

	private Date depDate;

	private Integer tourDays;

	private String depCode;

	private String arrCode;

	private Integer adultCount;

	private Integer childCount;

	private Integer basePrice;

	public TourInfoSearchCriteria() {
	}

	TourInfoSearchCriteria(Date depDate, Integer tourDays, String depCode, String arrCode, Integer adultCount,
			Integer childCount, Integer basePrice) {
		this.depDate = depDate;
		this.tourDays = tourDays;
		this.depCode = depCode;
		this.arrCode = arrCode;
		this.adultCount = adultCount;
		this.childCount = childCount;
		this.basePrice = basePrice;
	}

	public Date getDepDate() {
		return this.depDate;
	}

	public Integer getTourDays() {
		return this.tourDays;
	}

	public String getDepCode() {
		return this.depCode;
	}

	public String getArrCode() {
		return this.arrCode;
	}

	public Integer getAdultCount() {
		return this.adultCount;
	}

	public Integer getChildCount() {
		return this.childCount;
	}

	public Integer getBasePrice() {
		return this.basePrice;
	}

	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}

	public void setTourDays(Integer tourDays) {
		this.tourDays = tourDays;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public void setArrCode(String arrCode) {
		this.arrCode = arrCode;
	}

	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
	}

	public boolean equals(final Object o) {
		if (o == this)
			return true;
		if (!(o instanceof TourInfoSearchCriteria other))
			return false;
		if (!other.canEqual(this))
			return false;
		final Object this$depDate = this.getDepDate();
		final Object other$depDate = other.getDepDate();
		if (!Objects.equals(this$depDate, other$depDate))
			return false;
		final Object this$tourDays = this.getTourDays();
		final Object other$tourDays = other.getTourDays();
		if (!Objects.equals(this$tourDays, other$tourDays))
			return false;
		final Object this$depCode = this.getDepCode();
		final Object other$depCode = other.getDepCode();
		if (!Objects.equals(this$depCode, other$depCode))
			return false;
		final Object this$arrCode = this.getArrCode();
		final Object other$arrCode = other.getArrCode();
		if (!Objects.equals(this$arrCode, other$arrCode))
			return false;
		final Object this$adultCount = this.getAdultCount();
		final Object other$adultCount = other.getAdultCount();
		if (!Objects.equals(this$adultCount, other$adultCount))
			return false;
		final Object this$childCount = this.getChildCount();
		final Object other$childCount = other.getChildCount();
		if (!Objects.equals(this$childCount, other$childCount))
			return false;
		final Object this$basePrice = this.getBasePrice();
		final Object other$basePrice = other.getBasePrice();
		return Objects.equals(this$basePrice, other$basePrice);
	}

	protected boolean canEqual(final Object other) {
		return other instanceof TourInfoSearchCriteria;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $depDate = this.getDepDate();
		result = result * PRIME + ($depDate == null ? 43 : $depDate.hashCode());
		final Object $tourDays = this.getTourDays();
		result = result * PRIME + ($tourDays == null ? 43 : $tourDays.hashCode());
		final Object $depCode = this.getDepCode();
		result = result * PRIME + ($depCode == null ? 43 : $depCode.hashCode());
		final Object $arrCode = this.getArrCode();
		result = result * PRIME + ($arrCode == null ? 43 : $arrCode.hashCode());
		final Object $adultCount = this.getAdultCount();
		result = result * PRIME + ($adultCount == null ? 43 : $adultCount.hashCode());
		final Object $childCount = this.getChildCount();
		result = result * PRIME + ($childCount == null ? 43 : $childCount.hashCode());
		final Object $basePrice = this.getBasePrice();
		result = result * PRIME + ($basePrice == null ? 43 : $basePrice.hashCode());
		return result;
	}

	public String toString() {
		return "TourInfoSearchCriteria(depDate=" + this.getDepDate() + ", tourDays=" + this.getTourDays() + ", depCode="
				+ this.getDepCode() + ", arrCode=" + this.getArrCode() + ", adultCount=" + this.getAdultCount()
				+ ", childCount=" + this.getChildCount() + ", basePrice=" + this.getBasePrice() + ")";
	}

}
