package com.techelevator.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.ComicBook;
import com.techelevator.model.Publisher;
import com.techelevator.model.Volume;



@RestController
@RequestMapping("/search")
public class ComicVineController {
	
	 @RequestMapping("/addVolume/{volumeTitle}")
	public List<Volume> requestVolume(@PathVariable String volumeTitle, HttpServletRequest request) throws JsonProcessingException, IOException {
		
		String apiURL = "https://comicvine.gamespot.com/api/";
		String volumes = "volumes/?";
		String keyValue = "afbfa6151990fa3c6e4be5af3a29e9b47cd780a7";
		String getVolumes = apiURL + volumes + "api_key=" + keyValue + "&format=json&filter=name:" + volumeTitle;
				
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("user-agent", "Mozilla/5.0 Firefox/26.0");
		
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResponseEntity<String> response = restTemplate.exchange(getVolumes, HttpMethod.GET, httpEntity, String.class);

		
		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		List <Volume> volumeList = new ArrayList<Volume>();

		for (int i=0; i < jsonNode.findPath("results").size(); i++) {
			Volume volume = new Volume();
			
			String volumeNum = jsonNode.path("results").path(i).path("id").toString();
			String volumeImg = jsonNode.path("results").path(i).path("image").path("original_url").toString();
			String volumeName = jsonNode.path("results").path(i).path("name").toString();
			String publisher = jsonNode.path("results").path(i).path("publisher").path("name").toString();
			String volumeDescription = jsonNode.path("results").path(i).path("deck").toString();
			String numberOfIssues = jsonNode.path("results").path(i).path("count_of_issues").toString();
			String startYear = jsonNode.path("results").path(i).path("start_year").toString();
			
			volumeImg = volumeImg.substring(1, volumeImg.length() - 1);
			volumeName = volumeName.substring(1, volumeName.length() - 1);
			//publisher = publisher.substring(1, publisher.length());
			startYear = startYear.substring(1, startYear.length() - 1);
			
			if (volumeDescription.contentEquals("null")) {
				volumeDescription = "None Available";
			}else volumeDescription = volumeDescription.substring(1, volumeDescription.length()-1);
			if (volumeDescription.length() > 100) {
				volumeDescription.substring(0, 99);
				
			}
			
			volume.setVolumeNumber(volumeNum);
			volume.setVolumeImg(volumeImg);
			volume.setVolumeName(volumeName);
			volume.setPublisher(publisher);
			volume.setVolumeDescription(volumeDescription);
			volume.setNumberOfIssues(numberOfIssues);
			volume.setStartYear(startYear);
			
			volumeList.add(volume);

		}

				
		return volumeList;
		
	}
	
	@RequestMapping("/requestComic/{volumeNumber}/{issueNumber}")
	public ComicBook requestComic(@PathVariable String volumeNumber, @PathVariable String issueNumber, HttpServletRequest request) throws JsonProcessingException, IOException {

		String apiURL = "https://comicvine.gamespot.com/api/";
		String issues = "issues/?";
		String keyValue = "afbfa6151990fa3c6e4be5af3a29e9b47cd780a7";
		String getComic = apiURL + issues + "api_key=" + keyValue + "&format=json&filter=issue_number:" + issueNumber + ",volume:" + volumeNumber;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("user-agent", "Mozilla/5.0 Firefox/26.0");
		
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper objectMapper = new ObjectMapper();

		ResponseEntity<String> response = restTemplate.exchange(getComic, HttpMethod.GET, httpEntity, String.class);

		JsonNode jsonNode = objectMapper.readTree(response.getBody());


		String issueImg =jsonNode.path("results").path(0).path("image").path("original_url").toString();
		String issueTitle =jsonNode.path("results").path(0).path("name").toString();
		String issueDescription =jsonNode.path("results").path(0).path("description").toString();
		String pubDate =jsonNode.path("results").path(0).path("cover_date").toString();
		String volumeName =jsonNode.path("results").path(0).path("volume").path("name").toString();
	
		pubDate = pubDate.substring(1, pubDate.length() - 1);
		LocalDate publishDate = LocalDate.parse(pubDate);	
		issueImg = issueImg.substring(1, issueImg.length() - 1);
		volumeName = volumeName.substring(1, volumeName.length() - 1);
		issueTitle = issueTitle.substring(1, issueTitle.length() - 1);
	
		if (issueDescription.contentEquals("null")) {
			issueDescription = "None Available";
		}else { issueDescription = issueDescription.substring(1, issueDescription.length()-1);
		issueDescription = issueDescription.replaceAll("\\<.*?\\>", "");}
		if (issueDescription.length() > 1000) {
		issueDescription =	issueDescription.substring(0, 999);
		}
				
		ComicBook comicToAdd = new ComicBook();
		comicToAdd.setTitle(volumeName);
		comicToAdd.setIssueTitle(issueTitle);
		comicToAdd.setIssueNumber(Integer.parseInt(issueNumber));
		comicToAdd.setPublishDate(publishDate);
		comicToAdd.setDescription(issueDescription);
		comicToAdd.setImage(issueImg);
				
		return comicToAdd;
	}
	
