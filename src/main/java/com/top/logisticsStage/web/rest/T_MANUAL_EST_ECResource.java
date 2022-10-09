package com.top.logisticsStage.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.top.logisticsStage.service.T_MANUAL_EST_ECExcelService;
import com.top.logisticsStage.service.T_MANUAL_EST_ECService;
import com.top.logisticsStage.service.dto.T_MANUAL_EST_ECDTO;
import com.top.logisticsStage.web.rest.errors.ErrorVM;
import com.top.logisticsStage.web.rest.util.HeaderUtil;
import com.top.logisticsStage.web.rest.util.PaginationUtil;
import com.top.logisticsStage.web.rest.vm.T_MANUAL_EST_ECQueryVM;
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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "目标更新导入")
public class T_MANUAL_EST_ECResource {

    private final Logger log = LoggerFactory.getLogger(T_MANUAL_EST_ECResource.class);

    private static final String ENTITY_NAME = "T_MANUAL_EST_EC";

    private final T_MANUAL_EST_ECExcelService t_MANUAL_EST_ECExcelService;

    private final T_MANUAL_EST_ECService t_MANUAL_EST_ECService;

    public T_MANUAL_EST_ECResource(T_MANUAL_EST_ECExcelService t_MANUAL_EST_ECExcelService, T_MANUAL_EST_ECService t_MANUAL_EST_ECService) {
        this.t_MANUAL_EST_ECExcelService = t_MANUAL_EST_ECExcelService;
        this.t_MANUAL_EST_ECService = t_MANUAL_EST_ECService;
    }

    @PostMapping(value = {"/T_MANUAL_EST_EC/import"})
    @Timed
    @ApiOperation(value = "批量导入")
    public ResponseEntity<Boolean> uploadLoanFile(MultipartFile file) throws URISyntaxException {
        Workbook workbook = null;
        //UserExcelTask userExcelTask = null;
        //String tenantKey = TenantContext.get();
        try {
            workbook = t_MANUAL_EST_ECExcelService.checkFile(file);
            // userExcelTask = userExcelService.save(file);
        } catch (RuntimeException e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        //TenantContext.set(tenantKey);
        try {
            log.info(LocalDate.now()+"目标更新导入开始");
            t_MANUAL_EST_ECExcelService.excelImport(workbook);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/T_MANUAL_EST_EC")
    @Timed
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<List<T_MANUAL_EST_ECDTO>> getList(@ApiParam Pageable pageable, T_MANUAL_EST_ECQueryVM queryVM) {
        Page<T_MANUAL_EST_ECDTO> page = t_MANUAL_EST_ECService.findList(queryVM, pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account");
        //return new ResponseEntity<>(page.getContent(), headersString result = t_MANUAL_EST_ECService.getSSS(), HttpStatus.OK);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/T_MANUAL_EST_EC");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/T_MANUAL_EST_EC")
    @Timed
    @ApiOperation(value = "新增数据列表")
    public ResponseEntity<T_MANUAL_EST_ECDTO> postEntity(@RequestBody T_MANUAL_EST_ECDTO dto) throws URISyntaxException {
        T_MANUAL_EST_ECDTO result = null;
        try {
            result = t_MANUAL_EST_ECService.create(dto);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorVM(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(new URI("/api/T_MANUAL_EST_EC/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/T_MANUAL_EST_EC")
    @Timed
    @ApiOperation(value = "修改数据")
    public ResponseEntity<T_MANUAL_EST_ECDTO> putEntity(@RequestBody T_MANUAL_EST_ECDTO dto) {
        T_MANUAL_EST_ECDTO result = null;
        try {
            result = t_MANUAL_EST_ECService.update(dto);
        } catch (Exception se) {
            return new ResponseEntity(new ErrorVM(se.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/T_MANUAL_EST_EC/delete")
    @Timed
    public ResponseEntity<Void> deleteAnnex(T_MANUAL_EST_ECQueryVM queryVM) {
        log.debug("REST request to delete T_MANUAL_EST_EC : {}", queryVM.toString());
        Integer flg = t_MANUAL_EST_ECService.deleteByVm(queryVM);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "删除"+flg.toString()+"条: "+queryVM.toString())).build();
    }

    @GetMapping(value = "/T_MANUAL_EST_EC/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Timed
    @ApiOperation(value = "模板下载")
    public ResponseEntity<byte[]> getBrandsFile() throws UnsupportedEncodingException {
        String downloadFileName = "T_MANUAL_EST_EC模板.xlsx";
        downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
        byte[] OutputStream = t_MANUAL_EST_ECExcelService.downloadFile();
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
