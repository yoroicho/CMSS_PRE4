/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tokyo
 */
public class DtataTime {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.now();

  //LocalDate parsedDate = LocalDate.parse(text, formatter);
    String text = DateTimeFormatter.ISO_DATE_TIME.format(date);
  
        System.out.println(text);
    }
// UUIDをチェックする

public static Boolean isUUID(String uuid) {
  String reg = "[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}";
  
  Pattern p = Pattern.compile(reg);
  Matcher m = p.matcher(uuid);
  
  return m.find();
}


    
}
