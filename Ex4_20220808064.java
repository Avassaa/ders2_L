import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ex4_20220808064 {

}


class Computer{
   protected CPU cpu;
   protected RAM ram;

    public Computer(CPU cpu, RAM ram){
        this.cpu = cpu;
        this.ram = ram;
    }
    public void run(){
        int sum=0;
        int temp1;
        int temp2;

        for(int i =0;i<ram.getCapacity()-1;i++){
                temp1=ram.getValue(i,i);
                temp2=ram.getValue(i+1,i+1);
                sum+=cpu.compute(temp1,temp2);
        }
        ram.setValue(0,0,sum);
    }
    @Override
    public String toString() {
        return "Computer:" + cpu + " " + ram;
    }
}

class Laptop extends Computer{
    private int milliAmp;
    private int battery;

    public Laptop(CPU cpu, RAM ram, int milliAmp){
        super(cpu,ram);
        this.milliAmp = milliAmp;
        this.battery=(milliAmp*3)/10;

    }
    public int batteryPercentage(){
        return (int)(((double)this.battery/milliAmp)*100);
    }
    public void charge(){
        while(batteryPercentage()<90) {
            this.battery +=(milliAmp/100)*2;
        }
    }
    @Override
    public void run(){
        if(batteryPercentage()>5) {
            super.run();
          this.battery=(int)(this.battery-(milliAmp/100.0)*3);
        }
        else {
            charge();
        }
    }

    @Override
    public String toString(){
       return super.toString()+" "+battery;
    }


}

class Desktop extends Computer{
   private ArrayList <String>peripherals;

    public Desktop(CPU cpu, RAM ram , String ... peripherals){
        super(cpu,ram);
        this.peripherals= new ArrayList<>(Arrays.asList(peripherals));


    }
    @Override
    public void run(){
        int sum=0;
        for(int i=0;i<ram.getCapacity();i++){
            for(int j=0;j< ram.getCapacity()-1;j++){
                sum+=cpu.compute(ram.getValue(i,j),ram.getValue(i,j+1));
            }
        }
        ram.setValue(0,0,sum);
    }
    public void plugIn(String peripheral){
        peripherals.add(peripheral);
    }
    public String plugOut(){
        String rt=peripherals.get(peripherals.size()-1);
        peripherals.remove(peripherals.size()-1);
        return rt;
    }
    public String plugOut(int index){
        String rt=peripherals.get(index);
        peripherals.remove(index);
        return rt;
    }
    @Override
    public String toString(){
        String st=String.join(" ", peripherals);
        return super.toString()+" "+st;
    }
}

class CPU {
    private String name;
    private double clock;

    public CPU(String name, double clock){
        this.name = name;
        this.clock = clock;
    }

    public String getName() {
        return name;
    }

    public double getClock() {
        return clock;
    }

    public int compute(int a ,int b){
        return  a+b;
    }

    @Override
    public String toString() {
        return "CPU: " + "name=" + name + ", clock " + clock +" Ghz";
    }
}

class RAM {
    private String type;
    private int capacity;
    private int [][] memory;

    public RAM(String type, int capacity){
        this.type=type;
        this.capacity=capacity;
        initMemory();
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    private void initMemory(){
        Random rand = new Random();
        memory= new int[capacity][capacity];

        for(int i=0;i<capacity;i++){
            for(int j=0;j<capacity;j++){
                int rnd = rand.nextInt(0,11);
                memory[i][j]=rnd;
            }
        }

    }

    private boolean check(int i , int j){
        return  i>=0 && j>=0 && i<memory.length && j<memory[i].length;
    }



    public int getValue(int i , int j){
        boolean bool =check(i,j);
        if(bool){
            return memory[i][j];
        }
        return -1;
    }

    public void setValue(int i , int j,int value){
        boolean inIndex = check(i,j);
        if(inIndex){
            this.memory[i][j]=value;
        }
    }

    @Override
    public String toString(){
        return "RAM:"+type+" "+capacity+" GB";
    }
}