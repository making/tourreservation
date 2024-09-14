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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terasoluna.tourreservation.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class Age implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ageCode;

	private String ageName;

	private int ageRate;

	public Age() {
	}

	public String getAgeCode() {
		return this.ageCode;
	}

	public String getAgeName() {
		return this.ageName;
	}

	public int getAgeRate() {
		return this.ageRate;
	}

	public void setAgeCode(String ageCode) {
		this.ageCode = ageCode;
	}

	public void setAgeName(String ageName) {
		this.ageName = ageName;
	}

	public void setAgeRate(int ageRate) {
		this.ageRate = ageRate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Age age))
			return false;
		return getAgeRate() == age.getAgeRate() && Objects.equals(getAgeCode(), age.getAgeCode())
				&& Objects.equals(getAgeName(), age.getAgeName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAgeCode(), getAgeName(), getAgeRate());
	}

	@Override
	public String toString() {
		return "Age{" + "ageCode='" + ageCode + '\'' + ", ageName='" + ageName + '\'' + ", ageRate=" + ageRate + '}';
	}

}
