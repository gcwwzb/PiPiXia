package com.yangfan.Xmlpull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.yangfan.Entity.Plane;

import android.util.Xml;

public class Xmlpullplane {
	private static Plane plane;
	private static List<Plane>listplane;
	public List<Plane> getpullplane(InputStream in) throws Exception{
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(in, "UTF-8");
		int type=parser.getEventType();
		while(type!=XmlPullParser.END_DOCUMENT){
			
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				listplane=new ArrayList<Plane>();
				
				break;
			case XmlPullParser.START_TAG:
				if("AirlinesTime".equals(parser.getName())){
					plane=new Plane();
				}
				if("Company".equals(parser.getName())){
					String Company=parser.nextText();
					plane.setCompany(Company);
				}else if("AirlineCode".equals(parser.getName())){
					String AirlineCode=parser.nextText();
					plane.setAirlineCode(AirlineCode);
				}else if("StartDrome".equals(parser.getName())){
					String StartDrome=parser.nextText();
					plane.setStartDrome(StartDrome);
				}else if("ArriveDrome".equals(parser.getName())){
					String ArriveDrome=parser.nextText();
					plane.setArriveDrome(ArriveDrome);
				}else if("StartTime".equals(parser.getName())){
					String StartTime=parser.nextText();
					plane.setStartTime(StartTime);
				}else if("ArriveTime".equals(parser.getName())){
					String ArriveTime=parser.nextText();
					plane.setArriveTime(ArriveTime);
				}else if("Mode".equals(parser.getName())){
					String Mode=parser.nextText();
					plane.setMode(Mode);
				}else if("Week".equals(parser.getName())){
					String Week=parser.nextText();
					plane.setWeek(Week);
				}
				break;
			case XmlPullParser.END_TAG:
				if("AirlinesTime".equals(parser.getName())){
					listplane.add(plane);
					plane = null;
				}
				break;
			}
			type=parser.next();
		}
		return listplane;
		
	}
}
