package frame.tools;

import java.util.HashMap;
import java.util.Map;

import frame.tableinfo.TableInfo;

/**
 *  存放数据库连接
 *@author Gxy
 *@Since 2019-08-14 11:11:30
 */
public class Constant {
	
	//存放数据路连接信息
	public static Map<String,String> DBMAP = new HashMap<String,String>();
	
	// 存放配置文件
	public static Map<String,TableInfo> TABLEMAP = new HashMap<String,TableInfo>();
}
