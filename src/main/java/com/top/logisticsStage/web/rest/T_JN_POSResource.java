package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.T_JN_POSExcelService;
import com.top.logisticsStage.service.T_JN_POSService;
import com.top.logisticsStage.service.dto.T_JN_POSDTO;
import com.top.logisticsStage.web.rest.errors.ErrorVM;
import com.top.logisticsStage.web.rest.util.HeaderUtil;
import com.top.logisticsStage.web.rest.util.PaginationUtil;
import com.top.logisticsStage.web.rest.vm.T_JN_POSQueryVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
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
@Api(tags = "济南分POS数据")
public class T_JN_POSResource {

    private static final String ENTITY_NAME = "T_JN_POS";

    private final T_JN_POSExcelService t_JN_POSExcelService;

    private final T_JN_POSService t_JN_POSService;

    public T_JN_POSResource(T_JN_POSExcelService t_JN_POSExcelService, T_JN_POSService t_JN_POSService) {
        this.t_JN_POSExcelService = t_JN_POSExcelService;
        this.t_JN_POSService = t_JN_POSService;
    }

    @PostMapping(value = {"/T_JN_POS/import"})
    @Timed
    @ApiOperation(value = "批量导入")
    public ResponseEntity<Boolean> uploadLoanFile(MultipartFile file) throws URISyntaxException {
        Workbook workbook = null;
        try {
            workbook = t_JN_POSExcelService.checkFile(file);
        } catch (RuntimeException e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        //TenantContext.set(tenantKey);
        try {
            t_JN_POSExcelService.excelImport(workbook);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

//    @GetMapping("/T_JN_POS")
//    @Timed
//    @ApiOperation(value = "查询数据列表")
//    public ResponseEntity<List<T_JN_POSDTO>> getList(@ApiParam Pageable pageable, T_JN_POSQueryVM queryVM) {
//       // Page<T_JN_POSDTO> page = t_JN_POSService.findList(queryVM, pageable);
//        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account");
//        //return new ResponseEntity<>(page.getContent(), headersString result = t_JN_POSService.getSSS(), HttpStatus.OK);
//      //  HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/T_JN_POS");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    @PostMapping("/T_JN_POS")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<T_JN_POSDTO> postEntity(@RequestBody T_JN_POSDTO dto) throws URISyntaxException {
        T_JN_POSDTO result = null;
        try {
            result = t_JN_POSService.create(dto);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(new URI("/api/T_JN_POS/" + result.getBarCode()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getBarCode().toString()))
                .body(result);
    }

    @PutMapping("/T_JN_POS")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<T_JN_POSDTO> putEntity(@RequestBody T_JN_POSDTO dto) {
        T_JN_POSDTO result = null;
        try {
            result = t_JN_POSService.update(dto);
        } catch (Exception se) {
            return new ResponseEntity(new ErrorVM(se.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getBarCode()))
                .body(result);
    }

    @GetMapping(value = "/T_JN_POS/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Timed
    @ApiOperation(value = "模板下载")
    public ResponseEntity<byte[]> getBrandsFile() throws UnsupportedEncodingException {
        String downloadFileName = "T_JN_POS模板.xlsx";
        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
        byte[] OutputStream = t_JN_POSExcelService.downloadFile();
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

