package eap.codegen.model.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class TableModel {
	
	private Map<String, Object> tableMeta;
	
	private String name;
	private String comment;
	private List<ColumnModel> columnList = new ArrayList<ColumnModel>();
	
	public List<ColumnModel> addColumn(ColumnModel column) {
		columnList.add(column);
		return columnList;
	}
	
	public Map<String, Object> getTableMeta() {
		return tableMeta;
	}
	public void setTableMeta(Map<String, Object> tableMeta) {
		this.tableMeta = tableMeta;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<ColumnModel> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<ColumnModel> columnList) {
		this.columnList = columnList;
	}
}