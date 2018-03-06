package com.jrelax.core.web.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.jrelax.kit.DateKit;

import java.text.SimpleDateFormat;

/**
 * 解决延迟延迟加载属性转换JSON时 Session已关闭的问题
 */
public class JSONObjectMapper extends ObjectMapper{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4352074116272252209L;

	public JSONObjectMapper(){
		//this.registerModule(new JodaModule());
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		hibernate5Module.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
		this.registerModule(hibernate5Module);
		this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		this.enable(SerializationFeature.INDENT_OUTPUT);
		this.setSerializationInclusion(JsonInclude.Include.NON_NULL); //不序列化null
		this.setDateFormat(new SimpleDateFormat(DateKit.YYYY_MM_DD_HH_MM_SS));
	}
}
