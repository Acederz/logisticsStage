package com.top.logisticsStage.service;

import com.top.logisticsStage.domain.T_JN_POS;
import com.top.logisticsStage.repository.T_JN_POSRepository;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class T_JN_POSExcelService {


    private final String EXCELL_NAME = "classpath:templates/T_JN_POS.xlsx";
    private final T_JN_POSRepository t_JN_POSRepository;

    public T_JN_POSExcelService(T_JN_POSRepository t_JN_POSRepository) {
        this.t_JN_POSRepository = t_JN_POSRepository;
    }

    // 样式
    private CellStyle cellStyle;

    public Workbook checkFile(MultipartFile excelFile) {
        if (excelFile.getSize() == 0) {
            throw new RuntimeException("文件错误,文件不能为空!");
        }
        Workbook book = null;
        try {
            book = new XSSFWorkbook(excelFile.getInputStream());
        } catch (IOException e) {
            // log.error("文件验证发生异常：{}", e);
            throw new RuntimeException("文件异常,文件读取异常!");
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
                throw new RuntimeException("解析excel表格中 页签名：“" + sheet.getSheetName() + "” 中没有数据可导入，请检查！" + "\n");
            }

            Map<String, String> headMap = getHeadMap();
            //Map<String, Object> selectMap = getSelectMap();
            Map<String, String> telephoneAndName = new HashMap<>();
            Map<String, String> jobNumber = new HashMap<>();
            int errorCount = 0;
            List<T_JN_POS> list = new ArrayList<>();
            for (int i = 1; i < totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //用户临时存储map, 解析完成后，map转为UserTmp对象
                Map<String, Object> entity = new HashMap<>();
                //检查结果， 某列的错误信息
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
                        case "sysName":
                        case "sysCode":
                        case "storeName":
                        case "itemCode":
                        case "saleQuantity":
                        case "saleAmount":
                            cellIsNotNull(key, headMap.get(key), value, entity, result);
                            break;
                        case "saleDate":
                            toDate(key, headMap.get(key), value, entity, dateTimeFormatter, result);
                            break;
                        default: entity.put(key, value); break;
                    }
                    index++;
                }
                T_JN_POS t = mapToBean(entity);
                list.add(t);
                //错误信息记录
                //String s = JSONObject.fromObject(result).toString()
                if (result.size() > 0) {
                    errorCount ++;
                }

            }

            if (errorCount > 0) {
                throw new RuntimeException("有" + errorCount + "行数据不符合规范，请修改！");
            } else{
                tmpToEntity(list);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void tmpToEntity(List<T_JN_POS> list) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            t_JN_POSRepository.saveAll(list);
        } catch (Exception e) {
            throw new RuntimeException("插入数据库报错。");
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
            System.out.println(EXCELL_NAME+"下载模板error:"+e);
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
            List<T_JN_POS> list = t_JN_POSRepository.findAll();
            // 创建绘图对象
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
            //log.error("下载模板error", e);
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
     * 将map集合中的数据转化为指定对象的同名属性中
     */
    public <T> T mapToBean(Map<String, Object> map,Class<T> clazz) throws Exception {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public T_JN_POS mapToBean(Map<String, Object> map) throws Exception {
        T_JN_POS bean = new T_JN_POS();
        bean.setYear(map.get("year")==null?null:new BigDecimal(map.get("year").toString()));
        bean.setMonth(map.get("month")==null?null:new BigDecimal(map.get("month").toString()));
        bean.setSysName(map.get("sysName")==null?null:map.get("sysName").toString());
        bean.setSysCode(map.get("sysCode")==null?null:map.get("sysCode").toString());
        bean.setStoreName(map.get("storeName")==null?null:map.get("storeName").toString());
        bean.setItemCode(map.get("itemCode")==null?null:map.get("itemCode").toString());
        bean.setItemName(map.get("itemName")==null?null:map.get("itemName").toString());
        bean.setBarCode(map.get("barCode")==null?null:map.get("barCode").toString());
        bean.setSaleQuantity(map.get("saleQuantity")==null?null:new BigDecimal(map.get("saleQuantity").toString()));
        bean.setSaleAmount(map.get("saleAmount")==null?null:new BigDecimal(map.get("saleAmount").toString()));
        bean.setSaleDate(map.get("saleDate")==null?null:MapValueToDate(map.get("saleDate").toString()));
        bean.setUpdateTime(LocalDateTime.now());
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
                    throw new RuntimeException("日期转换错误");
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
                throw new RuntimeException("日期转换错误");
            }
        }
        return null;
    }

    /**
     * 必填字段，判断是否非空
     * @param key   列对应key
     * @param cellName 列名
     * @param cellValue 列值
     * @param user
     * @param result
     * @return
     */
    private boolean cellIsNotNull(String key, String cellName, String cellValue, Map<String, Object> user, Map<String, String> result){
        user.put(key, cellValue);
        if (StringUtils.isBlank(cellValue)) {
            result.put(key, cellName + "的值不能为空！");
            return false;
        }
        return true;
    }

    private boolean cellIsToSelect(String key, String cellName, String cellValue, Map<String, Object> select, Map<String, Object> user, Map<String, String> result) {
        user.put(key, cellValue);
        if (!select.containsKey(cellValue)) {
            result.put(key, cellName + "的值不符合下拉列表！");
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
                result.put(key, cellName + "不是规范日期格式，请格式化日期(yyyy-mm-dd)！");
            }

        } else {
            user.put(key, null);
        }
    }

    public void setDataCellStyles(Workbook workbook, Sheet sheet) {
        cellStyle = workbook.createCellStyle();
        // 设置边框
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        // 设置背景色
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        // 设置字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11); // 设置字体大小
        cellStyle.setFont(font);// 选择需要用到的字体格式
        // 设置单元格格式为文本格式（这里还可以设置成其他格式,可以自行百度）
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
    }

    public Map<String, String> getHeadMap(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("year", "年");
        map.put("month", "月");
        map.put("sysName", "系统名称");
        map.put("sysCode", "系统门店编号");
        map.put("storeName", "门店名称");
        map.put("itemCode", "商品编号");
        map.put("itemName", "商品名称");
        map.put("barCode", "条码");
        map.put("saleQuantity", "销售数量");
        map.put("saleAmount", "销售金额(含税)");
        map.put("saleDate", "销售日期");
        return map;
    }

    private String getCellValue(Cell cell, SimpleDateFormat format) {
        String value = "";
        if (null == cell) {
            return value;
        }
        switch (cell.getCellType()) {
            //数值型
            case Cell.CELL_TYPE_NUMERIC:
                short formatType = cell.getCellStyle().getDataFormat();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    value = format.format(date);
                } else if (formatType == 14 || formatType == 31 || formatType == 57 || formatType == 58) {
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    value = format.format(date);
                } else {// 纯数字
                    BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    //解决1234.0  去掉后面的.0
                    if (null != value && !"".equals(value.trim())) {
                        String[] item = value.split("[.]");
                        if (1 < item.length && "0".equals(item[1])) {
                            value = item[0];
                        }
                    }
                }
                break;
            //字符串类型
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue().toString();
                break;
            // 公式类型
            case Cell.CELL_TYPE_FORMULA:
                //读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue().toString();
                }
                break;
            // 布尔类型
            case Cell.CELL_TYPE_BOOLEAN:
                value = " " + cell.getBooleanCellValue();
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            // 故障
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
     * 加载员工导入模板，初始化下拉列表
     * @return
     */
    public Workbook loadExcelTemplatToAddSelect() {
        InputStream fileInputStream = null;

        Workbook workbook = null;
        try {

            fileInputStream = ResourceUtils.getURL(EXCELL_NAME).openStream();
            // 创建excel
            workbook = new XSSFWorkbook(fileInputStream);
            // 设置sheet 名称
            Sheet excelSheet = workbook.getSheetAt(0);
            // 设置样式
            this.setDataCellStyles(workbook, excelSheet);

            return workbook;
        } catch (FileNotFoundException e) {
            System.out.println("读取文件 error : "+ e.getMessage());
        } catch (Exception e) {
            System.out.println("生成模板异常 error : "+ e);
        } finally {
            try{
                fileInputStream.close();
            } catch (Exception e) {

            }
        }
        return null;
    }

}
