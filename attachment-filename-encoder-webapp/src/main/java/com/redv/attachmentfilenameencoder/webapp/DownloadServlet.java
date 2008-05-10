/**
 * Created on 2008-04-29 22:29:41 in Hatsudai of Japan.
 */
package com.redv.attachmentfilenameencoder.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.attachmentfilenameencoder.AttachmentFilenameEncoder;

/**
 * @author sutra
 * 
 */
public class DownloadServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(DownloadServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -4352161025548152982L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String filename = req.getParameter("filename");
		String encode = req.getParameter("encode");
		String attachmentFilename;
		if (encode != null) {
			attachmentFilename = AttachmentFilenameEncoder
					.encode(req, filename);
			StringBuffer sb = new StringBuffer();
			sb.append("filename: ").append(filename).append(
					", attachmentFilename: ").append(attachmentFilename)
					.append(", length: ").append(attachmentFilename.length());
			log.info(sb);
		} else {
			attachmentFilename = filename;
		}
		resp.setHeader("Content-Disposition", "attachment; filename=\""
				+ attachmentFilename + "\"");
		resp.setContentType("application/octet-stream; charset=UTF-8");
	}
}
