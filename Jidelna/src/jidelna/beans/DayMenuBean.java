/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jidelna.beans;

/**
 *
 * @author elopin
 */
public class DayMenuBean {
    
    private int id;
    
    private int day;
    private int month;
    private int year;
    
    private String menu1;
    private int price1;
    private String menu2;
    private int price2;

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
	return id;
    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }
    
    

    public String getMenu1() {
	return menu1;
    }

    public void setMenu1(String menu1) {
	this.menu1 = menu1;
    }

    public int getPrice1() {
	return price1;
    }

    public void setPrice1(int price1) {
	this.price1 = price1;
    }

    public String getMenu2() {
	return menu2;
    }

    public void setMenu2(String menu2) {
	this.menu2 = menu2;
    }

    public int getPrice2() {
	return price2;
    }

    public void setPrice2(int price2) {
	this.price2 = price2;
    }
    
    public void setData(DayMenuBean menu) {
        setId(menu.getId());
        setDay(menu.getDay());
        setMonth(menu.getMonth());
        setYear(menu.getYear());
        setMenu1(menu.getMenu1());
        setPrice1(menu.getPrice1());
        setMenu2(menu.getMenu2());
        setPrice2(menu.getPrice2());
    }
    
    
}
