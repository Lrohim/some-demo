package com.hy.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hy.domain.ExcelData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class ExcelApplicationTests {
    @Test
    void contextLoads() throws IOException {
        // 读取txt文件流
        JSONArray jsonArray =new JSONArray();
        BufferedReader bufReader = null;
        try(Stream<Path> paths = Files.walk(Paths.get("F:/workspace/excel/target/classes/excel/fromJson"))){
            List<Path> fileNames = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            for(Path path:fileNames){
                File file=path.toFile();
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                bufReader = new BufferedReader(inputStreamReader);
                String line="";
                //读取每行内容
                StringBuilder temp=new StringBuilder();
                while ((line=bufReader.readLine())!=null){
                    temp.append(line);
                }
                //去除空格
                String sbReplace = temp.toString().replace(" ", "");
                //转换成为JSONObject对象
                JSONObject jsonObj = JSON.parseObject(sbReplace);
                //数组形式
                jsonArray.addAll(jsonObj.getJSONObject("data").getJSONArray("records"));
            }
        } catch (IOException  e) {
            e.printStackTrace();
        } finally {
            bufReader.close();
        }
        List<ExcelData> list= jsonArray.toJavaList(ExcelData.class);
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量 {前缀.} 前缀可以区分不同的list
        String templateFileName="F:/workspace/excel/target/classes/excel/template/template.xlsx";
        String toFileName = "F:/workspace/excel/target/classes/excel/"+System.currentTimeMillis() + ".xlsx";
        // 写
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        Map<String, Object> map = MapUtils.newHashMap();
        for(int i=0;i<list.size();i++){
            ExcelData item = list.get(i);
            map.put("womaHealthno"+i, item.getWomaHealthno());
            map.put("womaName"+i, item.getWomaName());
            map.put("womaNoteTel"+i, item.getWomaNoteTel());
            map.put("womaLastMenstrualPeriod"+i, item.getWomaLastMenstrualPeriod());
            map.put("incaExpectedDate"+i,item.getIncaExpectedDate());
            map.put("womaPresentAddress"+i, item.getWomaPresentAddress());
        }
        EasyExcel.write(toFileName).withTemplate(templateFileName).sheet().doFill(map);
    }

}
