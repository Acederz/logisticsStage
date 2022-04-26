package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.T_VIETNAM_POA_FILEService;
import com.top.logisticsStage.service.dto.T_VIETNAM_POA_FILEDTO;
import com.top.logisticsStage.web.rest.errors.ErrorVM;
import com.top.logisticsStage.web.rest.util.HeaderUtil;
import com.top.logisticsStage.web.rest.util.PaginationUtil;
import com.top.logisticsStage.web.rest.vm.T_VIETNAM_POA_FILEQueryVM;
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
@Api(tags = "越南产品主档表")
public class T_VIETNAM_POA_FILEResource {

    private final Logger log = LoggerFactory.getLogger(T_VIETNAM_POA_FILEResource.class);

    private static final String ENTITY_NAME = "T_VIETNAM_POA_FILE";

   // private final T_VIETNAM_POA_FILEExcelService t_VIETNAM_POA_FILEExcelService;

    private final T_VIETNAM_POA_FILEService t_VIETNAM_POA_FILEService;

    public T_VIETNAM_POA_FILEResource(T_VIETNAM_POA_FILEService t_VIETNAM_POA_FILEService) {
        this.t_VIETNAM_POA_FILEService = t_VIETNAM_POA_FILEService;
    }

    //    @PostMapping(value = {"/T_VIETNAM_POA_FILE/import"})
//    @Timed
//    @ApiOperation(value = "批量导入")
//    public ResponseEntity<Boolean> uploadLoanFile(MultipartFile file) throws URISyntaxException {
//        Workbook workbook = null;
//        //UserExcelTask userExcelTask = null;
//        //String tenantKey = TenantContext.get();
//        try {
//            workbook = t_VIETNAM_POA_FILEExcelService.checkFile(file);
//            // userExcelTask = userExcelService.save(file);
//        } catch (RuntimeException e) {
//            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//        //TenantContext.set(tenantKey);
//        try {
//            t_VIETNAM_POA_FILEExcelService.excelImport(workbook);
//        } catch (Exception e) {
//            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//    }

    @GetMapping("/T_VIETNAM_POA_FILE")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<List<T_VIETNAM_POA_FILEDTO>> getList(@ApiParam Pageable pageable, T_VIETNAM_POA_FILEQueryVM queryVM) {
        Page<T_VIETNAM_POA_FILEDTO> page = t_VIETNAM_POA_FILEService.findList(queryVM, pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account");
        //return new ResponseEntity<>(page.getContent(), headersString result = t_VIETNAM_POA_FILEService.getSSS(), HttpStatus.OK);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/T_VIETNAM_POA_FILE");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/T_VIETNAM_POA_FILE")
    @Timed
    @ApiOperation(value = "新增数据列表")
    public ResponseEntity<T_VIETNAM_POA_FILEDTO> postEntity(@RequestBody T_VIETNAM_POA_FILEDTO dto) throws URISyntaxException {
        T_VIETNAM_POA_FILEDTO result = null;
        try {
            result = t_VIETNAM_POA_FILEService.create(dto);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(new URI("/api/T_VIETNAM_POA_FILE/" + result.getBarCode()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getBarCode().toString()))
                .body(result);
    }

    @PutMapping("/T_VIETNAM_POA_FILE")
    @Timed
    @ApiOperation(value = "修改数据")
    public ResponseEntity<T_VIETNAM_POA_FILEDTO> putEntity(@RequestBody T_VIETNAM_POA_FILEDTO dto) {
        T_VIETNAM_POA_FILEDTO result = null;
        try {
            result = t_VIETNAM_POA_FILEService.update(dto);
        } catch (Exception se) {
            return new ResponseEntity(new ErrorVM(se.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getBarCode().toString()))
                .body(result);
    }

//    @GetMapping(value = "/T_VIETNAM_POA_FILE/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
//    @Timed
//    @ApiOperation(value = "模板下载")
//    public ResponseEntity<byte[]> getBrandsFile() throws UnsupportedEncodingException {
//        String downloadFileName = "T_VIETNAM_POA_FILE模板.xlsx";
//        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
//        byte[] OutputStream = t_VIETNAM_POA_FILEExcelService.downloadFile();
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

