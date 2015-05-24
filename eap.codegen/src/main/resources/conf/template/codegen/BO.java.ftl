package ${packageName};

import java.math.BigDecimal;
import java.util.Date;

import eap.base.BaseBO;

<#if _env["codegen.bo.enableJSR303"] == "true">
import javax.validation.constraints.Size;
import javax.validation.constraints.Digits;
import eap.util.validator.Required;
import eap.util.validator.DateTime;
</#if>

/**
 * <p> 标题: </p>
 * <p> 描述: </p>
 * @作者 ${_env["codegen.author"]}
 * @创建时间 ${_env["codegen.currentTime"]}
 * @版本 1.00
 * @修改记录
 * <pre>
 * 版本       修改人         修改时间         修改内容描述
 * ----------------------------------------
 * 
 * ----------------------------------------
 * </pre>
 */
public class ${className} extends BaseBO {
	
	<#list fieldList as field>
	/** ${field.note} */
	<#list field.annotationList as annotation>
	${annotation}
	</#list>
	private ${field.type} ${field.name};
	</#list>
	
	<#list fieldList as field>
	public ${field.type} get${field.name?cap_first}() {
		return this.${field.name};
	}
	public void set${field.name?cap_first}(${field.type} ${field.name}) {
		this.${field.name} = ${field.name};
	}
	</#list>
}