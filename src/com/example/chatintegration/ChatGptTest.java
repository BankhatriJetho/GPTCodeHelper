package com.example.chatintegration;

public class ChatGptTest {

	public static void main(String[] args) {
		ChatGPTService chatGPTService = new ChatGPTService();
		
		//prompt goes here
		String prompt = "Generate a Java method to reverse a string";
		
		System.out.println("Sending prompt to ChatGPT.....");
		String response = chatGPTService.generateCode(prompt);
		
		System.out.println("Response from chatGPT:");
		System.out.println(response);
		
	}

}
