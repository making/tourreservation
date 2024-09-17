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

/**
 * Output of Price Calculation.<br>
 */
public class PriceCalculateOutput implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6982754828396123236L;

	/**
	 * adult price.
	 */
	private Integer adultUnitPrice = null;

	/**
	 * child price.
	 */
	private Integer childUnitPrice = null;

	/**
	 * adult count.
	 */
	private Integer adultCount = null;

	/**
	 * child count.
	 */
	private Integer childCount = null;

	/**
	 * adult price.
	 */
	private Integer adultPrice = null;

	/**
	 * child price.
	 */
	private Integer childPrice = null;

	/**
	 * sum of price.
	 */
	private Integer sumPrice = null;

	public PriceCalculateOutput() {
	}

	public Integer getAdultUnitPrice() {
		return this.adultUnitPrice;
	}

	public Integer getChildUnitPrice() {
		return this.childUnitPrice;
	}

	public Integer getAdultCount() {
		return this.adultCount;
	}

	public Integer getChildCount() {
		return this.childCount;
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

	public void setAdultUnitPrice(Integer adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	public void setChildUnitPrice(Integer childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
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

	public boolean equals(final Object o) {
		if (o == this)
			return true;
		if (!(o instanceof PriceCalculateOutput))
			return false;
		final PriceCalculateOutput other = (PriceCalculateOutput) o;
		if (!other.canEqual((Object) this))
			return false;
		final Object this$adultUnitPrice = this.getAdultUnitPrice();
		final Object other$adultUnitPrice = other.getAdultUnitPrice();
		if (this$adultUnitPrice == null ? other$adultUnitPrice != null
				: !this$adultUnitPrice.equals(other$adultUnitPrice))
			return false;
		final Object this$childUnitPrice = this.getChildUnitPrice();
		final Object other$childUnitPrice = other.getChildUnitPrice();
		if (this$childUnitPrice == null ? other$childUnitPrice != null
				: !this$childUnitPrice.equals(other$childUnitPrice))
			return false;
		final Object this$adultCount = this.getAdultCount();
		final Object other$adultCount = other.getAdultCount();
		if (this$adultCount == null ? other$adultCount != null : !this$adultCount.equals(other$adultCount))
			return false;
		final Object this$childCount = this.getChildCount();
		final Object other$childCount = other.getChildCount();
		if (this$childCount == null ? other$childCount != null : !this$childCount.equals(other$childCount))
			return false;
		final Object this$adultPrice = this.getAdultPrice();
		final Object other$adultPrice = other.getAdultPrice();
		if (this$adultPrice == null ? other$adultPrice != null : !this$adultPrice.equals(other$adultPrice))
			return false;
		final Object this$childPrice = this.getChildPrice();
		final Object other$childPrice = other.getChildPrice();
		if (this$childPrice == null ? other$childPrice != null : !this$childPrice.equals(other$childPrice))
			return false;
		final Object this$sumPrice = this.getSumPrice();
		final Object other$sumPrice = other.getSumPrice();
		if (this$sumPrice == null ? other$sumPrice != null : !this$sumPrice.equals(other$sumPrice))
			return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof PriceCalculateOutput;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $adultUnitPrice = this.getAdultUnitPrice();
		result = result * PRIME + ($adultUnitPrice == null ? 43 : $adultUnitPrice.hashCode());
		final Object $childUnitPrice = this.getChildUnitPrice();
		result = result * PRIME + ($childUnitPrice == null ? 43 : $childUnitPrice.hashCode());
		final Object $adultCount = this.getAdultCount();
		result = result * PRIME + ($adultCount == null ? 43 : $adultCount.hashCode());
		final Object $childCount = this.getChildCount();
		result = result * PRIME + ($childCount == null ? 43 : $childCount.hashCode());
		final Object $adultPrice = this.getAdultPrice();
		result = result * PRIME + ($adultPrice == null ? 43 : $adultPrice.hashCode());
		final Object $childPrice = this.getChildPrice();
		result = result * PRIME + ($childPrice == null ? 43 : $childPrice.hashCode());
		final Object $sumPrice = this.getSumPrice();
		result = result * PRIME + ($sumPrice == null ? 43 : $sumPrice.hashCode());
		return result;
	}

	public String toString() {
		return "PriceCalculateOutput(adultUnitPrice=" + this.getAdultUnitPrice() + ", childUnitPrice="
				+ this.getChildUnitPrice() + ", adultCount=" + this.getAdultCount() + ", childCount="
				+ this.getChildCount() + ", adultPrice=" + this.getAdultPrice() + ", childPrice=" + this.getChildPrice()
				+ ", sumPrice=" + this.getSumPrice() + ")";
	}

}
