public class Ex1_20220808064 {
    public static void main(String[] args) {
        Stock stock =new Stock("ORCL","Oracle Corporation");
        stock.previousClosingPrice=34.5;
        stock.currentPrice=34.35;
        System.out.println(stock.getChangePercent());

    }
}
 class Stock{
String symbol;
String name;
double previousClosingPrice;
double currentPrice;
Stock( String symbol,String name){
    this.symbol=symbol;
    this.name =name;
}
 public double getChangePercent(){
    return ((currentPrice-previousClosingPrice)/previousClosingPrice)*100;
}
}

class Fan{
   final static int SLOW =1;
   final static int MEDIUM=2  ;
   final static int FAST=3;
    private int speed =SLOW;
    private boolean on =false;
    private double radius=5;
    String color ="blue";

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    Fan(){}
    Fan(double radius , String color){
        this.radius=radius;
        this.color=color;
    }
    public String toString(){
        String str="";
        if(this.on){
            return str+speed+color+radius;
        }
        return str+color+" "+radius+"\nFan is off ";
    }
}