package com.example.carDealer.model.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


public enum Discount {
    PERCENT_0(0.0), PERCENT_5(0.05), PERCENT_10(0.10),PERCENT_15(0.15),
    PERCENT_20(0.20),PERCENT_30(0.30), PERCENT_40(0.40), PERCENT_50(0.5);
    private final Double value;

    Discount(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }



}
