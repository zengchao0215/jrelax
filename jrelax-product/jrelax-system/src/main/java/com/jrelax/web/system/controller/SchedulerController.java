package com.jrelax.web.system.controller;

import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.job.Job;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Scheduler;
import com.jrelax.web.system.entity.SchedulerLog;
import com.jrelax.web.system.service.SchedulerLogService;
import com.jrelax.web.system.service.SchedulerService;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zengchao
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/system/scheduler")
@ViewPrefix("/system/scheduler")
public class SchedulerController extends BaseController<Scheduler> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SchedulerService schedulerService;
    @Resource
    private SchedulerLogService schedulerLogService;

    /**
     * 首页
     *
     * @param model
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        return "index";
    }

    /**
     * 数据
     *
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/data", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> data(PageBean pageBean) {
        List<Scheduler> list = schedulerService.list(pageBean);
        return DataGridTransforms.JQGRID.transform(list, pageBean);
    }

    /**
     * 转向新增页面
     *
     * @param model
     * @param pid
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String add(Model model, String pid) {
        return "add";
    }

    /**
     * 执行新增
     *
     * @param scheduler
     * @return
     */
    @RequestMapping(value = "/add/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doAdd(Scheduler scheduler) {
        try {
            //校验任务名称
            if (schedulerService.has(Condition.NEW().eq("name", scheduler.getName())))
                return WebResult.error("任务名称已存在");
            if (schedulerService.has(Condition.NEW().eq("jobClass", scheduler.getJobClass())))
                return WebResult.error("任务类已存在");
            //校验时间
            if (!CronExpression.isValidExpression(scheduler.getCron())) {
                return WebResult.error("执行时间格式错误");
            }
            //校验执行类
            try {
                Class instance = Class.forName(scheduler.getJobClass());

                if (!(instance.newInstance() instanceof Job)) {
                    return WebResult.error("执行类必须继承自'Job'");
                }
            } catch (Exception e) {
                return WebResult.error(StringKit.format("'{0}'执行类不存在", scheduler.getJobClass()));
            }

            schedulerService.save(scheduler);
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }

    /**
     * 转向编辑页面
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(Model model, @PathVariable String id) {
        Scheduler scheduler = schedulerService.getById(id);
        if (!ObjectKit.isNotNull(scheduler))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("scheduler", scheduler);
        return "edit";
    }

    /**
     * 执行编辑
     *
     * @param scheduler
     * @return
     */
    @RequestMapping(value = "/edit/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doEdit(Scheduler scheduler) {
        try {
            //校验任务名称
            if (schedulerService.has(Condition.NEW().eq("name", scheduler.getName()).not(Restrictions.eq("id", scheduler.getId()))))
                return WebResult.error("任务已存在");
            //校验时间
            if (!CronExpression.isValidExpression(scheduler.getCron())) {
                return WebResult.error("执行时间格式错误");
            }
            //从数据库中获取最新数据
            Scheduler oldScheduler = schedulerService.getById(scheduler.getId());
            if (ObjectKit.isNull(oldScheduler)) {
                return WebResult.notAllow();
            }
            oldScheduler.setName(scheduler.getName());
            oldScheduler.setCron(scheduler.getCron());
            oldScheduler.setEnabled(scheduler.getEnabled());
            oldScheduler.setRemarks(scheduler.getRemarks());

            schedulerService.merge(oldScheduler);
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject delete(@PathVariable String id) {
        try {
            //删除相关的所有数据
            schedulerService.delete(id);
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }

    /**
     * 暂停任务
     * @param id
     * @return
     */
    @RequestMapping(value = "/pause/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject pause(@PathVariable String id) {
        try {
            //删除相关的所有数据
            schedulerService.pause(id);
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }

    /**
     * 恢复任务
     * @param id
     * @return
     */
    @RequestMapping(value = "/resume/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject resume(@PathVariable String id) {
        try {
            //删除相关的所有数据
            schedulerService.resume(id);
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }

    /**
     * 查看执行日志
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/log/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String log(Model model, @PathVariable String id) {
        Scheduler scheduler = schedulerService.getById(id);
        if (!ObjectKit.isNotNull(scheduler))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("scheduler", scheduler);
        return "log";
    }

    /**
     * 日志数据
     *
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/log/data", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> logData(PageBean pageBean, String id) {
        pageBean.getCondition().eq("scheduler.id", id);
        List<SchedulerLog> list = schedulerLogService.list(pageBean);
        return DataGridTransforms.JQGRID.transform(list, pageBean);
    }
}
