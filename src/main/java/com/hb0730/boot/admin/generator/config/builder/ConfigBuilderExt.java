package com.hb0730.boot.admin.generator.config.builder;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.hb0730.boot.admin.generator.config.ConstValExt;
import com.hb0730.boot.admin.generator.config.ExtConfig;
import lombok.Getter;

import java.io.File;
import java.util.Map;

/**
 * @author bing_huang
 */
public class ConfigBuilderExt extends ConfigBuilder {
    @Getter
    private ExtConfig extConfig;

    /**
     * 在构造器中处理配置
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param template         模板配置
     * @param globalConfig     全局配置
     */
    public ConfigBuilderExt(PackageConfig packageConfig,
                            DataSourceConfig dataSourceConfig,
                            StrategyConfig strategyConfig,
                            TemplateConfig template,
                            GlobalConfig globalConfig,
                            ExtConfig extConfig) {
        super(packageConfig, dataSourceConfig, strategyConfig, template, globalConfig);
        this.extConfig = extConfig;
        handlerPackageExt(getGlobalConfig().getOutputDir(), packageConfig);
    }


    protected void handlerPackageExt(String outputDir, PackageConfig config) {
        // 包信息
        Map<String, String> packageInfo = this.getPackageInfo();
        packageInfo.put(ConstValExt.DTO, joinPackage(config.getParent(), extConfig.getDtoPackage()));
        packageInfo.put(ConstValExt.PARAMS, joinPackage(config.getParent(), extConfig.getParamsPackage()));

        //// 生成路径信息
        setPathInfo(getPathInfo(), ConstValExt.DTO_TEMPLATE_JAVA, outputDir, ConstValExt.DTO_PATH, ConstValExt.DTO);
        setPathInfo(getPathInfo(), ConstValExt.PARAMS_TEMPLATE_JAVA, outputDir, ConstValExt.PARAMS_PATH, ConstValExt.PARAMS);
    }

    protected String joinPackage(String parent, String subPackage) {
        return StringUtils.isBlank(parent) ? subPackage : (parent + StringPool.DOT + subPackage);
    }

    private void setPathInfo(Map<String, String> pathInfo, String template, String outputDir, String path, String module) {
        if (StringUtils.isNotBlank(template)) {
            pathInfo.put(path, joinPath(outputDir, getPackageInfo().get(module)));
        }
    }

    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }


}
