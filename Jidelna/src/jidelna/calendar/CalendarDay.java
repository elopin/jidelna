package jidelna.calendar;

import java.util.Calendar;
import jidelna.beans.DayMenuBean;

public class CalendarDay {

    private DayMenuBean menuBean;
    
    private int dayInMonth;
    private int dayInWeek;
    private boolean currentChoice;
    private boolean menuAvailable;
    private boolean menuSelected;

    public CalendarDay(Calendar calendar) {
	this.dayInMonth = calendar.get(Calendar.DAY_OF_MONTH);
	this.dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public DayMenuBean getMenuBean() {
	return menuBean;
    }

    public void setMenuBean(DayMenuBean menuBean) {
	this.menuBean = menuBean;
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

    public boolean isCurrentChoice() {
	return currentChoice;
    }

    public void setCurrentChoice(boolean currentChoice) {
	this.currentChoice = currentChoice;
    }


    public boolean isMenuSelected() {
	return menuSelected;
    }

    public void setMenuSelected(boolean menuSelected) {
	this.menuSelected = menuSelected;
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	if (getDayInWeek() == Calendar.SATURDAY || getDayInWeek() == Calendar.SUNDAY) {
	    sb.append("<label style=\"background-color: green\">");
		sb.append(String.valueOf(getDayInMonth()));
		sb.append("</label>");
	} else {
	    if (getMenuBean() != null) {
		
		sb.append("<input type=\"submit\" name=\"menuDay\" value=\"");
		sb.append(String.valueOf(getDayInMonth()));
		sb.append("\">");
	    } else {
		sb.append("<label>");
		sb.append(String.valueOf(getDayInMonth()));
		sb.append("</label>");
	    }
	}
	return sb.toString();
    }
}
