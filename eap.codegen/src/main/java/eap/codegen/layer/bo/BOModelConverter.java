package eap.codegen.layer.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eap.codegen.model.FieldModel;
import eap.codegen.model.db.ColumnModel;
import eap.codegen.model.db.TableModel;
import eap.codegen.util.NameUtil;
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
public class BOModelConverter {
	
	public static BOModel toBOJavaModel(Properties env, TableModel tableModel) {
		String tableName = tableModel.getName();
		boolean enableJSR303 = Boolean.parseBoolean(env.getProperty("codegen.bo.enableJSR303", "false"));
		
		BOModel boModel = new BOModel();
		boModel.setPackageName(NameUtil.getBOPackageName(env));
		boModel.setClassName(NameUtil.getBOClassName(tableName));
//		List<String> importClassNameList = new ArrayList<String>();
//		importClassNameList.add("java.math.BigDecimal");
//		importClassNameList.add("java.util.Date");
//		importClassNameList.add("eap.base.BaseBO");
//		boModel.setImportClassNameList(importClassNameList);
		
		for (ColumnModel columnModel : tableModel.getColumnList()) {
			if (columnModel.isBaseColumn()) {
				continue;
			}
			
			FieldModel fieldModel = new FieldModel();
			fieldModel.setName(StringUtil.mapUnderscoreToCamelCase(columnModel.getName()));
			fieldModel.setType(columnModel.getJavaType());
			fieldModel.setNote(columnModel.getComment());
			
			List<String> annotationList = new ArrayList<String>();
			if (enableJSR303) {
				Map<String, Object> columnMeta = columnModel.getColumnMeta();
				if ("NO".equalsIgnoreCase((String)columnMeta.get("is_nullable"))) {
					annotationList.add("@Required");
				}
				if ("Integer".equals(columnModel.getJavaType()) || "BigDecimal".equals(columnModel.getJavaType())) {
					annotationList.add(String.format("@Digits(integer=%d,fraction=%d)", new Integer(columnMeta.get("numeric_precision").toString()), new Integer(columnMeta.get("numeric_scale").toString())));
				} else if ("Date".equals(columnModel.getJavaType())) {
					annotationList.add("@DateTime");
				} else {
					annotationList.add(String.format("@Size(min=%d,max=%d)", 0, new Integer(columnMeta.get("character_maximum_length").toString())));
				}
			}
			fieldModel.setAnnotationList(annotationList);
			
			boModel.addField(fieldModel);
		}
		
		boModel.setFilePath(NameUtil.getJavaFilePath(env, boModel.getPackageName(), boModel.getClassName()));
		return boModel;
	}
	
	public static BOModel toVOJavaModel(Properties env, TableModel tableModel) {
		String tableName = tableModel.getName();
		
		BOModel boModel = new BOModel();
		boModel.setPackageName(NameUtil.getVOPackageName(env));
		boModel.setClassName(NameUtil.getVOClassName(tableName));
		List<String> importClassNameList = new ArrayList<String>();
		importClassNameList.add(NameUtil.getBOFullClassName(env, tableName));
		boModel.setImportClassNameList(importClassNameList);
		
		boModel.setFilePath(NameUtil.getJavaFilePath(env, boModel.getPackageName(), boModel.getClassName()));
		return boModel;
	}
}