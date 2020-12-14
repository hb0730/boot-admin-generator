# boot-admin-generator

boot-admin code generator

基于[mybatis-plus](https://github.com/baomidou/mybatis-plus) 的 `mybatis-plus-generator`
适配 [boot-admin](https://github.com/hb0730/boot-admin) 的代码生成

只实现了对[velocity](http://velocity.apache.org/) 的支持

# 用法

```java
AutoGeneratorExt mp=new AutoGeneratorExt();
//扩展配置
        ExtConfig extConfig=new ExtConfig();
        mp.setExtConfig(extConfig);
```

# 扩展配置介绍 ExtConfig

```java
   /**
 * 需要继承的基础params (需要继承的过滤超类)
 */
public String superParamsClass;
/**
 * paramsName (当前过滤的名称) %s采用String#format 格式化
 */
public String paramsClass="%s";
/**
 * params包名(当前模块的包路径)
 */
public String params="params";
/**
 * 需要继承的DTO (需要继承的DTO超类)
 */
public String superDtoClass;
/**
 * dtoName (当前dto的名称) %s采用String#format 格式化
 */
public String dtoClass="%s";
/**
 * dto包名 (当前模块的包路径)
 */
public String dto="dto";

/**
 * id类型 （对应超类中Id泛型类型）
 */
public String idType2="String";
        }
```
