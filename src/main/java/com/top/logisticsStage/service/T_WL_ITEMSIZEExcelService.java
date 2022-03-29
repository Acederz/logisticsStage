package com.top.logisticsStage.service;
import com.top.logisticsStage.domain.T_WL_ITEMSIZE;
import com.top.logisticsStage.repository.T_WL_ITEMSIZERepository;
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

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class T_WL_ITEMSIZEExcelService {


    private final String EXCELL_NAME = "classpath:templates/T_WL_ITEMSIZE.xlsx";
    private final T_WL_ITEMSIZERepository t_wl_itemsizeRepository;

    public T_WL_ITEMSIZEExcelService(T_WL_ITEMSIZERepository t_wl_itemsizeRepository) {
        this.t_wl_itemsizeRepository = t_wl_itemsizeRepository;
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d");
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
            List<T_WL_ITEMSIZE> list = new ArrayList<>();
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
                        case "barCode":
                            if(cellIsNotNull(key, headMap.get(key), value, entity, result)) {
//                                if(t_wl_itemsizeRepository.existsById(value)) {
//                                    result.put(key, headMap.get(key) + "的值已存在！");
//                                }
                            }
                            break;
                        case "launchTime":
                            toDate(key, headMap.get(key), value, entity, dateTimeFormatter, result);
                            break;
                        case "itemCode":
                        case "productName":
                        case "length":
                        case "width":
                        case "height":
                        default: entity.put(key, value); break;
                    }
                    index++;
                }
                T_WL_ITEMSIZE t = mapToBean(entity);
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

    public void tmpToEntity(List<T_WL_ITEMSIZE> list) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            t_wl_itemsizeRepository.saveAll(list);
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
//            Cell remark = headRow.createCell(headMap.size());
//            remark.setCellValue("结果");
            setDataCellStyles(workbook, sheet);
            List<T_WL_ITEMSIZE> list = t_wl_itemsizeRepository.findAll();
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
//                    if (result.containsKey(key)){
//                        cell.setCellStyle(cellStyle);
//                        Comment comment = p.createCellComment(new XSSFClientAnchor(i+1, i+1, cellIndex, cellIndex, (short) 3, 3, (short) 5, 6));
//                        // 输入批注信息
//                        comment.setString(new XSSFRichTextString(result.get(key).toString()));
//                        // 添加作者,选中B5单元格,看状态栏
//                        comment.setAuthor("toad");
//                        // 将批注添加到单元格对象中
//                        cell.setCellComment(comment);
//                        resultStr.append(result.get(key) + "；");
//                    }
                    cellIndex++;
                }
