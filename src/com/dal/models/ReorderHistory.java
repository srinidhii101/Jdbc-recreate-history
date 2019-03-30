package com.dal.models;

import java.util.Date;

public class ReorderHistory {
    public Date reorderDate;
    public Integer reorderUnits;
    public Double unitPrice;
    public Double totalPrice;

    public Date getReorderDate() {
        return reorderDate;
    }

    public void setReorderDate(Date reorderDate) {
        this.reorderDate = reorderDate;
    }

    public Integer getReorderUnits() {
        return reorderUnits;
    }

    public void setReorderUnits(Integer reorderUnits) {
        this.reorderUnits = reorderUnits;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
