package com.jrelax.config;

import java.util.Enumeration;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.security.DecKit;
import org.springframework.core.io.Resource;

/**
 * Spring配置文件加载器
 * 继承自Spring的PropertyPlaceholderConfigurer
 * 重写processProperties方法，实现spring加载之前对参数进行处理
 * 如用户名密码加密等.
 * 创建人：ZENGCHAO
 * 创建时间：‎2012‎-12‎-03‎ 上午10:47:25
 *
 * @version 1.0
 */
public class JRelaxPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private Properties _hibernateProps = null;
    private Resource[] locations = null;
    private boolean encrypt = true;//是否加密

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {
        this._hibernateProps = props;
        if (this.encrypt) {
            Enumeration<?> enuProp = props.keys();
            DecKit des = new DecKit();// 实例化一个对像
            des.genKey(ApplicationCommon.DEC_KEY);// 生成密匙
            while (enuProp.hasMoreElements()) {
                String name = enuProp.nextElement().toString();
                if (name.endsWith(".username") || name.endsWith(".password")) {
                    String val = props.getProperty(name);
                    props.setProperty(name, des.getDesString(val));
                }
            }
        }
        super.processProperties(beanFactoryToProcess, props);
    }

    /**
     * 获取hibernate配置文件信息
     *
     * @return
     */
    public Properties getHibernateProperties() {
        return _hibernateProps;
    }

    /**
     * 数据源配置文件
     * @param locations
     */
    @Override
    public void setLocations(Resource... locations) {
        this.locations = locations;
        super.setLocations(locations);
    }

    /**
     * 获取数据源配置文件
     * @return
     */
    public Resource[] getLocations() {
        return locations;
    }
}
