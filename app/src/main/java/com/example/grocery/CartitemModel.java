package com.example.grocery;

import java.util.ArrayList;
import java.util.List;

public class CartitemModel {

    public static final int CART_ITEM=0;
    public static final int TOTAL_AMOUNT= 1;
    private int type;
    //////////cart item

    private String productID;
 private String productImage;
    private String productTitle;
    private long freecoupens;
    private String productPrice;
    private String cuttedPrice;
    private long productQuantity;
    private  long maxQuantity;
    private  long stockQuantity;

    private  long offersApplied;
    private long coupensApplied;
    private boolean inStock;
private List<String> qtyIDs;
private boolean qtyError;
private String selectedCoupenId;

    public CartitemModel(int type,String productID, String productImage, String productTitle, long freecoupens, String productPrice, String cuttedPrice, long productQuantity, long offersApplied, long coupensApplied,boolean inStock,long maxQuantity,long stockQuantity) {
        this.type = type;
        this.productID=productID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freecoupens = freecoupens;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.coupensApplied = coupensApplied;
        this.maxQuantity=maxQuantity;
        this.inStock = inStock;
        this.stockQuantity=stockQuantity;
        qtyIDs = new ArrayList<>();
        qtyError=false;
    }

    public String getSelectedCoupenId() {
        return selectedCoupenId;
    }

    public void setSelectedCoupenId(String selectedCoupenId) {
        this.selectedCoupenId = selectedCoupenId;
    }

    public boolean isQtyError() {
        return qtyError;
    }

    public void setQtyError(boolean qtyError) {
        this.qtyError = qtyError;
    }

    public List<String> getQtyIDs() {
        return qtyIDs;
    }

    public long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setQtyIDs(List<String> qtyIDs) {
        this.qtyIDs = qtyIDs;
    }

    public long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreecoupens() {
        return freecoupens;
    }

    public void setFreecoupens(long freecoupens) {
        this.freecoupens = freecoupens;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public long getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(long coupensApplied) {
        this.coupensApplied = coupensApplied;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //////////cart item

    //////cart total
/*
    private String totalItems;
    private String totalItemPrice;
    private String deliveryPrice;
    private String savedAmount;
    private String totalAmount;

    public CartitemModel(int type, String totalItems, String totalItemPrice, String deliveryPrice, String totalAmount, String savedAmount) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.deliveryPrice = deliveryPrice;
        this.totalAmount = totalAmount;
        this.savedAmount = savedAmount;

    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    //////cart total
    */

      public CartitemModel(int type){
          this.type=type;
      }

}
