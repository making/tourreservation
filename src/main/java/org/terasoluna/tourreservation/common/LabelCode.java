package org.terasoluna.tourreservation.common;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

@Builder(style = BuilderStyle.STAGED)
public record LabelCode(String label, String code) {
}