	@RequestMapping("/viewVolume/{volumeNumber}")
	public List<ComicBook> viewVolume(@PathVariable Integer volumeNumber) throws JsonProcessingException, IOException {
		
		String apiURL = "https://comicvine.gamespot.com/api/";
		String issues = "issues/?";
		String keyValue = "afbfa6151990fa3c6e4be5af3a29e9b47cd780a7";
		String getComics = apiURL + issues + "api_key=" + keyValue + "&format=json&filter=volume:" + volumeNumber;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("user-agent", "Mozilla/5.0 Firefox/26.0");
		
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper objectMapper = new ObjectMapper();
		
		
		ResponseEntity<String> response = restTemplate.exchange(getComics, HttpMethod.GET, httpEntity, String.class);
		
		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		List <ComicBook> volumeComics = new ArrayList<ComicBook>();

		for (int i=0; i < jsonNode.findPath("results").size(); i++) {
			ComicBook volumeIssues = new ComicBook();
		
			
			
		String issueImg = jsonNode.path("results").path(i).path("image").path("original_url").toString();
		String issueTitle = jsonNode.path("results").path(i).path("name").toString();
		String issueDescription = jsonNode.path("results").path(i).path("description").toString();
		String issNumber = jsonNode.path("results").path(i).path("issue_number").toString();
		String pubDate = jsonNode.path("results").path(i).path("cover_date").toString();
		String volumeName = jsonNode.path("results").path(i).path("volume").path("name").toString();
	
		issNumber = issNumber.substring(1, issNumber.length() - 1);
		int issueNumber = Integer.parseInt(issNumber);		
		issueImg = issueImg.substring(1, issueImg.length() - 1);
		volumeName = volumeName.substring(1, volumeName.length() - 1);		
		issueTitle = issueTitle.substring(1, issueTitle.length() - 1);
		
		pubDate = pubDate.substring(1, pubDate.length() - 1);
		
		if (issueDescription.contentEquals("null")) {
			issueDescription = "None Available";
		}else {issueDescription = issueDescription.substring(1, issueDescription.length()-1);
		issueDescription = issueDescription.replaceAll("\\<.*?\\>", "");}
		
		if (issueDescription.length() > 1000) {
			issueDescription = issueDescription.substring(0, 999);
		}		

		LocalDate publishDate = LocalDate.parse(pubDate);
			



			volumeIssues.setTitle(volumeName);
			volumeIssues.setImage(issueImg);
			volumeIssues.setIssueTitle(issueTitle);
			volumeIssues.setIssueNumber(issueNumber);
			volumeIssues.setPublishDate(publishDate);
			volumeIssues.setDescription(issueDescription);
			volumeIssues.setVolumeNumber(volumeNumber);




		volumeComics.add(volumeIssues);
		}
		return volumeComics;
		
	}
	@RequestMapping("/getPub/{volumeNumber}")
	public Publisher  getPublisher(@PathVariable Integer volumeNumber, HttpServletRequest request) throws JsonProcessingException, IOException {
		
		String apiURL = "https://comicvine.gamespot.com/api/";
		String volumes = "volumes/?";
		String keyValue = "afbfa6151990fa3c6e4be5af3a29e9b47cd780a7";
		String getVolumes = apiURL + volumes + "api_key=" + keyValue + "&format=json&filter=id:" + volumeNumber;
				
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("user-agent", "Mozilla/5.0 Firefox/26.0");
		
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResponseEntity<String> response = restTemplate.exchange(getVolumes, HttpMethod.GET, httpEntity, String.class);

		
		JsonNode jsonNode = objectMapper.readTree(response.getBody());

		
	
			
			String publisher = jsonNode.path("results").path(0).path("publisher").path("name").toString();
			
			publisher = publisher.substring(1, publisher.length() - 1);

		Publisher publish = new Publisher();
		publish.setPublisher(publisher);
				
		return publish;
		
	}

}