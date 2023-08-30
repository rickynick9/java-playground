package ztm.BigO;

import java.util.Arrays;
import java.util.List;

public class Example1 {
    public static void main(String[] args) {
        List<String> arrayList = Arrays.asList(
                "dory", "bruce", "marlin", "gill", "bloat", "nigel", "sqirt", "darla", "hank", "nemo"
        );
        for(int i =0; i< arrayList.size(); i++) {
            if(arrayList.get(i).equals("nemo")) {
                System.out.println("Found Nemo !");
                break;
            }
        }
    }
}
