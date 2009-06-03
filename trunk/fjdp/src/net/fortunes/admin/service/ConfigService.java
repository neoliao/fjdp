package net.fortunes.admin.service;

import java.util.Map;
import net.fortunes.admin.model.Config;
import net.fortunes.admin.model.Config.ConfigKey;
import net.fortunes.core.service.GenericService;

public class ConfigService extends GenericService<Config> {
	
	public void updateConfigs(Map<ConfigKey, String> maps) {
		for(Map.Entry<ConfigKey, String> e : maps.entrySet()){
			Config config = getConfigBykey(e.getKey());
			if(config == null){
				config =  new Config(e.getKey(),e.getValue(),e.getValue());
				getDefDao().add(config);
			}else{
				config.setConfigValue(e.getValue());
				getDefDao().update(config);
			}
		}
	}
	
	public Config getConfigBykey(ConfigKey key) {
		return findUniqueByProperty("configKey", key);
	}
	
}
