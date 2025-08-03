package com.jszw.bookstore.domain;

public enum ReviewRating {
    HALF(0.5),
    ONE(1.0),
    ONE_AND_HALF(1.5),
    TWO(2.0),
    TWO_AND_HALF(2.5),
    THREE(3.0),
    THREE_AND_HALF(3.5),
    FOUR(4.0),
    FOUR_AND_HALF(4.5),
    FIVE(5.0);

    private final double value;

    ReviewRating(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public static ReviewRating fromValue(double value) {
        for (ReviewRating rating : values()) {
            if (Double.compare(rating.value, value) == 0) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Valor de rating inválido: " + value);
    }
}

