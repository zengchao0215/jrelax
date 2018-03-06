package com.jrelax.third.fschart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 点阵图(雷达图)
 * @author ZENGCHAO
 *
 */
public class FSCharts_Scatter extends FSCharts_Object {
	private Map<String, String> colors = new LinkedHashMap<String, String>();// 颜色列表
	private Map<String,String> anchorBorderColors = new LinkedHashMap<String, String>();//节点边框颜色
	private Map<String,String> anchorBgColors = new LinkedHashMap<String, String>();//节点背景颜色
	private Map<String,Float> anchorSides = new LinkedHashMap<String, Float>();//边
	private Map<String,Float> anchorRadius = new LinkedHashMap<String, Float>();//节点半径
	private List<String> vTrendlines = new ArrayList<String>();//区域
	private String hTrendlines_startValue="";//起始值(分割线的值)
	private String hTrendlines_displayValue="";//起始值显示名称
	private String hTrendlines_color="";//分割线颜色
	private int hTrendlines_valueOnRight=1;//显示值是否右对齐显示
	
	/**
	 * @param 分割线颜色 要设置的 分隔线颜色
	 */
	public void setHTrendlines_color(String trendlines_color) {
		hTrendlines_color = trendlines_color;
	}
	/**
	 * @param 分割线显示名称 要设置的 分割线显示名称
	 */
	public void setHTrendlines_displayValue(String trendlines_displayValue) {
		hTrendlines_displayValue = trendlines_displayValue;
	}
	/**
	 * @param 起始值(分割线的值) 要设置的 起始值(分割线的值)
	 */
	public void setHTrendlines_startValue(String trendlines_startValue) {
		hTrendlines_startValue = trendlines_startValue;
	}
	/**
	 * @param 分割线显示名称是否右对齐显示(0为true，1为false) 要设置的 分割线显示名称是否右对齐显示(0为true，1为false) 
	 */
	public void setHTrendlines_valueOnRight(int trendlines_valueOnRight) {
		hTrendlines_valueOnRight = trendlines_valueOnRight;
	}
	/**
	 * 设置每个数据集展示时的颜色
	 * 
	 * @param 数据集名称
	 * @param 颜色值(6位十六进制值)
	 */
	public void addColor(String seriesName, String color) {
		this.colors.put(seriesName, color);
	}
	/**
	 * 折线节点背景颜色
	 * @param 数据集名称
	 * @param 折线背景颜色(6位十六进制值)
	 */
	public void addAnchorBgColors(String seriesName,String anchorBgColor_0){
		this.anchorBgColors.put(seriesName, anchorBgColor_0);
	}
	/**
	 * 折线节点边框颜色
	 * @param 数据集名称
	 * @param 折线节点颜色(6位十六进制值)
	 */
	public void addAnchorBorderColors(String seriesName,String anchorBorderColor_0){
		this.anchorBorderColors.put(seriesName, anchorBorderColor_0);
	}
	/**
	 * 节点边
	 * @param 数据集名称
	 * @param 大小
	 */
	public void addAnchorSides(String seriesName,Float anchorSides){
		this.anchorSides.put(seriesName, anchorSides);
	}
	/**
	 * 节点半径
	 * @param 数据集名称
	 * @param 半径
	 */
	public void addAnchorRadius(String seriesName,Float anchorRadius){
		this.anchorRadius.put(seriesName, anchorRadius);
	}
	/**
	 * 设置区域范围、透明度、颜色
	 * @param 起始值
	 * @param 结束值
	 * @param 透明度
	 * @param 颜色
	 */
	public void addVTrendlines(String startValue,String endValue,int alpha,String color){
		String vTrendlines = "startValue='"+startValue+"' endValue='"+endValue+"' alpha='"+alpha+"' color='"+color+"'";
		this.vTrendlines.add(vTrendlines);
	}
	/**
	 * 生成Flash字符串 (快速版)
	 * @param 分类 Map集合 键为X轴显示的值 值为X轴实际值
	 * @param 数据集 键为数据集名称 值为Map集合(键为Y轴值，值为X轴值)
	 * @param 图表标题
	 * @param X轴名称
	 * @param Y轴名称
	 * @param 高度
	 * @param 宽度
	 * @param session
	 * @return
	 */
	public String single_genderCode(Map<String,String> categorys,
			Map<String, Map<String,String>> dataset, String caption_2,String xAxisName_0,String yAxisName_0, int height,
			int width, HttpSession session) {
		gender_dataXml(categorys, dataset, caption_2,xAxisName_0,yAxisName_0, session, 0);// 生成XML文件形式的数据
		String flashCode1 = "<embed id=\"fsCharts_Scatter_0\" width=\""
				+ width
				+ "\" height=\""
				+ height
				+ "\" "
				+ "flashvars=\"chartWidth="
				+ width
				+ "&chartHeight="
				+ height
				+ "&debugMode=0&DOMId=ChartId&registerWithJS=0&scaleMode=noScale&lang=CN&"
				+ "dataURL=" + super.global_appname
				+ super.getDataXmlDirPath() + super.getDataUrl() + "\" "
				+ "allowscriptaccess=\"always\" " + "quality=\"high\" "
				+ "name=\"fsCharts_Scatter_0\" " + "src=\""
				+ super.global_appname + super.getFlashDirPath() + "Scatter.swf\" "
				+ "type=\"application/x-shockwave-flash\" wmode=\"transparent\">";
		super.setFlashCode(flashCode1);
		return super.getFlashCode();
	}

