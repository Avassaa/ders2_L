import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Ex6_20220808064 {

}

abstract class Product implements Comparable<Product> {
    private String name;
    private double price;
    public Product(String name, double price) {
        this.name = name;
        this.price =price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int compareTo(Product p1 ){
        if( this.price>p1.price )
            return 1;
        else if(this.price<p1.price)
            return -1;
        else {
            return 0;
        }
    }
    @Override
    public String toString() {
        return this.getClass().getName()+"[name ="+name+", price ="+price+"]";
    }



 }

abstract class Book extends Product{
private String author;
private int pageCount;

    public Book(String name, double price,String author, int pageCount) {
        super(name, price);
        this.author=author;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }
}

class ReadingBook extends Book{
    private String genre;

    public ReadingBook(String name, double price, String author, int pageCount, String genre) {
        super(name, price, author, pageCount);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}

class ColoringBook extends Book implements Colorable{
    private String color;

    public ColoringBook(String name, double price, String author, int pageCount) {
        super(name, price, author, pageCount);
    }

    public String getColor() {
        return color;
    }

    @Override
    public void paint(String color) {
        this.color=color;
    }
}

class ToyHorse extends Product implements Rideable{
    public ToyHorse(String name, double price) {
        super(name, price);
    }

    @Override
    public void ride() {
        System.out.println("Riding ToyHorse...");
    }
}


class Bicycle extends Product implements Colorable , Rideable{
    private String color;

    public Bicycle(String name, double price, String color) {
        super(name, price);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public void paint(String color) {
        this.color=color;
    }

    @Override
    public void ride() {
        System.out.println("Riding Bicycle...");
    }
}
class User{
    private String username;
    private String email;
    private PaymentMethod payment;
    private ArrayList<Product> cart;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        cart=new ArrayList<>();
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }
    public Product getProduct(int index){
        return cart.get(index);
        }

        public void removeProduct(int index){
        cart.remove(index);
        }

    public void addProduct(Product product){
        cart.add(product);
    }
    public void purchase(){
        double total=0;
        for(Product p:cart){
            total+=p.getPrice();
        }
    boolean canBuy=this.payment.pay(total);
        if(canBuy)
            cart.clear();
    }
}


class CreditCard implements PaymentMethod{
    private long cardNumber;
    private String cardHolderName;
    private Date expirationDate;
    private int cvv;

    public CreditCard(long cardNumber, String cardHolderName, Date expirationDate, int cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    @Override
    public boolean pay(double amount) {

   return false;
    }
}

class PayPal implements PaymentMethod {
    private String username;
    private String password;


    public PayPal(String username, String password) {
        char[] cArr = password.toCharArray();
        for (int i = 0; i < cArr.length; i++) {
            if ((int) cArr[i] >= 65 && (int) cArr[i] < 97) {
                cArr[i]++;
            }
            if ((int) cArr[i] == 96) {
                cArr[i] = 65;
            }
            if ((int) cArr[i] >= 97 && (int) cArr[i] < 122) {
                cArr[i]++;
            }
            if ((int) cArr[i] == 121) {
                cArr[i] = 97;
            }
        }
        String encrypted = "";
        for (int i = 0; i < cArr.length; i++) {
            encrypted += cArr[i];
        }
        this.username =username;
       this.password=encrypted;
    }
    @Override
    public boolean pay(double amount) {
        return false;
    }
}


interface Colorable {
    void paint(String color);
}

interface Rideable{
    void ride();
        }
interface PaymentMethod{
    boolean pay(double amount);
    }
