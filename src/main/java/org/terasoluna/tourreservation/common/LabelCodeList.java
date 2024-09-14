package org.terasoluna.tourreservation.common;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

public interface LabelCodeList {

	@Nullable
	default String getLabelFromCode(String code) {
		for (LabelCode labelCode : this.labelCodes()) {
			if (labelCode.code().equals(code)) {
				return labelCode.label();
			}
		}
		return null;
	}

	@Nullable
	default String getCodeFromLabel(String label) {
		for (LabelCode labelCode : this.labelCodes()) {
			if (labelCode.label().equals(label)) {
				return labelCode.code();
			}
		}
		return null;
	}

	List<LabelCode> labelCodes();

	default Map<String, String> asMap() {
		return this.labelCodes().stream().collect(Collectors.toMap(LabelCode::label, LabelCode::code));
	}

	static LabelCodeList of(List<LabelCode> labelCodes) {
		return () -> labelCodes;
	}

	static LabelCodeList of(LabelCode... labelCodes) {
		List<LabelCode> labelCodeList = Arrays.asList(labelCodes);
		return LabelCodeList.of(labelCodeList);
	}

	static LabelCodeList of(LabelCodeBuilders.Optionals... builders) {
		List<LabelCode> labelCodeList = Stream.of(builders).map(LabelCodeBuilders.Optionals::build).toList();
		return LabelCodeList.of(labelCodeList);
	}

}
