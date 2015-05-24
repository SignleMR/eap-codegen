package eap.codegen.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import eap.comps.datamapping.factory.object.template.freemarker.FtlTemplateEngine;
import eap.util.FileUtil;
import eap.util.IoUtil;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * @作者 chiknin@gmail.com
 * @创建时间 
 * @版本 1.00
 * @修改记录
 * <pre>
 * 版本	   修改人		 修改时间		 修改内容描述
 * ----------------------------------------
 * 
 * ----------------------------------------
 * </pre>
 */
public class FileGenerator {
	
	private static FtlTemplateEngine ftlTemplateEngine;
	
	static {
		Properties engineSettings = new Properties();
		engineSettings.setProperty("templateLoaderPath", "conf/template/codegen");
		engineSettings.setProperty("classicCompatible", "true");
		engineSettings.setProperty("default_encoding", "UTF-8");
		engineSettings.setProperty("number_format", "#.#");
		engineSettings.setProperty("time_format", "HH:mm:ss");
		engineSettings.setProperty("date_format", "yyyy-MM-dd");
		engineSettings.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
		engineSettings.setProperty("time_zone", "");
		engineSettings.setProperty("classic_compatible", "");
		engineSettings.setProperty("template_exception_handler", "ignore");
		engineSettings.setProperty("arithmetic_engine", "");
		engineSettings.setProperty("object_wrapper", "beans");
		engineSettings.setProperty("boolean_format", "");
		engineSettings.setProperty("output_encoding", "UTF-8");
		engineSettings.setProperty("url_escaping_charset", "");
		engineSettings.setProperty("strict_bean_models", "");
		
		ftlTemplateEngine = new FtlTemplateEngine();
		ftlTemplateEngine.initEngineSettings(engineSettings);
	}
	
	public static void generate(String dmName, String filePath, Map<String, Object> context) throws Exception {
		if (new File(filePath).exists()) {
			System.out.println("exists file: " + filePath);
			return;
		}
		
		FileUtil.mkdirs(filePath);
		String fileData = (String) ftlTemplateEngine.process(dmName + ".ftl", context, null);
		IoUtil.write(fileData, new FileOutputStream(filePath));
		System.out.println("save file: " + filePath);
	}
	
	public static void main(String[] args) throws Exception {
		FileGenerator.generate("BO.java", "/data/1.java", new HashMap<String, Object>());
	}
}