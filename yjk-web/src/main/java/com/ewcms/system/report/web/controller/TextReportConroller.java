package com.ewcms.system.report.web.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.system.externalds.service.BaseDsService;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.service.TextReportService;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/report/text")
public class TextReportConroller extends BaseCRUDController<TextReport, Long> {

	private TextReportService getTextReportService() {
		return (TextReportService) baseService;
	}

	@Autowired
	private BaseDsService baseDsService;

	public TextReportConroller() {
		setResourceIdentity("system:textreport");
	}

	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("baseDsList", baseDsService.findAllBaseDs());
	}

	@Override
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") TextReport m, BindingResult result,
			@RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("m") TextReport m, BindingResult result,
			@RequestParam(required = false) List<Long> selections, Model model, RedirectAttributes redirectAttributes,
			@RequestParam(value = "textReportFile", required = false) MultipartFile textReportFile,
			HttpServletRequest request) {
		if (hasError(m, result)) {
			return showSaveForm(model, selections);
		}

		setCommonData(model);

		InputStream in = null;
		try {
			Boolean annexFlag = Boolean.FALSE;
			request.setCharacterEncoding("UTF-8");
			if (textReportFile != null && textReportFile.getSize() > 0) {
				byte[] buffer = new byte[Integer.parseInt(String.valueOf(textReportFile.getSize()))];
				in = new BufferedInputStream(textReportFile.getInputStream(),
						Integer.parseInt(String.valueOf(textReportFile.getSize())));
				in.read(buffer);
				m.setTextEntity(buffer);
				in.close();
				annexFlag = Boolean.TRUE;
			}

			if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
				if (permissionList != null) {
					this.permissionList.assertHasUpdatePermission();
				}

				TextReport oldTextReport = baseService.findOne(m.getId());
				if (!annexFlag) {
					m.setTextEntity(oldTextReport.getTextEntity());
				}
				TextReport lastM = getTextReportService().update(m);

				selections.remove(0);
				if (selections == null || selections.isEmpty()) {
					model.addAttribute("close", true);
				}
				model.addAttribute("lastM", JSON.toJSONString(lastM));
			} else {
				if (permissionList != null) {
					this.permissionList.assertHasCreatePermission();
				}

				TextReport lastM = getTextReportService().save(m);

				model.addAttribute("m", newModel());
				model.addAttribute("lastM", JSON.toJSONString(lastM));
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		} finally {
			IOUtils.closeQuietly(in);
		}
		return showSaveForm(model, selections);
	}

	@RequestMapping(value = "{reportId}/download")
	public void download(@PathVariable(value = "reportId") TextReport textReport, HttpServletResponse response) {
		PrintWriter pw = null;
		InputStream in = null;
		try {
			if (textReport.getTextEntity() != null && textReport.getTextEntity().length != 0) {
				String fileName = String.valueOf(textReport.getName());
				fileName = URLEncoder.encode(fileName, "UTF-8");
				// fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");

				response.setContentType("application/jrxml");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".jrxml");

				byte[] bytes = new byte[textReport.getTextEntity().length];
				bytes = textReport.getTextEntity();

				pw = response.getWriter();

				response.setContentLength(bytes.length);
				in = new ByteArrayInputStream(bytes);
				int len = 0;
				while ((len = in.read()) > -1) {
					pw.write(len);
				}
				pw.flush();
			}
		} catch (IOException e) {
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(pw);
		}
	}
}
