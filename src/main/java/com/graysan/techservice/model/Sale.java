package com.graysan.techservice.model;

public class Sale {

    private long id;
    private String note;
    private int price;
    private long productId;

    public Sale() {
    }

    public Sale(long id, String note, int price, long productId) {
        this.id = id;
        this.note = note;
        this.price = price;
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

    public long getProduct_id() {
        return productId;
    }

    public void setProduct_id(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", price=" + price +
                ", productId=" + productId +
                '}';
    }
}
