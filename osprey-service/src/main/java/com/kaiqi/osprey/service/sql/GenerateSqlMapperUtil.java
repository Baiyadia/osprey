package com.kaiqi.osprey.service.sql;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jeff on 2018/12/5.
 */
public class GenerateSqlMapperUtil {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
//        generateSqlMapper(UserContactAddress.class, null);
//        generateSqlMapper(UserDetails.class, null);
//        generateSqlMapper(UserFeedback.class, null);
//        generateSqlMapper(UserInviteRecord.class, null);
//        generateSqlMapper(UserLoginRecord.class, null);
//        generateSqlMapper(UserNoticeRecord.class, null);
//        generateSqlMapper(UserSettings.class, null);

        generateSqlFromFile("/Users/sunny/IdeaProjects/kaiqi/osprey/osprey-service/src/main/java/com/kaiqi/osprey/service/sql/domain");
    }

    public static void generateSqlFromFile(String path) throws IOException {
        Set<String> all = new HashSet<>();
        File target = new File(path);
        File[] files = target.listFiles();
        for (File file : files) {
            if (file.getName().startsWith(".")) {
                continue;
            }
            StringBuilder sql = new StringBuilder();
            try (FileReader reader = new FileReader(file);
                 BufferedReader br = new BufferedReader(reader)
            ) {
                String line;
                String tableName;
                boolean flag = false;
                String desc = "";
                while ((line = br.readLine()) != null) {
                    line = line.trim().replaceAll(";", "");
                    if (line.contains(" class ")) {
                        tableName = transfer(line.trim().split(" ")[2]);
                        sql.append("create table ").append(tableName).append("( \r\n");
                    }
                    if (line.contains("/**")) {
                        flag = true;
                    }
                    if (line.contains("*/")) {
                        flag = false;
                    }
                    if (line.startsWith("* ") && flag) {
                        desc = line.replaceAll("\\*", "").trim();
                    }
                    if (line.startsWith("private ")) {
                        String[] colArr = line.split(" ");
                        sql.append(transfer(colArr[2])).append(" ");
                        if ("Long".equals(colArr[1]) && !"id".equals(colArr[2])) {
                            sql.append("bigint(16) DEFAULT '0'");
                        } else if ("Integer".equals(colArr[1])) {
                            sql.append("int(8) DEFAULT '0'");
                        } else if ("String".equals(colArr[1])) {
                            sql.append("varchar(32) DEFAULT ''");
                        } else if ("Date".equals(colArr[1])) {
                            sql.append("datetime");
                        }
                        if ("id".equals(colArr[2])) {
                            // 默认字段名ID为主键
                            sql.append("bigint(16) NOT NULL PRIMARY KEY AUTO_INCREMENT");
                        }
                        if ("createTime".equals(colArr[2])) {
                            // 默认字段名ID为主键
                            sql.append(" NOT NULL DEFAULT CURRENT_TIMESTAMP");
                        }
                        if ("updateTime".equals(colArr[2])) {
                            // 默认字段名ID为主键
                            sql.append(" NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP");
                        }
                        if (!StringUtils.isEmpty(desc)) {
                            sql.append(" COMMENT '").append(desc).append("'");
                            desc = "";
                        }
                        sql.append(",\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sql.delete(sql.lastIndexOf(","), sql.length()).append("\n)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;\r\n");
            System.out.println(sql);
        }
    }

    /**
     * @description: 将javabean中小驼峰命名变量的“大写字母”转换为“_小写字母”
     * @author wangs
     * @date 2020-02-05 15:36
     */
    private static String transfer(String source) {
        if (StringUtils.isEmpty(source)) {
            return source;
        }
        String result = source;
        int temp = 0;
        for (int i = 1; i < source.length(); i++) {
            if (Character.isUpperCase(source.charAt(i))) {
                result = result.substring(0, i + temp) + '_' + result.substring(i + temp, source.length() + temp);
                temp++;
            }
        }
        return "`" + result.toLowerCase() + "`";
    }

    public static void generateSqlMapper(Class obj, String tableName) throws IOException {
        Field[] fields = obj.getDeclaredFields();
        String param = null;
        String cameCaseColumn = null;
        String underScoreCaseColumn = null;
        StringBuilder sql = new StringBuilder();
        if (tableName == null || tableName.equals("")) {
            // 未传表明默认用类名
            tableName = obj.getName().substring(obj.getName().lastIndexOf(".") + 1);
        }
        /**
         * 以下部分生成建表Sql
         */
        sql.append("create table ").append(tableName).append("( \r\n");
        boolean firstId = true;
        for (Field f : fields) {
            cameCaseColumn = f.getName();
            underScoreCaseColumn = cameCaseColumn;
            for (int i = 0; i < cameCaseColumn.length(); i++)
                if (Character.isUpperCase(cameCaseColumn.charAt(i)))
                    // 将javabean中小驼峰命名变量的“大写字母”转换为“_小写字母”
                    underScoreCaseColumn = cameCaseColumn.substring(0, i) + '_' + cameCaseColumn.substring(i, i + 1).toLowerCase() + cameCaseColumn.substring(i + 1, cameCaseColumn.length());
            sql.append(underScoreCaseColumn).append(" ");
            param = f.getType().getTypeName();
            if (param.equals("java.lang.Integer")) {
                sql.append("INTEGER");
            } else {
                // 根据需要自行修改
                sql.append("VARCHAR(20)");
            }
            if (firstId) {
                // 默认第一个字段为ID主键
                sql.append(" PRIMARY KEY AUTO_INCREMENT");
                firstId = false;
            }
            sql.append(",\n");
        }
        sql.delete(sql.lastIndexOf(","), sql.length()).append("\n)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;\r\n");
        System.out.println(sql);

        /**
         * 以下部分生成Mapper
         */
//        firstId = true;
//        StringBuilder mapper = new StringBuilder();
//        StringBuilder resultMap = new StringBuilder();
//        StringBuilder insert = new StringBuilder();
//        StringBuilder insertValues = new StringBuilder();
//        StringBuilder update = new StringBuilder();
//        StringBuilder updateWhere = new StringBuilder();
//        StringBuilder delete = new StringBuilder();
//        StringBuilder deleteWhere = new StringBuilder();
//        mapper.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
//        mapper.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");
//        mapper.append("<mapper namespace=\"com..mapper.").append(tableName).append("Mapper\">\r\n");
//        resultMap.append("    <resultMap id=\"Base").append(tableName).append("\" type=\"").append(obj.getName()).append("\">\r\n");
//        insert.append("    <insert id=\"save").append(tableName).append("\" parameterType=\"").append(obj.getName()).append("\">\r\n");
//        insert.append("        INSERT INTO ").append(tableName.toLowerCase()).append(" (");
//        update.append("    <update id=\"update").append(tableName).append("\" parameterType=\"").append(obj.getName()).append("\">\r\n");
//        update.append("        UPDATE ").append(tableName.toLowerCase()).append(" SET ");
//        delete.append("    <delete id=\"delete").append(tableName).append("\" parameterType=\"int\">\r\n");
//        delete.append("        DELETE FROM ").append(tableName.toLowerCase());
//        for (Field f : fields) {
//            cameCaseColumn = f.getName();
//            underScoreCaseColumn = cameCaseColumn;
//            for (int i = 0; i < cameCaseColumn.length(); i++)
//                if (Character.isUpperCase(cameCaseColumn.charAt(i)))
//                    // 将javabean中小驼峰命名变量的“大写字母”转换为“_小写字母”
//                    underScoreCaseColumn = cameCaseColumn.substring(0, i) + '_' + cameCaseColumn.substring(i, i + 1).toLowerCase() + cameCaseColumn.substring(i + 1, cameCaseColumn.length());
//            resultMap.append("         ");
//            if (firstId) {
//                resultMap.append("<id column=\"").append(underScoreCaseColumn).append("\" property=\"").append(cameCaseColumn).append("\" jdbcType=\"");
//                updateWhere.append("         WHERE ").append(underScoreCaseColumn).append(" = #{").append(cameCaseColumn).append("}\r\n");
//                deleteWhere.append(" WHERE ").append(underScoreCaseColumn).append(" = #{").append(cameCaseColumn).append("}\r\n");
//                firstId = false;
//            } else {
//                resultMap.append("<result column=\"").append(underScoreCaseColumn).append("\" property=\"").append(cameCaseColumn).append("\" jdbcType=\"");
//                insert.append(underScoreCaseColumn).append(", ");
//                insertValues.append("#{").append(cameCaseColumn).append("},");
//                update.append(underScoreCaseColumn).append(" = #{").append(cameCaseColumn).append("}, ");
//            }
//            param = f.getType().getTypeName();
//            if (param.equals("java.lang.Integer")) {
//                resultMap.append("INTEGER\" />\r\n");
//            } else {
//                // 根据需要自行修改
//                resultMap.append("VARCHAR\" />\r\n");
//            }
//        }
//        resultMap.append("    </resultMap>\r\n");
//        insert.delete(insert.lastIndexOf(","), insert.length()).append(")\r\n");
//        insertValues.delete(insertValues.lastIndexOf(","), insertValues.length());
//        insert.append("              VALUES (");
//        insert.append(insertValues).append(")\r\n");
//        insert.append("    </insert>\r\n");
//        update.delete(update.lastIndexOf(","), update.length()).append("\r\n");
//        update.append(updateWhere);
//        update.append("    </update>\r\n");
//        delete.append(deleteWhere);
//        delete.append("    </delete>\r\n");
//        mapper.append(resultMap).append(insert).append(update).append(delete);
//        mapper.append("</mapper>");
//        System.out.println(mapper);
    }
}
