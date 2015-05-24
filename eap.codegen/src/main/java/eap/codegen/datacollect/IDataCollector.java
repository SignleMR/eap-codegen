package eap.codegen.datacollect;

import java.sql.DatabaseMetaData;

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
public interface IDataCollector {
	DatabaseModel getDatabaseModel();
	TableModel getTableModel(String table);
	DatabaseMetaData getDatabaseMetaData();
	String getDatabaseCatalog();
}