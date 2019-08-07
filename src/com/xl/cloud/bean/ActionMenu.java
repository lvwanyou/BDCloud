package com.xl.cloud.bean;

public class ActionMenu {
	private int id = -1;
    private String icon;
    private String actionName;
    private String parent;
    private String route;
    private String isHide;
    private int actionOrder= -1;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getIsHide() {
		return isHide;
	}
	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
	public int getActionOrder() {
		return actionOrder;
	}
	public void setActionOrder(int actionOrder) {
		this.actionOrder = actionOrder;
	}

    

    
}
