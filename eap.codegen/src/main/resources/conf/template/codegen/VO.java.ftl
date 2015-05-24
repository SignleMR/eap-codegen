package ${packageName};

<#list importClassNameList as importClassName>
import ${importClassName};
</#list>

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
public class ${className} extends ${_boModel.className} {
	
}