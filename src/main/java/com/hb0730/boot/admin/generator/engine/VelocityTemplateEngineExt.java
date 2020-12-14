package com.hb0730.boot.admin.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.hb0730.boot.admin.generator.config.ConstValExt;
import com.hb0730.boot.admin.generator.config.ExtConfig;
import com.hb0730.boot.admin.generator.config.builder.ConfigBuilderExt;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author bing_huang
 */
public class VelocityTemplateEngineExt extends VelocityTemplateEngine {

    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();
                ExtConfig extConfig = ((ConfigBuilderExt) getConfigBuilder()).getExtConfig();
                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initTableMap(tableInfo);
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                writerFile(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }
                String entityName = tableInfo.getEntityName();
                //params
                if (null != extConfig.getParams() && null != pathInfo.get(ConstValExt.PARAMS_PATH)) {
                    String paramsFile = String.format(pathInfo.get(ConstValExt.PARAMS_PATH) + File.separator + "%sParams" + suffixJavaOrKt(), entityName);
                    if (isCreate(FileType.OTHER, paramsFile)) {
                        writerFile(objectMap, templateFilePath(ConstValExt.PARAMS_TEMPLATE_JAVA), paramsFile);
                    }
                }

                //dto
                if (null != extConfig.getDto() && null != pathInfo.get(ConstValExt.DTO_PATH)) {
                    String paramsFile = String.format(pathInfo.get(ConstValExt.DTO_PATH) + File.separator + "%sDTO" + suffixJavaOrKt(), entityName);
                    if (isCreate(FileType.OTHER, paramsFile)) {
                        writerFile(objectMap, templateFilePath(ConstValExt.DTO_TEMPLATE_JAVA), paramsFile);
                    }
                }
                // Mp.java
                if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                    String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.ENTITY, entityFile)) {
                        writerFile(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                    }
                }
                // MpMapper.java
                if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                    String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.MAPPER, mapperFile)) {
                        writerFile(objectMap, templateFilePath(template.getMapper()), mapperFile);
                    }
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                    String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                    if (isCreate(FileType.XML, xmlFile)) {
                        writerFile(objectMap, templateFilePath(template.getXml()), xmlFile);
                    }
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                    String serviceFile = String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE, serviceFile)) {
                        writerFile(objectMap, templateFilePath(template.getService()), serviceFile);
                    }
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                    String implFile = String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                        writerFile(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                    }
                }
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                    String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.CONTROLLER, controllerFile)) {
                        writerFile(objectMap, templateFilePath(template.getController()), controllerFile);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    @Override
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        ExtConfig extConfig = ((ConfigBuilderExt) getConfigBuilder()).getExtConfig();

        Map<String, Object> map = super.getObjectMap(tableInfo);
        map.put("idType2", extConfig.getIdType2());
        map.put("superDtoClass", getSuperClassName(extConfig.getSuperDtoClass()));
        map.put("superDtoPackage", extConfig.getSuperDtoClass());
        map.put("dtoClass", getSuperClassName(format(extConfig.getDtoClass(), tableInfo.getEntityName())));
        map.put("dtoName", getSuperClassName(format(extConfig.getDtoClass(), tableInfo.getEntityName())) + "DTO" );
        map.put("superParamsClass", getSuperClassName(extConfig.getSuperParamsClass()));
        map.put("superParamsPackage", extConfig.getSuperParamsClass());
        map.put("paramsClass", getSuperClassName(format(extConfig.getParamsClass(), tableInfo.getEntityName())));
        map.put("paramsName", getSuperClassName(format(extConfig.getParamsClass(), tableInfo.getEntityName())) + "Params" );

        return map;
    }

    private String format(String name, String entityName) {
        return String.format(name, entityName);
    }


    /**
     * 获取类名
     *
     * @param classPath ignore
     * @return ignore
     */
    private String getSuperClassName(String classPath) {
        if (StringUtils.isBlank(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
    }
}
