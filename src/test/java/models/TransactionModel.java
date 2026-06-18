package models;

public class TransactionModel extends BaseModel {
   private String type;
   private String amount;
   private String description;
   private String date;

    //Getters and Setters
public String getType(){
    return type;
}
public void setType(String type){
    this.type=type;
}
public String getAmount(){
    return amount;
}
public void setAmount(String amount){
    this.amount=amount;
}

public String getDescription(){
    return description;
}
public void setDescription(String description){
    this.description=description;
}
public String getDate(){
    return date;
}
public void setDate(String date){
    this.date=date;
}
}
