package eap.codegen;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

import eap.Env;
import eap.codegen.datacollect.IDataCollector;
import eap.codegen.datacollect.MySqlDataCollector;
import eap.util.BeanUtil;
import eap.util.DateUtil;
import eap.util.PropertiesUtil;
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
public abstract class AbstractCodeGenMojo extends AbstractMojo {
	
	/** 
	 * @parameter property="basedir" 
	 * @required 
	 * @readonly 
	 */
	protected String basedir;
	
//	/** 
//	 * @parameter expression="${project.resources}" 
//	 * @required 
//	 * @readonly 
//	 */
//	protected List<Resource> resources;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		if (StringUtil.isBlank(System.getProperty("app.id"))) {
			System.setProperty("app.id", "1");
		}
		if (StringUtil.isBlank(System.getProperty("app.configPath"))) {
			System.setProperty("app.configPath", "file:" + basedir + "/src/main/resources/env.properties");
		}
		
		Env env = new Env();
		env.refresh(null);
		
		Properties envProps = new Properties();
		envProps.putAll(env.getEnvProperties());
		
		if (StringUtil.isBlank(env.getProperty("codegen.author")) && StringUtil.isNotBlank(env.getProperty("user.name"))) {
			envProps.put("codegen.author", env.getProperty("user.name"));
		}
		envProps.put("codegen.currentTime", DateUtil.formatFull(DateUtil.currDate()));
		
		executeImpl(envProps);
	}

	public abstract void executeImpl(Properties envProps) throws MojoExecutionException, MojoFailureException;
	
	protected DruidDataSource getDataSource(Properties env, String dataSourceId) throws SQLException {
		DruidDataSource dataSource = new DruidDataSource();
		Properties dataSourceProps = PropertiesUtil.filterForPrefix(env, "dataSource." + dataSourceId + ".");
		if (dataSourceProps.size() == 0) {
			throw new IllegalArgumentException("dataSource properties not be null");
		}
		
		BeanUtil.setProperty(dataSource, dataSourceProps);
		dataSource.init();
		
		return dataSource;
	}
	
	protected IDataCollector getDataCollector(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		// TODO only support mysql
		return new MySqlDataCollector(jdbcTemplate);
	}
}