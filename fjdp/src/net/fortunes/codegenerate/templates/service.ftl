package ${packagePrefix}.service;

import ${packagePrefix}.dao.${modelName}Dao;
import ${packagePrefix}.model.${modelName};
import net.fortunes.core.service.GenericService;

public class ${modelName}Service extends GenericService<${modelName}> {
	
	private ${modelName}Dao ${modelNameLower}Dao;
	
	public ${modelName}Dao get${modelName}Dao() {
		return ${modelNameLower}Dao;
	}

	public void set${modelName}Dao(${modelName}Dao ${modelNameLower}Dao) {
		this.${modelNameLower}Dao = ${modelNameLower}Dao;
	}
}
