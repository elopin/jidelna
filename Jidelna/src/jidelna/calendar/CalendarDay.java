package jidelna.calendar;

import java.util.Calendar;
import jidelna.beans.DayMenuBean;

public class CalendarDay {

    private DayMenuBean menuBean;
    
    private int dayInMonth;
    private int dayInWeek;
    private boolean currentChoice;
    private boolean menuSelected;

    private boolean isUserSelect;
    private boolean admin;
    
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
    
    public boolean isAdmin() {
        return admin;
    }
    
    public void setAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

    public void setUserSelect(boolean userSelect) {
        this.isUserSelect = userSelect;
    }
    
    public boolean isUserSelect() {
        return isUserSelect;
    }
    
    public String toString() {
	StringBuilder sb = new StringBuilder();
	if (getDayInWeek() == Calendar.SATURDAY || getDayInWeek() == Calendar.SUNDAY) {
	    sb.append("<label class=\"dayButton\" style=\"background-color: #FF3333\">");
		sb.append(String.valueOf(getDayInMonth()));
		sb.append("</label>");
	} else {
	    if (getMenuBean() != null || admin) {
		String buttonColor = resolveButtonColor();
                
		sb.append("<button class=\"dayButton\" style=\"cursor : pointer; background-color : ");
                sb.append(buttonColor);
                sb.append(" \" type=\"submit\" name=\"menuDay\" value=\"");
		sb.append(String.valueOf(getDayInMonth()));
		sb.append("\">");
		sb.append(String.valueOf(getDayInMonth()));
		sb.append("</button>");
	    } else {
		sb.append("<label>");
		sb.append(String.valueOf(getDayInMonth()));
		sb.append("</label>");
	    }
	}
	return sb.toString();
    }

    private String resolveButtonColor() {
        String buttonColor = "#F0F0F0";
        
        if(isUserSelect) {
            buttonColor = "#66FF00";
        }
        
        if(admin && !isUserSelect && getMenuBean() != null) {
            buttonColor = "#FF00FF";
        }
        
        return buttonColor;
    }
}
