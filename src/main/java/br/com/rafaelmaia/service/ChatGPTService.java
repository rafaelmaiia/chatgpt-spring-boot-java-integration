package br.com.rafaelmaia.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rafaelmaia.vo.request.ChatGptRequest;
import br.com.rafaelmaia.vo.response.ChatGptResponse;

@Service
public class ChatGPTService {
	
	private Logger logger = Logger.getLogger(ChatGPTService.class.getName());
	
	@Value("${openai.model}")
	private String model;
	
	@Value("${openai.api.url}")
	private String url;
	
	@Autowired
	private RestTemplate template;
	
	public String chat(String prompt) {
		
		logger.info("Starting Prompt");
		
		ChatGptRequest request = new ChatGptRequest(model, prompt);
		
		/* chamada para verificar o json que está sendo enviado
		String json = new ObjectMapper().writeValueAsString(request);
		logger.info(json); */
		
		logger.info("Processing Prompt");
		ChatGptResponse response = template.postForObject(url, request, ChatGptResponse.class);
		
		return response.getChoices().get(0).getMessage().getContent();
	}
}
