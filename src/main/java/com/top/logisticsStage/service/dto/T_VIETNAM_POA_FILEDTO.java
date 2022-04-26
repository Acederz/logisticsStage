package com.top.logisticsStage.service.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public class T_VIETNAM_POA_FILEDTO implements Serializable {

    @ApiModelProperty( value= "条码" )
    private String barCode;

    @ApiModelProperty( value= "品名" )
    private String itemName;

    @ApiModelProperty( value= "越南文" )
    private String vnString;

    @ApiModelProperty( value= "英文品名" )
    private String enString;

    @ApiModelProperty( value= "箱入数")
    private BigDecimal number;

    @ApiModelProperty( value= "产地" )
    private String productPlace;

    @ApiModelProperty( value= "国家" )
    private String country;

    @ApiModelProperty( value= "国家码" )
    private String countryCode;

    @ApiModelProperty( value= "品牌" )
    private String brand;

    @ApiModelProperty( value= "品类" )
    private String category;

    @ApiModelProperty( value= "品类代码" )
    private String categoryCode;

    @ApiModelProperty( value= "系列" )
    private String series;

    @ApiModelProperty( value= "系列代码" )
    private String seriesCode;

    @ApiModelProperty( value= "条码流水" )
    private String barCodeFlow;

    @ApiModelProperty( value= "料号" )
    private String itemCode;

    @ApiModelProperty( value= "箱条码" )
    private String boxCode;

    @ApiModelProperty( value= "品质规范产品简称" )
    private String qualityName;

    @ApiModelProperty( value= "HS_CODE" )
    private String hsCode;

    @ApiModelProperty( value= "产品尺寸" )
    private String itemSize;

    @ApiModelProperty( value= "箱尺寸" )
    private String boxSize;

    @ApiModelProperty( value= "箱体积")
    private BigDecimal boxVolume;

    @ApiModelProperty( value= "采购-箱体积")
    private BigDecimal buyBoxVolume;

    @ApiModelProperty( value= "毛重KG")
    private BigDecimal grossWeight;

    @ApiModelProperty( value= "净重KG")
    private BigDecimal netWeight;

    @ApiModelProperty( value= "库存表名称" )
    private String tableName;

    @ApiModelProperty( value= "POA001" )
    private String poa001;

    @ApiModelProperty( value= "POA002" )
    private String poa002;

    @ApiModelProperty( value= "POA003" )
    private String poa003;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getVnString() {
        return vnString;
    }

    public void setVnString(String vnString) {
        this.vnString = vnString;
    }

    public String getEnString() {
        return enString;
    }

    public void setEnString(String enString) {
        this.enString = enString;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public String getProductPlace() {
        return productPlace;
    }

    public void setProductPlace(String productPlace) {
        this.productPlace = productPlace;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    public String getBarCodeFlow() {
        return barCodeFlow;
    }

    public void setBarCodeFlow(String barCodeFlow) {
        this.barCodeFlow = barCodeFlow;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getQualityName() {
        return qualityName;
    }

    public void setQualityName(String qualityName) {
        this.qualityName = qualityName;
    }

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    public BigDecimal getBoxVolume() {
        return boxVolume;
    }

    public void setBoxVolume(BigDecimal boxVolume) {
        this.boxVolume = boxVolume;
    }

    public BigDecimal getBuyBoxVolume() {
        return buyBoxVolume;
    }

    public void setBuyBoxVolume(BigDecimal buyBoxVolume) {
        this.buyBoxVolume = buyBoxVolume;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPoa001() {
        return poa001;
    }

    public void setPoa001(String poa001) {
        this.poa001 = poa001;
    }

    public String getPoa002() {
        return poa002;
    }

    public void setPoa002(String poa002) {
        this.poa002 = poa002;
    }

    public String getPoa003() {
        return poa003;
    }

    public void setPoa003(String poa003) {
        this.poa003 = poa003;
    }

    @Override
    public String toString() {
        return "T_VIETNAM_POA_FILEDTO{" +
                "barCode='" + barCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", vnString='" + vnString + '\'' +
                ", enString='" + enString + '\'' +
                ", number=" + number +
                ", productPlace='" + productPlace + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", series='" + series + '\'' +
                ", seriesCode='" + seriesCode + '\'' +
                ", barCodeFlow='" + barCodeFlow + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", boxCode='" + boxCode + '\'' +
                ", qualityName='" + qualityName + '\'' +
                ", hsCode='" + hsCode + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", boxSize='" + boxSize + '\'' +
                ", boxVolume=" + boxVolume +
                ", buyBoxVolume=" + buyBoxVolume +
                ", grossWeight=" + grossWeight +
                ", netWeight=" + netWeight +
                ", tableName='" + tableName + '\'' +
                ", poa001='" + poa001 + '\'' +
                ", poa002='" + poa002 + '\'' +
                ", poa003='" + poa003 + '\'' +
                '}';
    }
}
