package com.sysu.moviepro.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sysu.moviepro.business.entity.User;
import com.sysu.moviepro.business.service.UserService;

@Controller
public class RegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/register", method= RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createUser(@ModelAttribute User user) {
		// 此处的user的合法性应该已经由前端保证,后台只负责验证重复性
		User result = userService.getUserByName(user.getName());
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (result.getId() == 0) {
			logger.info("Creating User. Data: "+user);
			int id = userService.createUser(user);
			modelMap.put("code", 0);
			return modelMap;
		} else {
			logger.info(""+user.getName()+" is already exist");
			modelMap.put("code", 1);
			return modelMap;
		}
	}
}
