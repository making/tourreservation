package org.terasoluna.tourreservation.common;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.terasoluna.tourreservation.common.LabelCodeBuilder.labelCode;

class RefreshableLabelCodeListTest {

	@Test
	void refresh() {
		AtomicInteger counter = new AtomicInteger(1);
		RefreshableLabelCodeList codeList = LabelCodeLists
			.refreshable(() -> LabelCodeList.of(labelCode().label("a" + counter.getAndIncrement()).code("1")));
		assertThat(codeList.labelCodes()).containsExactly(labelCode().label("a1").code("1").build());
		assertThat(codeList.labelCodes()).containsExactly(labelCode().label("a1").code("1").build());
		codeList.refresh();
		assertThat(codeList.labelCodes()).containsExactly(labelCode().label("a2").code("1").build());
		assertThat(codeList.labelCodes()).containsExactly(labelCode().label("a2").code("1").build());
		codeList.refresh();
		assertThat(codeList.labelCodes()).containsExactly(labelCode().label("a3").code("1").build());
		assertThat(codeList.labelCodes()).containsExactly(labelCode().label("a3").code("1").build());
	}

}