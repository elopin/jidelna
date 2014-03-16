package jidelna.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarMonth {
	
	private List<CalendarDay> days;
	
	public CalendarMonth(Calendar calendar) {
		this.days = new ArrayList<CalendarDay>();
		for(int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
		    calendar.set(Calendar.DAY_OF_MONTH, i);
		    days.add(new CalendarDay(calendar));
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<form action=\"\" method=\"post\">");
		sb.append("<table>");
		sb.append("<tr><th>Po</th><th>Út</th><th>St</th><th>Čt</th><th>Pá</th><th>So</th><th>Ne</th></tr><tr>");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
		    if(calendar.get(Calendar.DAY_OF_WEEK) == days.get(0).getDayInWeek()) {
			break;
		    }
		    
		    sb.append("<td></td>");
		    
		    calendar.add(Calendar.DAY_OF_WEEK, 1);
		}
		for(int i = 0; i < days.size(); i++) {	
			sb.append("<td>");
			sb.append(days.get(i).toString());
			sb.append("</td>");
			
			if(days.get(i).getDayInWeek() == Calendar.SUNDAY) {
				sb.append("</tr><tr>");
			}
		}
		sb.append("</tr></table></form>");
		
		return sb.toString();
	}
}
