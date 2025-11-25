import java.util.*;

public class desrecorder_storage {
String Address;
List<String> list;
    public desrecorder_storage(String Address, List<String> list) {
        this.Address = Address;
        this.list = list;

    }

    public String getAddress() {
        return Address;
    }

    public List<String> getList() {
        
        return list;
    }


    public void addHist(String sp, double Amount, String time) {
        String esp = sp + " " + Double.toString(Amount) + " " + time;
        list.add(esp);
        
    }

   
    
}
