/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transistor;

/**
 *
 * @author Geonmo
 */
public class TransistorInfo {
    private int pinchoff_voltage,v_ds,v_gs,threshold_voltage ;
    
    private TransistorInfo(){
        pinchoff_voltage=0;
        threshold_voltage=0;
        v_ds=0;
        v_gs=0;
    }
    public static TransistorInfo getInstacne() {
        return TransistorInfoHolder.INSTANCE;
    }
    public static class TransistorInfoHolder{
        private static final TransistorInfo INSTANCE = new TransistorInfo();        
    }
    public int getPinchOff() {
        return this.pinchoff_voltage;
    }
    public int getThreshold() {
        return this.threshold_voltage;
    }    
    public int getVds() {
        return this.v_ds;
    }
    public int getVgs() {
        return this.v_gs;
    }
    
    public void setPinchOff(int v_pinch) {
        this.pinchoff_voltage = v_pinch;
    }
    public void setVds(int vds) {
        this.v_ds = vds;
    }
    public void setVgs(int vgs) {
        this.v_gs = vgs;
    }
    public void setThreshold(int th) {
        this.threshold_voltage = th;
    }
}
