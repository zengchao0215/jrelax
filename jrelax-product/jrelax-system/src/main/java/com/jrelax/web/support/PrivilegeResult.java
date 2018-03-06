package com.jrelax.web.support;

import com.jrelax.web.system.entity.Resource;

/**
 * 权限验证结果
 * Created by zengchao on 2017-02-23.
 */
public class PrivilegeResult {
    private boolean hasPrivilege;
    private boolean disabled;
    private Resource resource;

    public PrivilegeResult(boolean hasPrivilege, boolean disabled, Resource resource) {
        this.hasPrivilege = hasPrivilege;
        this.disabled = disabled;
        this.resource = resource;
    }

    public boolean hasPrivilege() {
        return hasPrivilege;
    }

    public void setHasPrivilege(boolean hasPrivilege) {
        this.hasPrivilege = hasPrivilege;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
