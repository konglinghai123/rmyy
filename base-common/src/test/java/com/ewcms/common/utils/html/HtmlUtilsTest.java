package com.ewcms.common.utils.html;

import java.util.LinkedHashSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author wu_zhijun
 *
 */
public class HtmlUtilsTest {

	@Test
	public void testHtmlText() {
		String html = "<a>测试使用</a>&lt;a&gt;呵呵&lt;/a&gt;";
		Assert.assertEquals("测试使用呵呵", HtmlUtils.text(html));
	}

	@Test
	public void testHtmlTextWithMaxLength() {
		String html = "<a>测试使用</a>&lt;a&gt;呵呵&lt;/a&gt;";

		Assert.assertEquals("测试……", HtmlUtils.text(html, 2));
	}

	@Test
	public void testRemoveUnSafeTag() {
		String html = "<a onclick='alert(1)' onBlur='alert(1)'>测试使用</a><script>alert(1)</script><Script>alert(1)</SCRIPT>";
		Assert.assertEquals("<a>测试使用</a>", HtmlUtils.removeUnSafeTag(html));
	}

	@Test
	public void testRemoveTag() {
		String html = "<a onclick='alert(1)' onBlur='alert(1)'>测试使用</a><A>1</a>";
		Assert.assertEquals("", HtmlUtils.removeTag(html, "a"));
	}

	@Test
	public void testLinkedHashSet() {
		// 新建一个LinkHashSet集合，类型规定为字符串类型String（自己可以规定任意类型）
		LinkedHashSet<String> set = new LinkedHashSet<>();
		// 存入数据
		set.add("c");
		set.add("d");
		set.add("a");
		set.add("e");
		set.add("f");
		set.add("a");
		set.add("g");
		set.add("h");
		set.add("a");
		set.add("b");
		// 利用新式for循环遍历集合
		for (String s : set) {
			System.out.println(s);
		}
	}

}
