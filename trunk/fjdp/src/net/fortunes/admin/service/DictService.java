package net.fortunes.admin.service;

import java.io.Reader;
import java.util.List;

import net.fortunes.admin.model.Dict;
import net.fortunes.core.log.annotation.LoggerClass;
import net.fortunes.core.service.GenericService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

@Component
@LoggerClass
public class DictService extends GenericService<Dict> {
	
	public static String KEY_ATTRIBUTE = "key";
	public static String TEXT_ATTRIBUTE = "text";
	public static String ROOT_DICT_KEY = "root";
	
	@Override
	public Dict add(Dict entity) throws Exception {
		super.add(entity);
		if(entity.getParent() != null)
			entity.getParent().setLeaf(false);
		return entity;
	}
	
	@Override
	public void del(Dict entity) throws Exception {
		Dict parent = entity.getParent();
		super.del(entity);
		if(parent != null && parent.getChildren().size() <= 0)
			parent.setLeaf(true);
	}
	
	@SuppressWarnings("unchecked")
	public void initToDb(Reader reader) throws Exception{
		SAXReader xmlReader = new SAXReader(); 
		Document doc = xmlReader.read(reader);
		Element root = doc.getRootElement();
		
		this.delAll();
		List<Element> elements = root.elements();
		Dict rootDict = new Dict(ROOT_DICT_KEY,"根字典");
		getDefDao().add(rootDict);
		for (Element element : elements) {
			walkTree(element,rootDict);
		}
	}
	
	public List<Dict> getDictsByType(String key) {
		return findByProperty("parent.id", key);
	}
	
	@SuppressWarnings("unchecked")
	private void walkTree(Element element,Dict parentDict) throws Exception{
		Dict dict = new Dict();
		dict.setText(element.attributeValue(TEXT_ATTRIBUTE));
		if(parentDict.getId().equals(ROOT_DICT_KEY))
			dict.setId(element.attributeValue(KEY_ATTRIBUTE));
		else
			dict.setId(parentDict.getId()+"_"+element.attributeValue(KEY_ATTRIBUTE));
		dict.setParent(parentDict);
		this.add(dict);
		List<Element> subElements = element.elements();
		for(Element e : subElements){
			walkTree(e,dict);
		}
	}

	
}
  