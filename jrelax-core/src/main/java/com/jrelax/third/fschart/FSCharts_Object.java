package com.jrelax.third.fschart;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.jrelax.core.support.ApplicationCommon;

/**
 * fusionCharts(基于flash和xml的图形报表)父类 Set标签使用说明 name 横向坐标轴标签名称 value 数据值 color 颜色
 * link 链接(本窗口打开（链接地址），新窗口中打开为（n-链接地址）,调用JavaScript为JavaScript:函数名)
 * 
 * @author ZENGCHAO
 * 
 */
public class FSCharts_Object {
	// 功能特性
	private int animation = 1;// 是否以动画形式展示数据,1为True0为False,默认为True

	private int showName = 1;// 是否显示横向坐标轴标签名,1为True0为False，默认为True

	private int rotateNames = 0;// 是否旋转显示标签，,1为True0为False，默认为False

	private int showValues = 1;// 是否显示对应的数据值，,1为True0为false，默认为True
	private float xAxisMinValue = -1;//指定X轴最小值
	private float xAxisMaxValue = -1;//指定X轴最大值
	
	private float yAxisMinValue = -1;// 指定Y轴最小值

	private float yAxisMaxValue = -1;// 指定Y轴最大值

	private int showLimits = 1;// 是否显示图表限定值,1为True0为false，默认为True

	// 图表标题和轴名称
	private String caption = "";// 图表主标题

	private String subCaption = "";// 图表副标题

	private String xAxisName = "";// X轴的名称

	private String yAxisName = "";// Y轴的名称

	// 图表和画布的样式
	private String bgColor = "";// 图表背景色，6位十六进制颜色值

	private String canvasBgColor = "";// 画布背景色,6位十六进制颜色值

	private int canvasBgAlpha = 0;// 画布透明度，值为0~100

	private String canvasBorderColor = "";// 画布边框颜色,6位十六进制颜色值

	private int canvasBorderThickness = 0;// 画布边框厚度，值为0~100

	private int shadowAlpha = 0;// 投影透明度

	private int showLegend = 1;// 是否显示系列名，1为True，0为False，默认为True

	// 字体属性
	private String baseFont = "";// 图表字体样式

	private int baseFontSize = -1;// 图表字体大小

	private String baseFontColor = "";// 图表字体颜色，6位十六进制颜色值

	private String outCnvBaseFont = "";// 图表画布以外的字体样式

	private int outCnvBaseFontSize = -1;// 图表画布以外的字体大小

	private String outCnvBaseFontColor = "";// 图表画布以外的字体颜色，6位十六进制颜色值

	// 分区线和网格
	private int numDivLines = -1;// 画布内部水平分曲线条数

	private String divLineColor = "";// 水平分区线颜色，6位十六进制颜色值

	private int divLineThickness = -1;// 水平分区线厚度，值为1~5

	private int divLineAlpha = 0;// 水平分区线透明度，值为0~100

	private String showAlternateHGridColor = "";// 是否在横向网格带交替的颜色，1为True0为False，默认为False

	private String alternateHGridColor = "";// 横向网格带交替的颜色，6位十六进制颜色值

	private int alternateHGridAlpha = 0;// 横向网格带的透明度,值为0~100

	private int showDivLinues = 1;// 是否显示Div行的值，1为True0为False，默认为True

	private int numVDivLines = -1;// 画布内部垂直分区线条数

	private String vDivLineColor = "";// 垂直分区线颜色，6位十六进制颜色值

	private int vDivLineThickness = -1;// 垂直分区线厚度，值为1~5

	private int vDivLineAlpha = 0;// 垂直分区线透明度,值为0~100

	private int showAlternateVGridColor = 0;// 是否在纵向网格带显示交替的颜色，1为True0为False，默认为False

	private String alternateVGridColor = "";// 纵向网格带交替的颜色，6位十六进制颜色值

	private int alternateVGridAlpha = 0;// 纵向网格带的透明度，值为0~100

	// 数字格式
	private String numberPrefix = "";// 数字前缀

	private String numberSuffix = "";// 数字后缀

	private int formatNumberScale = 1;// 是否格式化数字，1为True0为False，默认为True
	
