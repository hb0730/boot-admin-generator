package com.hb0730.boot.admin.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.hb0730.boot.admin.generator.config.ExtConfig;
import com.hb0730.boot.admin.generator.config.builder.ConfigBuilderExt;
import com.hb0730.boot.admin.generator.engine.VelocityTemplateEngineExt;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 扩展代码生成
 *
 * @author bing_huang
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AutoGeneratorExt extends AutoGenerator {
    private static final Logger logger = LoggerFactory.getLogger(AutoGenerator.class);
    /**
     * 扩展信息
     */
    protected ExtConfig extConfig;

    @Override
    public void execute() {
        logger.debug("==========================准备生成文件...==========================");
        // 初始化配置
        if (null == config) {
            config = new ConfigBuilderExt(
                    this.getPackageInfo(),
                    this.getDataSource(),
                    this.getStrategy(),
                    this.getTemplate(),
                    this.getGlobalConfig(),
                    this.extConfig);
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
        if (null == this.getTemplateEngine()) {
            // 为了兼容之前逻辑，采用 Velocity 引擎 【 默认 】
            this.setTemplateEngine(new VelocityTemplateEngineExt());
        }
        // 模板引擎初始化执行文件输出
        this.getTemplateEngine().init(this.pretreatmentConfigBuilder(config)).mkdirs().batchOutput().open();
        logger.debug("==========================文件生成完成！！！==========================");
    }
}
