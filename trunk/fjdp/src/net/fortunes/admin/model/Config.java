package net.fortunes.admin.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.fortunes.core.Model;

@Entity
public class Config extends Model {
	
	public static enum ConfigKey {    
		ADMIN_EMAIL
	}
	@Id @GeneratedValue
	private long id;
	
	@Enumerated(EnumType.STRING)
	private ConfigKey configKey;
	private String configValue;
	private String defaultValue;
	
	public Config() {
	}
	
	public Config(ConfigKey key,String value,String defaultValue) {
		this.setConfigKey(key);
		this.configValue = value;
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "系统参数:"+getConfigKey();
	}
	
	//================================ setter and getter ====================================
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public void setConfigKey(ConfigKey configKey) {
		this.configKey = configKey;
	}

	public ConfigKey getConfigKey() {
		return configKey;
	}
	
}
