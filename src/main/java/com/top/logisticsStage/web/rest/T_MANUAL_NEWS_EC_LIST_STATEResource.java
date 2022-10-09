package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.T_MANUAL_NEWS_EC_LIST_STATEExcelService;
import com.top.logisticsStage.service.T_MANUAL_NEWS_EC_LIST_STATEService;
import com.top.logisticsStage.service.dto.T_MANUAL_NEWS_EC_LIST_STATEDTO;
import com.top.logisticsStage.web.rest.errors.ErrorVM;
import com.top.logisticsStage.web.rest.util.HeaderUtil;
import com.top.logisticsStage.web.rest.util.PaginationUtil;
import com.top.logisticsStage.web.rest.vm.T_MANUAL_EST_ECQueryVM;
import com.top.logisticsStage.web.rest.vm.T_MANUAL_NEWS_EC_LIST_STATEQueryVM;
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
@Api(tags = "新品目标清单")
public class T_MANUAL_NEWS_EC_LIST_STATEResource {

    private final Logger log = LoggerFactory.getLogger(T_MANUAL_NEWS_EC_LIST_STATEResource.class);

    private static final String ENTITY_NAME = "T_MANUAL_NEWS_EC_LIST_STATE";

    private final T_MANUAL_NEWS_EC_LIST_STATEExcelService t_MANUAL_NEWS_EC_LIST_STATEExcelService;

    private final T_MANUAL_NEWS_EC_LIST_STATEService t_MANUAL_NEWS_EC_LIST_STATEService;

    public T_MANUAL_NEWS_EC_LIST_STATEResource(T_MANUAL_NEWS_EC_LIST_STATEExcelService t_MANUAL_NEWS_EC_LIST_STATEExcelService, T_MANUAL_NEWS_EC_LIST_STATEService t_MANUAL_NEWS_EC_LIST_STATEService) {
        this.t_MANUAL_NEWS_EC_LIST_STATEExcelService = t_MANUAL_NEWS_EC_LIST_STATEExcelService;
        this.t_MANUAL_NEWS_EC_LIST_STATEService = t_MANUAL_NEWS_EC_LIST_STATEService;
    }

    @PostMapping(value = {"/T_MANUAL_NEWS_EC_LIST_STATE/import"})
    @Timed
    @ApiOperation(value = "批量导入")
    public ResponseEntity<Boolean> uploadLoanFile(MultipartFile file) throws URISyntaxException {
        Workbook workbook = null;
        //UserExcelTask userExcelTask = null;
        //String tenantKey = TenantContext.get();
        try {
            workbook = t_MANUAL_NEWS_EC_LIST_STATEExcelService.checkFile(file);
            // userExcelTask = userExcelService.save(file);
        } catch (RuntimeException e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        //TenantContext.set(tenantKey);
        try {
            t_MANUAL_NEWS_EC_LIST_STATEExcelService.excelImport(workbook);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/T_MANUAL_NEWS_EC_LIST_STATE")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<List<T_MANUAL_NEWS_EC_LIST_STATEDTO>> getList(@ApiParam Pageable pageable, T_MANUAL_NEWS_EC_LIST_STATEQueryVM queryVM) {
        Page<T_MANUAL_NEWS_EC_LIST_STATEDTO> page = t_MANUAL_NEWS_EC_LIST_STATEService.findList(queryVM, pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account");
        //return new ResponseEntity<>(page.getContent(), headersString result = t_MANUAL_NEWS_EC_LIST_STATEService.getSSS(), HttpStatus.OK);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/T_MANUAL_NEWS_EC_LIST_STATE");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/T_MANUAL_NEWS_EC_LIST_STATE")
    @Timed
    @ApiOperation(value = "新增数据列表")
    public ResponseEntity<T_MANUAL_NEWS_EC_LIST_STATEDTO> postEntity(@RequestBody T_MANUAL_NEWS_EC_LIST_STATEDTO dto) throws URISyntaxException {
        T_MANUAL_NEWS_EC_LIST_STATEDTO result = null;
        try {
            result = t_MANUAL_NEWS_EC_LIST_STATEService.create(dto);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(new URI("/api/T_MANUAL_NEWS_EC_LIST_STATE/" + result.getItemCode()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getItemCode()))
                .body(result);
    }

    @PutMapping("/T_MANUAL_NEWS_EC_LIST_STATE")
    @Timed
    @ApiOperation(value = "修改数据")
    public ResponseEntity<T_MANUAL_NEWS_EC_LIST_STATEDTO> putEntity(@RequestBody T_MANUAL_NEWS_EC_LIST_STATEDTO dto) {
        T_MANUAL_NEWS_EC_LIST_STATEDTO result = null;
        try {
            result = t_MANUAL_NEWS_EC_LIST_STATEService.update(dto);
        } catch (Exception se) {
            return new ResponseEntity(new ErrorVM(se.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getItemCode()))
                .body(result);
    }

    @DeleteMapping("/T_MANUAL_NEWS_EC_LIST_STATE/delete")
    @Timed
    public ResponseEntity<Void> deleteAnnex(T_MANUAL_NEWS_EC_LIST_STATEQueryVM queryVM) {
        log.debug("REST request to delete T_MANUAL_NEWS_EC_LIST_STATE : {}", queryVM.toString());
        Integer flg = t_MANUAL_NEWS_EC_LIST_STATEService.deleteByVm(queryVM);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "删除"+flg.toString()+"条: "+queryVM.toString())).build();
    }

    @GetMapping(value = "/T_MANUAL_NEWS_EC_LIST_STATE/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Timed
    @ApiOperation(value = "模板下载")
    public ResponseEntity<byte[]> getBrandsFile() throws UnsupportedEncodingException {
        String downloadFileName = "T_MANUAL_NEWS_EC_LIST_STATE模板.xlsx";
        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
        byte[] OutputStream = t_MANUAL_NEWS_EC_LIST_STATEExcelService.downloadFile();
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

