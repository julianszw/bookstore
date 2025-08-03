package com.jszw.bookstore.converter;

import com.jszw.bookstore.domain.ReviewRating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ReviewRatingConverter implements AttributeConverter<ReviewRating, Double> {

    @Override
    public Double convertToDatabaseColumn(ReviewRating attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public ReviewRating convertToEntityAttribute(Double dbData) {
        return dbData != null ? ReviewRating.fromValue(dbData) : null;
    }
}