	private int decimalPrecision = -1;// 指定小数位的位数
	
	private int divLineDecimalPrecision = -1;// 指定分区线的值的小数位的位数，值为0~10

	private int limitDecimalPrecision = -1;// 指定Y轴最大值和最小值的小数位的位数，值为0~10

	private int formatNumber = 1;// 是否用逗号来分隔数字，1为True0为False，默认为True

	private String decimalSeparator = ".";// 指定小数分隔符，默认为"."

	private String thousandSeparator = ",";// 指定千分位分隔符，默认为","

	// Tool-tip/Hover标题
	private int showHoverCap = 1;// 是否显示悬停说明框，1为True0为False，默认为True

	private String hoverCapBgColor = "";// 悬停说明框背景色，6位十六进制颜色值

	private String hoverCapBorderColor = "";// 悬停说明框边框颜色，6位十六进制颜色值

	private String hoverCapSepChar = ",";// 悬停说明框内值与值之间分隔符，默认为","

	// 折线图的参数
	private int lineThickness = -1;// 折线的厚度

	private float anchorRadius = -1;// 折线节点半径

	private int anchorBgAlpha = 0;// 折线节点透明度，值为0~100

	private String anchorBgColor = "";// 折线节点填充颜色，6位十六进制颜色值

	private String anchorBorderColor = "";// 折线节点边框颜色，6位十六进制颜色值

	// 其他参数
	private String dataXMLSavePath = "";// 程序生成的XML文件形式的数据文件存放地址

	private String dataUrl = "";// xml形式的数据文件地址

	private String flashCode = "";// 生成flash字符串
	
	private String dataXmlDirPath = "/framework/flash/fusionCharts/DataXML/";//存放生成的XML文件的文件夹路径
	
	private String flashDirPath = "/framework/flash/fusionCharts/";//图表flash存放路径
	
	public final String global_appname = ApplicationCommon.BASEPATH;

	/**
	 * @return xml形式的数据文件地址
	 */
	public String getDataUrl() {
		return dataUrl;
	}

	/**
	 * @return 生成flash字符串
	 */
	public String getFlashCode() {
		return flashCode;
	}

	/**
	 * @return 程序生成的XML文件形式的数据文件存放地址
	 */
	public String getDataXMLSavePath() {
		return dataXMLSavePath;
	}

	/**
	 * @return 横向网格带透明度
	 */
	public int getAlternateHGridAlpha() {
		return alternateHGridAlpha;
	}

	/**
	 * @return 横向网格带颜色
	 */
	public String getAlternateHGridColor() {
		return alternateHGridColor;
	}

	/**
	 * @return 纵向网格带透明度
	 */
	public int getAlternateVGridAlpha() {
		return alternateVGridAlpha;
	}

	/**
	 * @return 纵向网格带颜色
	 */
	public String getAlternateVGridColor() {
		return alternateVGridColor;
	}

	/**
	 * @return 折线节点透明度
	 */
	public int getAnchorBgAlpha() {
		return anchorBgAlpha;
	}

	/**
	 * @return 折线节点背景色
	 */
	public String getAnchorBgColor() {
		return anchorBgColor;
	}

	/**
	 * @return 折线节点边框颜色
	 */
	public String getAnchorBorderColor() {
		return anchorBorderColor;
	}

	/**
	 * @return 折线节点半径
	 */
	public float getAnchorRadius() {
		return anchorRadius;
	}

	/**
	 * @return 是否以动画形式展示图表
	 */
	public int getAnimation() {
		return animation;
	}

	/**
	 * @return 图表字体样式
	 */
	public String getBaseFont() {
		return baseFont;
	}

	/**
	 * @return 图表字体颜色
	 */
	public String getBaseFontColor() {
		return baseFontColor;
	}

	/**
	 * @return 图表字体大小
	 */
	public int getBaseFontSize() {
		return baseFontSize;
	}

	/**
	 * @return 图表背景色
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * @return 画布透明度
	 */
	public int getCanvasBgAlpha() {
		return canvasBgAlpha;
	}

	/**
	 * @return 画布背景色
	 */
	public String getCanvasBgColor() {
		return canvasBgColor;
	}

