package misc;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class CodeGenerator {
	
	public static String BUILD_PATH = "build/classes";
	
	private Configuration cfg;
	
	
	public CodeGenerator() {
		try {
			cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(BUILD_PATH + "/net/fortunes/codegenerate/templates"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CodeGenerator generator = new CodeGenerator();
		Map<String,Object> root = new HashMap<String, Object>();
		root.put("packagePrefix", "com.demo.test");
		Template tpl = generator.cfg.getTemplate("dao.ftl");
		Writer out = new OutputStreamWriter(System.out);
		tpl.process(root, out);
		out.flush();
		

	}

}
