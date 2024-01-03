package com.graysan.techservice.model;

import org.springframework.data.annotation.Id;

public class Proposal {
    private long id;
    private String note;
    private int price;
    private long userId;
    private long productId;

    public Proposal() {

    }

    public Proposal(long id, String note, int price, long userId, long productId) {
        this.id = id;
        this.note = note;
        this.price = price;
        this.userId = userId;
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", price=" + price +
                ", userId=" + userId +
                ", productId=" + productId +
                '}';
    }
}
