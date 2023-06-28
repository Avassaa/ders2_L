import java.util.*;

public class Ex9_20220808064 {
   
}
interface Common<T>{

    boolean isEmpty();
    T peek();
    int size();
}

interface Stack<T> extends Common<T>{
    boolean push(T item);
    T pop();
}

interface Node<T>{
      int DEFAULT_CAPACITY=2;

      void setNext(T item);
      T getNext();
      double getPriority();
}
interface PriorityQueue<T> extends Common<T>{
    int FLEET_CAPACITY=3;
    boolean enqueue(T item);
    T dequeue();

}


interface Sellable{

    String getName();
    double getPrice();
}

interface Package<T>{

    T extract();
    boolean pack(T item);
    boolean isEmpty();
    double getPriority();
}

interface Wrappable extends Sellable{}

abstract class Product implements Sellable{
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName()+" ("+name+", "+price+")";

    }

    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
}

class Mirror extends Product{
    private int width;
    private int height;

    public Mirror(int width, int height) {
        super("mirror", 2);
        this.width = width;
        this.height = height;
    }

    public int getArea(){
        return width*height;
    }
    public <T> T reflect(T item){
        System.out.println(item);
        return item;
    }


}

class Paper extends Product implements Wrappable{
    private String note;

    public Paper(String note) {
        super("A4", 3);
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

class Matroschka<T extends Wrappable> extends Product implements Wrappable, Package<T>{ //T ler aynı
    private T item;
    public Matroschka( T item) {
        super("Doll", item.getPrice()+5);
        this.item = item;
    }
    @Override
    public T extract() {
        if(item!=null){
            T data=item;
            item=null;
            return data;
        }
        return null;

    }

    @Override
    public boolean pack(T item) {

        if(isEmpty()) {
            this.item = item;
            return true;
        }
        return false;

    }

    @Override
    public boolean isEmpty() {
        return this.item==null;
    }

    @Override
    public double getPriority() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String toString() {
        return super.toString()+"{"+ item+"}";
    }

}

class Box<T extends Sellable> implements Package<T>{
    private T item;
    private boolean seal;
    private int distanceToAddress;


    public Box() {
        this.item=null;
        this.seal=false;
    }

    public Box(T item,int distanceToAddress) {
        this.item = item;
        this.distanceToAddress=distanceToAddress;
        this.seal = true;
    }

    @Override
    public T extract() {
        this.seal = false;
        if(!isEmpty()){
            T data=item;
            this.item=null;
            return data;
        }
        return null;

    }

    @Override
    public boolean pack(T item) {

        if(isEmpty()){
            this.item=item;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.item==null;
    }

    @Override
    public double getPriority() {
        return this.distanceToAddress/item.getPrice();
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"{"+item+"} Seal:"+seal;
    }
}
class Container implements Stack<Box<?>> ,Node<Container>,Comparable<Container>{
    private Box<?>[] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;

    public Container() {
        this.boxes=new Box[DEFAULT_CAPACITY];
        this.top=-1;
        this.priority=0;
    }


    @Override
    public boolean isEmpty() {
        return top==-1;
    }

    @Override
    public Box<?> peek() {
        if(isEmpty()){
            return null;
        }
        return boxes[top];
    }

    @Override
    public int size() {
        return top+1;
    }

    @Override
    public boolean push(Box<?> item) {
        if(size()<DEFAULT_CAPACITY){
            size++;
            boxes[++top]=item;
            return true;
        }
        return false;
    }

    @Override
    public Box<?> pop() {
        if(isEmpty())
            return null;
        else{
            Box<?> data=boxes[top];
            boxes[top--]=null;
            size--;
            return data;

        }
    }

    @Override
    public void setNext(Container item) {
        this.next=item;
    }

    @Override
    public Container getNext() {
        return this.next;
    }

    @Override
    public double getPriority() {
        double prioritySum=0;
        for(int i =0;i<this.size();i++){
            prioritySum+=boxes[i].getPriority();

        }
       this.priority=prioritySum;
        return this.priority;
    }





    @Override
    public int compareTo(Container o) {

        if(this.getPriority()>o.getPriority()){
            return -1;
        }
        else if(this.getPriority()==o.getPriority())
            return 0;
        else
            return 1;
    }

    @Override
    public String toString() {
        return "Container with priority:"+ priority;

    }

}
class CargoFleet implements PriorityQueue<Container> {
    private Container head;
    private int size;

    public CargoFleet() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;

    }

    @Override
    public Container peek() {
        return this.head;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean enqueue(Container item) {

            if (isEmpty()) {
                this.head = item;
                size++;
                return true;
            }
           else if (size == 1) {

                if (head.getPriority() > item.getPriority()) {
                    Container temp = head;
                    head = item;
                    head.setNext(temp);
                } else {

                    head.setNext(item);
                }
                size++;
                return true;


            } else if (size == 2) {
                Container next = head.getNext();
                Container tem = head;

                if (item.getPriority() < next.getPriority()) {  // 10 30   15
                    Container temp = next;
                    Container last=item;
                    next = last;
                    last=temp;
                    if (next.getPriority() < head.getPriority()) {
                        head = next;
                        next=tem;
                    }
                    head.setNext(next);
                    next.setNext(last);
                }
                size++;
                return true;

            }
            return false;
        }


    @Override
    public Container dequeue() {
        if (isEmpty())
            return null;
        else {
            Container data = head;
            head = head.getNext();
            size--;
            return data;
        }
    }
}


class CargoCompany {
        private Container stack;
        private CargoFleet queue;

        public CargoCompany() {
            this.stack = new Container();
            this.queue = new CargoFleet();
        }

        public <T extends Box<?>> void add(T box) {


            if (!stack.push(box)) {
                if (this.queue.enqueue(stack)) {
                    this.stack = new Container();
                    this.add(box);
                } else {
                    ship(this.queue);
                }
            }
        }

        private void ship(CargoFleet fleet) {
            while (!fleet.isEmpty())
                empty(fleet.dequeue());

        }

        private void empty(Container container) {
            while (!container.isEmpty()) {
                System.out.println(deliver((container.pop())));
            }
        }

        private <T extends Box<?>> Sellable deliver(T box) {
            return box.extract(); //  başlık -- private Sellable deliver(Box<?> box) --> return box.extract(); ? ?
        }



    }

