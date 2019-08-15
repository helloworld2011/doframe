package frame.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
   *  读取配置文件
 *@author Gxy
 *@Since 2019-08-13 09:37:57
 */
public class XMlFactory {
	
	public static Map<String, String> getXMLInfo(String filename){
		Map<String, String> jdbcMap = new HashMap<String,String>();
		File file = new File(filename);
		SAXReader reader = new SAXReader();
		Document document = null;
		Element rootElement = null;
		try {
			document = reader.read(file);
			rootElement = document.getRootElement();
			Element datasource = rootElement.element("datasource");
			Element jdbc = datasource.element("jdbc");
			List<Element> properts = jdbc.elements();
			for(Element element : properts) {
				Attribute name = element.attribute("name");
				Attribute value = element.attribute("value");
				if(value != null) {
					jdbcMap.put(name.getText(), value.getText());
				}else {
					jdbcMap.put(name.getText(), element.getText());
				}
			}
			Constant.DBMAP=jdbcMap;
			readBeanXML(rootElement);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jdbcMap;
	}
	
	
	/**
	 *   读配置地址连接
	 */
	private  static void readBeanXML(Element roots) {
		Element element = roots.element("orm-mapping");
		Element listelement = element.element("list");
		List<Element> elements = listelement.elements();
		List<String> filelist = new ArrayList<>();
		for(Element ele : elements) {
			filelist.add(ele.getText());
		}
		
	}
	
	private static void readXMLInfo(List<String> filelist) {
		Constant.TABLEMAP = TableInfoXMLUtils.getTableInfoXML(filelist);
	}
	
	public static void main(String[] args) {
		// 测试文件
		String file = XMlFactory.class.getResource("/dataSource.xml").getFile();
		Map<String, String> xmlInfo = getXMLInfo(file);
		xmlInfo.forEach((k,v)->{
			System.out.println(k+":"+v);
		});
	}
	
}
