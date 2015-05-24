package ${packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ${_uccModel.packageName}.${_uccModel.className};
<#list _bsModelList as _bsModel>
import ${_bsModel.packageName}.${_bsModel.className};
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
@Service("${domainName?uncap_first}UCC")
public class ${className} implements ${_uccModel.className} {
	<#list _bsModelList as _bsModel>
	
	@Autowired
	@Qualifier("${_bsModel.domainName?uncap_first}BS")
	private ${_bsModel.className} ${_bsModel.domainName?uncap_first}BS;
	</#list>
	
	
}