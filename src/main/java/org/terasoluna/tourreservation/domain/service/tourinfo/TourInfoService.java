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
package org.terasoluna.tourreservation.domain.service.tourinfo;

import java.util.Collections;
import java.util.List;

import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoRepository;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TourInfoService {

	private final TourInfoRepository tourInfoRepository;

	public TourInfoService(TourInfoRepository tourInfoRepository) {
		this.tourInfoRepository = tourInfoRepository;
	}

	public Page<TourInfo> searchTour(TourInfoSearchCriteria criteria, Pageable pageable) {

		long total = tourInfoRepository.countBySearchCriteria(criteria);
		List<TourInfo> content;
		if (0 < total) {
			content = tourInfoRepository.findPageBySearchCriteria(criteria, pageable);
		}
		else {
			content = Collections.emptyList();
		}

		Page<TourInfo> page = new PageImpl<>(content, pageable, total);
		return page;
	}

}