	/**
	 * @return 画布边框颜色
	 */
	public String getCanvasBorderColor() {
		return canvasBorderColor;
	}

	/**
	 * @return 画布边框厚度
	 */
	public int getCanvasBorderThickness() {
		return canvasBorderThickness;
	}

	/**
	 * @return 标题
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @return 小数分隔符
	 */
	public String getDecimalSeparator() {
		return decimalSeparator;
	}

	/**
	 * @return 水平分区线透明度
	 */
	public int getDivLineAlpha() {
		return divLineAlpha;
	}

	/**
	 * @return 水平分区线颜色
	 */
	public String getDivLineColor() {
		return divLineColor;
	}

	/**
	 * @return 分区线的值的小数位的位数
	 */
	public int getDivLineDecimalPrecision() {
		return divLineDecimalPrecision;
	}

	/**
	 * @return 水平分区线厚度
	 */
	public int getDivLineThickness() {
		return divLineThickness;
	}

	/**
	 * @return 是否用逗号来分隔数字
	 */
	public int getFormatNumber() {
		return formatNumber;
	}

	/**
	 * @return 数字格式化
	 */
	public int getFormatNumberScale() {
		return formatNumberScale;
	}

	/**
	 * @return 悬停说明框背景色
	 */
	public String getHoverCapBgColor() {
		return hoverCapBgColor;
	}

	/**
	 * @return 悬停说明框边框颜色
	 */
	public String getHoverCapBorderColor() {
		return hoverCapBorderColor;
	}

	/**
	 * @return 停说明框内值与值之间分隔符
	 */
	public String getHoverCapSepChar() {
		return hoverCapSepChar;
	}

	/**
	 * @return Y轴最大值和最小值的小数位的位数
	 */
	public int getLimitDecimalPrecision() {
		return limitDecimalPrecision;
	}

	/**
	 * @return 折线的厚度
	 */
	public int getLineThickness() {
		return lineThickness;
	}

	/**
	 * @return 数字前缀
	 */
	public String getNumberPrefix() {
		return numberPrefix;
	}

	/**
	 * @return 数字后缀
	 */
	public String getNumberSuffix() {
		return numberSuffix;
	}

	/**
	 * @return 画布内部水平分曲线条数
	 */
	public int getNumDivLines() {
		return numDivLines;
	}

	/**
	 * @return 画布内部垂直分曲线条数
	 */
	public int getNumVDivLines() {
		return numVDivLines;
	}

	/**
	 * @return 图表画布以外的字体样式
	 */
	public String getOutCnvBaseFont() {
		return outCnvBaseFont;
	}

	/**
	 * @return 图表画布以外的字体颜色
	 */
	public String getOutCnvBaseFontColor() {
		return outCnvBaseFontColor;
	}

	/**
	 * @return 图表画布以外的字体大小
	 */
	public int getOutCnvBaseFontSize() {
		return outCnvBaseFontSize;
	}

	/**
	 * @return 是否旋转显示标签
	 */
	public int getRotateNames() {
		return rotateNames;
	}

	/**
	 * @return 投影透明度
	 */
	public int getShadowAlpha() {
		return shadowAlpha;
	}

	/**
	 * @return 是否在纵向网格带显示交替的颜色
	 */
	public int getShowAlternateVGridColor() {
		return showAlternateVGridColor;
	}

	/**
	 * @return 是否在纵向网格带显示交替的颜色
	 */
	public int getShowDivLinues() {
		return showDivLinues;
	}

	/**
	 * @return 是否显示悬停说明框
	 */
	public int getShowHoverCap() {
		return showHoverCap;
	}

	/**
	 * @return 是否显示系列名
	 */
	public int getShowLegend() {
		return showLegend;
	}

	/**
	 * @return 是否显示图表限定值
	 */
	public int getShowLimits() {
		return showLimits;
	}

	/**
	 * @return 横向坐标轴标签名
	 */
	public int getShowName() {
		return showName;
	}

	/**
	 * @return 显示对应的数据值
	 */
	public int getShowValues() {
		return showValues;
	}