//                Cell reustlCell = row.createCell(headMap.size());
//                reustlCell.setCellValue(resultStr.toString());
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

    public T_WL_ITEMSIZE mapToBean(Map<String, Object> map) throws Exception {
        T_WL_ITEMSIZE bean = new T_WL_ITEMSIZE();
        bean.setBarCode(map.get("barCode").toString());
        bean.setItemCode(map.get("itemCode")==null?null:map.get("itemCode").toString());
        bean.setProductName(map.get("productName")==null?null:map.get("productName").toString());
        bean.setLength(map.get("length")==null?null:new BigDecimal(map.get("length").toString()));
        bean.setWidth(map.get("width")==null?null:new BigDecimal(map.get("width").toString()));
        bean.setHeight(map.get("height")==null?null:new BigDecimal(map.get("height").toString()));
        bean.setLaunchTime(MapValueToDate(map.get("launchTime").toString()));
        bean.setRemarks(map.get("remarks")==null?null:map.get("remarks").toString());
        bean.setUpdateTime(LocalDate.now());
        return bean;
    }

    private LocalDate MapValueToDate(String s){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d");
        if (StringUtils.isNotBlank(s)) {
            try {
                return LocalDate.parse(s, dateTimeFormatter);
            }catch (Exception e) {
                try {
                    return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy/mm/dd"));
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
        map.put("barCode", "条码");
        map.put("itemCode", "货品编号");
        map.put("productName", "品名");
        map.put("length", "长");
        map.put("width", "宽");
        map.put("height", "高");
        map.put("launchTime", "上架时间");
        map.put("remarks", "备注");
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

           // Map<String, String> headMap = getHeadMap();
            /*int headIndex = 0;
            Iterator<String> in = headMap.keySet().iterator();
            while (in.hasNext()){
                String key = in.next();
                Cell cell = head.createCell(headIndex);
                cell.setCellValue(headMap.get(key));
                headIndex++;
            }*/

//            Map<String, Object> selectMap = getSelectMap();
//            int hiddenIndex = 1;
//            for(String key : selectMap.keySet()) {
//                // 创建一个隐藏页和隐藏数据集
//                if (key.equals( "userPost") || key.equals( "organize")) {
//                    this.creatHideSheetAndFxName(workbook, key, hiddenIndex, selectMap.get(key));
//                    hiddenIndex++;
//                }
//            }

            // 创建一行数据
//            for (int i = 1; i < 2; i++) {
//                int cellIndex = 0;
//                Iterator<String> in1 = headMap.keySet().iterator();
//                while (in1.hasNext()){
//                    String key = in1.next();
//                    if (key.equals( "userPost") || key.equals( "organize")) {// 下拉列表长度不固定，采用隐藏页更改
//                        //不加二级联动先去掉
//                        DataValidation data_validation_list2 = this.getDataValidationByFormulaSheel(excelSheet, key + "_name", i, cellIndex + 1);
//                        excelSheet.addValidationData(data_validation_list2);
//                        /*
//                        //不加二级联动先去掉
//                        DataValidation data_validation_list2 = this.getDataValidationByFormula(excelSheet,"INDIRECT($" + getcellColumnFlag(cellIndex) + "$" + (i + 1) + ")", i, cellIndex + 1);
//                        excelSheet.addValidationData(data_validation_list2);
//                        /*System.out.println("初始值：" + data_validation_list2.getRegions().getCellRangeAddress(0).toString() + " ------ " + data_validation_list2.getValidationConstraint().getFormula1().toString());
//                        excelSheet.getDataValidations().forEach(hssfDataValidation -> {
//                            if (hssfDataValidation.getRegions().getCellRangeAddress(0).toString().equals(data_validation_list2.getRegions().getCellRangeAddress(0).toString()) ) {
//                                System.out.println("实际： " + hssfDataValidation.getValidationConstraint().getFormula1().toString());
//                            }
//                        });*/
//                    } else if (selectMap.containsKey(key) && i == 1) {
//                        // 得到验证对象
//                        DataValidation data_validation_list1 = this.getSelDataValidationByFormula(excelSheet, i, cellIndex + 1, (Map<String, Object>) selectMap.get(key));
//                        excelSheet.addValidationData(data_validation_list1);
//                    }
//
//                    cellIndex++;
//                }
//            }

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

//    private String doHandle(final int num) {
//        if (num == 0) {
//            return "Z";
//        }
//        String[] charArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
//                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
//                "W", "X", "Y", "Z" };
//        return charArr[num - 1].toString();
//    }

    // 根据数据值确定单元格位置（比如：28-AB）
//    private String getcellColumnFlag(int num) {
//        String columFiled = "";
//        int chuNum = 0;
//        int yuNum = 0;
//        if (num >= 1 && num <= 26) {
//            columFiled = this.doHandle(num);
//        } else {
//            chuNum = num / 26;
//            yuNum = num % 26;
//
//            columFiled += this.doHandle(chuNum);
//            columFiled += this.doHandle(yuNum);
//        }
//        return columFiled;
//    }

    /**
     * 创建数据域（下拉联动的数据），并创建下拉列表公式
     *
     * @param workbook
     * @param hideSheetName
     *            数据域名称
     */
//    private void creatHideSheetAndFxName(Workbook workbook, String hideSheetName, int sheetIndex, Object object) {
//        // 创建数据域
//        Sheet sheet = workbook.createSheet(hideSheetName);
//        Name name = workbook.createName();
//        //隐藏下拉sheet页
//        workbook.setSheetHidden(sheetIndex, true);
//        // 用于记录行
//        int rowRecord = 0;
//        // 获取行（从0下标开始）
//        Row row = sheet.createRow(rowRecord);
//        Map<String, Object> selectMap = (Map<String, Object>) object;
//
//        // 设置约束名称 创建一级下拉列表公式
//        name.setNameName(hideSheetName + "_name");
//        name.setRefersToFormula(hideSheetName + "!$A$1:$" + this.getcellColumnFlag(selectMap.size())+ "$1");
//        int cellIndex = 0;
//        Iterator<String> in = selectMap.keySet().iterator();
//        while (in.hasNext()) {
//            rowRecord++;
//            String key = in.next();
//            Cell userNameLableCell = row.createCell(cellIndex);
//            userNameLableCell.setCellValue(key);
//
//            if (selectMap.get(key) instanceof List) {
//                List<String> list = new ArrayList<String>();
//                // 我这里是写死的 ， 实际中应该从数据库直接获取更好
//                List<String> list1 = (List<String>) selectMap.get(key);
//                list.add(key);
//                list.addAll(list1);
//                //获取行
//                Row Cityrow = sheet.createRow(rowRecord);
//                // 创建联动二级数据
//                this.creatRow(Cityrow, list);
//            }
//            cellIndex++;
//        }
//
//        //创建二级联动公式  二级联动公式，名称容易非法字符，去掉
//        /*cellIndex = 0;
//        Iterator<String> in1 = selectMap.keySet().iterator();
//        while (in1.hasNext()) {
//            String key = in1.next();
//            if (selectMap.get(key) instanceof List) {
//                List<String> list = new ArrayList<String>();
//                list.add(0, key);
//                list.addAll((List<String>) selectMap.get(key));
//
//                Name childName = workbook.createName();
//                childName.setRefersToFormula(hideSheetName + "!$B$" + (cellIndex + 2) + ":$"
//                    + this.getcellColumnFlag(list.size()) + "$" + (cellIndex + 2));
//                cellIndex++;
//                childName.setNameName(key);
//            }
//        }*/
//
//    }

    /**
     * 创建一列数据
     *
     * @param currentRow
     * @param text
     */
//    public void creatRow(Row currentRow, List<String> text) {
//        if (text != null) {
//            int i = 0;
//            for (String cellValue : text) {
//                // 注意列是从（1）下标开始
//                Cell userNameLableCell = currentRow.createCell(i++);
//                userNameLableCell.setCellValue(cellValue);
//            }
//        }
//    }

    /**
     * 使用已定义的数据源方式设置一个数据验证
     *
     * @param formulaString
     * @param naturalRowIndex
     * @param naturalColumnIndex
     * @return
     */
//    public DataValidation getDataValidationByFormula(Sheet sheet, String formulaString,
//                                                     int naturalRowIndex, int naturalColumnIndex) {
//        // 四个参数分别是：起始行、终止行、起始列、终止列
//        int firstRow = naturalRowIndex;
//        int lastRow = naturalRowIndex;
//        int firstCol = naturalColumnIndex - 1;
//        int lastCol = naturalColumnIndex - 1;
//
//        DataValidation dataValidation = null;
//        if (sheet instanceof XSSFSheet) {
//            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
//            // 加载下拉列表内容
//            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(formulaString);
//            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
//            dataValidation = dvHelper.createValidation(dvConstraint, addressList);
//
//        } else if(sheet instanceof HSSFSheet){
//            // 加载下拉列表内容
//            DVConstraint constraint = DVConstraint.createFormulaListConstraint(formulaString);
//            // 设置数据有效性加载在哪个单元格上。
//            CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
//            // 数据有效性对象
//            dataValidation = new HSSFDataValidation(regions, constraint);
//        }
//
//        return dataValidation;
//    }

    /**
     * 使用已定义的数据源方式设置一个数据验证
     *
     * @param formulaString
     * @param naturalRowIndex
     * @param naturalColumnIndex
     * @return
     */
//    public DataValidation getDataValidationByFormulaSheel(Sheet sheet, String formulaString,
//                                                          int naturalRowIndex, int naturalColumnIndex) {
//        // 四个参数分别是：起始行、终止行、起始列、终止列
//        int firstRow = naturalRowIndex;
//        int lastRow = 10000;
//        int firstCol = naturalColumnIndex - 1;
//        int lastCol = naturalColumnIndex - 1;
//
//        DataValidation dataValidation = null;
//        if (sheet instanceof XSSFSheet) {
//            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
//            // 加载下拉列表内容
//            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(formulaString);
//            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
//            dataValidation = dvHelper.createValidation(dvConstraint, addressList);
//
//        } else if(sheet instanceof HSSFSheet){
//            // 加载下拉列表内容
//            DVConstraint constraint = DVConstraint.createFormulaListConstraint(formulaString);
//            // 设置数据有效性加载在哪个单元格上。
//            CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
//            // 数据有效性对象
//            dataValidation = new HSSFDataValidation(regions, constraint);
//        }
//
//        return dataValidation;
//    }

    /**
     * 使用已定义的数据源方式设置一个数据验证
     *
     * @param naturalRowIndex
     * @param naturalColumnIndex
     * @return
     */
//    public DataValidation getSelDataValidationByFormula(Sheet sheet, int naturalRowIndex, int naturalColumnIndex, Map<String, Object> body) {
//        // 四个参数分别是：起始行、终止行、起始列、终止列
//        int firstRow = naturalRowIndex;
//        int lastRow = 10000;
//        int firstCol = naturalColumnIndex - 1;
//        int lastCol = naturalColumnIndex - 1;
//        String[] formulaString = new String[body.size()];
//        Iterator<String> iterator = body.keySet().iterator();
//        int i = 0;
//        while (iterator.hasNext()){
//            String key = iterator.next();
//            formulaString[i] = key;
//            i ++;
//        }
//        DataValidation dataValidation = null;
//        if (sheet instanceof XSSFSheet) {
//            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
//            // 加载下拉列表内容
//            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(formulaString);
//            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
//            dataValidation = dvHelper.createValidation(dvConstraint, addressList);
//
//        } else if(sheet instanceof HSSFSheet){
//            // 加载下拉列表内容
//            DVConstraint constraint = DVConstraint.createExplicitListConstraint(formulaString);
//            // 设置数据有效性加载在哪个单元格上。
//            CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
//            // 数据有效性对象
//            dataValidation = new HSSFDataValidation(regions, constraint);
//        }
//
//        return dataValidation;
//    }

    /**
     * 获取下拉列表map
     * @return

    public Map<String, Object> getSelectMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("sex", getSex());
        map.put("certificate", getCertificate());
        map.put("organize", getOrganize());
        map.put("userPost", getUserPost());
        map.put("rank", getRank());
        map.put("natureWork", getNatureWork());
        map.put("household", getHousehold());
        map.put("trialCycle", getTrialCycle());
        map.put("positiveState", getPositiveState());
        map.put("politicalAppearance", getPoliticalAppearance());
        return map;
    } */

}
