package com.andreldm.timeclockapi.config.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime datetime) {
        return (datetime == null ? null : Timestamp.valueOf(datetime));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp datetime) {
        return (datetime == null ? null : datetime.toLocalDateTime());
    }
}
