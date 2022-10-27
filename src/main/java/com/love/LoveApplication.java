package com.love;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class LoveApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoveApplication.class, args);
	}

	@GetMapping("/api/k")
	public ResponseEntity<Map<String,String>> k() {
		Map<String,String> stringStringMap = new HashMap<>();
		stringStringMap.put("1","a");
		stringStringMap.put("2","b");
		return ResponseEntity.ok().body(stringStringMap);
	}

}
