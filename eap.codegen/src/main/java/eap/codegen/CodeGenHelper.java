package eap.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eap.codegen.datacollect.IDataCollector;
import eap.codegen.layer.bo.BOModel;
import eap.codegen.layer.bo.BOModelConverter;
import eap.codegen.layer.bs.BSModel;
import eap.codegen.layer.bs.BSModelConverter;
import eap.codegen.layer.dao.DAOModel;
import eap.codegen.layer.dao.DAOModelConverter;
import eap.codegen.layer.ucc.UCCModel;
import eap.codegen.layer.ucc.UCCModelConverter;
import eap.codegen.model.db.DatabaseModel;
import eap.codegen.model.db.TableModel;
import eap.codegen.util.FileGenerator;
import eap.codegen.util.NameUtil;
import eap.util.BeanUtil;
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
public class CodeGenHelper {
	
	public static void genBO(Properties env, IDataCollector dataCollector) throws Exception {
		DatabaseModel databaseModel = dataCollector.getDatabaseModel();
		for (TableModel tableModel : databaseModel.getTableList()) {
			genBO(env, tableModel);
		}
	}
	
	public static void genBO(Properties env, TableModel tableModel) throws Exception {
		BOModel boModel = BOModelConverter.toBOJavaModel(env, tableModel);
		Map<String, Object> boModelContext  = BeanUtil.toMap(boModel);
		boModelContext.put("_env", env);
		boModelContext.put("_tableModel", tableModel);
		FileGenerator.generate("BO.java", boModel.getFilePath(), boModelContext);
		
		BOModel voModel = BOModelConverter.toVOJavaModel(env, tableModel);
		Map<String, Object> voModelContext  = BeanUtil.toMap(voModel);
		voModelContext.put("_env", env);
		voModelContext.put("_tableModel", tableModel);
		voModelContext.put("_boModel", boModel);
		FileGenerator.generate("VO.java", voModel.getFilePath(), voModelContext);
	}
	
	public static void genModule(Properties env, IDataCollector dataCollector, boolean genUCC, boolean genBS, boolean genDAO) throws Exception {
		String[] modules = StringUtil.split(env.getProperty("codegen.modules"), ",");
		if (modules != null && modules.length > 0) {
			for (String modulePath : modules) {
				String modulePrefix = "codegen.module." + modulePath;
				String moduleName = env.getProperty(modulePrefix + ".moduleName");
				String[] tables = StringUtil.split(env.getProperty(modulePrefix + ".tables"), ",");
				
				List<DAOModel> daoModelList = new ArrayList<DAOModel>();
				if (genDAO) {
					if (tables != null && tables.length > 0) {
						for (String table : tables) {
							TableModel tableModel = dataCollector.getTableModel(table);
							BOModel boModel = BOModelConverter.toBOJavaModel(env, tableModel);
							
							DAOModel daoModel = DAOModelConverter.toDAOJavaModel(env, modulePath, tableModel);
							Map<String, Object> daoModelContext  = BeanUtil.toMap(daoModel);
							daoModelContext.put("_env", env);
							daoModelContext.put("_tableModel", tableModel);
							daoModelContext.put("_boModel", boModel);
							FileGenerator.generate("DAO.java", daoModel.getFilePath(), daoModelContext);
							
							DAOModel daoImplModel = DAOModelConverter.toDAOImplJavaModel(env, modulePath, tableModel);
							Map<String, Object> daoImplModelContext  = BeanUtil.toMap(daoImplModel);
							daoImplModelContext.put("_env", env);
							daoImplModelContext.put("_tableModel", tableModel);
							daoImplModelContext.put("_boModel", boModel);
							daoImplModelContext.put("_daoModel", daoModel);
							FileGenerator.generate("DAOImpl.java", daoImplModel.getFilePath(), daoImplModelContext);
							
							DAOModel daoImplXmlModel = DAOModelConverter.toDAOImplXmlModel(env, modulePath, tableModel);
							Map<String, Object> daoImplXmlModelContext  = BeanUtil.toMap(daoImplXmlModel);
							daoImplXmlModelContext.put("_env", env);
							daoImplXmlModelContext.put("_tableModel", tableModel);
							daoImplXmlModelContext.put("_boModel", boModel);
							daoImplXmlModelContext.put("daoClassName", NameUtil.getDAOClassName(table));
							FileGenerator.generate("DAOImpl.xml", daoImplXmlModel.getFilePath(), daoImplXmlModelContext);
							
							daoModelList.add(daoModel);
						}
					}
				}
				
				List<BSModel> bsModelList = new ArrayList<BSModel>();
				if (genBS) {
					BSModel bsModel = BSModelConverter.toBSJavaModel(env, modulePath, moduleName);
					Map<String, Object> bsModelContext  = BeanUtil.toMap(bsModel);
					bsModelContext.put("_env", env);
					FileGenerator.generate("BS.java", bsModel.getFilePath(), bsModelContext);
					
					BSModel bsImplModel = BSModelConverter.toBSImplJavaModel(env, modulePath, moduleName);
					Map<String, Object> bsImplModelContext  = BeanUtil.toMap(bsImplModel);
					bsImplModelContext.put("_env", env);
					bsImplModelContext.put("_bsModel", bsModel);
					bsImplModelContext.put("_daoModelList", daoModelList);
					FileGenerator.generate("BSImpl.java", bsImplModel.getFilePath(), bsImplModelContext);
					
					bsModelList.add(bsModel);
				}
				
				
				if (genUCC) {
					UCCModel uccModel = UCCModelConverter.toUCCJavaModel(env, modulePath, moduleName);
					Map<String, Object> uccModelContext  = BeanUtil.toMap(uccModel);
					uccModelContext.put("_env", env);
					FileGenerator.generate("UCC.java", uccModel.getFilePath(), uccModelContext);
					
					UCCModel uccImplModel = UCCModelConverter.toUCCImplJavaModel(env, modulePath, moduleName);
					Map<String, Object> uccImplModelContext  = BeanUtil.toMap(uccImplModel);
					uccImplModelContext.put("_env", env);
					uccImplModelContext.put("_uccModel", uccModel);
					uccImplModelContext.put("_bsModelList", bsModelList);
					FileGenerator.generate("UCCImpl.java", uccImplModel.getFilePath(), uccImplModelContext);
				}
			}
		}
	}
}