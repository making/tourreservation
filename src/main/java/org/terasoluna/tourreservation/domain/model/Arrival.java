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

public class Arrival implements Serializable {

	private static final long serialVersionUID = 1L;

	private String arrCode;

	private String arrName;

	public Arrival() {
	}

	public String getArrCode() {
		return this.arrCode;
	}

	public String getArrName() {
		return this.arrName;
	}

	public void setArrCode(String arrCode) {
		this.arrCode = arrCode;
	}

	public void setArrName(String arrName) {
		this.arrName = arrName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Arrival arrival))
			return false;
		return Objects.equals(getArrCode(), arrival.getArrCode()) && Objects.equals(getArrName(), arrival.getArrName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getArrCode(), getArrName());
	}

	@Override
	public String toString() {
		return "Arrival{" + "arrCode='" + arrCode + '\'' + ", arrName='" + arrName + '\'' + '}';
	}

}
