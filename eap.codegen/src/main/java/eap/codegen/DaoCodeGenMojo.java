package eap.codegen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.alibaba.druid.pool.DruidDataSource;

import eap.codegen.datacollect.IDataCollector;
import eap.codegen.layer.bo.BOModel;
import eap.codegen.layer.bo.BOModelConverter;
import eap.codegen.layer.dao.DAOModel;
import eap.codegen.layer.dao.DAOModelConverter;
import eap.codegen.model.db.TableModel;
import eap.codegen.util.FileGenerator;
import eap.codegen.util.NameUtil;
import eap.util.BeanUtil;
import eap.util.FileUtil;
import eap.util.StringUtil;

/** 
 * @goal dao 
 */  
public class DaoCodeGenMojo extends AbstractCodeGenMojo {
	
	/** 
	 * @parameter property="dataSourceId" default-value="ds1"
	 */
	private String dataSourceId;
	
	/** 
	 * @parameter property="clean" default-value=false
	 */
	private boolean clean;
	
	/**
	 * @parameter property="tables"
	 */
	private String daoTables;
	
	/**
	 * @parameter property="path"
	 */
	private String modulePath;
	
	@Override
	public void executeImpl(Properties env) throws MojoExecutionException, MojoFailureException {
		getLog().info("execute dao codegen...");
		
		if (StringUtil.isNotBlank(modulePath) && StringUtil.isBlank(daoTables)) {
			throw new MojoExecutionException("'tables' must not be empty");
		}
		
		String rootDir = basedir + "/codegen-output";
		String basePackage = env.getProperty("app.basePackage", "app.basePackage_IS_UNDEFINED");
		
		env.setProperty("codegen.rootDir", rootDir);
		env.setProperty("codegen.basePackage", basePackage);
		if (StringUtil.isNotBlank(modulePath)) {
			env.setProperty("codegen.daoPackage", basePackage + ".dao");
		}
		
		if (clean) {
			try {
				String cleanDir = rootDir+ "/" + basePackage.replaceAll("\\.", "\\/") + "/dao";
				FileUtil.deleteDirectory(new File(cleanDir));
				getLog().info("clean dir: " + cleanDir);
			} catch (Exception e) {
				getLog().debug(e.getMessage(), e);
			}
		}
		
		DruidDataSource dataSource = null;
		try {
			dataSource = getDataSource(env, dataSourceId);
			IDataCollector dataCollector = getDataCollector(dataSource);
			
			List<String> tableList = new ArrayList<String>();
			if (StringUtil.isBlank(daoTables)) {
				for (TableModel tableModel : dataCollector.getDatabaseModel().getTableList()) {
					tableList.add(tableModel.getName());
				}
			} else {
				tableList = Arrays.asList(StringUtil.split(daoTables, ","));
			}
			
			for (String table : tableList) {
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
			}
		} catch (Exception e) {
//			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		} finally {
			if (dataSource != null) {
				dataSource.close();
			}
		}
	}
}