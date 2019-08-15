package frame.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import frame.tableinfo.Id;
import frame.tableinfo.ManytoOne;
import frame.tableinfo.OnetoMany;
import frame.tableinfo.Property;
import frame.tableinfo.TableInfo;

/**
 *  读取 实体bean.xml映射到 Map<String,TableInfo> 中
 *@author Gxy
 *@Since 2019-08-14 03:50:59
 */
public class TableInfoXMLUtils {

	public static Map<String, TableInfo> getTableInfoXML(List<String> filelist) {
		Map<String, TableInfo> tableMap = new HashMap<String, TableInfo>();
		Document doc = null;
		Element rootElement = null;
		Id id = null;
		List<Property> plist = null;
		List<OnetoMany> onelist = null;
		List<ManytoOne> manylist = null;
		TableInfo table = new TableInfo();
		for (String filename : filelist) {
			File file = new File(TableInfoXMLUtils.class.getResource(filename).getFile());
			SAXReader saxReader = new SAXReader();
			try {
				doc = saxReader.read(file);
				rootElement = doc.getRootElement();
				// attributeValue 获取节点的属性值
				table.setClassname(rootElement.attributeValue("name"));
				table.setTablename(rootElement.attributeValue("table"));
				List<Element> elements = rootElement.elements();
				for (Element e : elements) {
					if (e.getName().equals("Id")) {
						id = new Id();
						id.setIdname(e.attributeValue("name"));
						id.setIdcolumn(e.attributeValue("column"));
					} else if (e.getName().equals("property")) {
						if (null == plist) {
							plist = new ArrayList<Property>();
						}
						Property property = new Property();
						property.setPropertycolumn(e.attributeValue("name"));
						property.setPropertyname(e.attributeValue("column"));
						plist.add(property);
					} else if (e.getName().equals("manytoone")) {
						if (null == manylist) {
							manylist = new ArrayList<ManytoOne>();
						}
						ManytoOne many = new ManytoOne();
						many.setManyname(e.attributeValue("name"));
						many.setManyclass(e.attributeValue("class"));
						many.setManycolumn(e.element("column").attributeValue("name"));
						manylist.add(many);
					} else if (e.getName().equals("onetomany")) {
						if (null == onelist) {
							onelist = new ArrayList<OnetoMany>();
						}
						OnetoMany one = new OnetoMany();
						one.setOnename(e.attributeValue("name"));
						one.setOneclass(e.attributeValue("class"));
						one.setOnecolumn(e.element("column").attributeValue("name"));
						onelist.add(one);
					}
				}
				table.setId(id);
				table.setPlist(plist);
				table.setOnelist(onelist);
				table.setManylist(manylist);
				tableMap.put(table.getClassname(), table);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tableMap;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		// ..退一级目录  /开始 绝对路径  非/开始 先对路径
		String filePath = "../bean/book.xml";
		list.add(filePath);
		Map<String, TableInfo> tableInfoXML = TableInfoXMLUtils.getTableInfoXML(list);
		tableInfoXML.forEach((k,v)->{
			System.out.println(k+":"+v);
		});
	}
}
