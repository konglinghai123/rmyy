package com.ewcms.personal.message.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.Constants;
import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.personal.message.entity.Message;
import com.ewcms.personal.message.entity.MessageContent;
import com.ewcms.personal.message.entity.MessageState;
import com.ewcms.personal.message.service.MessageApi;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

/**
 *
 * @author wu_zhijun
 */
@Controller
@RequestMapping("/personal/message")
public class MessageController extends BaseController<Message, Long>{
	
	@Autowired
	private MessageApi messageApi;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
	public String index(Model model) throws IOException{
		model.addAttribute("states", MessageState.values());
		return viewName("index");
	}
	
	@RequestMapping(value = "{state}/index", method = RequestMethod.GET)
	public String index(@PathVariable("state") MessageState state, Model model){
		return viewName("query");
	}
	
	@RequestMapping(value = "{state}/query")
	public @ResponseBody Map<String, Object> query(@CurrentUser User user, @PathVariable("state") MessageState state, @ModelAttribute SearchParameter<Long> searchParameter){
		Pageable pageable = SearchHelper.getPageable(searchParameter);
		
		Page<Message> messages = messageApi.findUserMessage(user.getId(), state, pageable);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", messages.getTotalElements());
		resultMap.put("rows", messages.getContent());
		
		return resultMap;
	}
	
	@RequestMapping("{m}")
	public String view(@CurrentUser User user, @PathVariable("m") Message m, Model model, RedirectAttributes redirectAttributes){
		if (m == null){
			redirectAttributes.addFlashAttribute(Constants.ERROR, "您查看的消息不存在");
			return redirectToUrl("index");
		}
		if (user.getId().equals(m.getReceiverId())){
			messageApi.markRead(m);
		}
		
		List<Message> messages = messageApi.findAncestorsAndDescendants(m);
		model.addAttribute("messages", messages);
		
		return viewName("view");
	}
	
	@RequestMapping("{m}/content")
	public String viewContent(@CurrentUser User user, @PathVariable("m") Message m, Model model, RedirectAttributes redirectAttributes){
		if (user.getId().equals(m.getReceiverId())){
			messageApi.markRead(m);
		}
		return viewName("viewContent");
	}
	
	@RequestMapping(value = "send", method = RequestMethod.GET)
	public String showSendForm(Model model){
		if (!model.containsAttribute("m")){
			model.addAttribute("m", newModel());
		}
		model.addAttribute(Constants.OP_NAME, "发送新消息");
		return viewName("send");
	}
	
	@RequestMapping(value = "send", method = RequestMethod.POST)
	public String send(@CurrentUser User user, @Valid @ModelAttribute("m") Message message, BindingResult result, @RequestParam(value = "receiver", required = false) String receiverUsername, Model model, RedirectAttributes redirectAttributes){
		User receiver = userService.findByUsername(receiverUsername);
		if (receiver == null){
			result.rejectValue("receiverId", "receiver.not.exists");
		}
		if (receiver.equals(user)){
			result.rejectValue("receiverId", "receiver.not.self");
		}
		if (result.hasErrors()){
			return showSendForm(model);
		}
		message.setReceiverId(receiver.getId());
		message.setSenderId(user.getId());
		messageApi.send(message);
		
		return redirectToUrl(viewName(MessageState.out_box + "/index"));
	}
	
	@RequestMapping(value = "{parent}/reply", method = RequestMethod.GET)
	public String showReplyForm(@PathVariable("parent") Message parent, Model model){
		if (!model.containsAttribute("m")){
			Message m = newModel();
			m.setParentId(parent.getId());
			m.setParentIds(parent.getParentIds());
			m.setReceiverId(parent.getSenderId());
			m.setTitle(MessageApi.REPLAY_PREFIX + parent.getTitle());
			model.addAttribute("m", m);
		}
		model.addAttribute(Constants.OP_NAME, "回复消息");
		return viewName("send");
	}
	
