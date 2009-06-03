package net.fortunes.codegenerate.model;

import net.fortunes.core.Model;

public class Field extends Model{

	public static enum FieldType {
		text, textArea, number, date, dateTime, time, dict, select
	}
	
	private FieldType type;
	private String name;
	private String label;
	private int length;
	
	
	public FieldType getType() {
		return type;
	}
	public void setType(FieldType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
}
