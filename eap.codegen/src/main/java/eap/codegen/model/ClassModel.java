package eap.codegen.model;

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
public class ClassModel {
	
	protected String filePath;
	
	protected String packageName;
	protected List<String> importClassNameList = new ArrayList<String>();
	protected String className;
	
	protected List<FieldModel> fieldList = new ArrayList<FieldModel>();
	
	public List<String> addImportClassName(String importClassName) {
		importClassNameList.add(importClassName);
		return importClassNameList;
	}
	
	public List<FieldModel> addField(FieldModel fieldModel) {
		fieldList.add(fieldModel);
		return fieldList;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<String> getImportClassNameList() {
		return importClassNameList;
	}

	public void setImportClassNameList(List<String> importClassNameList) {
		this.importClassNameList = importClassNameList;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<FieldModel> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<FieldModel> fieldList) {
		this.fieldList = fieldList;
	}
}