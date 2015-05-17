package kr.ac.shinhan.csp;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserLoginToken {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)  //아 저장되는 필드구나
	private Long key; //프라이머리
	
	@Persistent
	private String token;
	
	@Persistent
	private String userAccout;
	
	@Persistent
	private String expireDate;
	
	UserLoginToken(String token, String userAccout, String expireDate) {
		
		this.token = token;
		this.userAccout = userAccout;
		this.expireDate = expireDate;
		
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserAccout() {
		return userAccout;
	}

	public void setUserAccout(String userAccout) {
		this.userAccout = userAccout;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	
}
