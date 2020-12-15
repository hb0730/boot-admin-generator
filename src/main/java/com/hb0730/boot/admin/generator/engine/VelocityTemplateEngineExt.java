package com.hb0730.boot.admin.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
        super.batchOutput();
        List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
        for (TableInfo tableInfo : tableInfoList) {
            Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
            Map<String, Object> objectMap = getObjectMap(tableInfo);
            ExtConfig extConfig = ((ConfigBuilderExt) getConfigBuilder()).getExtConfig();
            String entityName = tableInfo.getEntityName();
            try {
                //params
                if (null != extConfig.getParamsPackage() && null != pathInfo.get(ConstValExt.PARAMS_PATH)) {
                    String paramsFile = String.format(pathInfo.get(ConstValExt.PARAMS_PATH) + File.separator + "%sParams" + suffixJavaOrKt(), entityName);
                    if (isCreate(FileType.OTHER, paramsFile)) {
                        writerFile(objectMap, templateFilePath(ConstValExt.PARAMS_TEMPLATE_JAVA), paramsFile);
                    }
                }

                //dto
                if (null != extConfig.getDtoPackage() && null != pathInfo.get(ConstValExt.DTO_PATH)) {
                    String paramsFile = String.format(pathInfo.get(ConstValExt.DTO_PATH) + File.separator + "%sDTO" + suffixJavaOrKt(), entityName);
                    if (isCreate(FileType.OTHER, paramsFile)) {
                        writerFile(objectMap, templateFilePath(ConstValExt.DTO_TEMPLATE_JAVA), paramsFile);
                    }
                }
            } catch (Exception e) {
                logger.error("无法创建文件，请检查配置信息！", e);
            }

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
        map.put("dtoName", getSuperClassName(format(extConfig.getDtoClass(), tableInfo.getEntityName())) + "DTO");
        map.put("superParamsClass", getSuperClassName(extConfig.getSuperParamsClass()));
        map.put("superParamsPackage", extConfig.getSuperParamsClass());
        map.put("paramsClass", getSuperClassName(format(extConfig.getParamsClass(), tableInfo.getEntityName())));
        map.put("paramsName", getSuperClassName(format(extConfig.getParamsClass(), tableInfo.getEntityName())) + "Params");

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
