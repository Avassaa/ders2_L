
public class Ex8_20220808064 {
}

interface Sellable{

    String getName();
    double getPrice();
}

interface Package<T>{

    T extract();
    boolean pack(T item);
    boolean isEmpty();
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
    public String toString() {
        return super.toString()+"{"+ item+"}";
    }

}

class Box<T extends Sellable> implements Package<T>{
    private T item;
    private boolean seal;

    public Box() {
        this.item=null;
        this.seal=false;
    }

    public Box(T item) {
        this.item = item;
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
    public String toString() {
        return this.getClass().getSimpleName()+"{"+item+"} Seal:"+seal;
    }
}
