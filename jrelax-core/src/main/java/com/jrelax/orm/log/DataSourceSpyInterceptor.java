package com.jrelax.orm.log;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.sql.Connection;

/**
 * Created by zengchao on 2016-10-25.
 */
public class DataSourceSpyInterceptor implements MethodInterceptor{
    private RdbmsSpecifics rdbmsSpecifics;

    public RdbmsSpecifics getRdbmsSpecifics(Connection conn) {
        if(rdbmsSpecifics == null)
            rdbmsSpecifics = DriverSpy.getRdbmsSpecifics(conn);
        return rdbmsSpecifics;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = methodInvocation.proceed();
        if(SpyLogFactory.getSpyLogDelegator().isJdbcLoggingEnabled()){
            if(result instanceof Connection){
                Connection conn = (Connection) result;
                return new ConnectionSpy(conn, getRdbmsSpecifics(conn));
            }
        }
        return result;
    }
}
