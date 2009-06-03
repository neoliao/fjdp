package net.fortunes.admin.action;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.fortunes.admin.model.Config;
import net.fortunes.admin.model.Config.ConfigKey;
import net.fortunes.admin.service.ConfigService;
import net.fortunes.core.action.BaseAction;
import net.sf.json.JSONObject;

public class ConfigAction extends BaseAction {
	
	private ConfigService configService; 
		
	public String updateConfig() throws Exception{
		Map<ConfigKey, String> maps = new EnumMap<ConfigKey, String>(ConfigKey.class);
		maps.put(ConfigKey.ADMIN_EMAIL, p("adminEmail"));
		configService.updateConfigs(maps);
		jo.put("success", true);
		jo.put("msg", "系统参数成功更新!");
		return render(jo);
	}
	
	public String loadConfig() throws Exception{
		List<Config> configs = configService.getAll();
		JSONObject data = new JSONObject();
		for(Config config : configs){
			codecConfig(config, ConfigKey.ADMIN_EMAIL, "adminEmail", data);
		}
		jo.put("success", true);
		jo.put("data", data);
		return render(jo);
	}
	
	private void codecConfig(Config config,ConfigKey configkey,String joKey,JSONObject data){
		if(config.getConfigKey().equals(configkey)){
			data.put(joKey, config.getConfigValue());
		}
	}
	
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}


	public ConfigService getConfigService() {
		return configService;
	}
	

}
