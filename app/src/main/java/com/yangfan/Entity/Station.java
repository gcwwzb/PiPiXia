package com.yangfan.Entity;

public class Station {
	public  String TrainCode;//车次
	public  String FirstStation;//发车站
	public  String LastStation;//终点站
	public  String StartStation;//发车站
	public  String StartTime;//时间
	public  String ArriveStation;//终点站
	public  String ArriveTime;//时间
	public  String KM ;//历程
	public  String UseDate ;//路程时间
	public  Station(String trainCode, String firstStation, String lastStation,
				   String startStation, String startTime, String arriveStation,
				   String arriveTime, String kM, String useDate) {
		super();
		TrainCode = trainCode;
		FirstStation = firstStation;
		LastStation = lastStation;
		StartStation = startStation;
		StartTime = startTime;
		ArriveStation = arriveStation;
		ArriveTime = arriveTime;
		KM = kM;
		UseDate = useDate;
	}
	public Station() {
		super();
	}
	public String getTrainCode() {
		return TrainCode;
	}
	public void setTrainCode(String trainCode) {
		TrainCode = trainCode;
	}
	public String getFirstStation() {
		return FirstStation;
	}
	public void setFirstStation(String firstStation) {
		FirstStation = firstStation;
	}
	public String getLastStation() {
		return LastStation;
	}
	public void setLastStation(String lastStation) {
		LastStation = lastStation;
	}
	public String getStartStation() {
		return StartStation;
	}
	public void setStartStation(String startStation) {
		StartStation = startStation;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getArriveStation() {
		return ArriveStation;
	}
	public void setArriveStation(String arriveStation) {
		ArriveStation = arriveStation;
	}
	public String getArriveTime() {
		return ArriveTime;
	}
	public void setArriveTime(String arriveTime) {
		ArriveTime = arriveTime;
	}
	public String getKM() {
		return KM;
	}
	public void setKM(String kM) {
		KM = kM;
	}
	public String getUseDate() {
		return UseDate;
	}
	public void setUseDate(String useDate) {
		UseDate = useDate;
	}

}
