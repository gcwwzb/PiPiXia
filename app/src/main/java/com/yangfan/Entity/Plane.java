package com.yangfan.Entity;

public class Plane {

	public String Company;//航空公司
	public String AirlineCode;//班次
	public String StartDrome;//出发地
	public String ArriveDrome;//目的地
	public String StartTime;//出发时间
	public String ArriveTime;//到达时间
	public String Mode;//飞机型号
	public String Week;//出发日期
	public Plane(String company, String airlineCode, String startDrome,
				 String arriveDrome, String startTime, String arriveTime,
				 String mode, String week) {
		super();
		Company = company;
		AirlineCode = airlineCode;
		StartDrome = startDrome;
		ArriveDrome = arriveDrome;
		StartTime = startTime;
		ArriveTime = arriveTime;
		Mode = mode;
		Week = week;
	}
	public Plane() {
		super();
	}
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		Company = company;
	}
	public String getAirlineCode() {
		return AirlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		AirlineCode = airlineCode;
	}
	public String getStartDrome() {
		return StartDrome;
	}
	public void setStartDrome(String startDrome) {
		StartDrome = startDrome;
	}
	public String getArriveDrome() {
		return ArriveDrome;
	}
	public void setArriveDrome(String arriveDrome) {
		ArriveDrome = arriveDrome;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getArriveTime() {
		return ArriveTime;
	}
	public void setArriveTime(String arriveTime) {
		ArriveTime = arriveTime;
	}
	public String getMode() {
		return Mode;
	}
	public void setMode(String mode) {
		Mode = mode;
	}
	public String getWeek() {
		return Week;
	}
	public void setWeek(String week) {
		Week = week;
	}

}
