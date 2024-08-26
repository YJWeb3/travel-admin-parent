/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.zheng.travel.admin.generator.config;


import com.zheng.travel.admin.generator.freemarker.tool.JDBCTypesUtils;
import com.zheng.travel.admin.generator.pojo.TableInfo;
import com.zheng.travel.admin.commons.utils.Tool;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库配置
 */
@Data
public class TravelDataTableSourceConfig {
    // 操作的表
    private String tablename;
    // 获取数据表的信息
    private List<TableInfo> tableInfos;
    // 读取所有的表
    private List<String> tables;


    private TravelDataTableSourceConfig() {
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String tablename;
        // 获取数据源
        private TravelDataSourceConfig travelDataSourceConfig;

        public Builder tablename(String tablename) {
            this.tablename = tablename;
            return this;
        }

        /**
         * 构建数据库配置
         *
         * @return 数据库配置
         */
        public TravelDataTableSourceConfig buildTableInfo(TravelDataSourceConfig travelDataSourceConfig) {
            this.travelDataSourceConfig = travelDataSourceConfig;
            TravelDataTableSourceConfig dataTableSourceConfig = new TravelDataTableSourceConfig();
            dataTableSourceConfig.tablename = tablename;
            dataTableSourceConfig.tableInfos = loadTable();
            return dataTableSourceConfig;
        }

        public TravelDataTableSourceConfig buildTables(TravelDataSourceConfig travelDataSourceConfig) {
            this.travelDataSourceConfig = travelDataSourceConfig;
            TravelDataTableSourceConfig dataTableSourceConfig = new TravelDataTableSourceConfig();
            dataTableSourceConfig.tables = loadAllTable();
            return dataTableSourceConfig;
        }


        public List<String> loadAllTable() {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs1 = null;
            try {
                conn = this.travelDataSourceConfig.getConn();
                stmt = conn.createStatement();
                rs1 = stmt.executeQuery("show tables");
                List<String> tables = new ArrayList<>();
                while (rs1.next()) {
                    tables.add(rs1.getString(1));
                }
                return tables;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (rs1 != null) {
                        rs1.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }


        public List<TableInfo> loadTable() {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs1 = null;
            try {
                conn = this.travelDataSourceConfig.getConn();
                stmt = conn.createStatement();
                rs1 = stmt.executeQuery("SELECT * FROM " + tablename);
                ResultSetMetaData rsmd = rs1.getMetaData();
                List<TableInfo> maps = new ArrayList<>();
                Map<String, Map<String, String>> columnCommentByTableName = getColumnCommentByTableName(tablename);
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String field = rsmd.getColumnName(i);
                    int type = rsmd.getColumnType(i);//拿到数据库具体数据类型代号
                    String strtype = JDBCTypesUtils.jdbcTypeToJavaType(type).getName();
                    TableInfo tableInfo = new TableInfo();
                    tableInfo.setColumn(field);
                    tableInfo.setName(Tool.lineToHump(field));
                    tableInfo.setHname(Tool.HlineToHump(field));
                    tableInfo.setType(strtype);
                    tableInfo.setDtype(JDBCTypesUtils.getJdbcName(type));
                    tableInfo.setCtype(strtype.substring(strtype.lastIndexOf(".") + 1));
                    tableInfo.setComment(columnCommentByTableName.get(field).get("comment"));
                    tableInfo.setDbField(columnCommentByTableName.get(field).get("field"));
                    tableInfo.setDbType(columnCommentByTableName.get(field).get("type").toLowerCase());
                    tableInfo.setClen(getTypeLen(columnCommentByTableName.get(field).get("type")));
                    tableInfo.setPrimarkey(columnCommentByTableName.get(field).get("iskey").equalsIgnoreCase("PRI"));
                    tableInfo.setNull(columnCommentByTableName.get(field).get("isnull").equalsIgnoreCase("YES"));
                    tableInfo.setDefaultVal(columnCommentByTableName.get(field).get("defaultval"));
                    tableInfo.setDate(tableInfo.getCtype().equalsIgnoreCase("date"));
                    maps.add(tableInfo);
                }
                return maps;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs1 != null) {
                        rs1.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }

        // 获得某表中所有字段的注释
        public Map<String, Map<String, String>> getColumnCommentByTableName(String tableName) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                conn = this.travelDataSourceConfig.getConn();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("show full columns from " + tableName);
                System.out.println("【" + tableName + "】");
                Map<String, Map<String, String>> mapMap = new HashMap<>();
                while (rs.next()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("field", rs.getString("Field"));
                    map.put("type", rs.getString("Type"));
                    map.put("isnull", rs.getString("Null"));
                    map.put("iskey", rs.getString("Key"));
                    map.put("defaultval", rs.getString("Default"));
                    map.put("comment", rs.getString("Comment"));
                    mapMap.put(rs.getString("Field"), map);
                }
                return mapMap;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }


    public static String getTypeLen(String type) {
        try {
            if (type.indexOf("date") != -1) {
                return "20";
            }
            String pattern = "\\((.+?)\\)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(type);
            if (m.find()) {
                return m.group(1);
            }
            return "255";
        } catch (Exception e) {
            return "";
        }
    }

    public static void main(String[] args) {

    }

}