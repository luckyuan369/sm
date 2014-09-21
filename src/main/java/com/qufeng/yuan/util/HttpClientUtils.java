package com.qufeng.yuan.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;

/**
 * @author Administrator
 */
public class HttpClientUtils {
	/**
	 * 瑙ｉ噴浼橀叿缃戠珯鑾峰彇瑙嗛杩炴帴
	 * 鏌愪竴鐢ㄦ埛鐨勭殑涓撹緫鎴栫敤鎴锋墍鍙戣〃鐨勮棰�
	 * 鍙敮鎸佷互涓嬩袱涓湴鍧�
	 * http://i.youku.com/u/UMzU2MTg5MTIw/videos****
	 * http://www.youku.com/playlist_show/id_5576128***
	 * @param Set<String> 瑙嗛鍦板潃鐨勯泦鍚�
	 */
	public Set<String> toHtmlSet(String urlStr) {
		String html = Common.getInputHtmlUTF8(urlStr);
		html = parseTag0(html, Div.class, "class", "items").getStringText();
		Set<String> sets = new HashSet<String>();// 鍘婚櫎閲嶅鐨�
		if (html != null && !"".equals(html)) {
			// 鍙栧緱瑙嗛杩炴帴鍐呭
			List<LinkTag> linkTags = parseTags(html, LinkTag.class);
			for (LinkTag linkTag : linkTags) {
				if (!Common.isEmpty(linkTag.getLink()) && linkTag.getLink().indexOf("v_show/id_") > -1) {
					sets.add(linkTag.getLink());
				}
			}
		}
		return sets;
	}

	/**
	 * 瑙嗛鏍囬
	 */
	String videoName = null;
	List<Map<String, String>> lists = null;

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	/**
	 * 杩斿洖瑙ｉ噴鍚庣殑杩炴帴鍦板潃 鏀寔:鍦熻眴,浼橀叿,鎴戜箰,鏂版氮,鐧惧害,鎼滅嫄,Youtube,閰峰叚,CCTV绛�8涓富娴佽棰戠綉绔欑殑蹇�熻В鏋�
	 * 
	 * @param urlStr
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getLinkTagsContent(String urlStr) throws Exception {

		urlStr = "http://www.flvcd.com/parse.php?kw=" + urlStr + "&flag=one&format=high";
		String html = Common.getInputHtmlGBK(urlStr);
		if (html != null) {
			if (html.indexOf("锛�</strong>") > -1) {
				// 鑾峰彇script涓殑鍐呭
				int beginLocal = html.indexOf("锛�</strong>") + 14;
				int endLocal = html.indexOf("<strong>锛�") - 4;
				// 鍙栧緱瑙嗛鏍囬
				videoName = html.substring(beginLocal, endLocal);

				// 鍙栧緱瑙嗛杩炴帴鍐呭
				String content = parseTag3(html, TableColumn.class, "class", "mn STYLE4").getStringText();

				List<LinkTag> linkTags = parseTags(content, LinkTag.class);
				List<String> list = new ArrayList<String>();
				for (LinkTag linkTag : linkTags) {
					list.add(linkTag.getLink());
				}

				return list;
			}
		}

		return null;
	}

	/**
	 * 杩斿洖鏍囩鐨勫唴瀹�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getHtmlTagsContent(String urlStr) throws Exception {

		Parser parser = new Parser();

		String html = Common.getInputHtmlGBK(urlStr);
		parser.setInputHTML(html);
		String filerStr = "li";
		NodeFilter filter = new TagNameFilter(filerStr);
		// 鍙栧緱椤甸潰鍐呭涓爣绛句负"dl"
		NodeList nodeList = parser.extractAllNodesThatMatch(filter);

		Tag tag = (Tag) nodeList.elementAt(0);

		return tag.toHtml();
	}

	/**
	 * 瑙ｉ噴涔愯缃� MV闊充箰
	 * 
	 * @param urlStr
	 * @return List<String>
	 * @throws Exception
	 */
	public List<Map<String, String>> getYueShiMusic() {

		String html = Common.getInputHtmlUTF8("http://music.letv.com/");

		html = parseTag0(html, Div.class, "class", "musrigbox").getStringText();
		if (html != null && !"".equals(html)) {
			// 鍙栧緱瑙嗛杩炴帴鍐呭
			List<LinkTag> linkTags = parseTags(html, LinkTag.class);
			List<ImageTag> imageTags = parseTags(html, ImageTag.class);
			Map<String, String> map = new HashMap<String, String>();
			for (LinkTag linkTag : linkTags) {
				map.put(linkTag.getLinkText().trim(), linkTag.getLink().trim());

			}
			lists = new ArrayList<Map<String, String>>();
			Map<String, String> mapVideo = null;
			int sun = 0;
			for (ImageTag imageTag : imageTags) {
				if (sun < 10) {
					if (map.get(imageTag.getAttribute("alt")) != null && !"".equals(imageTag.getAttribute("alt"))) {
						mapVideo = new HashMap<String, String>();
						mapVideo.put("videoTypeId", "1");
						mapVideo.put("videoName", "" + imageTag.getAttribute("alt"));
						mapVideo.put("videoSmallImagesUrl", "" + imageTag.getAttribute("data-src"));
						mapVideo.put("videoUrl", "" + map.get(imageTag.getAttribute("alt")));
						mapVideo.put("videoLable", "" + imageTag.getAttribute("alt").substring(0, 2));
						mapVideo.put("videoProfile", "" + imageTag.getAttribute("alt"));

						lists.add(mapVideo);
					}
					sun += 1;
				}

			}

		}

		return lists;
	}

