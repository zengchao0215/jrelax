package com.jrelax.core.web.velocity;

import com.jrelax.core.support.ApplicationCommon;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * 资源权限控制
 * Created by zengchao on 2016-09-12.
 */
public class IFAuthResourceDirective extends Directive{

    @Override
    public String getName() {
        return "ifAuthResource";
    }

    @Override
    public int getType() {
        return BLOCK;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Object resUrl = node.jjtGetChild(0).value(context);
        if(resUrl == null) return false;

        Object resAuth = context.get(ApplicationCommon.AUTH_RESOURCE);
        if(resAuth instanceof String){
            node.jjtGetChild(1).render(context, writer);
            return true;
        }else{
            List<String> list = (List<String>) resAuth;
            if(list != null){
                //判断URL是否存在
                for(String url : list){
                    if(url.equals(resUrl)){
                        node.jjtGetChild(1).render(context, writer);
                        return true;
                    }
                }
            }
            return false;
        }


    }
}
