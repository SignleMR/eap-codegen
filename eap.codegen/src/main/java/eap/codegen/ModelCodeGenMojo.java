package eap.codegen;

import java.io.File;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.alibaba.druid.pool.DruidDataSource;

import eap.codegen.datacollect.IDataCollector;
import eap.util.FileUtil;

/** 
 * @goal model 
 */  
public class ModelCodeGenMojo extends AbstractCodeGenMojo {
	
	/** 
	 * @parameter property="dataSourceId" default-value="ds1"
	 */
	private String dataSourceId;
	
	/** 
	 * @parameter property="enableJSR303" default-value=false
	 */
	private boolean enableJSR303;
	
	/** 
	 * @parameter property="clean" default-value=false
	 */
	private boolean clean;
	
	@Override
	public void executeImpl(Properties env) throws MojoExecutionException, MojoFailureException {
		getLog().info("execute model codegen...");
		
		String rootDir = basedir + "/codegen-output";
		String basePackage = env.getProperty("app.basePackage", "app.basePackage_IS_UNDEFINED");
		
		env.setProperty("codegen.rootDir", rootDir);
		env.setProperty("codegen.basePackage", basePackage);
		env.setProperty("codegen.bo.enableJSR303", enableJSR303 + "");
		
		if (clean) {
			try {
				String cleanDir = rootDir+ "/" + basePackage.replaceAll("\\.", "\\/") + "/model"; // "/common/model";
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
			
			CodeGenHelper.genBO(env, dataCollector);
		} catch (Exception e) {
			getLog().error(e.getMessage(), e);
		} finally {
			if (dataSource != null) {
				dataSource.close();
			}
		}
	}
}