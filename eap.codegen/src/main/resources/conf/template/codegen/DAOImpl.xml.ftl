<#assign boClassName=_boModel.className />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${modulePath!daoClassName}">
	
	<insert id="save${boClassName}" parameterType="${boClassName}">
		INSERT INTO ${_tableModel.name}(<#list _tableModel.columnList as column>${column.name}<#if column_has_next>,</#if></#list>) 
		VALUES(<#list _tableModel.columnList as column>${'#'}{${column.name0}}<#if column_has_next>,</#if></#list>)
	</insert>
	
	<delete id="delete${boClassName}ById" parameterType="long">
		DELETE FROM ${_tableModel.name} WHERE id=${'#'}{id}
	</delete>
	
	<update id="update${boClassName}ById" parameterType="${boClassName}">
		UPDATE ${_tableModel.name}
		SET update_time=${'#'}{updateTime}
		<#list _tableModel.columnList as column>
			<#if column.name != 'id' && column.name != 'created_time' && column.name != 'update_time'>
				<#if column.javaType == "String">
		<if test="${column.name0}!=null and ${column.name0}!=''">,${column.name}=${'#'}{${column.name0}}</if>
				<#else>
		<if test="${column.name0}!=null">,${column.name}=${'#'}{${column.name0}}</if>
				</#if>
			</#if>
		</#list>
 		WHERE id=${'#'}{id}
	</update>
	
	<select id="find${boClassName}ById" parameterType="long" resultType="${boClassName}">
		SELECT <#list _tableModel.columnList as column>${column.name}<#if column_has_next>,</#if></#list> 
		FROM ${_tableModel.name} 
		WHERE id=${'#'}{id}
	</select>
</mapper>