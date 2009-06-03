<!-- ${modelName} -->
<bean id="${modelNameLower}Dao" class="${packagePrefix}.dao.${modelName}Dao" parent="baseDao"/>
<bean id="${modelNameLower}Service" class="${packagePrefix}.service.${modelName}Service">
	<property name="defDao" ref="${modelNameLower}Dao"/>
</bean>   
<bean id="${modelNameLower}Action" class="${packagePrefix}.action.${modelName}Action" scope="prototype">
	<property name="defService" ref="${modelNameLower}Service"/>
</bean>