	/**
	 * 瑙ｉ噴浼橀叿鎼炵瑧瑙嗛
	 * 
	 * @param urlStr
	 * @return List<String>
	 * @throws Exception
	 */
	public List<Map<String, String>> getYouKuFunny() {

		String html = Common.getInputHtmlUTF8("http://fun.youku.com/index/focus");

		html = parseTag0(html, Div.class, "class", "body").getStringText();

		if (html != null && !"".equals(html)) {
			// 鍙栧緱瑙嗛杩炴帴鍐呭
			List<LinkTag> linkTags = parseTags(html, LinkTag.class);
			List<ImageTag> imageTags = parseTags(html, ImageTag.class);
			Map<String, String> map = new HashMap<String, String>();
			for (LinkTag linkTag : linkTags) {
				map.put(linkTag.getLinkText().trim(), linkTag.getLink().trim());

			}
			lists = new ArrayList<Map<String, String>>();
			Map<String, String> mapVideo = null;
			int sun = 0;
			for (ImageTag imageTag : imageTags) {
				if (sun < 10) {
					if (map.get(imageTag.getAttribute("alt").trim()) != null && !"".equals(imageTag.getAttribute("alt").trim())) {
						mapVideo = new HashMap<String, String>();
						mapVideo.put("videoTypeId", "2");
						mapVideo.put("videoName", "" + imageTag.getAttribute("alt").trim());
						mapVideo.put("videoSmallImagesUrl", "" + imageTag.getImageURL().trim());
						mapVideo.put("videoUrl", "" + map.get(imageTag.getAttribute("alt").trim()));
						mapVideo.put("videoLable", "" + imageTag.getAttribute("alt").trim().substring(0, 2));
						mapVideo.put("videoProfile", "" + imageTag.getAttribute("alt").trim());

						lists.add(mapVideo);
					}
					sun += 1;
				}

			}

		}

		return lists;
	}

