package com.example.carDealer.model.entity;

public enum Discount {
    PERCENT_0(0f), PERCENT_5(0.05f), PERCENT_10(0.10f),PERCENT_15(0.15f),
    PERCENT_20(0.20f),PERCENT_30(0.30f), PERCENT_40(0.40f), PERCENT_50(0.5f);
    private final float value;

    Discount(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }



}
