package Components;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MsaFilterer {

    public static ArrayList<String> filter(String file_name) {
        Vector msa_species = new Vector();
        Vector msa_names = new Vector();
       

        System.out.println(file_name);
        try {
            String line;
            char start_char;
            String temp_species = "";

            //prepare a BufferedReader for file io



            BufferedReader br = new BufferedReader(new FileReader(file_name));
            try {
                msa_names = new Vector();
                msa_species = new Vector();
                while ((line = br.readLine()) != null) {
                    start_char = line.charAt(0);
                    //System.out.println(line);
                    if (start_char == '>') {
                        line = line.trim();
                        msa_names.addElement(line);
                        if (temp_species.length() > 0) {
                            temp_species = temp_species.toUpperCase();
                            temp_species = temp_species.trim();
                            msa_species.addElement(temp_species);
                        }
                        //System.out.println(line);
                        //System.out.println(number);
                        temp_species = "";

                    } else {
                        temp_species = temp_species.concat(line);
                        //System.out.println(temp_species);
                    }
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
            // add the last sequence as buffer has reached EOF
            if (temp_species.length() > 0) {
                temp_species = temp_species.toUpperCase();
                msa_species.addElement(temp_species);
            }
            br.close();
        } catch (FileNotFoundException ex) {
            //can't find file specified by args[0]
            ex.printStackTrace();
        } catch (IOException e) {
        }

//        System.out.println("msa row size :" + msa_species.size());
        // COMPARE
        int i, j, count;
        int counter = 0;
        Vector removals = new Vector();
        // count the similarities of the main species and the rest of the msa

        for (i = 1; i < msa_species.size() - 1; i++) {
            String main_spec = (String) msa_species.elementAt(0);
            String comp_spec = (String) msa_species.elementAt(i);
            count = 0;
//            System.out.println(main_spec.length());
            for (j = 0; j < main_spec.length() - 1; j++) {
                if (j < comp_spec.length()
                        && main_spec.charAt(j) == comp_spec.charAt(j)) {
                    count = count + 1;
                }
            }
//            System.out.println(msa_names.get(0));
//            System.out.println(msa_names.get(i + 1));
//            System.out.println(count);

            //calculate the percentage of similarities
            double simil;
            simil = (double) count / (double) main_spec.length();

            //System.out.println( count + " # " + simil);
            // if a species out of the msa is to similar or to different cut it out
//		 	if (simil < 0.3 || simil > 0.9){ // filterung von Roman
//			    removals.addElement(i);
//		 	}

////            if(msa_names.get(i + 1).toString().contains("Q0ZBT3/1-189 ")){
////                System.out.println(count  +" : "+ simil  );
////            }
////            
////                if(msa_names.get(i + 1).toString().contains("Q0ZBT4/1-189")){
////                System.out.println(count  +" : "+ simil  );
////            }
//            Q0ZBT3/1-189 

            if (simil < 0.2 || simil > 0.9) { // filterung von H2r Paper
                removals.addElement(i);
//                 System.out.println(msa_names.get(i + 1));
                counter++;
            }
        }

//        System.out.println("counter deletion: " + counter);
        for (i = removals.size() - 1; i > 0; i--) {
            int rem;
            rem = ((Integer) removals.elementAt(i)).intValue();
            //System.out.println(rem);
            msa_species.remove(rem);
            msa_names.remove(rem);
        }
        ArrayList<String> msa = new ArrayList<String>();
        msa.addAll(msa_species);
     
//                 for(int m=0;m<msa_species.size();m++){
//            System.out.println(msa_names.get(m)+" \n "+ msa_species.get(m));
//        }
     
//        for(int m=0;m<msa.size();m++){
//            System.out.println(msa_names.get(m)+" \n "+ msa.get(m));
//        }
//       System.exit(0);
//        if(msa.size()>700){
//        Random rnd=new Random();
//        while(msa.size()>700){
//            int delete=1+rnd.nextInt(msa.size()-1);
//            msa.remove(delete);
//        }
//        }

//        System.out.println("msa row size :" + msa.size());
        return msa;
    }
}
