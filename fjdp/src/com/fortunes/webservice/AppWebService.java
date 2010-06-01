package com.fortunes.webservice;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

@WebService
@Service
public class AppWebService {
	
	
	public void demoMethod(){
		
		System.out.println("test");
		
	}
	
	public String hello(String world){
		return "hello, "+world;
	}
	
	public HouseBean bigObject(){
		HouseBean d = new HouseBean();
		d.setAccountId(1000);
		d.setHouseName("bighouse");
		return d;
	}
	

	
	
}


