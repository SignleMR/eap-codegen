package eap.codegen.layer.dao;

import java.util.Properties;

import eap.codegen.model.db.TableModel;
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
public class DAOModelConverter {
	
	public static DAOModel toDAOJavaModel(Properties env, String modulePath, TableModel tableModel) {
		String tableName = tableModel.getName();
		
		DAOModel daoModel = new DAOModel();
		daoModel.setModulePath(modulePath);
		daoModel.setDomainName(NameUtil.getDomainName(tableName));
		daoModel.setPackageName(NameUtil.getDAOPackageName(env, modulePath));
		daoModel.setClassName(NameUtil.getDAOClassName(tableName));
//		List<String> importClassNameList = new ArrayList<String>();
//		importClassNameList.add("java.math.BigDecimal");
//		importClassNameList.add("java.util.Date");
//		importClassNameList.add(NameUtil.getBOFullClassName(env, tableName));
//		daoModel.setImportClassNameList(importClassNameList);
		
//		daoModel.setBoClassName(NameUtil.getBOClassName(tableName));
		
		daoModel.setFilePath(NameUtil.getJavaFilePath(env, daoModel.getPackageName(), daoModel.getClassName()));
		return daoModel;
	}
	
	public static DAOModel toDAOImplJavaModel(Properties env, String modulePath, TableModel tableModel) {
		String tableName = tableModel.getName();
		
		DAOModel daoImplModel = new DAOModel();
		daoImplModel.setModulePath(modulePath);
		daoImplModel.setDomainName(NameUtil.getDomainName(tableName));
		daoImplModel.setPackageName(NameUtil.getDAOImplPackageName(env, modulePath));
		daoImplModel.setClassName(NameUtil.getDAOImplClassName(tableName));
//		List<String> importClassNameList = new ArrayList<String>();
////		importClassNameList.add("java.math.BigDecimal");
////		importClassNameList.add("java.util.Date");
//		importClassNameList.add("org.springframework.stereotype.Repository");
//		importClassNameList.add("eap.comps.orm.mybatis.BaseDAO");
//		importClassNameList.add(NameUtil.getBOFullClassName(env, tableName));
//		importClassNameList.add(NameUtil.getDAOFullClassName(env, modulePath, tableName));
//		daoImplModel.setImportClassNameList(importClassNameList);
		
//		daoImplModel.setBoClassName(NameUtil.getBOClassName(tableName));
		
		daoImplModel.setFilePath(NameUtil.getJavaFilePath(env, daoImplModel.getPackageName(), daoImplModel.getClassName()));
		return daoImplModel;
	}
	
	public static DAOModel toDAOImplXmlModel(Properties env, String modulePath, TableModel tableModel) {
		String tableName = tableModel.getName();
		
		DAOModel daoImplModel = new DAOModel();
		daoImplModel.setModulePath(modulePath);
		daoImplModel.setDomainName(NameUtil.getDomainName(tableName));
		daoImplModel.setPackageName(NameUtil.getDAOImplPackageName(env, modulePath));
//		daoImplModel.setBoClassName(NameUtil.getBOClassName(tableName));
		
		daoImplModel.setFilePath(NameUtil.getXmlFilePath(env, daoImplModel.getPackageName(), NameUtil.getDAOImplClassName(tableName)));
		return daoImplModel;
	}
}