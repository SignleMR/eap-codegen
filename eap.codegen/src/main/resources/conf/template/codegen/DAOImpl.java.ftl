<#assign boClassName=_boModel.className />
<#assign daoClassName=_daoModel.className />
package ${packageName};

import org.springframework.stereotype.Repository;

import ${_daoModel.packageName}.${daoClassName};
import ${_boModel.packageName}.${boClassName};

import eap.comps.orm.mybatis.BaseDAO;

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
@Repository("${domainName?uncap_first}DAO")
public class ${className} extends BaseDAO implements ${daoClassName} {
	
	public int save${boClassName}(${boClassName} ${boClassName?uncap_first}) {
		return sqlExecutor.insert("${modulePath!daoClassName}.save${boClassName}", ${boClassName?uncap_first});
	}
	
	public int delete${boClassName}ById(Long id) {
		return sqlExecutor.delete("${modulePath!daoClassName}.delete${boClassName}ById", id);
	}
	
	public int update${boClassName}ById(${boClassName} ${boClassName?uncap_first}) {
		return sqlExecutor.update("${modulePath!daoClassName}.update${boClassName}ById", ${boClassName?uncap_first});
	}
	
	public ${boClassName} find${boClassName}ById(Long id) {
		return sqlExecutor.selectOne("${modulePath!daoClassName}.find${boClassName}ById", id);
	}
}