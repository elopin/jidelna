package jidelna.calendar.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.JPanel;

public class CalendarMonth extends JPanel {
	
	
	private int positionInYear;
	
	private List<CalendarDay> days;
	
	public CalendarMonth(int positionInYear) {
		this.positionInYear = positionInYear;
		this.days = new ArrayList<CalendarDay>();
		
		Calendar calendar = Calendar.getInstance(new Locale("cs","CZ"));
		calendar.set(Calendar.MONTH, positionInYear);
		for(int i = 1; i <= calendar.getMaximum(Calendar.DAY_OF_MONTH); i++) {
			days.add(new CalendarDay(i));
		}
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<form action=\"\" method=\"post\">");
		sb.append("<table>");
		for(int i = 0; i < days.size();) {
			if(i%7 == 0) {
				sb.append("<tr>");
				for(int j = 0; j < 7 && i < days.size(); j++, i++) {
					sb.append("<td>");
					sb.append(days.get(i).toString());
					sb.append("</td>");
				}
				sb.append("</tr>");
			}
		}
		sb.append("</table></form>");
		
		return sb.toString();
	}
}