	/**
	 * 生成Flash字符串 (快速版)
	 * @param 分类 Map集合 键为X轴显示的值 值为X轴实际值
	 * @param 数据集 键为数据集名称 值为Map集合(键为Y轴值，值为X轴值)
	 * @param 图表标题
	 * @param X轴名称
	 * @param Y轴名称
	 * @param 高度
	 * @param 宽度
	 * @param session
	 * @return
	 */
	public String senior_genderCode(Map<String,String> categorys,
			Map<String, Map<String,String>> dataset, String caption_2,String xAxisName_0,String yAxisName_0, int height,
			int width, HttpSession session) {
		gender_dataXml(categorys, dataset, caption_2,xAxisName_0,yAxisName_0, session, 1);// 生成XML文件形式的数据
		String flashCode1 = "<embed id=\"fsCharts_Scatter_0\" width=\""
				+ width
				+ "\" height=\""
				+ height
				+ "\" "
				+ "flashvars=\"chartWidth="
				+ width
				+ "&chartHeight="
				+ height
				+ "&debugMode=0&DOMId=ChartId&registerWithJS=0&scaleMode=noScale&lang=CN&"
				+ "dataURL=" + super.global_appname
				+ super.getDataXmlDirPath() + super.getDataUrl() + "\" "
				+ "allowscriptaccess=\"always\" " + "quality=\"high\" "
				+ "name=\"fsCharts_Scatter_0\" " + "src=\""
				+ super.global_appname + super.getFlashDirPath() + "Scatter.swf\" "
				+ "type=\"application/x-shockwave-flash\" wmode=\"transparent\">";
		super.setFlashCode(flashCode1);
		return super.getFlashCode();
	}