	/**
	 * 瑙ｉ噴鏂版氮鏂伴椈
	 * 
	 * @param urlStr
	 * @return List<String> @
	 */
	public List<Map<String, String>> getXinLangNews() {

		String html = Common.getInputHtmlGBK("http://v.sina.com.cn/v/paike.html");

		html = parseTag0(html, Div.class, "class", "p_left").getStringText();
		if (html != null && !"".equals(html)) {
			// 鍙栧緱瑙嗛杩炴帴鍐呭
			// 鑾峰彇script涓殑鍐呭
			List<LinkTag> linkTags = parseTags(html, LinkTag.class);
			List<ImageTag> imageTags = parseTags(html, ImageTag.class);
			Map<String, String> map = new HashMap<String, String>();

			for (ImageTag imageTag : imageTags) {
				if (imageTag.getAttribute("alt").trim() != null && !"".equals(imageTag.getAttribute("alt"))) {
					map.put(imageTag.getAttribute("alt").trim(), imageTag.getAttribute("src"));
				}

			}
			lists = new ArrayList<Map<String, String>>();
			Map<String, String> mapVideo = null;
			for (LinkTag linkTag : linkTags) {
				if (map.get(linkTag.getLinkText()) != null && !"".equals(map.get(linkTag.getLinkText()))) {
					mapVideo = new HashMap<String, String>();
					mapVideo.put("videoTypeId", "3");
					mapVideo.put("videoName", "" + linkTag.getLinkText());
					mapVideo.put("videoSmallImagesUrl", "" + map.get(linkTag.getLinkText()));
					mapVideo.put("videoUrl", "" + linkTag.getLink());
					mapVideo.put("videoLable", "" + linkTag.getLinkText().substring(0, 2));
					mapVideo.put("videoProfile", "" + linkTag.getLinkText());

					lists.add(mapVideo);
				}

			}

		}

		return lists;
	}

	/**
	 * 瑙ｉ噴鍑ゅ嚢 濞变箰鍏寕
	 * 
	 * @param urlStr
	 * @return List<String> @
	 */
	public List<Map<String, String>> getFengHuangBaGua() {

		String html = Common.getInputHtmlUTF8("http://v.ifeng.com/ent/");

		if (html != null && !"".equals(html)) {
			// 鍙栧緱瑙嗛杩炴帴鍐呭
			// 鑾峰彇script涓殑鍐呭
			int beginLocal = html.indexOf("<div class=\"left\">");
			int endLocal = html.indexOf("<div class=\"ad660\">");
			String content = html.substring(beginLocal, endLocal);
			List<LinkTag> linkTags = parseTags(content, LinkTag.class);
			List<ImageTag> imageTags = parseTags(content, ImageTag.class);
			Map<String, String> map = new HashMap<String, String>();

			for (LinkTag linkTag : linkTags) {
				if (linkTag.getLink().trim().indexOf("ent") > -1) {
					map.put(linkTag.getLinkText().trim(), linkTag.getLink().trim());
				}

			}
			lists = new ArrayList<Map<String, String>>();
			Map<String, String> mapVideo = null;
			int sun = 0;
			for (ImageTag imageTag : imageTags) {
				if (sun < 10) {
					if (map.get(imageTag.getAttribute("alt").trim()) != null && !"".equals(imageTag.getAttribute("alt").trim())) {
						mapVideo = new HashMap<String, String>();
						mapVideo.put("videoTypeId", "4");
						mapVideo.put("videoName", "" + imageTag.getAttribute("alt").trim());
						mapVideo.put("videoSmallImagesUrl", "" + imageTag.getImageURL().trim());
						mapVideo.put("videoUrl", "" + map.get(imageTag.getAttribute("alt").trim()));
						mapVideo.put("videoLable", "" + imageTag.getAttribute("alt").trim().substring(0, 2));
						mapVideo.put("videoProfile", "" + imageTag.getAttribute("alt").trim());

						lists.add(mapVideo);
						sun += 1;
					}

				}
			}
		}

		return lists;
	}

