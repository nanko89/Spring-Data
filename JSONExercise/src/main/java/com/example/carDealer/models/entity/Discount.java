package com.example.carDealer.models.entity;

public enum Discount {
    DISCOUNT_0(0f), DISCOUNT_5(0.05f), DISCOUNT_10(0.10f), DISCOUNT_15(0.15f),
    DISCOUNT_20(0.20f),DISCOUNT_30(0.30f), DISCOUNT_40(0.40f), DISCOUNT_50(0.5f);
    private final float value;

    Discount(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }



}
