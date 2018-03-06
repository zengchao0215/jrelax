# JRelax-Token

## 功能说明
* 对外提供Http形式的接口时需要保存用户状态，模拟HttpSession机制的用户状态保持
* HttpSession采用Cookie，JRelax-Token使用Http Header

使用流程，请求链接 -> 从ResponseHeader获得Token -> 以后每次请求在RequestHeader中添加Token

## 集成说明
``` xml
<filter>
    <filter-name>Token</filter-name>
    <filter-class>com.jrelax.token.TokenSessionFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>Token</filter-name>
    <url-pattern>/open/token/*</url-pattern>
</filter-mapping>
```
### 与SpringMVC集成说明
``` xml
<!--参数注解-->
<bean class="com.jrelax.core.web.annotation.RequestMappingHandlerAdapter">
    <property name="customArgumentResolvers">
        <list>
            <bean class="com.jrelax.token.springmvc.TokenSessionArgumentResolver"/>
        </list>
    </property>
</bean>
<!--Token验证-->
<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
    <property name="interceptors">
        <list>
            <bean class="com.jrelax.token.springmvc.TokenSessionInterceptor"/>
        </list>
    </property>
</bean>
```

### 注解说明
* @TokenSessionAttribute TokenSession中获取参数
* @TokenAuth Token验证注解

## 如何获取TokenSession
- Servlet API方式： 
```java_holder_method_tree
   TokenSesion session = request.getAttribute(TokenSessionConfig.ATTRIBUTE_NAME);
```
- SpringMVC 方式
```java_holder_method_tree
//直接参数注入
public void method(TokenSession session){
    
}
```