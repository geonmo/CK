/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transistor;

import java.awt.Graphics;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Geonmo
 */
public class JFETGraphViewer extends ChartPanel {
    TransistorInfo info;
    JFreeChart chart;
    XYSeries s;
    XYSeriesCollection data;
    XYPointerAnnotation pointer;
    /**
     * Creates new form JFETGraphViewer
     * @param chart
     */
    public JFETGraphViewer(JFreeChart chart) {
        super(chart);       
        s = new XYSeries("JFET_Current(I_D)");   
        data = new XYSeriesCollection();
        data.addSeries(s);
        info =  TransistorInfo.getInstacne();
        this.makeNewSeries();
        
        
        
        initComponents();        
        
        //this.chart = ChartFactory.createXYLineChart("Output characteristic V-I curves","V_ds","I_d",data,PlotOrientation.VERTICAL,true,true,false);        
        //pointer = new XYPointerAnnotation("This point",info.getVds(), getDrainCurrent(info.getVds()), PI/4.0);
        //XYPlot plot = (XYPlot)chart.getPlot();
        //plot.addAnnotation(pointer);                
        
    }
    public void makeNewSeries(){
        s.clear();
        for(int i=0; i <50 ; i++){
            s.add(i,getDrainCurrent(i));     
            //System.out.printf("%d %f\n",i,getDrainCurrent(i));
        }
    }
    public double getDrainCurrent(double Vds){
        //int Vds = info.getVds();
        int Vgs = info.getVgs();
        int Vpoff = info.getPinchOff();
        
        double I_d = 0.0;
        double I_dss = 2000; 
        
        // Channel-Off(or pinch-off) region.  I_drain must be 0,
        if ( Vgs< Vpoff ) {
            I_d = 0.0;
        }
        else {
            int Vsat = Vgs-Vpoff;
            // If Vds is ohmic region,
            if( Vds < Vsat) {
                I_d = 2*I_dss/(Vpoff*Vpoff)*(Vgs-Vpoff-Vds/2.0)*Vds;
            }
            // If Vds is saturation region,
            else if (Vds>= Vsat) {                
                double a_value = 1-(double)Vgs/(double)Vpoff;
                I_d = I_dss*a_value*a_value;
            }
            
        }
        return I_d;
        
    }
    public void printInformation(){        
        System.out.printf("This messaage come from 2Dviwer. Vds is %d\n", info.getVds() );
        System.out.printf("This messaage come from 2Dviwer. Vgs is %d\n", info.getVgs() );
        System.out.printf("This messaage come from 2Dviwer. V_p is %d\n", info.getPinchOff() );
        System.out.printf("This messaage come from 2Dviwer. V_th is %d\n", info.getThreshold());        
    }
    public JFreeChart getResultChart(){  
        this.makeNewSeries();
        
        this.chart = ChartFactory.createXYLineChart("Output characteristic V-I curves","V_ds","I_d",data,PlotOrientation.VERTICAL,true,true,false);        
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.getRangeAxis().setRange(0,2100);
        pointer = new XYPointerAnnotation("V_ds",info.getVds(), getDrainCurrent(info.getVds()), PI/4.0);
        plot.getAnnotations().clear();
        plot.addAnnotation(pointer);                
        
        
        return this.chart;
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        System.out.println("Calling!!");
        if ( info.isChanged() ) {
            this.chart = this.getResultChart();
            this.setChart(this.chart);
            info.appliedChanged();
        }
    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
