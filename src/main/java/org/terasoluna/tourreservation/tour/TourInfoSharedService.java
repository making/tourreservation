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

import java.time.Clock;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class TourInfoSharedService {

	private static final Logger log = LoggerFactory.getLogger(TourInfoSharedService.class);

	private final TourInfoMapper tourInfoMapper;

	private final Clock clock;

	public TourInfoSharedService(TourInfoMapper tourInfoMapper, Clock clock) {
		this.tourInfoMapper = tourInfoMapper;
		this.clock = clock;
	}

	public TourInfo findOneWithDetails(String tourCode) {
		return tourInfoMapper.findOneWithDetails(tourCode);
	}

	public boolean isOverPaymentLimit(TourInfo tour) {
		Assert.notNull(tour, "tour must not be null");
		LocalDate today = LocalDate.now(this.clock);
		LocalDate paymentLimit = tour.getPaymentLimit();

		log.debug("today={}, paymentLimit={}", today, paymentLimit);
		return today.isAfter(paymentLimit);
	}

	public TourInfo findOneWithDetailsForUpdate(String tourCode) {
		return tourInfoMapper.findOneWithDetailsForUpdate(tourCode);
	}

}
