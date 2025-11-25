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

    public void AddHistory(String Addr, String Type, double Amount, String time) {   

            mpList.get(Addr).addHist(Type, Amount, time);
            
        }

        public void ViewHistory(String Addr) {

            System.out.println("Looking for " + Addr + "...\n");

            if(mpList.containsKey(Addr)) {

<<<<<<< HEAD
                List<String> viewList = mpList.get(Addr).getList(); 
=======
                List<String> viewList = mpList.get(Addr).getList(); //get designated list -w-
                System.out.println("---------------------------------------------");
>>>>>>> dab3f1519c4338d7effc7be8cd28aee27d07f6ba
                for(String sp : viewList) {
                    System.out.println(sp);
                }
                System.out.println("---------------------------------------------");
            } else {
                System.out.println("Invalid, No View. ");
            }

        }


    }

    
 
    





