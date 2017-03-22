package com.waylau.spring.boot.security.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import com.waylau.spring.boot.security.domain.Client;
import com.waylau.spring.boot.security.repository.ClientRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientDetailsServiceTest {

	@Autowired 
	private ClientRepository clientRepository;
	@Autowired 
	private ClientDetailsService clientDetailsService;
	@Test
	public void testLoadClientByClientId(){
		
		String clientId = "9964899f-7b65-4f0e-9495-10c76ac5ed9a";
		Client client = clientRepository.findByClientId(clientId);
		System.out.print(client.getClientSecret());
		Client c = (Client)clientDetailsService.loadClientByClientId(clientId);
		System.out.print(c.getClientSecret());
		System.out.print(c.getAuthorizedGrantTypes());
	}

}
