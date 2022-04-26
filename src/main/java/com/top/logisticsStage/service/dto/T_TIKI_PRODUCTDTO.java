package com.top.logisticsStage.service.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class T_TIKI_PRODUCTDTO implements Serializable {

    @ApiModelProperty( value= "SKU" )
    private String sku;

    @ApiModelProperty( value= "PRODUCT" )
    private String product;

    @ApiModelProperty( value= "PRODUCTID" )
    private String productId;

    @ApiModelProperty( value= "ID" )
    private String id;

    @ApiModelProperty( value= "MID" )
    private String mid;

    @ApiModelProperty( value= "MSKU" )
    private String msku;

    @ApiModelProperty( value= "PID" )
    private String pid;

    @ApiModelProperty( value= "PSKU" )
    private String psku;

    @ApiModelProperty( value= "PT001" )
    private String pt001;

    @ApiModelProperty( value= "PT002" )
    private String pt002;

    @ApiModelProperty( value= "PT003" )
    private String pt003;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPsku() {
        return psku;
    }

    public void setPsku(String psku) {
        this.psku = psku;
    }

    public String getPt001() {
        return pt001;
    }

    public void setPt001(String pt001) {
        this.pt001 = pt001;
    }

    public String getPt002() {
        return pt002;
    }

    public void setPt002(String pt002) {
        this.pt002 = pt002;
    }

    public String getPt003() {
        return pt003;
    }

    public void setPt003(String pt003) {
        this.pt003 = pt003;
    }

    @Override
    public String toString() {
        return "T_TIKI_PRODUCTDTO{" +
                "sku='" + sku + '\'' +
                ", product='" + product + '\'' +
                ", productId='" + productId + '\'' +
                ", id='" + id + '\'' +
                ", mid='" + mid + '\'' +
                ", msku='" + msku + '\'' +
                ", pid='" + pid + '\'' +
                ", psku='" + psku + '\'' +
                ", pt001='" + pt001 + '\'' +
                ", pt002='" + pt002 + '\'' +
                ", pt003='" + pt003 + '\'' +
                '}';
    }
}
