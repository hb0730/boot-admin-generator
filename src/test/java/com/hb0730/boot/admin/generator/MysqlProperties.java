package com.hb0730.boot.admin.generator;

/**
 * @author bing_huang
 */
public class MysqlProperties {
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/boot-admin?characterEncoding=UTF-8&useSSL=false&useUnicode=true&serverTimezone=UTC";
    public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String MYSQL_USERNAME = "root";
    public static final String MYSQL_PASSWORD = "123456";
    public static final String SUPER_ENTITY_CLASS = "com.hb0730.admin.boot.domain.model.entity.BaseDomain";
    public static final String[] SUPER_ENTITY_COLUMNS = new String[]{"create_user_id", "create_time", "update_user_id", "update_time", "is_enabled", "del_flag", "version"};
    public static final String SUPER_CONTROLLER_CLASS = "com.hb0730.admin.boot.domain.controller.SuperSimpleBaseController";
    public static final String VERSION_FILE_NAME = "version";
    public static final String LOGIC_DELETE_FILE_NAME = "del_flag";
}
