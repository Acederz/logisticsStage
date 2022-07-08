package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.T_NEW_RETAIL_DYGMVExcelService;
import com.top.logisticsStage.service.T_NEW_RETAIL_DYGMVService;
import com.top.logisticsStage.service.dto.T_NEW_RETAIL_DYGMVDTO;
import com.top.logisticsStage.web.rest.errors.ErrorVM;
import com.top.logisticsStage.web.rest.util.PaginationUtil;
import com.top.logisticsStage.web.rest.vm.T_NEW_RETAIL_DYGMVQueryVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
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
@Api(tags = "抖音电商轮盘价格分析")
public class T_NEW_RETAIL_DYGMVResource {

    private final Logger log = LoggerFactory.getLogger(T_NEW_RETAIL_DYGMVResource.class);

    private static final String ENTITY_NAME = "T_NEW_RETAIL_DYGMV";

    private final T_NEW_RETAIL_DYGMVExcelService t_NEW_RETAIL_DYGMVExcelService;

    private final T_NEW_RETAIL_DYGMVService t_NEW_RETAIL_DYGMVService;

    public T_NEW_RETAIL_DYGMVResource(T_NEW_RETAIL_DYGMVExcelService t_NEW_RETAIL_DYGMVExcelService, T_NEW_RETAIL_DYGMVService t_NEW_RETAIL_DYGMVService) {
        this.t_NEW_RETAIL_DYGMVExcelService = t_NEW_RETAIL_DYGMVExcelService;
        this.t_NEW_RETAIL_DYGMVService = t_NEW_RETAIL_DYGMVService;
    }

    @PostMapping(value = {"/T_NEW_RETAIL_DYGMV/import"})
    @Timed
    @ApiOperation(value = "批量导入")
    public ResponseEntity<Boolean> uploadLoanFile(MultipartFile file) throws URISyntaxException {
        Workbook workbook = null;
        //UserExcelTask userExcelTask = null;
        //String tenantKey = TenantContext.get();
        String fileName = file.getOriginalFilename();
        if(fileName==null) {
            return new ResponseEntity(new ErrorVM("文件名不为空"), HttpStatus.BAD_REQUEST);
        }
        try {
            workbook = t_NEW_RETAIL_DYGMVExcelService.checkFile(file);
            // userExcelTask = userExcelService.save(file);
        } catch (RuntimeException e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        //TenantContext.set(tenantKey);
        try {
            t_NEW_RETAIL_DYGMVExcelService.excelImport(workbook,fileName);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/T_NEW_RETAIL_DYGMV")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<List<T_NEW_RETAIL_DYGMVDTO>> getList(@ApiParam Pageable pageable, T_NEW_RETAIL_DYGMVQueryVM queryVM) {
        Page<T_NEW_RETAIL_DYGMVDTO> page = t_NEW_RETAIL_DYGMVService.findList(queryVM, pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account");
        //return new ResponseEntity<>(page.getContent(), headersString result = t_NEW_RETAIL_DYGMVService.getSSS(), HttpStatus.OK);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/T_NEW_RETAIL_DYGMV");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/T_NEW_RETAIL_DYGMV/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Timed
    @ApiOperation(value = "模板下载")
    public ResponseEntity<byte[]> getBrandsFile() throws UnsupportedEncodingException {
        String downloadFileName = "T_NEW_RETAIL_DYGMV模板.xlsx";
        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
        byte[] OutputStream = t_NEW_RETAIL_DYGMVExcelService.downloadFile();
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
