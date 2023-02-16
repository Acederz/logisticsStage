package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.T_MANUAL_FEE_XLS_SJExcelService;
import com.top.logisticsStage.service.T_MANUAL_FEE_XLS_SJService;
import com.top.logisticsStage.service.dto.T_MANUAL_FEE_XLS_SJDTO;
import com.top.logisticsStage.web.rest.errors.ErrorVM;
import com.top.logisticsStage.web.rest.util.PaginationUtil;
import com.top.logisticsStage.web.rest.vm.T_MANUAL_FEE_XLS_SJQueryVM;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "新零售费用实际")
public class T_MANUAL_FEE_XLS_SJResource {

    private final Logger log = LoggerFactory.getLogger(T_MANUAL_FEE_XLS_SJResource.class);

    private static final String ENTITY_NAME = "T_MANUAL_FEE_XLS_SJ";

    private final T_MANUAL_FEE_XLS_SJExcelService t_MANUAL_FEE_XLS_SJExcelService;

    private final T_MANUAL_FEE_XLS_SJService t_MANUAL_FEE_XLS_SJService;

    public T_MANUAL_FEE_XLS_SJResource(T_MANUAL_FEE_XLS_SJExcelService t_MANUAL_FEE_XLS_SJExcelService, T_MANUAL_FEE_XLS_SJService t_MANUAL_FEE_XLS_SJService) {
        this.t_MANUAL_FEE_XLS_SJExcelService = t_MANUAL_FEE_XLS_SJExcelService;
        this.t_MANUAL_FEE_XLS_SJService = t_MANUAL_FEE_XLS_SJService;
    }

    @PostMapping(value = {"/T_MANUAL_FEE_XLS_SJ/import"})
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
            workbook = t_MANUAL_FEE_XLS_SJExcelService.checkFile(file);
            // userExcelTask = userExcelService.save(file);
        } catch (RuntimeException e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        //TenantContext.set(tenantKey);
        try {
            t_MANUAL_FEE_XLS_SJExcelService.excelImport(workbook);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/T_MANUAL_FEE_XLS_SJ")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<List<T_MANUAL_FEE_XLS_SJDTO>> getList(@ApiParam Pageable pageable, T_MANUAL_FEE_XLS_SJQueryVM queryVM) {
        Page<T_MANUAL_FEE_XLS_SJDTO> page = t_MANUAL_FEE_XLS_SJService.findList(queryVM, pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account");
        //return new ResponseEntity<>(page.getContent(), headersString result = t_MANUAL_FEE_XLS_SJService.getSSS(), HttpStatus.OK);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/T_MANUAL_FEE_XLS_SJ");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/T_MANUAL_FEE_XLS_SJ/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Timed
    @ApiOperation(value = "模板下载")
    public ResponseEntity<byte[]> getBrandsFile() throws UnsupportedEncodingException {
        String downloadFileName = "T_MANUAL_FEE_XLS_SJ模板.xlsx";
        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
        byte[] OutputStream = t_MANUAL_FEE_XLS_SJExcelService.downloadFile();
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
