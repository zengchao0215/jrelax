var DateUtil = {
    format: function (o) {
        var s = "";
        if (typeof(o) == "string") {
            s = o;
        } else {
            s = o.value;
        }
        if (s.length == 4) {
            o.value = s + "-";
        }
        if (s.length == 7) {
            o.value = s + "-";
        }
        if (s.length > 10) {
            o.value = s.substring(0, 10);
        }
    },
    parse: function (str) {
        if (typeof str == 'string') {
            var results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) *$/);
            if (results && results.length > 3)
                return new Date(parseInt(results[1]), parseInt(results[2]) - 1, parseInt(results[3]));
            results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);
            if (results && results.length > 6)
                return new Date(parseInt(results[1]), parseInt(results[2]) - 1, parseInt(results[3]), parseInt(results[4]), parseInt(results[5]), parseInt(results[6]));
            results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2})\.(\d{1,9}) *$/);
            if (results && results.length > 7)
                return new Date(parseInt(results[1]), parseInt(results[2]) - 1, parseInt(results[3]), parseInt(results[4]), parseInt(results[5]), parseInt(results[6]), parseInt(results[7]));
        }
        return str;
    },
    addDay: function (date, days) {
        var a = new Date(date)
        a = a.valueOf()
        a = a + days * 24 * 60 * 60 * 1000
        a = new Date(a)
        return a;
    },
    // 计算当前日期在本年的周数
    getWeekOfYear: function (date) {
        var weekStart = 0;
        if (typeof date === 'string')
            date = DateUtil.parse(date);

        var year = date.getFullYear();
        var firstDay = new Date(year, 0, 1);
        var firstWeekDays = 7 - firstDay.getDay() + weekStart;
        var dayOfYear = (((new Date(year, date.getMonth(), date.getDate())) - firstDay) / (24 * 3600 * 1000)) + 1;
        return Math.ceil((dayOfYear - firstWeekDays) / 7) + 1;
    },
    // 计算当前日期在本月份的周数
    getWeekOfMonth: function (date) {
        var weekStart = 0;
        if (typeof date === 'string')
            date = DateUtil.parse(date);

        var dayOfWeek = date.getDay();
        var day = date.getDate();
        return Math.ceil((day - dayOfWeek - 1) / 7) + ((dayOfWeek >= weekStart) ? 1 : 0);
    },
    //获取周数
    getWeeksOfMonth: function (date) {
        if (typeof date === 'string')
            date = DateUtil.parse(date);
        date = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        var w = date.getDay(), d = date.getDate();
        return Math.ceil(
            (d + 6 - w) / 7
        );
    },
    //获取周数
    getWeeksOfYear: function (date) {
        alert("暂未实现");
        return -1;
    }
};
