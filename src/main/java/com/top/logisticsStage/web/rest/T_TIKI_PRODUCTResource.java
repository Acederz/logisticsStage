package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.T_TIKI_PRODUCTService;
import com.top.logisticsStage.service.dto.T_TIKI_PRODUCTDTO;
import com.top.logisticsStage.web.rest.errors.ErrorVM;
import com.top.logisticsStage.web.rest.util.HeaderUtil;
import com.top.logisticsStage.web.rest.util.PaginationUtil;
import com.top.logisticsStage.web.rest.vm.T_TIKI_PRODUCTQueryVM;
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
@Api(tags = "tiki产品ID对照表")
public class T_TIKI_PRODUCTResource {

    private final Logger log = LoggerFactory.getLogger(T_TIKI_PRODUCTResource.class);

    private static final String ENTITY_NAME = "T_TIKI_PRODUCT";

    //private final T_TIKI_PRODUCTExcelService t_TIKI_PRODUCTExcelService;

    private final T_TIKI_PRODUCTService t_TIKI_PRODUCTService;

    public T_TIKI_PRODUCTResource(T_TIKI_PRODUCTService t_TIKI_PRODUCTService) {
        this.t_TIKI_PRODUCTService = t_TIKI_PRODUCTService;
    }

    //    @PostMapping(value = {"/T_TIKI_PRODUCT/import"})
//    @Timed
//    @ApiOperation(value = "批量导入")
//    public ResponseEntity<Boolean> uploadLoanFile(MultipartFile file) throws URISyntaxException {
//        Workbook workbook = null;
//        //UserExcelTask userExcelTask = null;
//        //String tenantKey = TenantContext.get();
//        try {
//            workbook = t_TIKI_PRODUCTExcelService.checkFile(file);
//            // userExcelTask = userExcelService.save(file);
//        } catch (RuntimeException e) {
//            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//        //TenantContext.set(tenantKey);
//        try {
//            t_TIKI_PRODUCTExcelService.excelImport(workbook);
//        } catch (Exception e) {
//            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//    }

    @GetMapping("/T_TIKI_PRODUCT")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<List<T_TIKI_PRODUCTDTO>> getList(@ApiParam Pageable pageable, T_TIKI_PRODUCTQueryVM queryVM) {
        Page<T_TIKI_PRODUCTDTO> page = t_TIKI_PRODUCTService.findList(queryVM, pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account");
        //return new ResponseEntity<>(page.getContent(), headersString result = t_TIKI_PRODUCTService.getSSS(), HttpStatus.OK);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/T_TIKI_PRODUCT");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/T_TIKI_PRODUCT")
    @Timed
    @ApiOperation(value = "新增数据列表")
    public ResponseEntity<T_TIKI_PRODUCTDTO> postEntity(@RequestBody T_TIKI_PRODUCTDTO dto) throws URISyntaxException {
        T_TIKI_PRODUCTDTO result = null;
        try {
            result = t_TIKI_PRODUCTService.create(dto);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(new URI("/api/T_TIKI_PRODUCT/" + result.getSku()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getSku().toString()))
                .body(result);
    }

    @PutMapping("/T_TIKI_PRODUCT")
    @Timed
    @ApiOperation(value = "修改数据")
    public ResponseEntity<T_TIKI_PRODUCTDTO> putEntity(@RequestBody T_TIKI_PRODUCTDTO dto) {
        T_TIKI_PRODUCTDTO result = null;
        try {
            result = t_TIKI_PRODUCTService.update(dto);
        } catch (Exception se) {
            return new ResponseEntity(new ErrorVM(se.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getSku().toString()))
                .body(result);
    }

//    @GetMapping(value = "/T_TIKI_PRODUCT/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
//    @Timed
//    @ApiOperation(value = "模板下载")
//    public ResponseEntity<byte[]> getBrandsFile() throws UnsupportedEncodingException {
//        String downloadFileName = "T_TIKI_PRODUCT模板.xlsx";
//        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
//        byte[] OutputStream = t_TIKI_PRODUCTExcelService.downloadFile();
//        if (OutputStream == null) {
//            return new ResponseEntity<byte[]>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Disposition", "attachment;filename=" + downloadFileName);
//        httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//        httpHeaders.setContentLength(OutputStream.length);
//        return new ResponseEntity<byte[]>(OutputStream, httpHeaders, HttpStatus.OK);
//    }
}