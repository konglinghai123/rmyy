package com.ewcms.hzda.zd.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.hzda.zd.entity.CertificateType;

@Controller
@RequestMapping(value = "/hzda/zd/certificatetype")
public class CertificateTypeController extends BaseCRUDController<CertificateType, Long> {

}
