/**
 * Created on 2008-04-29 22:29:41 at Hatsudai of Japan.
 */
package com.redv.attachmentfilenameencoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author sutra
 * 
 */
public class AttachmentFilenameEncoder {
	private static final Log log = LogFactory
			.getLog(AttachmentFilenameEncoder.class);

	private static final int IE6_MAX_LEN = 150;

	private static String urlencodeNoPlusSign(String originalString)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(originalString, "UTF-8").replaceAll("\\+",
				"%20");
	}

	private static String urlencodeNoPlusSign(char c)
			throws UnsupportedEncodingException {
		return urlencodeNoPlusSign(Character.toString(c));
	}

	private static void limitAppend(StringBuffer appendBuffer, String str,
			int limit) throws UnsupportedEncodingException {
		char[] chars = str.toCharArray();
		String s;
		for (int i = 0, l = chars.length; i < l; i++) {
			s = urlencodeNoPlusSign(chars[i]);
			if (appendBuffer.length() + s.length() <= limit) {
				appendBuffer.append(s);
			} else {
				break;
			}
		}
	}

	/**
	 * <code>Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; .NET CLR 1.1.4322)</code>
	 * <code>Mozilla/5.0 (Macintosh; U; Intel Mac OS X; zh-CN; rv:1.8.1.14) Gecko/20080404 Firefox/2.0.0.14</code>
	 * 
	 * <h2> Internet Explorer 6.0.3790.0 on Windows 2003</h2>
	 * <ol>
	 * <li>当长度为154时，下载对话框文件名显示为文件名加上“[1]”，保存也正常。</li>
	 * <li>当长度为155时，下载对话框文件名显示为文件名加上“[1]”，但是保存到C盘根目录（或者其他目录）下的话，
	 * 文件会跑到桌面去，并且文件名为前8个字符（如果桌面已经存在这8个字符为文件名的文件，
	 * 则文件名是8个字符加一个空格再加一个括弧括起来的数字，从2开始，依次递增，如abcdefgh (2)）。</li>
	 * <li>当长度为156时，下载对话框文件名显示为url的文件名
	 * （比如url为http://localhost:8080/test/download,则显示为download）
	 * 当你点击保存时就会给你一个错误：Internet Explorer 无法下载 download 来自 10.0.2.2。 Internet
	 * Explorer 无法打开该 Internet 站点。请求的站点不可用，或找不到。请以后再试。</li>
	 * </ol>
	 * 
	 * @param baseFilename
	 *            name of downloaded file without extention
	 * @param extension
	 *            extension of downloaded file without dot character
	 * @return encoded filename for IE
	 * @throws UnsupportedEncodingException
	 *             never happened
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html
	 * @see http://support.microsoft.com/?kbid=816868
	 */
	public static String encodeForIE6(final String baseName,
			final String extension) throws UnsupportedEncodingException {
		final StringBuffer ret = new StringBuffer();

		String encodedExtensionSeparator;
		int encodedExtensionSeparatorLen;

		// Cut extension.
		final StringBuffer encodedExtension = new StringBuffer();
		if (extension != null && extension.length() != 0) {
			encodedExtensionSeparator = urlencodeNoPlusSign(FilenameUtils.EXTENSION_SEPARATOR_STR);
			encodedExtensionSeparatorLen = encodedExtensionSeparator.length();

			int firstCharOfBaseNameLen = (baseName != null && baseName.length() != 0) ? urlencodeNoPlusSign(
					baseName.charAt(0)).length()
					: 0;

			int extensionMaxLen = IE6_MAX_LEN - firstCharOfBaseNameLen
					- encodedExtensionSeparatorLen;
			final String wholeUrlEncodedExtension = urlencodeNoPlusSign(extension);
			if (wholeUrlEncodedExtension.length() <= extensionMaxLen) {
				encodedExtension.append(wholeUrlEncodedExtension);
			} else {
				limitAppend(encodedExtension, extension, extensionMaxLen);
			}
		} else {
			// if no extension, separator should be empty.
			encodedExtensionSeparator = "";
			encodedExtensionSeparatorLen = 0;
		}

		// Cut base name.
		// UTF8 URL encoding only works in IE
		final String wholeUrlEncodedBaseName = urlencodeNoPlusSign(baseName);

		// Bug of IE (http://support.microsoft.com/?kbid=816868)
		// Encoded filename cannot be more than 150
		final int limit = IE6_MAX_LEN - encodedExtensionSeparatorLen
				- encodedExtension.length();
		if (wholeUrlEncodedBaseName.length() <= limit) {
			ret.append(wholeUrlEncodedBaseName);
		} else {
			limitAppend(ret, baseName, limit);
		}

		// Build return value.
		ret.append(encodedExtensionSeparator);
		ret.append(encodedExtension);
		return ret.toString();
	}

	/**
	 * 
	 * @param filename
	 *            whole filename
	 * @return encoded filename for Internet Explorer 6.0
	 * @throws UnsupportedEncodingException
	 *             never happened
	 */
	public static String encodeForIE6(String filename)
			throws UnsupportedEncodingException {
		return encodeForIE6(FilenameUtils.getBaseName(filename), FilenameUtils
				.getExtension(filename));
	}

	/**
	 * 
	 * @param filename
	 *            whole filename
	 * @return encoded filename for Firefox
	 * @throws UnsupportedEncodingException
	 *             never happened
	 */
	public static String encodeForFirefox(String filename)
			throws UnsupportedEncodingException {
		StringBuffer ret = new StringBuffer();
		ret.append("=?UTF-8?B?");
		ret.append(new String(Base64.encodeBase64(filename.getBytes("UTF-8"))));
		ret.append("?=");
		return ret.toString();
	}

	public static String encode(HttpServletRequest request, String filename)
			throws UnsupportedEncodingException {
		String ret;
		String agent = request.getHeader("user-agent");
		log.debug("agent: " + agent);
		if (agent == null) {
			ret = filename;
		} else if (agent.indexOf("Firefox") != -1) {
			ret = encodeForFirefox(filename);
		} else if (agent.indexOf("MSIE 7.0") != -1) {
			// TODO
			ret = encodeForIE6(filename);
		} else if (agent.indexOf("MSIE 6.0") != -1) {
			ret = encodeForIE6(filename);
		} else if (agent.indexOf("MSIE 5.5") != -1) {
			ret = encodeForIE6(filename);
		} else if (agent.indexOf("MSIE") != -1) {
			ret = encodeForIE6(filename);
		} else {
			ret = filename;
		}
		return ret;
	}
}
