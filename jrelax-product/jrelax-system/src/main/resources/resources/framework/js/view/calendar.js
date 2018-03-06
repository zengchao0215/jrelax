/**
 * 日历
 * 基于FullCalendar
 */
ns.component("view.calendar", {
    init : function(){
        //引入必要的样式+JS文件
        ns.requireCSS("/framework/plugins/fullcalendar/fullcalendar.css");
        ns.requireJS("/framework/plugins/fullcalendar/moment.min.js");
        ns.requireJS("/framework/plugins/fullcalendar/fullcalendar.js");
        ns.requireJS("/framework/plugins/fullcalendar/locale/zh-cn.js");
    }
});
