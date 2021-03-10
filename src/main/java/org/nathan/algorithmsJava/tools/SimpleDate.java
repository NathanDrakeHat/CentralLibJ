package org.nathan.algorithmsJava.tools;

public final class SimpleDate {
    public final int year;
    public final int month;
    public final int day;
    public final String s;

    public SimpleDate(int y, int m, int d) {
        this.year = y;
        this.month = m;
        this.day = d;
        s = String.format("[%d,%d,%d]",year,month,day);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public SimpleDate newCopy(){
        return new SimpleDate(this.year,this.month,this.day);
    }

    @Override
    public String toString(){
        return s;
    }
}
