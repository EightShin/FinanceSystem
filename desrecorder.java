import java.util.*;

public class desrecorder   {
    int listcount = 0;

    HashMap<String, desrecorder_storage> mpList = new HashMap<>();

    public void AssignList(String Address, String namelist) {
        
       if(mpList.containsKey(Address) || listcount >= 5) {
        System.out.print("List Key Invalid. ");
       } else {
        mpList.put(Address, new desrecorder_storage(namelist, new ArrayList<>()));
        listcount++;
       }

    }

    public void AddHistory(String Addr, String Type, double Amount) {   

            mpList.get(Addr).addHist(Type, Amount);
            
        }

        public void ViewHistory(String Addr) {

            System.out.println("Looking for " + Addr + "...");

            if(mpList.containsKey(Addr)) {

                List<String> viewList = mpList.get(Addr).printList(); //get designated list -w-
            System.out.println(viewList.toString());

            } else {
                System.out.println("Invalid, No View. ");
            }

        }


    }

    
 
    