	/**
	 * @return 横向网格带交替的颜色
	 */
	public String getShowAlternateHGridColor() {
		return showAlternateHGridColor;
	}

	/**
	 * @return 副标题
	 */
	public String getSubCaption() {
		return subCaption;
	}

	/**
	 * @return 千分位分隔符
	 */
	public String getThousandSeparator() {
		return thousandSeparator;
	}

	/**
	 * @return 垂直分区线透明度
	 */
	public int getVDivLineAlpha() {
		return vDivLineAlpha;
	}

	/**
	 * @return 垂直分区线颜色
	 */
	public String getVDivLineColor() {
		return vDivLineColor;
	}

	/**
	 * @return 垂直分区线厚度
	 */
	public int getVDivLineThickness() {
		return vDivLineThickness;
	}

	/**
	 * @return X轴的名称
	 */
	public String getXAxisName() {
		return xAxisName;
	}

	/**
	 * @return y轴的最大值
	 */
	public float getYAxisMaxValue() {
		return yAxisMaxValue;
	}

	/**
	 * @return y轴的最小值
	 */
	public float getYAxisMinValue() {
		return yAxisMinValue;
	}

	/**
	 * @return y轴的名称
	 */
	public String getYAxisName() {
		return yAxisName;
	}

	/**
	 * @param 横向网格透明度
	 *            要设置的 横向网格透明度
	 */
	public void setAlternateHGridAlpha(int alternateHGridAlpha) {
		this.alternateHGridAlpha = alternateHGridAlpha;
	}

	/**
	 * @param 横向网格带交替的颜色
	 *            横向网格带交替的颜色
	 */
	public void setAlternateHGridColor(String alternateHGridColor) {
		this.alternateHGridColor = alternateHGridColor;
	}

	/**
	 * @param 纵向网格透明度
	 *            要设置的 纵向网格透明度
	 */
	public void setAlternateVGridAlpha(int alternateVGridAlpha) {
		this.alternateVGridAlpha = alternateVGridAlpha;
	}

	/**
	 * @param 纵向网格带交替的颜色
	 *            要设置的 纵向网格带交替的颜色
	 */
	public void setAlternateVGridColor(String alternateVGridColor) {
		this.alternateVGridColor = alternateVGridColor;
	}

	/**
	 * @param 折线节点透明度
	 *            要设置的 折线节点透明度
	 */
	public void setAnchorBgAlpha(int anchorBgAlpha) {
		this.anchorBgAlpha = anchorBgAlpha;
	}

	/**
	 * @param 折线节点填充颜色
	 *            要设置的 折线节点填充颜色
	 */
	public void setAnchorBgColor(String anchorBgColor) {
		this.anchorBgColor = anchorBgColor;
	}

	/**
	 * @param 折线节点边框颜色
	 *            要设置的 折线节点边框颜色
	 */
	public void setAnchorBorderColor(String anchorBorderColor) {
		this.anchorBorderColor = anchorBorderColor;
	}

	/**
	 * @param 折线节点半径
	 *            要设置的 折线节点半径
	 */
	public void setAnchorRadius(float anchorRadius) {
		this.anchorRadius = anchorRadius;
	}

	/**
	 * @param 是否动画显示数据
	 *            要设置的 是否动画显示数据
	 */
	public void setAnimation(int animation) {
		this.animation = animation;
	}

	/**
	 * @param 图表字体样式
	 *            要设置的 图表字体样式
	 */
	public void setBaseFont(String baseFont) {
		this.baseFont = baseFont;
	}

	/**
	 * @param 图表字体颜色
	 *            要设置的 图表字体颜色
	 */
	public void setBaseFontColor(String baseFontColor) {
		this.baseFontColor = baseFontColor;
	}

	/**
	 * @param 图表字体大小
	 *            要设置的 图表字体大小
	 */
	public void setBaseFontSize(int baseFontSize) {
		this.baseFontSize = baseFontSize;
	}

	/**
	 * @param 图表背景色
	 *            要设置的 图表背景色
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * @param 画布透明度
	 *            要设置的 画布透明度
	 */
	public void setCanvasBgAlpha(int canvasBgAlpha) {
		this.canvasBgAlpha = canvasBgAlpha;
	}

