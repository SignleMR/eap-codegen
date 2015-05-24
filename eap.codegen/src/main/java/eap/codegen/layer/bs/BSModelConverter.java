package eap.codegen.layer.bs;

import java.util.Properties;

import eap.codegen.util.NameUtil;

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
public class BSModelConverter {
	public static BSModel toBSJavaModel(Properties env, String modulePath, String moduleName) {
		BSModel bsModel = new BSModel();
		bsModel.setModulePath(modulePath);
		bsModel.setDomainName(moduleName);
		bsModel.setPackageName(NameUtil.getBSPackageName(env, modulePath));
		bsModel.setClassName(NameUtil.getBSClassName(moduleName));
		
		bsModel.setFilePath(NameUtil.getJavaFilePath(env, bsModel.getPackageName(), bsModel.getClassName()));
		return bsModel;
	}
	
	public static BSModel toBSImplJavaModel(Properties env, String modulePath, String moduleName) {
		BSModel bsModel = new BSModel();
		bsModel.setModulePath(modulePath);
		bsModel.setDomainName(moduleName);
		bsModel.setPackageName(NameUtil.getBSImplPackageName(env, modulePath));
		bsModel.setClassName(NameUtil.getBSImplClassName(moduleName));
		
		bsModel.setFilePath(NameUtil.getJavaFilePath(env, bsModel.getPackageName(), bsModel.getClassName()));
		return bsModel;
	}
}
