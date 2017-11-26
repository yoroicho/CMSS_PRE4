/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author tokyo
 * シングルトン。
 */
public class SystemPropertiesItem {
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASS;
    public static String SHIP_BASE;
    public static String VOYAGE_BASE;
    public static String BOOKING_BASE;
    public static String CARGO_BASE;
    private SystemPropertiesItem() {
        
    }
    
    public static SystemPropertiesItem getInstance() {
        return SystemPropertiesHolder.INSTANCE;
    }
    
    private static class SystemPropertiesHolder {

        private static final SystemPropertiesItem INSTANCE = new SystemPropertiesItem();
    }
}