	/**
	 * @param 画布背景色
	 *            要设置的 画布背景色
	 */
	public void setCanvasBgColor(String canvasBgColor) {
		this.canvasBgColor = canvasBgColor;
	}

	/**
	 * @param 画布边框颜色
	 *            要设置的 画布边框颜色
	 */
	public void setCanvasBorderColor(String canvasBorderColor) {
		this.canvasBorderColor = canvasBorderColor;
	}

	/**
	 * @param 画布边框厚度
	 *            要设置的 画布边框厚度
	 */
	public void setCanvasBorderThickness(int canvasBorderThickness) {
		this.canvasBorderThickness = canvasBorderThickness;
	}

	/**
	 * @param 图表主标题
	 *            要设置的 图表主标题
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @param 小数分隔符
	 *            要设置的 小数分隔符
	 */
	public void setDecimalSeparator(String decimalSeparator) {
		this.decimalSeparator = decimalSeparator;
	}

	/**
	 * @param 水平分区线透明度
	 *            要设置的 水平分区线透明度
	 */
	public void setDivLineAlpha(int divLineAlpha) {
		this.divLineAlpha = divLineAlpha;
	}

	/**
	 * @param 水平分区线颜色
	 *            要设置的 水平分区线颜色
	 */
	public void setDivLineColor(String divLineColor) {
		this.divLineColor = divLineColor;
	}

	/**
	 * @param 水平分区线的值小数位的位数
	 *            要设置的 水平分区线的值小数位的位数
	 */
	public void setDivLineDecimalPrecision(int divLineDecimalPrecision) {
		this.divLineDecimalPrecision = divLineDecimalPrecision;
	}

	/**
	 * @param  水平分区线厚度
	 *            要设置的  水平分区线厚度
	 */
	public void setDivLineThickness(int divLineThickness) {
		this.divLineThickness = divLineThickness;
	}

	/**
	 * @param 逗号来分隔数字
	 *            要设置的 逗号来分隔数字
	 */
	public void setFormatNumber(int formatNumber) {
		this.formatNumber = formatNumber;
	}

	/**
	 * @param 格式化数字
	 *            要设置的 格式化数字
	 */
	public void setFormatNumberScale(int formatNumberScale) {
		this.formatNumberScale = formatNumberScale;
	}

	/**
	 * @param 悬停说明框背景色
	 *            要设置的 悬停说明框背景色
	 */
	public void setHoverCapBgColor(String hoverCapBgColor) {
		this.hoverCapBgColor = hoverCapBgColor;
	}

	/**
	 * @param 悬停说明框边框颜色
	 *            要设置的 悬停说明框边框颜色
	 */
	public void setHoverCapBorderColor(String hoverCapBorderColor) {
		this.hoverCapBorderColor = hoverCapBorderColor;
	}

	/**
	 * @param 悬停说明框内值与值之间分隔符
	 *            要设置的 悬停说明框内值与值之间分隔符
	 */
	public void setHoverCapSepChar(String hoverCapSepChar) {
		this.hoverCapSepChar = hoverCapSepChar;
	}

	/**
	 * @param 指定y轴最大、最小值的小数位的位数
	 *            要设置的 指定y轴最大、最小值的小数位的位数
	 */
	public void setLimitDecimalPrecision(int limitDecimalPrecision) {
		this.limitDecimalPrecision = limitDecimalPrecision;
	}

	/**
	 * @param 折线的厚度
	 *            要设置的 折线的厚度
	 */
	public void setLineThickness(int lineThickness) {
		this.lineThickness = lineThickness;
	}

