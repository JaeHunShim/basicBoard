package org.board.domain;

public class UserVO {
	
	private String uid; 
	private String upw;
	private String uname;
	private int upoint;
	private String email;
	private String authCode;
	private String verity;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUpw() {
		return upw;
	}
	public void setUpw(String upw) {
		this.upw = upw;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getUpoint() {
		return upoint;
	}
	public void setUpoint(int upoint) {
		this.upoint = upoint;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getVerity() {
		return verity;
	}
	public void setVerity(String verity) {
		this.verity = verity;
	}
	@Override
	public String toString() {
		return "UserVO [uid=" + uid + ", upw=" + upw + ", uname=" + uname + ", upoint=" + upoint + ", email=" + email
				+ ", authCode=" + authCode + ", verity=" + verity + ", getUid()=" + getUid() + ", getUpw()=" + getUpw()
				+ ", getUname()=" + getUname() + ", getUpoint()=" + getUpoint() + ", getEmail()=" + getEmail()
				+ ", getAuthCode()=" + getAuthCode() + ", getVerity()=" + getVerity() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
