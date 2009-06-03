package net.fortunes.codegenerate.action;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import net.fortunes.codegenerate.model.Field;
import net.fortunes.codegenerate.model.Field.FieldType;
import net.fortunes.core.action.BaseAction;

public class CodeGenerateAction extends BaseAction {
	public static String PACKAGE_PREFIX_KEY  = "packagePrefix";
	public static String MODEL_NAME_KEY  = "modelName";
	public static String MODEL_NAME_LOWER_KEY  = "modelNameLower";
	public static String ID_TYPE_KEY  = "idType";
	public static String ID_TYPE_LOWER_KEY  = "idTypeLower";
	public static String FIELDS_KEY  = "fields";
	
	private Configuration cfg;
	public static String BUILD_PATH = "/WEB-INF/classes";
	
	public static String OUT_PATH = "d:/codeGenerate";
	
	private String modelName;
	private String packagePrefix;
	private String idType;
	
	private String[] fieldTypes;
	private String[] fieldNames;
	private String[] fieldLabels;
	private String[] fieldExtend; //kind,url,maxLength
	private String[] fieldAllowBlank;
	private int[] fieldLengths;
	
	
	public String generate() throws Exception{
		init();
		
		Map<String,Object> root = new HashMap<String, Object>();
		root.put(PACKAGE_PREFIX_KEY, packagePrefix);
		root.put(MODEL_NAME_KEY, modelName);
		root.put(MODEL_NAME_LOWER_KEY, getLower(modelName));
		root.put(ID_TYPE_KEY, idType);
		root.put(ID_TYPE_LOWER_KEY, getLower(idType));
		root.put(FIELDS_KEY, processFields());
		
		//renerate
		generate(root, "Dao");
		generate(root, "Service");
		generate(root, "Action");
		generate(root, "Model");
		generate(root, "Spring-conf");
		generate(root, "Widget");
		
		setJsonMessage(true, "代码成功生成");
		return render(jo);
	}
	
	private void init() throws Exception{
		cfg = new Configuration();
		
		String contextRealPath = request.getSession().getServletContext().getRealPath("/");
		System.out.println(contextRealPath);
		cfg.setDirectoryForTemplateLoading(new File(
				contextRealPath + BUILD_PATH + "/net/fortunes/codegenerate/templates"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		File outPath = new File(OUT_PATH);
		if(outPath.exists()){
			FileUtils.deleteDirectory(outPath);
		}
	}
	
	private List<Field> processFields(){
		List<Field> fields = new ArrayList<Field>();
		for (int i = 0; i < fieldTypes.length; i++) {
			Field field = new Field();
			if(fieldTypes[i].equals(FieldType.text.name())){
				field.setType(FieldType.text);
				field.setName(fieldNames[i]);
				field.setLabel(fieldLabels[i]);
				if(fieldLengths[i] > 0)
					field.setLength(fieldLengths[i]);
			}
			fields.add(field);
		}
		return fields;
	}
	
	private void generate(Map<String,Object> root,String fileType) throws Exception{
		Template tpl = cfg.getTemplate(getLower(fileType)+".ftl");
		String fileName = "";
		if(fileType.equals("Dao") || fileType.equals("Action") || fileType.equals("Service")){
			fileName = getOutPath() + "/src" + "/" + packagePrefix.replace('.', '/') + "/" +
				getLower(fileType) + "/" +modelName + fileType + ".java";
		}else if(fileType.equals("Model")){
			fileName = getOutPath() + "/src" + "/" + packagePrefix.replace('.', '/') +  "/" +
				getLower(fileType) + "/" +modelName + ".java";
		}else if(fileType.equals("Spring-conf")){
			fileName = getOutPath() + "/conf" + "/" + modelName + fileType + ".xml";
		}else if(fileType.equals("Widget")){
			fileName = getOutPath() + "/WebContent/widget/app/" + getLower(modelName) + ".js";
		}
		System.out.println(fileName);
		File codeFile = new File(fileName);
		if(!codeFile.getParentFile().exists()){
			codeFile.getParentFile().mkdirs();
		}
		Writer out = new FileWriter(codeFile);
		tpl.process(root, out);
		out.flush();
	}
	
	private String getOutPath(){
		return OUT_PATH;
	}
	
	private String getLower(String s){
		return StringUtils.uncapitalize(s);
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getPackagePrefix() {
		return packagePrefix;
	}

	public void setPackagePrefix(String packagePrefix) {
		this.packagePrefix = packagePrefix;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String[] getFieldTypes() {
		return fieldTypes;
	}

	public void setFieldTypes(String[] fieldTypes) {
		this.fieldTypes = fieldTypes;
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	public String[] getFieldLabels() {
		return fieldLabels;
	}

	public void setFieldLabels(String[] fieldLabels) {
		this.fieldLabels = fieldLabels;
	}

	public int[] getFieldLengths() {
		return fieldLengths;
	}

	public void setFieldLengths(int[] fieldLengths) {
		this.fieldLengths = fieldLengths;
	}

	public void setFieldExtend(String[] fieldExtend) {
		this.fieldExtend = fieldExtend;
	}

	public String[] getFieldExtend() {
		return fieldExtend;
	}

	public void setFieldAllowBlank(String[] fieldAllowBlank) {
		this.fieldAllowBlank = fieldAllowBlank;
	}

	public String[] getFieldAllowBlank() {
		return fieldAllowBlank;
	}
}
