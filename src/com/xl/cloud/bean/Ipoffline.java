package com.xl.cloud.bean;

public class Ipoffline {

	private int id;
	private long minip;
	private long maxip;
	private String continent; 
	private String areacode;
	private String country; 
	private String multiarea;
	private String user;

		public int getId() {
			return id;
		}
		public void setId(int id){
			this.id=id;
		}
		public long getMinip() {
			return minip;
		}
		public void setMinip(long minip){
			this.minip=minip;
		}
		public long getMaxip() {
			return maxip;
		}
		public void setMaxip(long maxip){
			this.maxip=maxip;
		}
		public String getContinent() {
			return continent;
		}
		public void setContinent(String continent){
			this.continent=continent;
		}
		public String getAreacode() {
			return areacode;
		}
		public void setAreacode(String areacode){
			this.areacode=areacode;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country){
			this.country=country;
		}
		public String getMultiarea() {
			return multiarea;
		}
		public void setMultiarea(String multiarea){
			this.multiarea=multiarea;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user){
			this.user=user;
		}	
}