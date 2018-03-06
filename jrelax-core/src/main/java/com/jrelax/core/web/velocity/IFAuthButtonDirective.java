package com.jrelax.core.web.velocity;

import com.jrelax.core.support.ApplicationCommon;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.directive.Foreach;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 按钮权限控制
 * Created by zengchao on 2016-09-12.
 */
public class IFAuthButtonDirective extends Directive{

    @Override
    public String getName() {
        return "ifAuthButton";
    }

    @Override
    public int getType() {
        return BLOCK;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Object btnCode = node.jjtGetChild(0).value(context);
        if(btnCode == null) return false;

        Object btnAuth = context.get(ApplicationCommon.AUTH_BUTTON);
        if(btnAuth instanceof String){
            node.jjtGetChild(1).render(context, writer);
            return true;
        }else{
            Map<String, Object> btnMap = (Map<String, Object>) btnAuth;
            if(btnMap != null && btnMap.get(btnCode) != null){
                node.jjtGetChild(1).render(context, writer);
                return true;
            }
            return false;
        }


    }
}
