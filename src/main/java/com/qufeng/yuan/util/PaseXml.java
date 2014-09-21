package com.qufeng.yuan.util;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class PaseXml {
	public static boolean doc2XmlFile(Document document, String filename) {
        boolean flag = true;
        try {
           
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }

    public static Document load(String filename) {
        Document document = null;
        try {
        	// 1.寰楀埌DOM瑙ｆ瀽鍣ㄧ殑宸ュ巶瀹炰緥
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
         // 2.浠嶥OM宸ュ巶閲岃幏鍙朌OM瑙ｆ瀽鍣�
            DocumentBuilder builder = factory.newDocumentBuilder();
         // 3.瑙ｆ瀽XML鏂囨。锛屽緱鍒癲ocument锛屽嵆DOM鏍�
            document = builder.parse(new File(filename));
          //Document瀵硅薄璋冪敤normalize()锛屽彲浠ュ幓鎺塜ML鏂囨。涓綔涓烘牸寮忓寲鍐呭鐨勭┖鐧借�屾槧灏勫湪DOM鏍戜腑鐨勪笉蹇呰鐨凾ext Node瀵硅薄銆�
          //鍚﹀垯浣犲緱鍒扮殑DOM鏍戝彲鑳藉苟涓嶅浣犳墍鎯宠薄鐨勯偅鏍枫�傜壒鍒槸鍦ㄨ緭鍑虹殑鏃跺�欙紝杩欎釜normalize()鏇翠负鏈夌敤銆� 
            document.normalize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

   
    public static void xmlUpdateDemo() {
    	String s = PaseXml.class.getResource("/").toString();
    	s = s.substring(0,s.indexOf("WEB-INF/"));
    	s = s.substring(s.indexOf("D:/"));
    	s = s+"xml/images.xml";
        Document document = load(s);
        try {
        	NodeList list=document.getElementsByTagName("menu");
        	for(int i=0;i<list.getLength();i++){
        	Element brandElement=(Element) list.item(i);
        		brandElement.setAttribute("a", "00");
        	}
        }catch (Exception e) {
		}
        doc2XmlFile(document, s);
    }

    public static void main(String args[]) throws Exception {
    	//PaseXml.xmlUpdateDemo();
    	
    	HttpClientUtils httpClientUtils = new HttpClientUtils();
    	List<String> ls= httpClientUtils.getLinkTagsContent("http://v.youku.com/v_show/id_XNTM4NjY5MDgw.html?f=19121485");
    	for (String object : ls) {
			System.out.println(object);
		}
    }

}
