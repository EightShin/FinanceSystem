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

    public List<String> printList() {
        StringBuilder vt = new StringBuilder();
        List<String> res = list;
        for(String elm : res) {
            vt.append(elm).append("\n");
        } 
        return res; //attempt to vertical. still does not work
        
    }

    public void addHist(String sp, double Amount) {
        String esp = sp + " " + Double.toString(Amount);
        list.add(esp);
        
    }

   
    
}
