package com.graysan.techservice.model;

import java.util.Date;

public class SaleLog {

    private long id;
    private long saleId;
    private long userId;
    private Date saleLogDate;
    private String creditCardNumber;

    public SaleLog() {
    }

    public SaleLog(long id, long saleId, long userId, Date saleLogDate, String creditCardNumber) {
        this.id = id;
        this.saleId = saleId;
        this.userId = userId;
        this.saleLogDate = saleLogDate;
        this.creditCardNumber = creditCardNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getSaleLogDate() {
        return saleLogDate;
    }

    public void setSaleLogDate(Date saleLogDate) {
        this.saleLogDate = saleLogDate;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public String toString() {
        return "SaleLog{" +
                "id=" + id +
                ", saleId=" + saleId +
                ", userId=" + userId +
                ", saleLogDate=" + saleLogDate +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                '}';
    }
}
