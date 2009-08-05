	<!-- ${modelName} -->
	<bean id="${modelName?uncap_first}Service" class="${packagePrefix}.service.${modelName}Service" parent="genericService"/>
	<bean id="${modelName?uncap_first}Action" class="${packagePrefix}.action.${modelName}Action" scope="prototype">
		<property name="defService" ref="${modelName?uncap_first}Service"/>
	</bean>