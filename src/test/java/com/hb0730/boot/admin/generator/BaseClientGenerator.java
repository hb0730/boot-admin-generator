package com.hb0730.boot.admin.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.hb0730.boot.admin.generator.config.ExtConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bing_huang
 */
public class BaseClientGenerator {
    public static void main(String[] args) {
        AutoGeneratorExt mp = new AutoGeneratorExt();
        //数据源
        DataSourceConfig sourceConfig = new DataSourceConfig();
        sourceConfig
                .setDbType(DbType.MYSQL)
                .setUrl(MysqlProperties.MYSQL_URL)
                .setDriverName(MysqlProperties.DRIVER_NAME)
                .setUsername(MysqlProperties.MYSQL_USERNAME)
                .setPassword(MysqlProperties.MYSQL_PASSWORD);
        mp.setDataSource(sourceConfig);

        //数据库表配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("j_base_" )
                .setSuperEntityClass(MysqlProperties.SUPER_ENTITY_CLASS)
                .setSuperEntityColumns(MysqlProperties.SUPER_ENTITY_COLUMNS)
                .setSuperServiceClass("com.hb0730.boot.admin.domain.service.ISuperBaseService" )
                .setSuperServiceImplClass(" com.hb0730.boot.admin.domain.service.impl.SuperBaseServiceImpl" )
                .setSuperControllerClass(MysqlProperties.SUPER_CONTROLLER_CLASS)
                .setInclude("j_base_client" )
                .setEntityColumnConstant(true)
                .setEntityBuilderModel(true)
                .setChainModel(true)
                .setEntityLombokModel(true)
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setVersionFieldName(MysqlProperties.VERSION_FILE_NAME)
                .setLogicDeleteFieldName(MysqlProperties.LOGIC_DELETE_FILE_NAME);
        mp.setStrategy(strategyConfig);

        // 包名配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.hb0730.admin.boot.project.base" )
                .setModuleName("client" )
                .setEntity("model.entity" )
                .setService("service" )
                .setServiceImpl("service.impl" )
                .setMapper("mapper" )
                .setController("controller" );
        mp.setPackageInfo(packageConfig);

        //扩展配置
        ExtConfig extConfig = new ExtConfig();
        extConfig.setDtoClass("%s" );
        extConfig.setParamsClass("%s" );
        extConfig.setIdType2("Long" );
        extConfig.setSuperParamsClass("com.hb0730.boot.admin.domain.model.query.BaseParams" );
        extConfig.setSuperDtoClass("com.hb0730.boot.admin.domain.model.dto.BaseDTO" );
        extConfig.setParamsPackage("model.params" );
        extConfig.setDtoPackage("model.dto" );
        mp.setExtConfig(extConfig);
        //模板配置

        //全局策略 globalConfig 配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir" );
        globalConfig.setOutputDir(projectPath + "/src/main/java" )
                .setFileOverride(false)
                .setOpen(false)
                .setEnableCache(false)
                .setAuthor("bing_huang" )
                .setKotlin(false)
                .setSwagger2(false)
                .setDateType(DateType.TIME_PACK)
                .setEntityName("%s" )
                .setMapperName("I%sMapper" )
                .setXmlName("%sMapper" )
                .setServiceName("I%sService" )
                .setServiceImplName("%sServiceImpl" )
                .setControllerName("%sController" );
        mp.setGlobalConfig(globalConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mp.setCfg(cfg);

        mp.execute();
    }
}
