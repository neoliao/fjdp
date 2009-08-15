package net.fortunes.admin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import net.fortunes.core.Model;

@Entity
public class Privilege  extends Model {
	
	@Id @GeneratedValue
	private long id;
	
	private String code;
	
	private String text;
	
	private boolean leaf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Privilege parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Privilege> children = new ArrayList<Privilege>();

	private String description;

    public Privilege() {
    }
    
    public Privilege(long id) {
    	this.id = id;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	public Privilege getParent() {
		return parent;
	}

	public void setChildren(List<Privilege> children) {
		this.children = children;
	}

	public List<Privilege> getChildren() {
		return children;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isLeaf() {
		return leaf;
	}



}


