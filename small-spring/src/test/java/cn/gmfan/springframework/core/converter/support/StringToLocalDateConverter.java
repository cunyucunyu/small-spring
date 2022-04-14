package cn.gmfan.springframework.core.converter.support;

import cn.gmfan.springframework.core.convert.converter.Converter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author gmfan
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    private final DateTimeFormatter DATE_TIME_FORMATTER;

    public StringToLocalDateConverter(String pattern) {
        DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source,DATE_TIME_FORMATTER);
    }
}
