package org.terasoluna.tourreservation.common;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.util.UriComponentsBuilder;

public final class QueryStringEncoder {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String encode(Object bean) {
		Map<String, Object> map = objectMapper.convertValue(bean, new TypeReference<>() {
		});
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		map.forEach((k, v) -> {
			if (v instanceof Collection<?> col) {
				builder.queryParam(k, col);
			}
			else {
				builder.queryParam(k, v);
			}
		});
		return builder.encode().build().getQuery();
	}

}
