package eap.codegen;

import java.io.File;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.alibaba.druid.pool.DruidDataSource;

import eap.codegen.datacollect.IDataCollector;
import eap.codegen.model.db.TableModel;
import eap.util.FileUtil;
import eap.util.StringUtil;

/** 
 * @goal module 
 */  
public class ModuleCodeGenMojo extends AbstractCodeGenMojo {
	
	/** 
	 * @parameter property="dataSourceId" default-value="ds1"
	 */
	private String dataSourceId;
	
	/** 
	 * @parameter property="clean" default-value=false
	 */
	private boolean clean;
	
	/** 
	 * @parameter property="genModel" default-value=true
	 */
	private boolean genModel;
	
	/**
	 * @parameter property="path"
	 * @required
	 */
	private String modulePath;
	
	/**
	 * @parameter property="name"
	 * @required
	 */
	private String moduleName;
	
	/**
	 * @parameter property="tables"
	 */
	private String moduleTables;
	
	/** 
	 * @parameter property="enableJSR303" default-value=false
	 */
	private boolean enableJSR303;
	
	/** 
	 * @parameter property="genUCC" default-value=true
	 */
	private boolean genUCC;
	
	/** 
	 * @parameter property="genDAO" default-value=true
	 */
	private boolean genDAO;
	
	@Override
	public void executeImpl(Properties env) throws MojoExecutionException, MojoFailureException {
		getLog().info("execute module codegen...");
		
		String rootDir = basedir + "/codegen-output";
		String basePackage = env.getProperty("app.basePackage", "app.basePackage_IS_UNDEFINED");
		
		env.setProperty("codegen.rootDir", rootDir);
		env.setProperty("codegen.basePackage", basePackage);
		env.setProperty("codegen.modules", modulePath);
		env.setProperty("codegen.module." + modulePath + ".moduleName", moduleName);
		if (moduleTables != null && moduleTables.length() > 0) {
			env.setProperty("codegen.module." + modulePath + ".tables", moduleTables);
		}
		
		if (clean) {
			try {
				String cleanDir = rootDir+ "/" + basePackage.replaceAll("\\.", "\\/") + "/" + modulePath.replaceAll("\\.", "\\/");
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
			
			CodeGenHelper.genModule(env, dataCollector, genUCC, true, genDAO);
			
			if (genModel) {
				env.setProperty("codegen.bo.enableJSR303", enableJSR303 + "");
				
				if (moduleTables != null && moduleTables.length() > 0) {
					String[] tables = StringUtil.split(moduleTables, ",");
					for (String table : tables) {
						TableModel tableModel = dataCollector.getTableModel(table);
						CodeGenHelper.genBO(env, tableModel);
					}
				}
			}
		} catch (Exception e) {
			getLog().error(e.getMessage(), e);
		} finally {
			if (dataSource != null) {
				dataSource.close();
			}
		}
	}
}