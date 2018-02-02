package com.matmatch;

import java.io.IOException;

import org.jsoup.Jsoup;

public class TestJsoupTable {

	public static void main(String[] args) throws IOException {
		// Document dom =
		// Jsoup.connect("https://matmatch.com/materials/aasx002-advanced-alloy-services-rene-80").get();
		// if (null != dom) {
		// // System.out.println(dom.select("table tbody tr"));
		// // System.out.println(Xsoup.select(dom, "//table/tbody/tr").list());
		// System.out.println(Selectors.xpath("//table/tbody/tr").selectList(dom));
		// }
		System.out.println(Jsoup.parse("<tr><td>aaa</td></tr>"));
		System.out.println(Jsoup.parse("<table><td>aaa</td></table>"));
		String a = "<tr><td>aaa</td></tr>";
		System.out.println(a.matches("^<(?:tbody|th|tr|td)>.*$"));
	}
}
