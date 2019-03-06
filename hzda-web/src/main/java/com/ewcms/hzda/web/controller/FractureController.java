package com.ewcms.hzda.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.ImageUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.hzda.entity.BoneDensity;
import com.ewcms.hzda.entity.Fracture;
import com.ewcms.hzda.web.controller.util.HzdaUtil;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping(value = "/hzda/fracture")
public class FractureController extends BaseCRUDController<Fracture, Long> {

	public FractureController() {
		// setListAlsoSetCommonData(true);
		setResourceIdentity("hzda:fracture");
	}

	@Override
	public String index(Model model) {
		return HzdaUtil.HZDA_GENERAL_INFORMATION_INDEX_URL;
	}

	@RequestMapping(value = "index/{generalInformationId}")
	public String index(@PathVariable(value = "generalInformationId") Long generalInformationId, Model model) {
		return super.index(model);
	}

	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "query/{generalInformationId}")
	@ResponseBody
	public List<Fracture> query(@ModelAttribute SearchParameter<Long> searchParameter,
			@PathVariable(value = "generalInformationId") Long generalInformationId) {
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, BoneDensity.class);
		searchable.addSearchFilter("generalInformationId", SearchOperator.EQ, generalInformationId);
		searchable.addSort(Direction.DESC, "id");

		return baseService.findAllWithSort(searchable);
	}

	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "save/{generalInformationId}", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "generalInformationId") Long generalInformationId, Model model,
			@RequestParam(required = false) List<Long> selections) {
		return super.showSaveForm(model, selections);
	}

	@Override
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	public String save(Model model, Fracture m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "save/{generalInformationId}", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") Fracture m,
			@RequestParam(value = "uploadPicture") MultipartFile uploadPicture, BindingResult result,
			@CurrentUser User user, @PathVariable(value = "generalInformationId") Long generalInformationId,
			@RequestParam(required = false) List<Long> selections, RedirectAttributes redirectAttributes) {
		if (hasError(m, result)) {
			return showSaveForm(model, selections);
		}

		setCommonData(model);

		if (permissionList != null) {
			this.permissionList.assertHasCreatePermission();
		}

		m.setUserId(user.getId());
		m.setOrganizationId(user.getOrganizationJobs().get(0).getOrganizationId());
		m.setGeneralInformationId(generalInformationId);
		try {
			m.setFormatName(ImageUtil.getFormatName(uploadPicture));
			m.setUploadPicture(uploadPicture.getBytes());
		} catch (IOException e) {
			m.setFormatName(null);
			m.setUploadPicture(null);
		}
		if (m.getId() != null) {
			model.addAttribute("lastM", JSON.toJSONString(baseService.update(m)));
		} else {

			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(baseService.save(m)));
		}

		return showSaveForm(generalInformationId, model, selections);
	}

	@RequestMapping(value = "delete/discard")
	@ResponseBody
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "delete/{generalInformationId}")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections,
			@PathVariable(value = "generalInformationId") Long generalInformationId) {
		return super.delete(selections);
	}

	@ResponseBody
	@RequestMapping("/lookpicture")
	public void lookPicture(@PathVariable(value = "fractureId") Fracture fracture, final HttpServletResponse response) {
		byte[] license_img = fracture.getUploadPicture();
		OutputStream os = null;
		if (license_img != null) {
			try {
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(license_img));
				response.setContentType("application/x-msdownload");
				os = response.getOutputStream();
				ImageIO.write(image, "gif", os);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
