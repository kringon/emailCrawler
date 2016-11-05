package org.fischer.crawler.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.ArrayUtils;
import org.fischer.crawler.dto.ContactInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailCrawler implements Runnable {

	public static AtomicInteger counter;
	public static ArrayList<ContactInfo> infoList;

	Logger logger = LoggerFactory.getLogger(EmailCrawler.class);

	@Autowired
	public static ArrayList<Integer> falseIds = new ArrayList<Integer>();

	public void run() {
		int i = counter.incrementAndGet();

		if (!falseIds.contains(i)) {
			try {
				logger.info("fetching: " + "https://www.fotball.no/fotballdata/klubb/hjem/?fiksId=" + i);
				Document doc = Jsoup.connect("https://www.fotball.no/fotballdata/klubb/hjem/?fiksId=" + i).get();

				Elements elements = doc.select(".grid");
				Element kontaktinformasjon = elements.get(1).child(0);

				if (kontaktinformasjon != null && kontaktinformasjon.html().contains("Kontaktinformasjon")) {

					ContactInfo info = new ContactInfo();

					// Hjemmeside
					String hjemmeside = kontaktinformasjon.getElementsByAttributeValueContaining("href", "http").attr("href");
					// logger.info("Hjemmeside: " + hjemmeside);
					info.setHjemmeside(hjemmeside);

					// Leder
					Elements test = kontaktinformasjon.getElementsContainingOwnText("Leder:");
					String leder = kontaktinformasjon.getElementsContainingOwnText("Leder").first().nextElementSibling().html();
					// logger.info("Leder: " + leder);
					info.setLeder(leder);

					// Daglig Leder
					if (kontaktinformasjon.getElementsContainingOwnText("Daglig leder").size() > 0) {
						String dagligLeder = kontaktinformasjon.getElementsContainingOwnText("Daglig leder").first().nextElementSibling().html();
						// logger.info("Daglig Leder: " + dagligLeder);
						info.setDagligLeder(dagligLeder);
					}

					// Klubbnavn
					String klubbnavn = doc.title().split("-")[0];
					logger.info("Klubbnavn: " + klubbnavn);
					info.setKlubbnavn(klubbnavn);

					// Epost
					String epost = new String(Base64.getDecoder().decode(kontaktinformasjon.getElementsByAttributeValueStarting("href", "#").attr("data-address")),
							StandardCharsets.UTF_8);
					 logger.info("Epost: " + epost);
					if (epost.contains("@"))
						info.setEmailAddress(epost);

					// Telefon
					String telefon = new String(Base64.getDecoder().decode(kontaktinformasjon.getElementsByAttributeValueStarting("class", "tel-link").attr("data-address")),
							StandardCharsets.UTF_8);
					// logger.info("Telefon: " + telefon);
					info.setTelephoneNumber(telefon);

					// Kilde
					info.setKilde("https://www.fotball.no/fotballdata/klubb/hjem/?fiksId=" + i);
					info.setId(i);
					// logger.info("");
					infoList.add(info);

				}
			} catch (IOException e) {
				logger.error("Something went wrong on iteration: " + i);
				logger.error(e.getMessage());
				falseIds.add(i);
				e.printStackTrace();

			}
		}

	}

}
