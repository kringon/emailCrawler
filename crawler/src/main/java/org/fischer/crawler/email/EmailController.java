package org.fischer.crawler.email;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Base64.Decoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.fischer.crawler.dto.ContactInfo;
import org.fischer.crawler.service.EmailCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import groovyjarjarantlr.collections.List;

@RestController
public class EmailController {

	Logger logger = LoggerFactory.getLogger(EmailController.class);

	

	
	
	ArrayList<ContactInfo> infoList = new ArrayList<ContactInfo>();

	@RequestMapping("/updateList")
	public void fetchEmails() {
		EmailCrawler.counter = new AtomicInteger(1);
		EmailCrawler.infoList = new ArrayList<ContactInfo>();
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for(int i=0; i<3250; i++){
			pool.execute(new EmailCrawler());
			
		}
		pool.shutdown();
		try {
			pool.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/update/{id}")
	public void fetchEmails(@PathVariable String id) {
		
		
	}
	
	
	@RequestMapping("/fetchInfo")
	public ArrayList<ContactInfo> fetchInfo(){
		Collections.sort(EmailCrawler.infoList,new Comparator<ContactInfo>(){

			public int compare(ContactInfo o1, ContactInfo o2) {
				return o1.getId() - o2.getId();
			}
			
		});
		return EmailCrawler.infoList ;
		
	}
	
	@RequestMapping("/fetchFalse")
	public ArrayList<Integer> fetchFalse(){
		Collections.sort(EmailCrawler.falseIds,new Comparator<Integer>(){

			public int compare(Integer o1, Integer o2) {
				
				return o1 - o2;
			}

			
			
		});
		return EmailCrawler.falseIds ;
		
	}
}
