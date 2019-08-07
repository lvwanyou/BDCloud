package com.xl.cloud.bean;

import java.util.ArrayList;
import java.util.List;

public class TicketLinksDTO {
	private List licketLinkList = new ArrayList<TicketLinkDTO>();
	private String linkman;
	public List getLicketLinkList() {
		return licketLinkList;
	}
	public void setLicketLinkList(List licketLinkList) {
		this.licketLinkList = licketLinkList;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
}
