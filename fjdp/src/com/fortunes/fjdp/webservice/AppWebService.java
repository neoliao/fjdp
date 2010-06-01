package com.fortunes.fjdp.webservice;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

@WebService
@Service
public class AppWebService {
	
	
	public String hello(String world){
		return "hello, "+world;
	}
	
	
}


