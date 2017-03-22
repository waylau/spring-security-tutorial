package com.waylau.spring.boot.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.waylau.spring.boot.security.controlller.util.StringUtil;

/**
 * Client 实体.
 * 为了演示方便，只实现部分属性
 * 
 * @since 1.0.0 2017年3月5日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Entity // 实体
public class Client implements ClientDetails {
 
	private static final long serialVersionUID = 1L;

	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Long id; // 用户的唯一标识
	
	@Column(nullable = false, length = 250 , unique = true) 
	private String clientId;

	@Column(length = 250) 
	private String clientSecret;

	@Column(length = 250)
	private String scope;
	
	@Column(length = 250)
	private String authorizedGrantTypes;
	
	@Column(nullable = true, length = 250)
	private String registeredRedirectUri;
	
	@Column(length = 250)
	private String authorities;
	
	@Column
	private Integer accessTokenValiditySeconds;
	
	@Column
	private Integer refreshTokenValiditySeconds;
 
	protected Client() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public void setRegisteredRedirectUri(String registeredRedirectUri) {
		this.registeredRedirectUri = registeredRedirectUri;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#getClientId()
	 */
	@Override
	public String getClientId() {
		return this.clientId;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#isSecretRequired()
	 */
	@Override
	public boolean isSecretRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#getClientSecret()
	 */
	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#isScoped()
	 */
	@Override
	public boolean isScoped() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#getScope()
	 */
	@Override
	public Set<String> getScope() {
		return StringUtil.convertToSet(this.scope);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#getAuthorizedGrantTypes()
	 */
	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return StringUtil.convertToSet(this.authorizedGrantTypes);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#getRegisteredRedirectUri()
	 */
	@Override
	public Set<String> getRegisteredRedirectUri() {
		return StringUtil.convertToSet(this.registeredRedirectUri);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#getAuthorities()
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<String> list = StringUtil.convertToList(this.authorities);
		List<GrantedAuthority> simpleAuthorities = new ArrayList<>();
		for(String authority : list){
			simpleAuthorities.add(new SimpleGrantedAuthority(authority));
		}
		return simpleAuthorities;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#getAccessTokenValiditySeconds()
	 */
	@Override
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValiditySeconds;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#getRefreshTokenValiditySeconds()
	 */
	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return this.refreshTokenValiditySeconds;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetails#isAutoApprove(java.lang.String)
	 */
	@Override
	public boolean isAutoApprove(String scope) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> getResourceIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return null;
	}

}
