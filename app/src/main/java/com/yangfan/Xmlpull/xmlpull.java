package com.yangfan.Xmlpull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

import com.yangfan.Entiy.Station;

import android.util.Xml;

public class xmlpull {
	private static Station aaaaa;
	private static List<Station>list;
	public List<Station> getpull(InputStream in) throws Exception{
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(in, "UTF-8");
		int type=parser.getEventType();
		while(type!=XmlPullParser.END_DOCUMENT){
			
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				list=new ArrayList<Station>();
				
				break;
			case XmlPullParser.START_TAG:
				if("TimeTable".equals(parser.getName())){
					aaaaa = new Station();
				}
				if("TrainCode".equals(parser.getName())){
					String TrainCode=parser.nextText();
					aaaaa.setTrainCode(TrainCode);
				}else if("FirstStation".equals(parser.getName())){
					String FirstStation=parser.nextText();
					aaaaa.setFirstStation(FirstStation);
				}else if("LastStation".equals(parser.getName())){
					String LastStation=parser.nextText();
					aaaaa.setLastStation(LastStation);
				}else if("StartStation".equals(parser.getName())){
					String StartStation=parser.nextText();
					aaaaa.setStartStation(StartStation);
				}else if("StartTime".equals(parser.getName())){
					String StartTime=parser.nextText();
					aaaaa.setStartTime(StartTime);
				}else if("ArriveStation".equals(parser.getName())){
					String ArriveStation=parser.nextText();
					aaaaa.setArriveStation(ArriveStation);
				}else if("ArriveTime".equals(parser.getName())){
					String ArriveTime=parser.nextText();
					aaaaa.setArriveTime(ArriveTime);
				}else if("KM".equals(parser.getName())){
					String KM=parser.nextText();
					aaaaa.setKM(KM);
				}else if("UseDate".equals(parser.getName())){
					String UseDate=parser.nextText();
					aaaaa.setUseDate(UseDate);
				}
				break;
			case XmlPullParser.END_TAG:
				if("TimeTable".equals(parser.getName())){
					list.add(aaaaa);
					aaaaa = null;
				}
				break;
			}
			type=parser.next();
		}
		return list;
		
	}
}
