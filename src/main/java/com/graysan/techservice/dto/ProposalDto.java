package com.graysan.techservice.dto;

public class ProposalDto {

    private String productName;
    private String note;
    private float price;

    public ProposalDto() {
    }

    public ProposalDto(String productName, String note, float price) {
        this.productName = productName;
        this.note = note;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProposalDto{" +
                "productName='" + productName + '\'' +
                ", note='" + note + '\'' +
                ", price=" + price +
                '}';
    }
}
