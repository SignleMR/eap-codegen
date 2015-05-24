<#assign boClassName=_boModel.className />
package ${packageName};

import ${_boModel.packageName}.${boClassName};

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
public interface ${className} {
	int save${boClassName}(${boClassName} ${boClassName?uncap_first});
	int delete${boClassName}ById(Long id);
	int update${boClassName}ById(${boClassName} ${boClassName?uncap_first});
	${boClassName} find${boClassName}ById(Long id);
}