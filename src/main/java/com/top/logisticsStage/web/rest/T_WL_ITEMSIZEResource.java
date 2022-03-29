package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.T_WL_ITEMSIZEExcelService;
import com.top.logisticsStage.service.T_WL_ITEMSIZEService;
import com.top.logisticsStage.service.dto.T_WL_ITEMSIZEDTO;
import com.top.logisticsStage.web.rest.errors.ErrorVM;
import com.top.logisticsStage.web.rest.util.HeaderUtil;
import com.top.logisticsStage.web.rest.util.PaginationUtil;
import com.top.logisticsStage.web.rest.vm.T_WL_ITEMSIZEQueryVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "产品尺寸表")
public class T_WL_ITEMSIZEResource {

    private final Logger log = LoggerFactory.getLogger(T_WL_ITEMSIZEResource.class);

    private static final String ENTITY_NAME = "T_WL_ITEMSIZE";

    private final T_WL_ITEMSIZEExcelService t_wl_itemsizeExcelService;

    private final T_WL_ITEMSIZEService t_wl_itemsizeService;

    public T_WL_ITEMSIZEResource(T_WL_ITEMSIZEExcelService t_wl_itemsizeExcelService, T_WL_ITEMSIZEService t_wl_itemsizeService) {
        this.t_wl_itemsizeExcelService = t_wl_itemsizeExcelService;
        this.t_wl_itemsizeService = t_wl_itemsizeService;
    }

    @PostMapping(value = {"/T_WL_ITEMSIZE/import"})
    @Timed
    @ApiOperation(value = "批量导入")
    public ResponseEntity<Boolean> uploadLoanFile(MultipartFile file) throws URISyntaxException {
        Workbook workbook = null;
        //UserExcelTask userExcelTask = null;
        //String tenantKey = TenantContext.get();
        try {
            workbook = t_wl_itemsizeExcelService.checkFile(file);
           // userExcelTask = userExcelService.save(file);
        } catch (RuntimeException e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        //TenantContext.set(tenantKey);
        try {
            t_wl_itemsizeExcelService.excelImport(workbook);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/T_WL_ITEMSIZE")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<List<T_WL_ITEMSIZEDTO>> getList(@ApiParam Pageable pageable, T_WL_ITEMSIZEQueryVM queryVM) {
        Page<T_WL_ITEMSIZEDTO> page = t_wl_itemsizeService.findList(queryVM, pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account");
       //return new ResponseEntity<>(page.getContent(), headersString result = t_wl_itemsizeService.getSSS(), HttpStatus.OK);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/T_WL_ITEMSIZE");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/T_WL_ITEMSIZE")
    @Timed
    @ApiOperation(value = "新增数据列表")
    public ResponseEntity<T_WL_ITEMSIZEDTO> postEntity(@RequestBody T_WL_ITEMSIZEDTO dto) throws URISyntaxException {
        T_WL_ITEMSIZEDTO result = null;
        try {
            result = t_wl_itemsizeService.create(dto);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(new URI("/api/T_WL_ITEMSIZE/" + result.getBarCode()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getBarCode().toString()))
                .body(result);
    }

    @PutMapping("/T_WL_ITEMSIZE")
    @Timed
    @ApiOperation(value = "修改数据")
    public ResponseEntity<T_WL_ITEMSIZEDTO> putEntity(@RequestBody T_WL_ITEMSIZEDTO dto) {
        T_WL_ITEMSIZEDTO result = null;
        try {
            result = t_wl_itemsizeService.update(dto);
        } catch (Exception se) {
            return new ResponseEntity(new ErrorVM(se.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getBarCode()))
                .body(result);
    }

    @GetMapping(value = "/T_WL_ITEMSIZE/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Timed
    @ApiOperation(value = "模板下载")
    public ResponseEntity<byte[]> getBrandsFile() throws UnsupportedEncodingException {
        String downloadFileName = "T_WL_ITEMSIZE模板.xlsx";
        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
        byte[] OutputStream = t_wl_itemsizeExcelService.downloadFile();
        if (OutputStream == null) {
            return new ResponseEntity<byte[]>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=" + downloadFileName);
        httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        httpHeaders.setContentLength(OutputStream.length);
        return new ResponseEntity<byte[]>(OutputStream, httpHeaders, HttpStatus.OK);
    }
}
