package com.haska.zkdemo;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;

public class JsonUtil {
	private static ObjectMapper om = new ObjectMapper();
	
	static
	{
		om.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		om.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
	}

	public static String encodeAsString(Object obj)throws IOException, JsonGenerationException, JsonMappingException
	{		
	    return om.writeValueAsString(obj);
	}
    
}
