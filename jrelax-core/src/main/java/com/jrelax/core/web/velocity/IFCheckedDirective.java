package com.jrelax.core.web.velocity;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

/**
 * checkbox操作
 * Created by zengchao on 2016-09-20.
 */
public class IFCheckedDirective extends Directive {
    @Override
    public String getName() {
        return "ifChecked";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Object val = node.jjtGetChild(0).value(context);
        if (val == null) return false;
        boolean match = false;
        if (node.jjtGetNumChildren() == 2) {//如果有两个参数，那么将这两个参数进行对比
            Object val2 = node.jjtGetChild(1).value(context);
            match = val.toString().equals(val2 + "");
        } else {//如果只有一个参数，那么将此参数转换为boolean型
            match = Boolean.parseBoolean(val + "");
        }
        if (match) {
            writer.write("checked='checked'");
        }
        return false;
    }
}
