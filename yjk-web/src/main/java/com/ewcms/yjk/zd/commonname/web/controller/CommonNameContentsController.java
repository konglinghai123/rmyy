package com.ewcms.yjk.zd.commonname.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;


/**
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/zd/commonnamecontents")
public class CommonNameContentsController extends BaseCRUDController<CommonNameContents, Long> {
    public CommonNameContentsController() {
        setResourceIdentity("yjk:commonnamecontents");
    }
}
