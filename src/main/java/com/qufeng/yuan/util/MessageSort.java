package com.qufeng.yuan.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.qufeng.yuan.entity.QunMessages;

/**
 * QQ缇や俊鎭帓搴�
 * @author lanyuan
 * 2013-11-19
 * @Email: mmm333zzz520@163.com
 * @version 1.0v
 */
public class MessageSort {
	
	/**
	 * 鏂囦欢娴�
	 * @param inputStream
	 * @return
	 * @throws Exception 
	 */
	public List<QunMessages> sortQQ(InputStream inputStream) throws Exception {
		BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));// 鍒嗗壊瀛楃娴�
		return toQQInfp(bfr);
	}
	/**
	 * 鏂囦欢璺緞
	 * @param filePath
	 * @return銆�Map<String, Integer>
	 * @throws Exception 
	 */
	public List<QunMessages> sortQQ(String filePath) throws Exception {
		FileReader fr = new FileReader(filePath);// 鍒涘缓瀛楃娴�
		BufferedReader bfr = new BufferedReader(fr);// 鍒嗗壊瀛楃娴�
		return toQQInfp(bfr);
	}
	
	/**
	 * 杩斿洖鎺掑簭濂界殑鏁版嵁
	 * @param BufferedReader
	 * @return Map<String, Integer>
	 */
	public List<QunMessages> toQQInfp(BufferedReader bfr) {
		List<String> strList = new ArrayList<String>();// 瀛樻斁鎵�鏈夋秷鎭褰�
		Map<String, Integer> strMap = new HashMap<String, Integer>();
		HashSet<String> strSet = new HashSet<String>();// HashSet鐢变簬鏁版嵁涓嶄細閲嶅鐨勶紟鎵�浠ヨ褰曟煇涓兢鐨勬垚鍛樻暟閲�
		List<QunMessages> qms = new ArrayList<QunMessages>();//瀛樻斁鎺掑簭鍚庣殑鏁版嵁
		try {
			while (bfr.read() != -1) {// 寰幆璇诲彇
				String tmp = bfr.readLine();
				if (tmp.lastIndexOf(" ") > 0) {
					if (tmp.indexOf("(") >= 0) {
						if (tmp.lastIndexOf(" ") < tmp.lastIndexOf(")")) {
							tmp = tmp.substring(tmp.lastIndexOf(" ") + 1,
									tmp.lastIndexOf(")") + 1);
							strList.add(tmp);// 娣诲姞鍒癓ist涓�浠�
							strSet.add(tmp);
						}
					} else if (tmp.indexOf("<") >= 0) {
						if (tmp.lastIndexOf(" ") < tmp.lastIndexOf(">")) {
							tmp = tmp.substring(tmp.lastIndexOf(" ") + 1,
									tmp.lastIndexOf(">") + 1);
							strList.add(tmp);// 娣诲姞鍒癓ist涓�浠�
							strSet.add(tmp);
						}
					}
				}

			}
			for (String map : strSet) {
				boolean b = false;
				if(map.lastIndexOf("(")>-1&&map.lastIndexOf(")")>-1){
					String sun = map.substring(map.lastIndexOf("(") + 1,map.lastIndexOf(")"));
					if(isNum(sun)){
						b=true;
					}
				}else if(map.indexOf("@qq.com")>-1){
					b=true;
				}
				if(b){
					int count = 0;
					for (int j = 0; j < strList.size(); j++) {
						//String tmp = (String) strSet.toArray()[i];
						if (map.equals(strList.get(j))) {
							++count;
							strMap.put(map, count);
							strList.remove(j);
						} else {
							continue;
						}
					}
//					int count = 0;
//					for (String s : strList) {
//						if (map.equals(s)) {
//							++count;
//							strMap.put(map, count);
//							strList.remove(s);
//						}
//					}
				}
			}
			ArrayList<Integer> lists = new ArrayList<Integer>();//鎺掑簭
			 for (Map.Entry<String, Integer> entity : strMap.entrySet()) {   
				 lists.add(entity.getValue());//瀛樻斁娆℃暟
			 };
			Collections.sort(lists);//Collections鍐呯疆鎻愪緵鐨勫崌搴�
			for (Integer sb : lists) {
				 for (Map.Entry<String, Integer> entity : strMap.entrySet()) {  
					 QunMessages qq=new QunMessages();
					 Integer value = entity.getValue();
					if(value.equals(sb)){
						//System.out.println(entity.getKey()+"锛氬彂琛ㄣ��"+entity.getValue()+"銆�娆�");
						boolean b = false;
						String map = entity.getKey();
						String sun="";
						String name="";
						if(map.lastIndexOf("(")>-1&&map.lastIndexOf(")")>-1){
							sun = map.substring(map.lastIndexOf("(") + 1,map.lastIndexOf(")"));
							b=true;
							name =  map.substring(0,map.lastIndexOf("("));
							qq.setName(name);
							qq.setQq(sun);
						}else if(map.lastIndexOf("<")>-1&&map.lastIndexOf(">")>-1){
							sun =map.substring(map.lastIndexOf("<")+1,map.lastIndexOf(">"));
							name =  map.substring(0,map.lastIndexOf("<"));
							qq.setName(name);
							qq.setQq(sun);
							b=true;
						}
						if(b){
							qq.setMark("鍙戣〃锛�");
							qq.setCount(entity.getValue().toString());
							qms.add(qq);
						}
						continue;
					}
				 }
			};
			//鍘婚櫎閲嶅
//			for (QunMessages qq : qms) {
//				for (QunMessages qq2 : qms) {
//					if(qq.getQq().equals(qq2.getQq())){
//						if(Integer.parseInt(qq.getCount())<Integer.parseInt(qq2.getCount())){
//							qms.remove(qq);
//						}
//					}
//				}
//			}
			   for (int i = 0; i < qms.size() - 1; i++) {                             //寰幆閬嶅巻闆嗕綋涓殑鍏冪礌
			         for (int j = qms.size() - 1; j > i; j--) {                         //杩欓噷闈炲父宸у锛岃繖閲屾槸鍊掑簭鐨勬槸姣旇緝
			              if (qms.get(j).getQq().equals(qms.get(i).getQq())) {
			            	  qms.remove(i);
			              }
			        }
			    }
		} catch (Exception e) {
		}
		return qms;
	}

	public boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public void quick(Integer[] str) {
		if (str.length > 0) { // 鏌ョ湅鏁扮粍鏄惁涓虹┖
			_quickSort(str, 0, str.length - 1);
		}
	}

	public void _quickSort(Integer[] list, int low, int high) {
		if (low < high) {
			int middle = getMiddle(list, low, high); // 灏唋ist鏁扮粍杩涜涓�鍒嗕负浜�
			_quickSort(list, low, middle - 1); // 瀵逛綆瀛楄〃杩涜閫掑綊鎺掑簭
			_quickSort(list, middle + 1, high); // 瀵归珮瀛楄〃杩涜閫掑綊鎺掑簭
		}
	}

	public int getMiddle(Integer[] list, int low, int high) {
		int tmp = list[low]; // 鏁扮粍鐨勭涓�涓綔涓轰腑杞�
		while (low < high) {
			while (low < high && list[high] > tmp) {
				high--;
			}
			list[low] = list[high]; // 姣斾腑杞村皬鐨勮褰曠Щ鍒颁綆绔�
			while (low < high && list[low] < tmp) {
				low++;
			}
			list[high] = list[low]; // 姣斾腑杞村ぇ鐨勮褰曠Щ鍒伴珮绔�
		}
		list[low] = tmp; // 涓酱璁板綍鍒板熬
		return low; // 杩斿洖涓酱鐨勪綅缃�
	}
}
