package com.example.grocery;

public class HorizontalProductScrollModel {
    private String productId;
    private String product_Image;
    private String productTitle;
    private String productDesc;
    private  String productPrice;

    public HorizontalProductScrollModel(String productId,String product_image, String productTitle, String productDesc, String productPrice) {
      this.productId=productId;
        product_Image = product_image;
        this.productTitle = productTitle;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProduct_Image() {
        return product_Image;
    }

    public void setProduct_Image(String product_Image) {
        this.product_Image = product_Image;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
