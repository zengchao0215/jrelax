package com.jrelax.third.fschart;

public class TrendLine {
	private float startValue = 0;
	private String color = "";
	private String displayValue = "";
	/**
	 * @return 颜色
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @return 描述
	 */
	public String getDisplayValue() {
		return displayValue;
	}
	/**
	 * @return 起始值
	 */
	public float getStartValue() {
		return startValue;
	}
	/**
	 * @param 颜色 要设置的 颜色
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @param 描述 要设置的 描述
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	/**
	 * @param 起始值 要设置的 起始值
	 */
	public void setStartValue(float startValue) {
		this.startValue = startValue;
	}
	
}