	/**
	 * @param 数字前缀
	 *            要设置的 数字前缀
	 */
	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}

	/**
	 * @param 数字后缀
	 *            要设置的 数字后缀
	 */
	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}

	/**
	 * @param 画布内部水平分区线条数
	 *            要设置的 画布内部水平分区线条数
	 */
	public void setNumDivLines(int numDivLines) {
		this.numDivLines = numDivLines;
	}

	/**
	 * @param 画布内部垂直分区线条数
	 *            要设置的 画布内部垂直分区线条数
	 */
	public void setNumVDivLines(int numVDivLines) {
		this.numVDivLines = numVDivLines;
	}

	/**
	 * @param 图表画布以外的字体样式
	 *            要设置的 图表画布以外的字体样式
	 */
	public void setOutCnvBaseFont(String outCnvBaseFont) {
		this.outCnvBaseFont = outCnvBaseFont;
	}

	/**
	 * @param 图表画布以外的字体颜色
	 *            要设置的 图表画布以外的字体颜色
	 */
	public void setOutCnvBaseFontColor(String outCnvBaseFontColor) {
		this.outCnvBaseFontColor = outCnvBaseFontColor;
	}

	/**
	 * @param 图表画布以外的字体大小
	 *            要设置的 图表画布以外的字体大小
	 */
	public void setOutCnvBaseFontSize(int outCnvBaseFontSize) {
		this.outCnvBaseFontSize = outCnvBaseFontSize;
	}

	/**
	 * @param 是否旋转显示标签
	 *            要设置的 是否旋转显示标签
	 */
	public void setRotateNames(int rotateNames) {
		this.rotateNames = rotateNames;
	}

	/**
	 * @param 投影透明度
	 *            要设置的 投影透明度
	 */
	public void setShadowAlpha(int shadowAlpha) {
		this.shadowAlpha = shadowAlpha;
	}

	/**
	 * @param 是否在纵向网格带交替的颜色
	 *            要设置的 是否在纵向网格带交替的颜色
	 */
	public void setShowAlternateVGridColor(int showAlternateVGridColor) {
		this.showAlternateVGridColor = showAlternateVGridColor;
	}

	/**
	 * @param 显示Div行的值
	 *            要设置的 显示Div行的值
	 */
	public void setShowDivLinues(int showDivLinues) {
		this.showDivLinues = showDivLinues;
	}

	/**
	 * @param showHoverCap
	 *            要设置的 showHoverCap
	 */
	public void setShowHoverCap(int showHoverCap) {
		this.showHoverCap = showHoverCap;
	}

	/**
	 * @param 是否显示系列名
	 *            要设置的 是否显示系列名
	 */
	public void setShowLegend(int showLegend) {
		this.showLegend = showLegend;
	}

	/**
	 * @param 是否显示图表限值(y轴最大、最小值)
	 *            要设置的 是否显示图表限值(y轴最大、最小值)
	 */
	public void setShowLimits(int showLimits) {
		this.showLimits = showLimits;
	}

	/**
	 * @param 是否显示横向坐标轴(x轴)标签名称
	 *            要设置的 是否显示横向坐标轴(x轴)标签名称
	 */
	public void setShowName(int showName) {
		this.showName = showName;
	}

	/**
	 * @param  是否在图表显示对应的数据值
	 *            要设置的  是否在图表显示对应的数据值
	 */
	public void setShowValues(int showValues) {
		this.showValues = showValues;
	}

	/**
	 * @param  是否在横向网格带交替的颜色
	 *            要设置的  是否在横向网格带交替的颜色
	 */
	public void setShwoAlternateHGridColor(String shwoAlternateHGridColor) {
		this.showAlternateHGridColor = shwoAlternateHGridColor;
	}

	/**
	 * @param 图表副标题
	 *            要设置的 图表副标题
	 */
	public void setSubCaption(String subCaption) {
		this.subCaption = subCaption;
	}

	/**
	 * @param 千分位分隔符
	 *            要设置的 千分位分隔符
	 */
	public void setThousandSeparator(String thousandSeparator) {
		this.thousandSeparator = thousandSeparator;
	}

	/**
	 * @param  水平分区线透明度
	 *            要设置的  水平分区线透明度
	 */
	public void setVDivLineAlpha(int divLineAlpha) {
		vDivLineAlpha = divLineAlpha;
	}

	/**
	 * @param 水平分区线颜色
	 *            要设置的 水平分区线颜色
	 */
	public void setVDivLineColor(String divLineColor) {
		vDivLineColor = divLineColor;
	}

	/**
	 * @param  水平分区线厚度
	 *            要设置的  水平分区线厚度
	 */
	public void setVDivLineThickness(int divLineThickness) {
		vDivLineThickness = divLineThickness;
	}

	/**
	 * @param 横向坐标轴(x轴)名称
	 *            要设置的 横向坐标轴(x轴)名称
	 */
	public void setXAxisName(String axisName) {
		xAxisName = axisName;
	}

	/**
	 * @param 纵轴(y轴)最大值
	 *            要设置的 纵轴(y轴)最大值
	 */
	public void setYAxisMaxValue(float axisMaxValue) {
		yAxisMaxValue = axisMaxValue;
	}

	/**
	 * @param 纵轴(y轴)最小值
	 *            要设置的 纵轴(y轴)最小值
	 */
	public void setYAxisMinValue(float axisMinValue) {
		yAxisMinValue = axisMinValue;
	}

	/**
	 * @param 纵向坐标轴(y轴)名称
	 *            要设置的 纵向坐标轴(y轴)名称
	 */
	public void setYAxisName(String axisName) {
		yAxisName = axisName;
	}

	/**
	 * @param dataXMLSavePath
	 *            要设置的 dataXMLSavePath
	 */
	public void setDataXMLSavePath(String dataXMLSavePath, HttpSession session) {
		dataXMLSavePath = session.getServletContext().getRealPath("/")
				+ "resources/framework/flash/fusionCharts/DataXML/" + dataXMLSavePath;
		String filepath = session.getServletContext().getRealPath("/")
				+ "resources/framework/flash/fusionCharts/DataXML/";
		// 首先删除目录下所有xml文件
		File file = new File(filepath);
		File[] files = file.listFiles();
		if(files!=null){
			for (File f : files) {
				if(!f.getName().substring(f.getName().indexOf("_")+1).startsWith(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()))){
					f.delete();
				}
			}
		}
		// 创建xml文件
		file = new File(dataXMLSavePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.dataXMLSavePath = dataXMLSavePath;
	}

	/**
	 * @param dataUrl
	 *            要设置的 dataUrl
	 */
	public void setDataUrl(String dataUrl, HttpSession session) {
		java.util.Date vDate = new java.util.Date(System.currentTimeMillis());
		java.text.SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		dataUrl += "_" + vSimpleDateFormat.format(vDate)
				+ new Random().nextInt(1000) + ".xml";
		setDataXMLSavePath(dataUrl, session);
		this.dataUrl = dataUrl;
	}

	/**
	 * @param flashCode
	 *            要设置的 flashCode
	 */
	public void setFlashCode(String flashCode) {
		this.flashCode = flashCode;
	}

	/**
	 * @return decimalPrecision
	 */
	public int getDecimalPrecision() {
		return decimalPrecision;
	}

	/**
	 * @param decimalPrecision 要设置的 decimalPrecision
	 */
	public void setDecimalPrecision(int decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}

	/**
	 * @param showAlternateHGridColor 要设置的 showAlternateHGridColor
	 */
	public void setShowAlternateHGridColor(String showAlternateHGridColor) {
		this.showAlternateHGridColor = showAlternateHGridColor;
	}

	/**
	 * @return X轴最大值
	 */
	public float getXAxisMaxValue() {
		return xAxisMaxValue;
	}

	/**
	 * @return X轴最小值
	 */
	public float getXAxisMinValue() {
		return xAxisMinValue;
	}

	/**
	 * @param X轴最大值 要设置的 X轴最大值
	 */
	public void setXAxisMaxValue(float axisMaxValue) {
		xAxisMaxValue = axisMaxValue;
	}

	/**
	 * @param X轴最小值 要设置的 X轴最小值
	 */
	public void setXAxisMinValue(float axisMinValue) {
		xAxisMinValue = axisMinValue;
	}

	public String getDataXmlDirPath() {
		return dataXmlDirPath;
	}

	public void setDataXmlDirPath(String dataXmlDirPath) {
		this.dataXmlDirPath = dataXmlDirPath;
	}

	public String getFlashDirPath() {
		return flashDirPath;
	}

	public void setFlashDirPath(String flashDirPath) {
		this.flashDirPath = flashDirPath;
	}
}