	/**
	 * 生成xml文件
	 * 
	 * @param 数据
	 * @param 标题
	 * @param X轴名称
	 * @param Y轴名称
	 * @param session
	 * @param 类型
	 *            0为快速版，1为高级版
	 */
	private void gender_dataXml(Map<String,String> categorys,
			Map<String,Map<String,String>> dataset, String caption_2,String xAxisName_0,String yAxisName_0,
			HttpSession session, int type) {
		super.setDataUrl("Scatter", session);
		String filepath = super.getDataXMLSavePath();
		File file = new File(filepath);
		if (file.exists()) {
			String code = "";
			switch (type) {
			case 0:// 快速版
				code = "<chart caption='"
						+ caption_2
						+ "' yAxisName='"+yAxisName_0+"' xAxisName='"+xAxisName_0+"'>\n";
				break;
			case 1:// 高级版
				code = "<chart ";
				// 功能特性设置
				code += " animation = '" + super.getAnimation() + "' ";
				code += " showNames = '" + super.getShowName() + "' ";
				code += " rotateNames = '" + super.getRotateNames() + "' ";
				code += " showValues = '" + super.getShowValues() + "' ";
				if(super.getXAxisMinValue()>-1)
					code +=" xAxisMinValue = '"+super.getXAxisMinValue()+"'";
				if(super.getXAxisMaxValue()>-1)
					code += " xAxisMaxValue = '"+super.getXAxisMaxValue()+"'";
				if (super.getYAxisMinValue() > -1)
					code += " yAxisMinValue = '" + super.getYAxisMinValue()
							+ "' ";
				if (super.getYAxisMaxValue() > -1)
					code += " yAxisMaxValue = '" + super.getYAxisMaxValue()
							+ "' ";
				code += " showLimits = '" + super.getShowLimits() + "' ";
				// 图表标题和轴名称
				code += " caption = '"
						+ (super.getCaption().length() > 0 ? super.getCaption()
								: caption_2) + "' ";
				if (super.getSubCaption().trim().length() > 0)
					code += " subCaption = '" + super.getSubCaption() + "' ";
				if (super.getXAxisName().trim().length() > 0)
					code += " xAxisName = '" + super.getXAxisName().trim()
							+ "' ";
				if (super.getYAxisName().trim().length() > 0)
					code += " yAxisName = '" + super.getYAxisName().trim()
							+ "' ";
				if (super.getBgColor().trim().length() > 0)
					code += " bgColor = '" + super.getBgColor() + "' ";
				if (super.getCanvasBgColor().trim().length() > 0)
					code += " canvasBgColor = '" + super.getCanvasBgColor()
							+ "' ";
				code += " canvasBgAlpha = '" + super.getCanvasBgAlpha() + "' ";
				if (super.getCanvasBorderColor().trim().length() > 0)
					code += " canvasBorderColor = '"
							+ super.getCanvasBorderColor() + "' ";
				if (super.getCanvasBorderThickness() > 0)
					code += " canvasBorderThickness = '"
							+ super.getCanvasBorderThickness() + "' ";
				code += " shadowAlpha = '" + super.getShadowAlpha() + "' ";
				// 字体属性
				if (super.getBaseFont().trim().length() > 0)
					code += " baseFont = '" + super.getBaseFont() + "' ";
				if (super.getBaseFontSize() > 0)
					code += " baseFontSize = '" + super.getBaseFontSize()
							+ "' ";
				if (super.getBaseFontColor().trim().length() > 0)
					code += " baseFontColor = '" + super.getBaseFontColor()
							+ "' ";
				if (super.getOutCnvBaseFont().trim().length() > 0)
					code += " outCnvBaseFont = '" + super.getOutCnvBaseFont()
							+ "' ";
				if (super.getOutCnvBaseFontSize() > -1)
					code += " outCnvBaseFontSize = '"
							+ super.getOutCnvBaseFontSize() + "' ";
				if (super.getOutCnvBaseFontColor().trim().length() > 0)
					code += " outCnvBaseFontColor = '"
							+ super.getOutCnvBaseFontColor() + "' ";
				// 分区线和网格
				if (super.getNumDivLines() > -1)
					code += " numDivLines = '" + super.getNumDivLines() + "' ";
				if (super.getDivLineColor().trim().length() > 0)
					code += " divLineColor = '" + super.getDivLineColor()
							+ "' ";
				if (super.getDivLineThickness() > -1)
					code += " divLineThickness = '"
							+ super.getDivLineThickness() + "' ";
				if (super.getDivLineAlpha() > 0)
					code += " divLineAlpha = '" + super.getDivLineAlpha()
							+ "' ";
				if (super.getShowAlternateHGridColor().trim().length() > 0)
					code += " showAlternateHGridColor = '"
							+ super.getShowAlternateHGridColor() + "' ";
				if (super.getAlternateHGridColor().trim().length() > 0)
					code += " alternateHGridColor = '"
							+ super.getAlternateHGridColor() + "' ";
				if (super.getAlternateHGridAlpha() > 0)
					code += " alternateHGridAlpha = '"
							+ super.getAlternateHGridAlpha() + "' ";
				if (super.getShowDivLinues() > 0)
					code += " showDivLinues = '" + super.getShowDivLinues()
							+ "' ";
				if (super.getNumVDivLines() > -1)
					code += " numVDivLines = '" + super.getNumVDivLines()
							+ "' ";
				if (super.getVDivLineColor().trim().length() > 0)
					code += " vDivLineColor = '" + super.getVDivLineColor()
							+ "' ";
				if (super.getVDivLineThickness() > -1)
					code += " vDivLineThickness = '"
							+ super.getVDivLineThickness() + "' ";
				if (super.getVDivLineAlpha() > 0)
					code += " vDivLineAlpha = '" + super.getVDivLineAlpha()
							+ "' ";
				code += " showAlternateVGridColor = '"
						+ super.getShowAlternateVGridColor() + "' ";
				if (super.getAlternateVGridColor().trim().length() > 0)
					code += " alternateVGridColor = '"
							+ super.getAlternateVGridColor() + "' ";
				if (super.getAlternateVGridAlpha() > 0)
					code += " alternateVGridAlpha = '"
							+ super.getAlternateVGridAlpha() + "' ";
				// 数字格式
				if (super.getNumberPrefix().trim().length() > 0)
					code += " numberPrefix = '" + super.getNumberPrefix()
							+ "' ";
				if (super.getNumberSuffix().trim().length() > 0)
					code += " numberSuffix = '" + super.getNumberSuffix()
							+ "' ";
				code += " formatNumberScale = '" + super.getFormatNumberScale()
						+ "' ";
				if (super.getDecimalPrecision() > -1)
					code += " decimalPrecision = '"
							+ super.getDecimalPrecision() + "' ";
				if (super.getDivLineDecimalPrecision() > -1)
					code += " divLineDecimalPrecision = '"
							+ super.getDivLineDecimalPrecision() + "' ";
				if (super.getLimitDecimalPrecision() > -1)
					code += " limitsDecimalPrecision = '"
							+ super.getLimitDecimalPrecision() + "' ";
				code += " formatNumber = '" + super.getFormatNumber() + "' ";
				if (super.getDecimalSeparator().trim().length() > 0)
					code += " decimalSeparator = '"
							+ super.getDecimalSeparator() + "' ";
				if (super.getThousandSeparator().trim().length() > 0)
					code += " thousandSeparator = '"
							+ super.getThousandSeparator() + "' ";
				// Tool-tip/hover标题
				code += " showHoverCap = '" + super.getShowHoverCap() + "' ";
				if (super.getHoverCapBgColor().trim().length() > 0)
					code += " hoverCapBgColor = '" + super.getHoverCapBgColor()
							+ "' ";
				if (super.getHoverCapBorderColor().trim().length() > 0)
					code += " hoverCapBorderColor = '"
							+ super.getHoverCapBorderColor() + "' ";
				code += " hoverCapSepChar = '" + super.getHoverCapSepChar()
						+ "' ";
				// 折线参数
				if (super.getLineThickness() > -1)
					code += " lineThickness = '" + super.getLineThickness()
							+ "' ";
				if (super.getAnchorRadius() > -1)
					code += " anchorRadius = '" + super.getAnchorRadius()
							+ "' ";
				if (super.getAnchorBgAlpha() > -1)
					code += " anchorRadius = '" + super.getAnchorRadius()
							+ "' ";
				if (super.getAnchorBgColor().trim().length() > 0)
					code += " anchorBgColor = '" + super.getAnchorBgColor()
							+ "' ";
				if (super.getAnchorBorderColor().trim().length() > 0)
					code += " anchorBorderColor = '"
							+ super.getAnchorBorderColor() + "' ";
				break;
			}
			code += ">\n";
			// 分类
			code += "<categories verticalLineColor='666666' verticalLineThickness='1'>\n";
			for (String label : categorys.keySet()) {
				code += "\t<category label='" + label + "' x='"+categorys.get(label)+"' showVerticalLine='1'/>\n";
			}
			code += "</categories>\n";
			// 数据集
			for (String key : dataset.keySet()) {
				code += "<dataset seriesName='"
						+ key
						+ "' color='"
						+ (this.colors.get(key)!=null && this.colors.get(key).trim().length()>0 ? this.colors
								.get(key).trim()
								: "F1683C") + "' anchorBorderColor='"+(this.anchorBorderColors.get(key)!=null && this.anchorBorderColors.get(key).trim().length() > 0 ? this.anchorBorderColors
										.get(key).trim()
										: "F1683C")+"' anchorBgColor='"+(this.anchorBgColors.get(key)!=null && this.anchorBgColors.get(key).trim().length() > 0 ? this.anchorBgColors
												.get(key).trim()
												: "F1683C")+"' anchorSides='"+(this.anchorSides.get(key)!=null && this.anchorSides.get(key)>0?this.anchorSides.get(key):3)+"' " +
														"anchorRadius='"+(this.anchorRadius.get(key)!=null && this.anchorRadius.get(key)>0?this.anchorRadius.get(key):4)+"'>\n";
				for (String y : dataset.get(key).keySet()) {
					code += "\t<set y='" + y + "' x='"+dataset.get(key).get(y)+"' />\n";
				}
				code += "</dataset>\n";
			}
			code+="<vTrendlines>\n";
			for(String vTrendline:vTrendlines){
				code+="\t<line "+vTrendline+" />\n";
			}
			code+="</vTrendlines>\n";
			code+="<hTrendlines>\n";
			code+="\t<line startValue='"+this.hTrendlines_startValue+"' displayValue='"+this.hTrendlines_displayValue+"' lineThickness='2' color='"+this.hTrendlines_color+"' valueOnRight='"+this.hTrendlines_valueOnRight+"' dashed='1' dashGap='5' />\n";
			code+="</hTrendlines>\n";
			code += "</chart>";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				Writer out = new OutputStreamWriter(fos);
				out.write(code);// 讲数据写入xml文件中
				out.close();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
