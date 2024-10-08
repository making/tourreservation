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

public class Accommodation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accomCode;

	private String accomName;

	private String accomTel;

	public Accommodation() {
	}

	public String getAccomCode() {
		return this.accomCode;
	}

	public String getAccomName() {
		return this.accomName;
	}

	public String getAccomTel() {
		return this.accomTel;
	}

	public void setAccomCode(String accomCode) {
		this.accomCode = accomCode;
	}

	public void setAccomName(String accomName) {
		this.accomName = accomName;
	}

	public void setAccomTel(String accomTel) {
		this.accomTel = accomTel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Accommodation that))
			return false;
		return Objects.equals(getAccomCode(), that.getAccomCode())
				&& Objects.equals(getAccomName(), that.getAccomName())
				&& Objects.equals(getAccomTel(), that.getAccomTel());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAccomCode(), getAccomName(), getAccomTel());
	}

	@Override
	public String toString() {
		return "Accommodation{" + "accomCode='" + accomCode + '\'' + ", accomName='" + accomName + '\'' + ", accomTel='"
				+ accomTel + '\'' + '}';
	}

}
