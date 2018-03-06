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
 * 多系列图表类型 - 3D柱状图
 * 
 * @author ZENGCHAO
 * 
 */
public class FSCharts_MS_3DColumn extends FSCharts_Object {
	private Map<String,String> colors = new LinkedHashMap<String, String>();//颜色列表
	
	/**
	 * 设置每个数据集展示时的颜色
	 * @param 数据集名称
	 * @param 颜色值(6位十六进制值)
	 */
	public void addColor(String seriesName,String color){
		this.colors.put(seriesName, color);
	}
	private List<TrendLine> trendlines = new ArrayList<TrendLine>();
	public void addTrendLines(float startValue,String color,String displayValue){
		TrendLine t = new TrendLine();
		t.setStartValue(startValue);
		t.setColor(color);
		t.setDisplayValue(displayValue);
		trendlines.add(t);
	}
	/**
	 * 生成Flash地址（快速版，使用默认设置，可以设置的参数很少）
	 * 
	 * @param 数据
	 * @param 标题
	 * @param x轴名称
	 * @param y轴名称
	 * @param 高度
	 * @param 宽度
	 * @param session
	 * @return
	 */
	public String single_genderCode(List<String> categorys,
			Map<String, List<String>> dataset, String caption_2, int height,
			int width, HttpSession session) {
		gender_dataXml(categorys, dataset, caption_2, session, 0);// 生成XML文件形式的数据
		String flashCode1 = "<embed id=\"fsCharts_MS_3DColumn_0\" width=\""
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
				+ "name=\"fsCharts_MS_3DColumn_0\" " + "src=\""
				+ super.global_appname
				+ super.getFlashDirPath() + "MSColumn3D.swf\" "
				+ "type=\"application/x-shockwave-flash\" wmode=\"transparent\">";
		super.setFlashCode(flashCode1);
		return super.getFlashCode();
	}

	/**
	 * 生成Flash地址（高级版，使用用户自定义配置，可以设置的参数很丰富）
	 * 
	 * @param 数据
	 * @param 标题
	 * @param x轴名称
	 * @param y轴名称
	 * @param 高度
	 * @param 宽度
	 * @param session
	 * @return
	 */
	public String senior_genderCode(List<String> categorys,
			Map<String, List<String>> dataset, String caption_2, int height,
			int width, HttpSession session) {
		gender_dataXml(categorys, dataset, caption_2, session, 1);// 生成XML文件形式的数据
		String flashCode1 = "<embed id=\"fsCharts_MS_3DColumn_0\" width=\""
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
				+ "name=\"fsCharts_MS_3DColumn_0\" " + "src=\""
				+ super.global_appname
				+ super.getFlashDirPath() + "MSColumn3D.swf\" "
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
	private void gender_dataXml(List<String> categorys,
			Map<String, List<String>> dataset, String caption_2,
			HttpSession session, int type) {
		super.setDataUrl("MS_3DColumn", session);
		String filepath = super.getDataXMLSavePath();
		File file = new File(filepath);
		if (file.exists()) {
			String code = "";
			switch (type) {
			case 0:// 快速版
				code = "<chart caption='"
						+ caption_2
						+ "' showValues='1' decimals='1' formatNumberScale='0'>\n";
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
			code += "<categories>\n";
			for (String cate : categorys) {
				code += "\t<category label='" + cate + "' />\n";
			}
			code += "</categories>\n";
			// 数据集
			for (String key : dataset.keySet()) {
				code += "<dataset seriesName='" + key
						+ "' color='"+(this.colors.get(key)!=null && this.colors.get(key).trim().length()>0?this.colors.get(key).trim():"AFD8F8")+"' showValues='0'>\n";
				for (String value : dataset.get(key)) {
					code += "\t<set value='" + value + "' />\n";
				}
				code += "</dataset>\n";
			}
			if(this.trendlines!=null && this.trendlines.size()>0){
				code +="<trendlines>";
				for(TrendLine t : trendlines){
					code += "<line startValue='"+t.getStartValue()+"' color='"+t.getColor()+"' displayValue='"+t.getDisplayValue()+"' showOnTop='1'/>";
				}
				code+="</trendlines>";
			}
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
