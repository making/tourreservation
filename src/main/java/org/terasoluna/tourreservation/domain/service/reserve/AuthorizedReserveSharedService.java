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
package org.terasoluna.tourreservation.domain.service.reserve;

import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.repository.reserve.ReserveRepository;

import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuthorizedReserveSharedService {

	private final ReserveRepository reserveRepository;

	public AuthorizedReserveSharedService(ReserveRepository reserveRepository) {
		this.reserveRepository = reserveRepository;
	}

	@PostAuthorize("returnObject == null or returnObject.customer.customerCode == principal.customer.customerCode")
	@Nullable
	public Reserve findOne(String reserveNo) {
		return reserveRepository.findById(reserveNo).orElse(null);
	}

}
