package com.ewcms.empi.card.manage.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.empi.card.manage.entity.MessageLog;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/messagelog")
public class MessageLogController extends BaseCRUDController<MessageLog,Long> {

}
