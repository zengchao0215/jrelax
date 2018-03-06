##系统变量定义

* basePath    项目路径名，系统自动根据访问路径计算，无法设置（结尾无 `/`）
* resPath     资源路径，sys_config表中配置
* uploadPath 上传文件路径，如未开启远程服务器上传功能，则同 `resPath`， 否则值为sys_config表中配置的值（upload.remote.view）
* BigDecimalKit 工具类，`com.jrelax.kit.BigDecimalKit`

###内部变量
* _firstRes   一级菜单资源 LIST
* _secondRes  二级菜单资源 MAP key=一级菜单ID
* _thirdRes   三级菜单资源 MAP key=二级菜单ID
& theme       当前皮肤


## 权限控制

* 菜单权限  firstRes + secondRes + thirdRes
* 按钮权限  com.jrelax.core.web.velocity.IFAuthButtonDirective
* 字段权限  com.jrelax.core.web.velocity.IFAutoColumnDirective

## 配置文件说明

* core-config.properties 系统配置参数
* ehcache-config ehcache缓存配置
* jdbc_*.properties 数据库相关配置文件
* log4j.properties Log4j日志配置
* plugins.xml 系统插件配置
* service-context.xml Spring配置文件
* servlet-context.xml SpringMVC配置文件