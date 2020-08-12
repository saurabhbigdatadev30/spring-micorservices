package com.apps.ui.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/UserService")
public class UsersController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	private Environment env;
	
	
	@GetMapping("/status/check")
	public String status()
	{
		System.out.println(">>>>>>>>><<<<<<<<<<<<<< STATUS EXECUTED");
		LOGGER.info(">>>>>>>>><<<<<<<<<<<<<<<< testGetVerifiedUsers +++++++++++++++++");
		LOGGER.debug(">< testGetVerifiedUsers +++++++++++++++++");
		return "Working on port + =" + env.getProperty("local.server.port");
	}
 
	
	
}
