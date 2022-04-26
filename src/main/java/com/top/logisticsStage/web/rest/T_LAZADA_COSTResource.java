package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.T_LAZADA_COSTService;
import com.top.logisticsStage.service.dto.T_LAZADA_COSTDTO;
import com.top.logisticsStage.web.rest.errors.ErrorVM;
import com.top.logisticsStage.web.rest.util.HeaderUtil;
import com.top.logisticsStage.web.rest.util.PaginationUtil;
import com.top.logisticsStage.web.rest.vm.T_LAZADA_COSTQueryVM;
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
@Api(tags = "Lazada费用说明对照表")
public class T_LAZADA_COSTResource {

    private final Logger log = LoggerFactory.getLogger(T_LAZADA_COSTResource.class);

    private static final String ENTITY_NAME = "T_LAZADA_COST";

    //private final T_LAZADA_COSTExcelService t_LAZADA_COSTExcelService;

    private final T_LAZADA_COSTService t_LAZADA_COSTService;

    public T_LAZADA_COSTResource(T_LAZADA_COSTService t_LAZADA_COSTService) {
        this.t_LAZADA_COSTService = t_LAZADA_COSTService;
    }

//    @PostMapping(value = {"/T_LAZADA_COST/import"})
//    @Timed
//    @ApiOperation(value = "批量导入")
//    public ResponseEntity<Boolean> uploadLoanFile(MultipartFile file) throws URISyntaxException {
//        Workbook workbook = null;
//        //UserExcelTask userExcelTask = null;
//        //String tenantKey = TenantContext.get();
//        try {
//            workbook = t_LAZADA_COSTExcelService.checkFile(file);
//            // userExcelTask = userExcelService.save(file);
//        } catch (RuntimeException e) {
//            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//        //TenantContext.set(tenantKey);
//        try {
//            t_LAZADA_COSTExcelService.excelImport(workbook);
//        } catch (Exception e) {
//            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//    }

    @GetMapping("/T_LAZADA_COST")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<List<T_LAZADA_COSTDTO>> getList(@ApiParam Pageable pageable, T_LAZADA_COSTQueryVM queryVM) {
        Page<T_LAZADA_COSTDTO> page = t_LAZADA_COSTService.findList(queryVM, pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account");
        //return new ResponseEntity<>(page.getContent(), headersString result = t_LAZADA_COSTService.getSSS(), HttpStatus.OK);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/T_LAZADA_COST");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/T_LAZADA_COST")
    @Timed
    @ApiOperation(value = "新增数据列表")
    public ResponseEntity<T_LAZADA_COSTDTO> postEntity(@RequestBody T_LAZADA_COSTDTO dto) throws URISyntaxException {
        T_LAZADA_COSTDTO result = null;
        try {
            result = t_LAZADA_COSTService.create(dto);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(new URI("/api/T_LAZADA_COST/" + result.getCt001()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getCt001().toString()))
                .body(result);
    }

    @PutMapping("/T_LAZADA_COST")
    @Timed
    @ApiOperation(value = "修改数据")
    public ResponseEntity<T_LAZADA_COSTDTO> putEntity(@RequestBody T_LAZADA_COSTDTO dto) {
        T_LAZADA_COSTDTO result = null;
        try {
            result = t_LAZADA_COSTService.update(dto);
        } catch (Exception se) {
            return new ResponseEntity(new ErrorVM(se.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getCt001().toString()))
                .body(result);
    }

//    @GetMapping(value = "/T_LAZADA_COST/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
//    @Timed
//    @ApiOperation(value = "模板下载")
//    public ResponseEntity<byte[]> getBrandsFile() throws UnsupportedEncodingException {
//        String downloadFileName = "T_LAZADA_COST模板.xlsx";
//        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
//        byte[] OutputStream = t_LAZADA_COSTExcelService.downloadFile();
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

