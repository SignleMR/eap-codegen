package eap.codegen.datacollect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import eap.codegen.model.db.ColumnModel;
import eap.codegen.model.db.DatabaseModel;
import eap.codegen.model.db.TableModel;

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
public class MySqlDataCollector implements IDataCollector {
	
	private JdbcTemplate jdbcTemplate;
	
	public MySqlDataCollector(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public DatabaseModel getDatabaseModel() {
		DatabaseModel databaseModel = new DatabaseModel();
		databaseModel.setDatabaseMetaData(getDatabaseMetaData());
		
		List<String> tables = jdbcTemplate.queryForList("show tables", String.class);
		for (String table : tables) {
			databaseModel.addTable(getTableModel(table.toLowerCase()));
		}
		
		return databaseModel;
	}
	
	public TableModel getTableModel(String table) {
		String catalog = getDatabaseCatalog();
		Map<String, Object> tableMeta = null;
		List<Map<String, Object>> columnMetaList = null;
		try {
			tableMeta = jdbcTemplate.queryForMap("SELECT * FROM information_schema.tables WHERE table_schema = ? and table_name = ?", catalog, table);
			columnMetaList = jdbcTemplate.queryForList("SELECT * FROM information_schema.columns WHERE table_schema = ? and table_name = ? ORDER BY ordinal_position ASC ", catalog, table);
		} catch (RuntimeException e) {
			System.out.println("table '" + table + "' not exists");
			throw e;
		}
		
		TableModel tableModel = new TableModel();
		tableModel.setTableMeta(tableMeta);
		tableModel.setName(table);
		tableModel.setComment((String) tableMeta.get("table_comment"));
		
		for (Map<String, Object> columnMeta : columnMetaList) {
			tableModel.addColumn(new ColumnModel(
				columnMeta, 
				((String) columnMeta.get("column_name")).toLowerCase(), 
				(String) columnMeta.get("data_type"),
				sqlTypeToJavaType((String) columnMeta.get("data_type")), 
				(String) columnMeta.get("column_comment")
			));
		}
		
		return tableModel;
	}
	
	public DatabaseMetaData getDatabaseMetaData() {
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			return conn.getMetaData();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	public String getDatabaseCatalog() {
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			return conn.getCatalog();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	private String sqlTypeToJavaType(String sqlType) {
		if ("bit".equalsIgnoreCase(sqlType) || "tinyint".equalsIgnoreCase(sqlType) || "integer".equalsIgnoreCase(sqlType) || "smallint".equalsIgnoreCase(sqlType) || "int".equalsIgnoreCase(sqlType)) {
			return "Integer";
		} 
		else if ("bigint".equalsIgnoreCase(sqlType)) {
			return "Long";
		}
		else if ("float".equalsIgnoreCase(sqlType) || "real".equalsIgnoreCase(sqlType) || "double".equalsIgnoreCase(sqlType) || "numeric".equalsIgnoreCase(sqlType) || "decimal".equalsIgnoreCase(sqlType)) {
			return "BigDecimal";
		}
		else if ("time".equalsIgnoreCase(sqlType) || "date".equalsIgnoreCase(sqlType) || "timestamp".equalsIgnoreCase(sqlType) || "datetime".equalsIgnoreCase(sqlType)) {
			return "Date";
		}
		
		return "String";
	}
}