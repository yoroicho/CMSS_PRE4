/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author tokyo
 */
public class SyatemFlags {
    
    private boolean isSystemPropertiesExsists = false;
    
    private SyatemFlags() {
    }
    
    public static SyatemFlags getInstance() {
        return SyatemFlagsHolder.INSTANCE;
    }
    
    private static class SyatemFlagsHolder {

        private static final SyatemFlags INSTANCE = new SyatemFlags();
    }

    /**
     * @return the isSystemPropertiesExsists
     */
    public boolean isIsSystemPropertiesExsists() {
        return isSystemPropertiesExsists;
    }

    /**
     * @param isSystemPropertiesExsists the isSystemPropertiesExsists to set
     */
    public void setIsSystemPropertiesExsists(boolean isSystemPropertiesExsists) {
        this.isSystemPropertiesExsists = isSystemPropertiesExsists;
    }
}