	/**
	 * 鎻愬彇鍏锋湁鏌愪釜灞炴�у�肩殑鏍囩鍒楄〃
	 * 
	 * @param <T>
	 * @param html
	 *            琚彁鍙栫殑HTML鏂囨湰
	 * @param tagType
	 *            鏍囩绫诲瀷
	 * @param attributeName
	 *            鏌愪釜灞炴�х殑鍚嶇О
	 * @param attributeValue
	 *            灞炴�у簲鍙栫殑鍊�
	 * @return 鏍囩鍒楄〃
	 */
	public static <T extends TagNode> List<T> parseTags(String html, final Class<T> tagType, final String attrbuteName, final String attrbutValue) {
		try {
			Parser parser = new Parser();
			parser.setInputHTML(html);
			NodeList list = parser.parse(new NodeFilter() {

				public boolean accept(Node node) {
					if (node.getClass() == tagType) {
						T tagNode = (T) node;
						if (attrbuteName == null) {
							return true;
						}
						String attrValue = tagNode.getAttribute(attrbuteName);
						if (attrValue != null && attrValue.equals(attrbutValue)) {
							return true;
						}
					}

					return false;
				}
			});
			List<T> tagsList = new ArrayList<T>();
			for (int i = 0; i < list.size(); i++) {
				T t = (T) list.elementAt(i);
				tagsList.add(t);
			}
			return tagsList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param <T>
	 *            闇�瑕佹煡鎵剧殑鏍囩
	 * @param html
	 *            琚彁鍙栫殑HTML鏂囨湰
	 * @param tagType
	 *            鏍囩绫诲瀷
	 * @return 鏍囩鍒楄〃
	 */
	public static <T extends TagNode> List<T> parseTags(String html, final Class<T> tagType) {
		return parseTags(html, tagType, null, null);
	}

	/**
	 * 鑾峰彇鏌愪竴鎸囧畾鏍囩灞炴�х殑鍐呭
	 * 
	 * @param <T>
	 ** @param html
	 *            琚彁鍙栫殑HTML鏂囨湰
	 * @param tagType
	 *            鏍囩绫诲瀷
	 * @param attributeName
	 *            鏌愪釜灞炴�х殑鍚嶇О
	 * @param attributeValue
	 *            灞炴�у簲鍙栫殑鍊�
	 * @return 绗竴涓爣绛�
	 */
	public static <T extends TagNode> T parseTag0(String html, final Class<T> tagType, final String attrbuteName, final String attrbutValue) {
		List<T> tags = parseTags(html, tagType, attrbuteName, attrbutValue);
		if (tags != null && tags.size() > 0) {
			return tags.get(0);
		}
		return null;
	}

	/**
	 * 鑾峰彇鏌愪竴鎸囧畾鏍囩灞炴�х殑鍐呭
	 * 
	 * @param <T>
	 ** @param html
	 *            琚彁鍙栫殑HTML鏂囨湰
	 * @param tagType
	 *            鏍囩绫诲瀷
	 * @param attributeName
	 *            鏌愪釜灞炴�х殑鍚嶇О
	 * @param attributeValue
	 *            灞炴�у簲鍙栫殑鍊�
	 * @return 绗�3涓爣绛�
	 */
	public static <T extends TagNode> T parseTag3(String html, final Class<T> tagType, final String attrbuteName, final String attrbutValue) {
		List<T> tags = parseTags(html, tagType, attrbuteName, attrbutValue);
		if (tags != null && tags.size() > 2) {
			return tags.get(2);
		}
		return null;
	}

	/**
	 * 鑾峰彇鏌愪竴鎸囧畾鏍囩灞炴�х殑鍐呭
	 * 
	 * @param <T>
	 *            闇�瑕佹煡鎵剧殑鏍囩
	 * @param html
	 *            琚彁鍙栫殑HTML鏂囨湰
	 * @param tagType
	 *            鏍囩绫诲瀷
	 * @return 绗竴涓爣绛�
	 */
	public static <T extends TagNode> T parseTag0(String html, final Class<T> tagType) {
		return parseTag0(html, tagType, null, null);
	}

	/**
	 * 鑾峰彇鏌愪竴鎸囧畾鏍囩灞炴�х殑鍐呭
	 * 
	 * @param <T>
	 *            闇�瑕佹煡鎵剧殑鏍囩
	 * @param html
	 *            琚彁鍙栫殑HTML鏂囨湰
	 * @param tagType
	 *            鏍囩绫诲瀷
	 * @return 绗�3涓爣绛�
	 */
	public static <T extends TagNode> T parseTag3(String html, final Class<T> tagType) {
		return parseTag3(html, tagType, null, null);
	}
}
