package com.helloJob.commons.utils;

import java.beans.PropertyEditorSupport;

import org.springframework.web.util.HtmlUtils;

public class StringEscapeEditor extends PropertyEditorSupport {
    public StringEscapeEditor() {}

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null) {
            setValue(null);
        } else {
            setValue(HtmlUtils.htmlEscape(text));
        }
    }

}
