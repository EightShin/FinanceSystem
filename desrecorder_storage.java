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
        String esp = String.format("%s | %s | ₱%.2f", time, sp.trim(), Amount);
        list.add(esp);
        
    }
}
