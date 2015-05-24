package eap.codegen.model.db;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

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
public class DatabaseModel {
	
	private DatabaseMetaData databaseMetaData;
	
	private List<TableModel> tableList = new ArrayList<TableModel>();
	
	public List<TableModel> addTable(TableModel table) {
		tableList.add(table);
		return tableList;
	}
	
	public DatabaseMetaData getDatabaseMetaData() {
		return databaseMetaData;
	}
	public void setDatabaseMetaData(DatabaseMetaData databaseMetaData) {
		this.databaseMetaData = databaseMetaData;
	}

	public List<TableModel> getTableList() {
		return tableList;
	}

	public void setTableList(List<TableModel> tableList) {
		this.tableList = tableList;
	}
}