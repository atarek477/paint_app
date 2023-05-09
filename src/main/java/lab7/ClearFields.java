package lab7;

import javax.swing.JTextField;


public class ClearFields {
    public static boolean  isEmptyFeild(JTextField...textFields)
    {
         for( JTextField jflds: textFields){
             if(jflds.getText().isBlank())
             return true;
         }
         return false;
    }
     public static boolean  isNonNumeric(JTextField...textFields)
    {
         for( JTextField jflds: textFields)
         {
         try{
         double d = Double.parseDouble(jflds.getText());             
                 }catch(NumberFormatException n){
                     return true;
                 }

      
         }   
          return false;
       
    }
    
     public static void clearFields (JTextField...textFields)
    {
         for(JTextField jflds: textFields)
             jflds.setText("");
    }
}
