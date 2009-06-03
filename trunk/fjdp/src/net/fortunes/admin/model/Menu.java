package net.fortunes.admin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import net.fortunes.core.Model;

@Entity
public class Menu extends Model {

	public static enum MenuType {
		ROOT,CATEGORY,NODE
	}

	@Id @GeneratedValue
	private long id;
	private String name;
	private String text;
	private String url;
	private String icon;
	private boolean display;
	
	@Enumerated(EnumType.STRING)
	private MenuType type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Menu parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Menu> children = new ArrayList<Menu>();
	
    public Menu() {
    }
    
    public void setType(String typeString){
    	if(typeString.equals("node")){
    		this.type = MenuType.NODE;
    	}else if(typeString.equals("category")){
    		this.type = MenuType.CATEGORY;
    	}else if (typeString.equals("root")){
    		this.type = MenuType.ROOT;
    	}
    }
    

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	
	public void setDisplay(boolean display) {
		this.display = display;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setType(MenuType type) {
		this.type = type;
	}

	public MenuType getType() {
		return type;
	}

}


