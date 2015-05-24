package eap.codegen.layer.ucc;

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
public class UCCModelConverter {
	
	public static UCCModel toUCCJavaModel(Properties env, String modulePath, String moduleName) {
		UCCModel uccModel = new UCCModel();
		uccModel.setModulePath(modulePath);
		uccModel.setDomainName(moduleName);
		uccModel.setPackageName(NameUtil.getUCCPackageName(env, modulePath));
		uccModel.setClassName(NameUtil.getUCCClassName(moduleName));
		
		uccModel.setFilePath(NameUtil.getJavaFilePath(env, uccModel.getPackageName(), uccModel.getClassName()));
		return uccModel;
	}
	
	public static UCCModel toUCCImplJavaModel(Properties env, String modulePath, String moduleName) {
		UCCModel uccModel = new UCCModel();
		uccModel.setModulePath(modulePath);
		uccModel.setDomainName(moduleName);
		uccModel.setPackageName(NameUtil.getUCCImplPackageName(env, modulePath));
		uccModel.setClassName(NameUtil.getUCCImplClassName(moduleName));
		
		uccModel.setFilePath(NameUtil.getJavaFilePath(env, uccModel.getPackageName(), uccModel.getClassName()));
		return uccModel;
	}
}
