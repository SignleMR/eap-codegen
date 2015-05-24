package eap.codegen.util;

import java.util.Properties;

import eap.util.StringUtil;

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
public class NameUtil {
	
	public static String getJavaFilePath(Properties env, String packageName, String className) {
		return env.getProperty("codegen.rootDir")  + "/" + packageName.replaceAll("\\.", "\\/") + "/" + className + ".java";
	}
	public static String getXmlFilePath(Properties env, String packageName, String fileName) {
		return env.getProperty("codegen.rootDir")  + "/" + packageName.replaceAll("\\.", "\\/") + "/" + fileName + ".xml";
	}
	
	public static String getDomainName(String tableName) {
		return StringUtil.capitalize(StringUtil.mapUnderscoreToCamelCase(tableName.replaceFirst("[tT]_", "")));
	}
	
	/* BO */
	public static String getBOPackageName(Properties env) {
		return env.getProperty("codegen.basePackage") + ".model.bo"; // ".common.model.bo";
	}
	public static String getBOClassName(String tableName) {
		return getDomainName(tableName) + "BO";
	}
	public static String getBOFullClassName(Properties env, String tableName) {
		return getBOPackageName(env) + "." + getBOClassName(tableName);
	}
	
	/* VO */
	public static String getVOPackageName(Properties env) {
		return env.getProperty("codegen.basePackage") + ".model.vo"; // ".common.model.vo";
	}
	public static String getVOClassName(String tableName) {
		return getDomainName(tableName) + "VO";
	}
	
	/* DAO */
	public static String getDAOPackageName(Properties env, String modulePath) {
		if (env.containsKey("codegen.daoPackage")) {
			if (StringUtil.isNotBlank(modulePath)) {
				return env.getProperty("codegen.daoPackage") + "." + modulePath;
			} else {
				return env.getProperty("codegen.daoPackage");
			}
		} else if (StringUtil.isNotBlank(modulePath)) {
			return env.getProperty("codegen.basePackage") + "." + modulePath + ".dao";
		} else {
			return env.getProperty("codegen.basePackage") + ".dao";
		}
	}
	public static String getDAOClassName(String tableName) {
		return "I" + NameUtil.getDomainName(tableName) + "DAO";
	}
	public static String getDAOFullClassName(Properties env, String modulePath, String tableName) {
		return getDAOPackageName(env, modulePath) + "." + getDAOClassName(tableName);
	}
	
	/* DAOImpl */
	public static String getDAOImplPackageName(Properties env, String modulePath) {
		if (env.containsKey("codegen.daoPackage")) {
			if (StringUtil.isNotBlank(modulePath)) {
				return env.getProperty("codegen.daoPackage") + "." + modulePath + ".impl";
			} else {
				return env.getProperty("codegen.daoPackage");
			}
		} else if (StringUtil.isNotBlank(modulePath)) {
			return env.getProperty("codegen.basePackage") + "." + modulePath + ".dao.impl";
		} else {
			return env.getProperty("codegen.basePackage") + ".dao.impl";
		}
	}
	public static String getDAOImplClassName(String tableName) {
		return getDomainName(tableName) + "DAOImpl";
	}
	public static String getDAOImplFullClassName(Properties env, String modulePath, String tableName) {
		return getDAOImplPackageName(env, modulePath) + "." + getDAOImplClassName(tableName);
	}
	
	/* BS */
	public static String getBSPackageName(Properties env, String modulePath) {
		return env.getProperty("codegen.basePackage") + "." + modulePath + ".bs";
	}
	public static String getBSClassName(String moduleName) {
		return "I" + moduleName + "BS";
	}
	public static String getBSFullClassName(Properties env, String modulePath, String moduleName) {
		return getBSPackageName(env, modulePath) + "." + getBSClassName(moduleName);
	}
	
	/* BSImpl */
	public static String getBSImplPackageName(Properties env, String modulePath) {
		return env.getProperty("codegen.basePackage") + "." + modulePath + ".bs.impl";
	}
	public static String getBSImplClassName(String moduleName) {
		return moduleName + "BSImpl";
	}
	public static String getBSImplFullClassName(Properties env, String modulePath, String moduleName) {
		return getBSImplPackageName(env, modulePath) + "." + getBSImplClassName(moduleName);
	}
	
	/* UCC */
	public static String getUCCPackageName(Properties env, String modulePath) {
		return env.getProperty("codegen.basePackage") + "." + modulePath + ".ucc";
	}
	public static String getUCCClassName(String moduleName) {
		return "I" + moduleName + "UCC";
	}
	public static String getUCCFullClassName(Properties env, String modulePath, String moduleName) {
		return getUCCPackageName(env, modulePath) + "." + getUCCClassName(moduleName);
	}
	
	/* UCCImpl */
	public static String getUCCImplPackageName(Properties env, String modulePath) {
		return env.getProperty("codegen.basePackage") + "." + modulePath + ".ucc.impl";
	}
	public static String getUCCImplClassName(String moduleName) {
		return moduleName + "UCCImpl";
	}
	public static String getUCCImplFullClassName(Properties env, String modulePath, String moduleName) {
		return getUCCImplPackageName(env, modulePath) + "." + getUCCImplClassName(moduleName);
	}
	
//	public static String getModule(String modulePath) {
//		int i = modulePath.lastIndexOf(".");
//		if (i > 0) {
//			return modulePath.substring(i + 1);
//		}
//		return modulePath;
//	}
}