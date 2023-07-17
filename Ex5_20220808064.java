import java.util.ArrayList;

public class Ex5_20220808064 {
    public static void main(String[] args){
        System.out.println("test");
    }
}
class Account{
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance){
        if(balance<0) {
            throw new InsufficientFundsException(balance);
        }
        else{
            this.balance=balance;
        }
        this.accountNumber=accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void deposit(double amount) throws InvalidTransactionException{
        if(!(amount<0))
            this.balance+=amount;
        else
        throw new InvalidTransactionException(amount);

    }
    public void withdraw(double amount) throws InvalidTransactionException{
       if(amount<0)
           throw new InvalidTransactionException(amount);
       else if(balance<amount){
           throw new InsufficientFundsException(balance,amount );
       }
        else{
            balance-=amount;
       }

    }
    @Override
    public String toString() {
        return "Account:"+accountNumber+", Balance: "+balance;
    }
}



class Customer{
    private String name;
    private ArrayList<Account> accounts;

    public Customer(String name) {
        this.name = name;
        accounts=new ArrayList<>() ;
    }
    public Account getAccount(String accountNumber){
        for(Account account : accounts){
            if(account.getAccountNumber().equals(accountNumber)){
                return account;
            }
        }

            throw new AccountNotFoundException(accountNumber);

    }
    public void addAccount(Account account){
        boolean notFound=false;
        try{
            getAccount(account.getAccountNumber());
        }
        catch(AccountNotFoundException ex){
            notFound=true;
            accounts.add(account);
        }finally{
            if(!notFound){
                throw new AccountAlreadyExistsException(account.getAccountNumber());
            }
            System.out.println(this);
            System.out.println("Added account:"+account.getAccountNumber()+" with "+account.getBalance());
        }

    }
    public void removeAccount(String accountNumber){
        accounts.remove(getAccount(accountNumber));
        }

    public void transfer(Account fromAccount, Account toAccount,double amount) throws InvalidTransactionException{
        try{
           fromAccount.withdraw(amount);
           toAccount.deposit(amount);
        }catch(InvalidTransactionException e){
            throw new InvalidTransactionException(e," cannot transfer" +
                    "funds from account "+ fromAccount+" to account "+toAccount);
        }
    }
    @Override
    public String toString(){
        String rtn="\t";
        for(Account account:accounts){
            rtn+=account+"\n\t";
        }
        return "Customer "+ name+":\n"+rtn;
    }
}
class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(double balance){
        super("Wrong balance:"+balance);
    }
    public InsufficientFundsException(double balance,double amount){
        super("Required amount is "+amount +" but only "+balance +" remaining");

    }
}
class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String accountNumber){
        super("Account number "+accountNumber+" already exists.");
    }
}
class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String accountNumber){
        super("Account number "+accountNumber+" not found");
    }{

    }
}
class InvalidTransactionException extends Exception{
    public InvalidTransactionException(double amount ){
        super("Invalid amount "+ amount );
    }
    public InvalidTransactionException(InvalidTransactionException e,String message){

        super("Message :\n\t"+e.getMessage()+"\n"+message);
    }
}