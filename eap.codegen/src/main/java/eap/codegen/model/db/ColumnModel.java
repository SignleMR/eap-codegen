package eap.codegen.model.db;

import java.util.Map;

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
public class ColumnModel {
	
	private Map<String, Object> columnMeta;
	
	private String name;
	private String sqlType;
	private String javaType;
	private String comment;
	
	private boolean baseColumn = false;
	
	private String name0;
	
	public ColumnModel(Map<String, Object> columnMeta, String name, String sqlType, String javaType, String comment) {
		this.columnMeta = columnMeta;
		this.setName(name);
		this.sqlType = sqlType;
		this.javaType = javaType;
		this.comment = comment;
	}
	
	public Map<String, Object> getColumnMeta() {
		return columnMeta;
	}
	public void setColumnMeta(Map<String, Object> columnMeta) {
		this.columnMeta = columnMeta;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		if (name.equalsIgnoreCase("id") 
			|| name.equalsIgnoreCase("created_time") 
			|| name.equalsIgnoreCase("created_by") 
			|| name.equalsIgnoreCase("update_time") 
			|| name.equalsIgnoreCase("update_by") 
			|| name.equalsIgnoreCase("dept_cd") 
			|| name.equalsIgnoreCase("org_cd")) {
			
			baseColumn = true;
		}
		
		if (name != null) {
			this.name0 = StringUtil.mapUnderscoreToCamelCase(name);
		}
	}
	
	public String getSqlType() {
		return sqlType;
	}
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isBaseColumn() {
		return baseColumn;
	}
	public void setBaseColumn(boolean baseColumn) {
		this.baseColumn = baseColumn;
	}
	public String getName0() {
		return name0;
	}
	public void setName0(String name0) {
		this.name0 = name0;
	}
}
