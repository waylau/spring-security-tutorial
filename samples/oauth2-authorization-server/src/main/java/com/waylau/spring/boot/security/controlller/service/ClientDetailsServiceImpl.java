package com.waylau.spring.boot.security.controlller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.waylau.spring.boot.security.repository.ClientRepository;

/**
 * ClientDetailsService 实现.
 *
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

	@Autowired
	private ClientRepository clientRepository;

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetailsService#loadClientByClientId(java.lang.String)
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clientRepository.findByClientId(clientId);
	}

}
