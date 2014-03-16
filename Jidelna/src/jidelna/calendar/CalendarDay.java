package jidelna.calendar;

import java.util.Calendar;

public class CalendarDay {

    
    private int dayInMonth;
    private int dayInWeek;

    public CalendarDay(Calendar calendar) {
	this.dayInMonth = calendar.get(Calendar.DAY_OF_MONTH);
	this.dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getDayInMonth() {
	return dayInMonth;
    }

    public void setDayInMonth(int dayInMonth) {
	this.dayInMonth = dayInMonth;
    }

    public int getDayInWeek() {
	return dayInWeek;
    }

    public void setDayInWeek(int dayInWeek) {
	this.dayInWeek = dayInWeek;
    }

    

    public String toString() {
	StringBuilder sb = new StringBuilder();
	if ( getDayInWeek() == Calendar.SATURDAY || getDayInWeek() == Calendar.SUNDAY) {
	    sb.append(String.valueOf(getDayInMonth()));
	} else {
	    sb.append("<a href=\"\">");
	    sb.append(String.valueOf(getDayInMonth()));
	    sb.append("</a>");
	}
	return sb.toString();
    }
}