	@RequestMapping(value = "{parent}/reply", method = RequestMethod.POST)
	public String reply(@CurrentUser User user, @PathVariable("parent") Message parent, @ModelAttribute("m") Message m, BindingResult result, RedirectAttributes redirectAttributes, Model model){
		if (result.hasErrors()){
			return showReplyForm(parent, model);
		}
		m.setSenderId(user.getId());
		messageApi.send(m);
		
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "回复成功");
		return redirectToUrl(viewName(MessageState.out_box + "/index"));
	}
	
	@RequestMapping(value = "{parent}/forward", method = RequestMethod.GET)
	public String showForwardForm(@PathVariable("parent") Message parent, Model model){
		String receiverUsername = userService.findOne(parent.getReceiverId()).getUsername();
		String senderUsername = userService.findOne(parent.getSenderId()).getUsername();
		
		if (!model.containsAttribute("m")){
			Message m = newModel();
			m.setTitle(MessageApi.FOWRARD_PREFIX + parent.getTitle());
			m.setContent(new MessageContent());
			m.getContent().setContent(String.format(MessageApi.FOWRARD_TEMPLATE, senderUsername, receiverUsername, parent.getTitle(), parent.getContent().getContent()));
			model.addAttribute("m", m);
		}
		model.addAttribute(Constants.OP_NAME, "转发消息");
		return viewName("send");
	}
	
	@RequestMapping(value = "{parent}/forward", method = RequestMethod.POST)
	public String forward(@CurrentUser User user, @RequestParam(value = "username", required = false) String username, @PathVariable("parent") Message parent, @ModelAttribute("m") Message m, BindingResult result, RedirectAttributes redirectAttributes, Model model){
		User receiver = userService.findByUsername(username);
		if (receiver == null){
			result.rejectValue("receiverId", "receiver.not.exists");
		}
		if (receiver.equals(user)){
			result.rejectValue("receiverId", "receiver.not.self");
		}
		if (result.hasErrors()){
			return showForwardForm(parent, model);
		}
		m.setReceiverId(receiver.getId());
		m.setSenderId(user.getId());
		messageApi.send(m);
		
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "转发成功");
		return redirectToUrl(viewName(MessageState.out_box + "/index"));
	}
	
	@RequestMapping(value = "draft/save", method = RequestMethod.POST)
	public String saveDraft(@CurrentUser User user, @RequestParam(value = "username", required = false) String username, @ModelAttribute("m") Message m, RedirectAttributes redirectAttributes){
		User receiver = userService.findByUsername(username);
		if (receiver != null){
			m.setReceiverId(receiver.getId());
		}
		m.setSenderId(user.getId());
		
		messageApi.saveDraft(m);
		
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "保存草稿成功");
		return redirectToUrl(viewName(MessageState.draft_box + "/index"));
	}
	
	@RequestMapping(value = "draft/{m}/send", method = RequestMethod.GET)
	public String showResendDraftForm(@PathVariable("m") Message m, Model model){
		if (m.getReceiverId() != null){
			User user = userService.findOne(m.getReceiverId());
			if (user != null){
				model.addAttribute("username", user.getUsername());
			}
		}
		model.addAttribute("m", m);
		String viewName = showSendForm(model);
		model.addAttribute(Constants.OP_NAME, "发送草稿");
		return viewName;
	}
	
	@RequestMapping(value = "draft/{m}/send", method = RequestMethod.POST)
	public String resendDraft(@CurrentUser User user, @Valid @ModelAttribute("m") Message m, BindingResult result, @RequestParam(value = "username", required = false) String username, Model model, RedirectAttributes redirectAttributes){
		String viewName = send(user, m, result, username, model, redirectAttributes);
		model.addAttribute(Constants.OP_NAME, "发送草稿");
		return viewName;
	}
	
	@RequestMapping("batch/store")
	public String batchStore(@CurrentUser User user, @RequestParam(value = "ids", required = false) Long[] ids, RedirectAttributes redirectAttributes){
		messageApi.store(user.getId(), ids);
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "收藏成功");
		return redirectToUrl(viewName(MessageState.store_box + "/index"));
	}
	
	@RequestMapping("batch/delete")
	public String batchRecycle(@CurrentUser User user, @RequestParam(value = "ids", required = false) Long[] ids, RedirectAttributes redirectAttributes){
		messageApi.delete(user.getId(), ids);
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功");
		return redirectToUrl(viewName(MessageState.trash_box + "/index"));
	}
	
	@RequestMapping("clear/{state}")
	public String clear(@CurrentUser User user, @PathVariable("state") MessageState state, RedirectAttributes redirectAttributes){
		messageApi.clearBox(user.getId(), state);
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, String.format("清空%s成功", state.getInfo()));
		return redirectToUrl(viewName(MessageState.trash_box + "/index"));
	}
	
	@RequestMapping("markRead")
	public String markRead(@CurrentUser User user, @RequestParam(value = "ids", required = false) Long[] ids, @RequestParam("BackURL") String backURL, RedirectAttributes redirectAttributes){
		messageApi.markRead(user.getId(), ids);
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "成功标记为已读");
		return redirectToUrl(backURL);
	}
	
	@RequestMapping(value = "unreadCount")
	public @ResponseBody String unreadCount(@CurrentUser User user){
		return String.valueOf(messageApi.countUnread(user.getId()));
	}
}
