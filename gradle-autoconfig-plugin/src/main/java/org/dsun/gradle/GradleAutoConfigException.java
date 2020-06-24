package org.dsun.gradle;

import org.slf4j.helpers.FormattingTuple;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;

public class GradleAutoConfigException extends RuntimeException {
    private final FormattingTuple formattingTuple;

    /**
     * @see org.slf4j.helpers.MessageFormatter
     */
    public GradleAutoConfigException(String message, Object... args) {
        formattingTuple = arrayFormat(message, args);
        initCause(formattingTuple.getThrowable());
    }

    @Override
    public String getMessage() {
        return formattingTuple.getMessage();
    }
}

