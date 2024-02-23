package com.example.project.phetzing_shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.project.phetzing_shop.model.Member;
import com.example.project.phetzing_shop.repository.MemberRepository;

@SpringBootApplication
public class PhetZingShopApplication implements CommandLineRunner{

	private final MemberRepository memberRepository;

	
	public PhetZingShopApplication(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PhetZingShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		memberRepository.save(new Member("Kritsanachai", "kritsanachai@gmail.com", "$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue", "testAddress", "1"));
	}
	


}
