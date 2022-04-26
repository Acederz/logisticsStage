package com.top.logisticsStage.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

public class T_TIKI_PRODUCTQueryVM {

    @ApiModelProperty( value ="SKU" )
    private String sku;

    @ApiModelProperty( value ="PRODUCT" )
    private String product;

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
}
