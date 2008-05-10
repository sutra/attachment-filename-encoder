/**
 * 
 */
package com.redv.attachmentfilenameencoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author sutra
 * 
 */
public class AttachmentFilenameEncoderTest extends TestCase {
	private static final Log log = LogFactory
			.getLog(AttachmentFilenameEncoderTest.class);

	/**
	 * Test method for
	 * {@link com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder#encodeForIE6(java.lang.String, java.lang.String)}.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void testEncodeForIE6StringString()
			throws UnsupportedEncodingException {
		assertEquals("test.txt", URLEncoder.encode("test.txt", "UTF-8"));
		assertEquals("test.txt", AttachmentFilenameEncoder.encodeForIE6("test",
				"txt"));
	}

	/**
	 * Test method for
	 * {@link com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder#encodeForIE6(java.lang.String, java.lang.String)}.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void testEncodeForIE6StringString150()
			throws UnsupportedEncodingException {
		String filename = "";
		for (int i = 0; i < 15; i++) {
			filename += "abcdefghij";
		}
		String urlencoded = URLEncoder.encode(filename, "UTF-8");
		assertEquals(filename, urlencoded);
		assertEquals(150, urlencoded.length());
		assertEquals(filename, AttachmentFilenameEncoder.encodeForIE6(filename,
				""));
	}

	/**
	 * Test method for
	 * {@link com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder#encodeForIE6(java.lang.String, java.lang.String)}.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void testEncodeForIE6StringString151()
			throws UnsupportedEncodingException {
		String filename150 = "";
		for (int i = 0; i < 15; i++) {
			filename150 += "abcdefghij";
		}
		String filename = filename150 + "1";
		String urlencoded = URLEncoder.encode(filename, "UTF-8");
		assertEquals(filename, urlencoded);
		assertEquals(151, urlencoded.length());
		String aee = AttachmentFilenameEncoder.encodeForIE6(filename, "");
		assertEquals(filename150, aee);
		assertEquals(150, aee.length());
	}

	/**
	 * Test method for
	 * {@link com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder#encodeForIE6(java.lang.String, java.lang.String)}.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void testEncodeForIE6StringStringChinese()
			throws UnsupportedEncodingException {
		assertEquals("%E6%B5%8B%E8%AF%95.txt", URLEncoder.encode("测试.txt",
				"UTF-8"));
		assertEquals("%E6%B5%8B.%E8%AF%95.txt", URLEncoder.encode("测.试.txt",
				"UTF-8"));
		assertEquals("%E6%B5%8B%E8%AF%95.txt", AttachmentFilenameEncoder
				.encodeForIE6("测试", "txt"));
	}

	/**
	 * Test method for
	 * {@link com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder#encodeForIE6(java.lang.String, java.lang.String)}.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void testEncodeForIE6StringStringChinese17()
			throws UnsupportedEncodingException {
		String s1 = URLEncoder.encode("一二三四五六七八九十一二三四五六", "UTF-8");
		log.debug("s1.length: " + s1.length());
		String s2 = AttachmentFilenameEncoder.encodeForIE6("一二三四五六七八九十一二三四五六七",
				"");
		log.debug("s2.length: " + s2.length());
		log.debug("s2 decoded:" + URLDecoder.decode(s2, "UTF-8"));
		assertEquals(s1, s2);
		assertEquals(16, URLDecoder.decode(s1, "UTF-8").length());
		assertEquals(144, s2.length());
	}

	/**
	 * Test method for
	 * {@link com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder#encodeForIE6(java.lang.String, java.lang.String)}.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void testEncodeForIE6StringStringChinese17WithExtension()
			throws UnsupportedEncodingException {
		String s1 = URLEncoder.encode("一二三四五六七八九十一二三四五六.txt", "UTF-8");
		log.debug("s1.length: " + s1.length());
		String s2 = AttachmentFilenameEncoder.encodeForIE6("一二三四五六七八九十一二三四五六七",
				"txt");
		log.debug("s2.length: " + s2.length());
		log.debug("s2 decoded:" + URLDecoder.decode(s2, "UTF-8"));
		assertEquals(s1, s2);
		assertEquals(20, URLDecoder.decode(s1, "UTF-8").length());
		assertEquals(148, s2.length());
	}

	/**
	 * Test method for
	 * {@link com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder#encodeForIE6(java.lang.String, java.lang.String)}.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void testEncodeForIE6StringStringSpace()
			throws UnsupportedEncodingException {
		String s1 = URLEncoder.encode("一二三四五六七八九十  一二三四五.txt", "UTF-8")
				.replaceAll("\\+", "%20");
		log.debug("s1.length: " + s1.length());
		String s2 = AttachmentFilenameEncoder.encodeForIE6(
				"一二三四五六七八九十  一二三四五六七", "txt");
		log.debug("s2.length: " + s2.length());
		log.debug("s2 decoded:" + URLDecoder.decode(s2, "UTF-8"));
		assertEquals(s1, s2);
		assertEquals(21, URLDecoder.decode(s1, "UTF-8").length());
		assertEquals(145, s2.length());
	}

	/**
	 * Test method for
	 * {@link com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder#encodeForIE6(java.lang.String, java.lang.String)}.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void testEncodeForIE6StringStringExtension150()
			throws UnsupportedEncodingException {
		// 149
		String extension = StringUtils.repeat("a", 149);
		String actual = AttachmentFilenameEncoder.encodeForIE6("", extension);
		assertEquals(150, actual.length());
		assertEquals("." + extension, actual);

		// 150
		extension = StringUtils.repeat("a", 150);
		actual = AttachmentFilenameEncoder.encodeForIE6("", extension);
		assertEquals(150, actual.length());
		assertEquals("." + StringUtils.repeat("a", 149), actual);

		// 151
		extension = StringUtils.repeat("a", 151);
		actual = AttachmentFilenameEncoder.encodeForIE6("", extension);
		assertEquals(150, actual.length());
		assertEquals("." + StringUtils.repeat("a", 149), actual);

		extension = StringUtils.repeat("a", 149);
		actual = AttachmentFilenameEncoder.encodeForIE6("b", extension);
		assertEquals(150, actual.length());
		assertEquals("b." + StringUtils.repeat("a", 148), actual);

		extension = StringUtils.repeat("a", 150);
		actual = AttachmentFilenameEncoder.encodeForIE6("bcd", extension);
		assertEquals(150, actual.length());
		assertEquals("b." + StringUtils.repeat("a", 148), actual);

		extension = StringUtils.repeat("a", 150);
		actual = AttachmentFilenameEncoder.encodeForIE6("哈cd", extension);
		assertEquals(150, actual.length());
		assertEquals("%E5%93%88." + StringUtils.repeat("a", 140), actual);

		extension = StringUtils.repeat("哈", 17);
		actual = AttachmentFilenameEncoder.encodeForIE6("哈cd", extension);
		log.debug("actual: " + actual);
		assertEquals(9 * (1 + 15) + "cd.".length(), actual.length());
		assertEquals("%E5%93%88cd." + StringUtils.repeat("%E5%93%88", 15),
				actual);

		extension = StringUtils.repeat("哈", 17);
		actual = AttachmentFilenameEncoder.encodeForIE6("哈哈", extension);
		log.debug("actual: " + actual);
		assertEquals(9 * (1 + 15) + ".".length(), actual.length());
		assertEquals("%E5%93%88." + StringUtils.repeat("%E5%93%88", 15), actual);
	}

	/**
	 * Test method for
	 * {@link com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder#encodeForIE6(java.lang.String)}.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void testEncodeForIE6String() throws UnsupportedEncodingException {
		assertEquals("test", AttachmentFilenameEncoder.encodeForIE6("test"));
		assertEquals("test.txt", AttachmentFilenameEncoder
				.encodeForIE6("test.txt"));
	}
}
