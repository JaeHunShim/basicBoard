package org.board.dto;

public class LoginDTO {
	
	private String uid;
	private String upw;
	private String verity;
	private boolean useCookie;
	
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
	public String getVerity() {
		return verity;
	}
	public void setVerity(String verity) {
		this.verity = verity;
	}
	public boolean isUseCookie() {
		return useCookie;
	}
	public void setUseCookie(boolean useCookie) {
		this.useCookie = useCookie;
	}
	@Override
	public String toString() {
		return "LoginDTO [uid=" + uid + ", upw=" + upw + ", verity=" + verity + ", useCookie=" + useCookie
				+ ", getUid()=" + getUid() + ", getUpw()=" + getUpw() + ", getVerity()=" + getVerity()
				+ ", isUseCookie()=" + isUseCookie() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
