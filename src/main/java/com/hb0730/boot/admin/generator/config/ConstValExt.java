package com.hb0730.boot.admin.generator.config;

import com.baomidou.mybatisplus.generator.config.ConstVal;

/**
 * @author bing_huang
 */
public interface ConstValExt extends ConstVal {
    String DTO_PATH = "dto_path";
    String PARAMS_PATH = "params_path";

    String DTO = "dto";
    String PARAMS = "params";


    String PARAMS_TEMPLATE_JAVA = "/templates/params.java.vm";
    String DTO_TEMPLATE_JAVA = "/templates/dto.java.vm";

}
