package jidelna.calendar.model;


public class CalendarDay {
	
	private int dayInMonth;
	
	public CalendarDay(int day) {
		this.dayInMonth = day;
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"/homepage.jsp\">");
		sb.append(String.valueOf(dayInMonth));
		sb.append("</a>");
		return sb.toString();
	}

}
