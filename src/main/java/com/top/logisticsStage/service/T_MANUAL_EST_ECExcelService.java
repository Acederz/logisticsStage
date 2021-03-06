package com.top.logisticsStage.service;

import com.top.logisticsStage.domain.T_MANUAL_EST_EC;
import com.top.logisticsStage.domain.T_MANUAL_NEWS_EC_LIST_STATE;
import com.top.logisticsStage.domain.enumeration.TargetType;
import com.top.logisticsStage.repository.T_MANUAL_EST_ECRepository;
import com.top.logisticsStage.repository.T_MANUAL_NEWS_EC_LIST_STATERepository;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class T_MANUAL_EST_ECExcelService {

    private final Logger log = LoggerFactory.getLogger(T_MANUAL_EST_ECExcelService.class);

    private final String EXCELL_NAME = "classpath:templates/T_MANUAL_EST_EC.xlsx";
    private final T_MANUAL_EST_ECRepository t_MANUAL_EST_ECRepository;
    private final T_MANUAL_NEWS_EC_LIST_STATERepository t_MANUAL_NEWS_EC_LIST_STATERepository;

    public T_MANUAL_EST_ECExcelService(T_MANUAL_EST_ECRepository t_MANUAL_EST_ECRepository, T_MANUAL_NEWS_EC_LIST_STATERepository t_MANUAL_NEWS_EC_LIST_STATERepository) {
        this.t_MANUAL_EST_ECRepository = t_MANUAL_EST_ECRepository;
        this.t_MANUAL_NEWS_EC_LIST_STATERepository = t_MANUAL_NEWS_EC_LIST_STATERepository;
    }

    // ??????
    private CellStyle cellStyle;

    public Workbook checkFile(MultipartFile excelFile) {
        if (excelFile.getSize() == 0) {
            throw new RuntimeException("????????????,??????????????????!");
        }
        Workbook book = null;
        try {
            book = new XSSFWorkbook(excelFile.getInputStream());
        } catch (IOException e) {
            // log.error("???????????????????????????{}", e);
            throw new RuntimeException("????????????,??????????????????!");
        }
        return book;
    }

    public void excelImport(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            int totalRows = sheet.getPhysicalNumberOfRows();
            if (totalRows < 1) {
                throw new RuntimeException("??????excel????????? ???????????????" + sheet.getSheetName() + "??? ???????????????????????????????????????" + "\n");
            }

            Map<String, String> headMap = getHeadMap();
            //Map<String, Object> selectMap = getSelectMap();
            //Map<String, String> telephoneAndName = new HashMap<>();
            //Map<String, String> jobNumber = new HashMap<>();
            int errorCount = 0;
            List<T_MANUAL_EST_EC> list = new ArrayList<>();
            for (int i = 1; i < totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //??????????????????map, ??????????????????map??????UserTmp??????
                Map<String, Object> entity = new HashMap<>();
                //??????????????? ?????????????????????
                Map<String, String> result = new LinkedHashMap<>();
                Iterator<String> iterator = headMap.keySet().iterator();
                String name = null;
                int index = 0;
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = getCellValue(row.getCell(index), format);
                    value = StringUtils.isBlank(value)?null:value;
                    switch (key) {
                        case "year":
                        case "month":
                        case "itemCode":
                        case "targetType":
                        case "saleNumber":
                        case "salePrice":
                            cellIsNotNull(key, headMap.get(key), value, entity, result);
                            break;
                        default: entity.put(key, value); break;
                    }
                    index++;
                }
                T_MANUAL_EST_EC t = mapToBean(entity);
                list.add(t);
                //??????????????????
                //String s = JSONObject.fromObject(result).toString()
                if (result.size() > 0) {
                    errorCount ++;
                }

            }

            if (errorCount > 0) {
                throw new RuntimeException("???" + errorCount + "???????????????????????????????????????");
            } else{
                tmpToEntity(list);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void tmpToEntity(List<T_MANUAL_EST_EC> list) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            List<String> itemCodes = new ArrayList<>();
            list.forEach(e->{
                if(!t_MANUAL_NEWS_EC_LIST_STATERepository.existsByItemCode(e.getItemCode())) {
                    itemCodes.add(e.getItemCode());
                    throw new RuntimeException("??????????????????????????????????????????");
                }
                T_MANUAL_EST_EC t = t_MANUAL_EST_ECRepository.findAllByItemCodeAndYearAndMonthAndTargetType(e.getItemCode(),e.getYear(),e.getMonth(),e.getTargetType());
                if(t!=null) {
                    throw new RuntimeException("???????????????????????????(?????????????????????????????????)????????????????????????");
                }
            });
            if(itemCodes!=null&&itemCodes.size()>0) {
                System.out.println(itemCodes.stream().collect(Collectors.toSet()).toString());
                throw new RuntimeException("??????????????????????????????????????????");
            }
            t_MANUAL_EST_ECRepository.saveAll(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] downloadFile(){
        ByteArrayOutputStream outputStream = null;
        Workbook workbook = null;
        try {
            workbook = loadExcelTemplatToAddSelect();
            outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.out.println(EXCELL_NAME+"????????????error:"+e);
        } finally {
            try{
                workbook.close();
                outputStream.close();;
            } catch (Exception e) {

            }
        }

        return null;
    }

    public byte[] downloadFile(Long excelId){
        ByteArrayOutputStream outputStream = null;
        Workbook workbook = null;
        try {
            workbook = loadExcelTemplatToAddSelect();
            Sheet sheet = workbook.getSheetAt(0);
            Row headRow = sheet.getRow(0);
            Map<String, String> headMap = getHeadMap();
            setDataCellStyles(workbook, sheet);
            List<T_MANUAL_EST_EC> list = t_MANUAL_EST_ECRepository.findAll();
            // ??????????????????
            Drawing p = sheet.createDrawingPatriarch();
            for (int i = 0; i < list.size(); i++) {
                JSONObject jsonObject = JSONObject.fromObject(list.get(i));
                Iterator<String> in1 = headMap.keySet().iterator();
                Row row = sheet.getRow(i + 1);
                if (row == null) {
                    row = sheet.createRow(i + 1);
                }
                int cellIndex = 0;
                //JSONObject result = JSONObject.fromObject(jsonObject.getString("excelResult"));
                //StringBuffer resultStr = new StringBuffer("");
                while (in1.hasNext()) {
                    String key = in1.next();
                    Cell cell = row.getCell(cellIndex);
                    if (cell == null) {
                        cell = row.createCell(cellIndex);
                    }
                    cell.setCellValue(jsonObject.getString(key));
                    cellIndex++;
                }
            }
            outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            //log.error("????????????error", e);
        } finally {
            try{
                workbook.close();
                outputStream.close();;
            } catch (Exception e) {

            }
        }

        return null;
    }

    /**
     * ???map?????????????????????????????????????????????????????????
     */
    public <T> T mapToBean(Map<String, Object> map,Class<T> clazz) throws Exception {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public T_MANUAL_EST_EC mapToBean(Map<String, Object> map) throws Exception {
        T_MANUAL_EST_EC bean = new T_MANUAL_EST_EC();
        bean.setItemCode(map.get("itemCode")==null?null:map.get("itemCode").toString());
        bean.setYear(map.get("year")==null?null:new BigDecimal(map.get("year").toString()));
        bean.setMonth(map.get("month")==null?null:new BigDecimal(map.get("month").toString()));;
        bean.setTargetType(map.get("targetType")==null?null: TargetType.valueOf(map.get("targetType").toString()));
        bean.setSaleAmount(map.get("saleAmount")==null?null:new BigDecimal(map.get("saleAmount").toString()).setScale(2, RoundingMode.HALF_DOWN));
        bean.setSalePrice(map.get("salePrice")==null?null:new BigDecimal(map.get("salePrice").toString()).setScale(2, RoundingMode.HALF_DOWN));
        bean.setSaleNumber(map.get("saleNumber")==null?null:new BigDecimal(map.get("saleNumber").toString()).setScale(2, RoundingMode.HALF_DOWN));
        return bean;
    }

    private LocalDate MapValueToDate(String s){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if (StringUtils.isNotBlank(s)) {
            try {
                return LocalDate.parse(s, dateTimeFormatter);
            }catch (Exception e) {
                try {
                    return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy/M/d"));
                } catch (Exception ee) {
                    throw new RuntimeException("??????????????????");
                }
            }
        }
        return null;
    }

    private LocalDate MapValueToDate(String key, Map<String, Object> map, DateTimeFormatter dateTimeFormatter){
        if (StringUtils.isNotBlank(map.get(key).toString())) {
            try {
                return LocalDate.parse(map.get(key).toString(), dateTimeFormatter);
            }catch (Exception e) {
                throw new RuntimeException("??????????????????");
            }
        }
        return null;
    }

    /**
     * ?????????????????????????????????
     * @param key   ?????????key
     * @param cellName ??????
     * @param cellValue ??????
     * @param user
     * @param result
     * @return
     */
    private boolean cellIsNotNull(String key, String cellName, String cellValue, Map<String, Object> user, Map<String, String> result){
        user.put(key, cellValue);
        if (StringUtils.isBlank(cellValue)) {
            log.info(key+"  "+cellName + "?????????????????????");
            result.put(key, cellName + "?????????????????????");
            return false;
        }
        return true;
    }

    private boolean cellIsToSelect(String key, String cellName, String cellValue, Map<String, Object> select, Map<String, Object> user, Map<String, String> result) {
        user.put(key, cellValue);
        if (!select.containsKey(cellValue)) {
            log.info(key+"  "+cellName + "??????????????????????????????");
            result.put(key, cellName + "??????????????????????????????");
            return true;
        }
        return false;
    }

    private void toDate(String key, String cellName, String cellValue, Map<String, Object> user, DateTimeFormatter dateTimeFormatter, Map<String, String> result){
        if (StringUtils.isNotBlank(cellValue)) {
            user.put(key, cellValue);
            try {
                LocalDate date = LocalDate.parse(cellValue, dateTimeFormatter);
            } catch (Exception e) {
                log.info(key+"  "+cellName + "?????????????????????????????????????????????(yyyy-MM-dd)???");
                result.put(key, cellName + "?????????????????????????????????????????????(yyyy-MM-dd)???");
            }

        } else {
            user.put(key, null);
        }
    }

    public void setDataCellStyles(Workbook workbook, Sheet sheet) {
        cellStyle = workbook.createCellStyle();
        // ????????????
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        // ???????????????
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        // ????????????
        Font font = workbook.createFont();
        font.setFontName("??????");
        font.setFontHeightInPoints((short) 11); // ??????????????????
        cellStyle.setFont(font);// ?????????????????????????????????
        // ???????????????????????????????????????????????????????????????????????????,?????????????????????
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
    }

    public Map<String, String> getHeadMap(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("itemCode", "??????");
        map.put("year", "???");
        map.put("month", "???");
        map.put("targetType", "????????????");
        map.put("saleNumber", "??????????????????");
        map.put("salePrice", "????????????????????????");
        map.put("saleAmount", "?????????????????????");
        return map;
    }

    private String getCellValue(Cell cell, SimpleDateFormat format) {
        String value = "";
        if (null == cell) {
            return value;
        }
        switch (cell.getCellType()) {
            //?????????
            case Cell.CELL_TYPE_NUMERIC:
                short formatType = cell.getCellStyle().getDataFormat();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //?????????date????????? ????????????cell???date???
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    value = format.format(date);
                } else if (formatType == 14 || formatType == 31 || formatType == 57 || formatType == 58) {
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    value = format.format(date);
                } else {// ?????????
                    BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    //??????1234.0  ???????????????.0
                    if (null != value && !"".equals(value.trim())) {
                        String[] item = value.split("[.]");
                        if (1 < item.length && "0".equals(item[1])) {
                            value = item[0];
                        }
                    }
                }
                break;
            //???????????????
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue().toString();
                break;
            // ????????????
            case Cell.CELL_TYPE_FORMULA:
                //??????????????????
                value = String.valueOf(cell.getNumericCellValue());
                if (value.equals("NaN")) {// ????????????????????????????????????,???????????????????????????
                    value = cell.getStringCellValue().toString();
                }
                break;
            // ????????????
            case Cell.CELL_TYPE_BOOLEAN:
                value = " " + cell.getBooleanCellValue();
                break;
            // ??????
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            // ??????
            case Cell.CELL_TYPE_ERROR:
                value = "";
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        if ("null".endsWith(value.trim())) {
            value = "";
        }
        return value.trim();

    }

    /**
     * ????????????????????????????????????????????????
     * @return
     */
    public Workbook loadExcelTemplatToAddSelect() {
        InputStream fileInputStream = null;

        Workbook workbook = null;
        try {

            fileInputStream = ResourceUtils.getURL(EXCELL_NAME).openStream();
            // ??????excel
            workbook = new XSSFWorkbook(fileInputStream);
            // ??????sheet ??????
            Sheet excelSheet = workbook.getSheetAt(0);
            // ????????????
            this.setDataCellStyles(workbook, excelSheet);

            return workbook;
        } catch (FileNotFoundException e) {
            System.out.println("???????????? error : "+ e.getMessage());
        } catch (Exception e) {
            System.out.println("?????????????????? error : "+ e);
        } finally {
            try{
                fileInputStream.close();
            } catch (Exception e) {

            }
        }
        return null;
    }
}
