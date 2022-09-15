package com.self.mycollegeapp.service.student.controller;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.mycollegeapp.service.student.model.StudentCourseRepository;

public abstract class AbstractController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
    @Autowired
    public StudentCourseRepository studentCourseRepository;
    
    public static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    public Hashtable successResponse(List list) {
    	Hashtable hashtable = new Hashtable();
        Map<String, Object> map = new HashMap();
        map.put("list", list);
        map.put("status", "success");
        hashtable.putAll(map);
    	return hashtable;
    }
    
    public Hashtable successResponse(Object obj) {
    	Hashtable hashtable = new Hashtable();
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(obj, Map.class);
        map.put("status", "success");
        hashtable.putAll(map);
    	return hashtable;
    }
    
    public Hashtable successResponse(Hashtable hashtable) {
        hashtable.put("status", "success");
    	return hashtable;
    }
    
    public Hashtable failedResponse(Object obj, String failedMsg) {
    	Hashtable hashtable = new Hashtable();
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(obj, Map.class);
        map.put("status", "failed");
        map.put("failedMsg", failedMsg);
        hashtable.putAll(map);
    	return hashtable;
    }
    
    public Hashtable<String, Object> failedResponse(Hashtable<String, Object> hashtable, String failedMsg) {
    	hashtable.put("status", "failed");
    	hashtable.put("failedMsg", failedMsg);
    	return hashtable;
    }

}
