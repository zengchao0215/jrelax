package com.jrelax.web.system.service;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.BlackList;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author zengchao
 * @version 1.0
 * @since 1.0
 */
@Service
public class BlackListService extends BaseService<BlackList> {
    /**
     * 同步缓存
     */
    public void sync() {
        List<?> list = super.listToObject("select rules from BlackList where enabled=true");
        getApplicationCache().put(ApplicationCommon.CACHE_IP_BLACK_LIST, list);
    }

    /**
     * 判断IP是否被允许
     *
     * @param ip
     * @return
     */
    public boolean isAllow(String ip) {
        //判断IP地址是否合法
        if (!ip.matches("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))")) return true;
        List<String> ruleList = getApplicationCache().get(ApplicationCommon.CACHE_IP_BLACK_LIST);
        for (String rule : ruleList) {
            if (matchOfSingle(rule, ip) || matchOfRange(rule, ip) || matchOfLike(rule, ip) || matchOfRegex(rule, ip))
                return false;
        }
        return true;
    }

    /**
     * 单个匹配
     *
     * @param rule 匹配规则
     * @param ip   要匹配的IP地址
     * @return
     */
    private boolean matchOfSingle(String rule, String ip) {
        return rule.equals(ip);
    }

    /**
     * 范围匹配
     *
     * @param rule 匹配规则
     * @param ip   要匹配的IP地址
     * @return
     */
    private boolean matchOfRange(String rule, String ip) {
        if (!rule.contains("-")) return false;
        String[] ranges = rule.split("-");
        if (ranges.length < 2) return false;
        String beginIp = ranges[0].trim();
        String endIp = ranges[1].trim();

        return ipToLong(beginIp) <= ipToLong(ip) && ipToLong(ip) <= ipToLong(endIp);
    }

    private long ipToLong(String ip) {
        String[] ips = ip.split("\\.");
        long ip2long = 0L;
        for (int i = 0; i < 4; ++i) {
            ip2long = ip2long << 8 | Integer.parseInt(ips[i]);
        }
        return ip2long;

    }

    /**
     * 模糊匹配
     *
     * @param rule 匹配规则
     * @param ip   要匹配的IP地址
     * @return
     */
    private boolean matchOfLike(String rule, String ip) {
        rule = rule.replaceAll("\\*", ".*");
        return matchOfRegex(rule, ip);
    }

    /**
     * 正则匹配
     *
     * @param rule 匹配规则
     * @param ip   要匹配的IP地址
     * @return
     */
    private boolean matchOfRegex(String rule, String ip) {
        boolean isMatch = false;
        try {
            isMatch = ip.matches(rule);
        } catch (Exception e) {
            isMatch = false;
        }
        return isMatch;
    }
}
