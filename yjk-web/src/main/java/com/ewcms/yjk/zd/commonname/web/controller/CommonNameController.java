package com.ewcms.yjk.zd.commonname.web.controller;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author wu_zhijun
 */
@Controller
@RequestMapping(value = "/yjk/zd/commonname")
public class CommonNameController extends BaseCRUDController<CommonName, Long> {


    public CommonNameController() {
        setResourceIdentity("yjk:commonname");
    }
}
