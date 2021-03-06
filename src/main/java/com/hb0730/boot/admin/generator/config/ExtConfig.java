package com.hb0730.boot.admin.generator.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 扩展字段
 *
 * @author bing_huang
 * @see com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder
 */
@Data
@Accessors(chain = true)
public class ExtConfig {
    /**
     * 需要继承的基础params
     */
    public String superParamsClass;

    public void setSuperParamsClass(String superParamsClass) {
        this.superParamsClass = superParamsClass;
    }

    public void setSuperParamsClass(Class<?> superParamsClass) {
        this.superParamsClass = superParamsClass.getName();
    }

    /**
     * paramsName
     */
    public String paramsClass = "%s";
    /**
     * params包名
     */
    public String paramsPackage = "params";
    /**
     * 需要继承的DTO
     */
    public String superDtoClass;

    public void setSuperDtoClass(String superDtoClass) {
        this.superDtoClass = superDtoClass;
    }

    public void setSuperDtoClass(Class<?> superDtoClass) {
        this.superDtoClass = superDtoClass.getName();
    }

    /**
     * dtoName
     */
    public String dtoClass = "%s";
    /**
     * dto包名
     */
    public String dtoPackage = "dto";

    /**
     * id类型
     */
    public String idType2 = "String";
}
