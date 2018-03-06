var chinaDataStatus = [{ cha: 'HKG', name: '香港'},
                             { cha: 'HAI', name: '海南'},
                             { cha: 'YUN', name: '云南'},
                             { cha: 'BEJ', name: '北京是IAP-IAS的示范点'},
                             { cha: 'TAJ', name: '天津'},
                             { cha: 'XIN', name: '新疆'},
                             { cha: 'TIB', name: '西藏'},
                             { cha: 'QIH', name: '青海'},
                             { cha: 'GAN', name: '甘肃'},
                             { cha: 'NMG', name: '内蒙古'},
                             { cha: 'NXA', name: '宁夏'},
                             { cha: 'SHX', name: '山西'},
                             { cha: 'LIA', name: '辽宁'},
                             { cha: 'JIL', name: '吉林'},
                             { cha: 'HLJ', name: '黑龙江'},
                             { cha: 'HEB', name: '河北'},
                             { cha: 'SHD', name: '山东'},
                             { cha: 'HEN', name: '河南'},
                             { cha: 'SHA', name: '陕西'},
                             { cha: 'SCH', name: '四川'},
                             { cha: 'CHQ', name: '重庆'},
                             { cha: 'HUB', name: '湖北'},
                             { cha: 'ANH', name: '安徽'},
                             { cha: 'JSU', name: '江苏'},
                             { cha: 'SHH', name: '上海'},
                             { cha: 'ZHJ', name: '浙江'},
                             { cha: 'FUJ', name: '福建'},
                             { cha: 'TAI', name: '台湾'},
                             { cha: 'JXI', name: '江西'},
                             { cha: 'HUN', name: '湖南'},
                             { cha: 'GUI', name: '贵州'},
                             { cha: 'GXI', name: '广西'}, 
                             { cha: 'GUD', name: '广东'}];
					
var BEJDataStatus= [{ cha: 'CHAOYANG', name: '朝阳区'},
							 { cha: 'CHONGWEN', name: '崇文区'},
							 { cha: 'DONGCHENG', name: '东城区'},
							 { cha: 'FENGTAI', name: '丰台区'},
							 { cha: 'HAIDIAN', name: '海淀区'},
							 { cha: 'SHIJINGSHAN', name: '石景山区'},
							 { cha: 'XICHENG', name: '西城区'},
							 { cha: 'XUANWU', name: '宣武区'},
                             { cha: 'CHANGPING', name: '昌平区'},
                             { cha: 'DAXING', name: '大兴区'},
                             { cha: 'FANGSHAN', name: '房山县'},
                             { cha: 'HUAIROU', name: '怀柔区'},
                             { cha: 'MENTOUGOU', name: '门头沟区'},
                             { cha: 'MIYUN', name: '密云县'},
                             { cha: 'PINGGU', name: '平谷县'},
                             { cha: 'SHUNYI', name: '顺义区'},
                             { cha: 'TONGZHOU', name: '通州区'},
                             { cha: 'YANQING', name: '延庆县'}];
							 
function getDataStatus(code){
	switch(code){
		case 'chinaDataStatus':
			return chinaDataStatus;
		case 'BEJDataStatus':
			return BEJDataStatus;
	}
}