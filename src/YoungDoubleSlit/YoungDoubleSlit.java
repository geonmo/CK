/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YoungDoubleSlit;
//import java.lang.Math;

//import java.applet.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;
//import javax.swing.*;
/**
 *
 * @author Geonmo
 */
public class YoungDoubleSlit extends javax.swing.JApplet {
    int wavelength, slit_width, slit_distance,bin_size;   
    javax.swing.Timer timer ;
    boolean isTimerOn;  
    boolean single_mode;
    
    /**
     * Initializes the applet YoungDoubleSlit
     */

    @Override
    
    public void init() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(YoungDoubleSlit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YoungDoubleSlit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YoungDoubleSlit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YoungDoubleSlit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        wavelength =300;
        slit_width = 0;
        slit_distance= 0;        
        bin_size = 501;
        single_mode = true;
        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();                    
                    isTimerOn = false;   
                    timer = null;
                }                
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }           
    }
    public void timerStart()
    {   if ( timer == null ){
            System.out.println("Start Timer!!");                    
            timer = new javax.swing.Timer(100,new aListener()); 
            timer.stop();
        }
        timer.start();
        isTimerOn = true;
    }
    public void timerStop()
    {
        timer.stop();
        isTimerOn = false;
    }
    public class aListener implements ActionListener 
    {       
                public void actionPerformed(ActionEvent e) {                    
                    if ( isTimerOn ) {
                        //System.out.println("On Timer!!");
                        //System.out.println( (int) theWaveLengthSlider.getValue()) ;
                        upperView.repaint();
                        resultView.repaint();
                    }
                    else {
                        System.out.println("Already Stop Timer!!");
                    }
                }
            //}
    };
    
    public class UpperViewPane extends javax.swing.JPanel{                
        ArrayList<Integer> x_pos    = new ArrayList<>();        
        ArrayList<Integer> wavelength_array = new ArrayList<>();        
        
        ArrayList<Integer> after_x_pos    = new ArrayList<>();        
        ArrayList<Integer> after_wavelength = new ArrayList<>();      
        
        int call;
        //int current_wavelength;
        boolean single_slit;
        boolean double_slit;
        UpperViewPane(){
            super();
            x_pos.add(0);
            call = 0;
            wavelength_array.add(wavelength);
            //current_wavelength = 300;            
        }
        public ArrayList<Integer> getRadius(){
            return x_pos;
        }
        public ArrayList<Integer> getWavelengths(){
            return wavelength_array;
        }
        public ArrayList<Integer> getSlitRadius(){
            return after_x_pos;
        }
        public ArrayList<Integer> getSlitWavelengths(){
            return after_wavelength;
        }
        public void newWave(int hp)
        {
            x_pos.add(0);
            if (hp ==0) wavelength_array.add(wavelength);            
            else wavelength_array.add(-wavelength);
        }
        public Color changeColor(int wave){
            Color co = Color.BLACK;
            double rel = (1-(wave-300.)/(800.0-300.0))*0.8;
            if ( wave>0) {
                co = Color.getHSBColor( (float)rel, 1f, 1f);            
            }
            else {                                
                co = Color.getHSBColor( (float)rel, 0.5f, 0.5f);            
            }
            return co;
        }
        public void removePlaneWave(){
            if ( x_pos.get(0)> (int)(this.getWidth()*0.5+40) ) {
                after_x_pos.add(x_pos.get(0));
                after_wavelength.add(wavelength_array.get(0));
                x_pos.remove(0);
                wavelength_array.remove(0);
            }
            return;
        }
        
        public void paintComponent(Graphics g)                
        {   
            int height = this.getHeight();
            int width = this.getWidth();
            final int STEP = 5;            
            
            super.paintComponent(g);
            g.setColor(Color.black);

                //g.clearRect(351, 0, this.getWidth(),this.getHeight());
            if ( !isTimerOn ) return;
            call= call+1;
            int ch = wavelength/100;
            if ( ch != 0  ) {
                if ( call%ch ==0 ) newWave( (call/ch)%2 );
            }
            
            removePlaneWave();
            int size = x_pos.size();
            for(int i=0 ; i< size ; i++){
                g.setColor(changeColor(wavelength_array.get(i)));                            
                x_pos.set(i,x_pos.get(i)+STEP);            
                g.drawLine(x_pos.get(i),60,x_pos.get(i), 230);                
            }
            
            for( int i=0 ; i< after_x_pos.size(); i++){
                g.setColor(changeColor(after_wavelength.get(i)));
                after_x_pos.set(i,after_x_pos.get(i)+STEP);
                int x = (int)(this.getWidth()*0.5)+50;
                int w = (after_x_pos.get(i) - x)*2;
                int y = (int)(this.getHeight()/2)-w/2;
                int h = w;
                
                //System.out.format("%d %d %d %d\n",w,h,x,y);
                if ( single_mode) {
                g.drawArc(x-h/2, y, w, h, -90, 180);  
                }
                else {
                    g.drawArc(x-h/2, y-65, w, h, -90, 180);                    
                    g.drawArc(x-h/2, y+65, w, h, -90, 180);                    
                }    
            }
            if ( single_mode) {
                g.setColor(Color.BLACK);
                g.fillRect((int)(width*0.5),0,50,120);
                g.fillRect((int)(width*0.5),height-120,50,120);
            }
            else {
                g.setColor(Color.BLACK);
                g.fillRect((int)(width*0.5),0,50,70);
                g.fillRect((int)(width*0.5),100,50,100);
                g.fillRect((int)(width*0.5),230,50,height);
            }
            //System.out.format("%d %d\n",radius.get(0), wavelength );            
        }
        public void restart(){
            x_pos.clear();
            after_x_pos.clear();
            wavelength_array.clear();
            after_wavelength.clear();
            x_pos.add(0);
            wavelength_array.add(wavelength);
            call=0;
        }

    }

    public JFreeChart getResultChart(){
        // XY시리즈 생성.
        XYSeries series = new XYSeries("Histogram of light amplitude");            
        Integer radius_size = ((UpperViewPane)upperView).getRadius().size();
        Integer wavelength_value = ((UpperViewPane)upperView).getWavelengths().get(0);            
        Double bin_width = 31.0/bin_size;
            
        for( int i=0 ; i< bin_size ; i++){
            double theta = Math.toRadians(-15.5 + bin_width*i);
            //double alpha = Math.PI* slit_width/wavelength_value*Math.sin(theta);
            //double beta  = Math.PI* slit_distance/wavelength_value*Math.sin(theta);
            double alpha = Math.PI* slit_width*Math.sin(theta);
            double beta  = Math.PI* slit_distance*Math.sin(theta);
            double amplitude = Math.cos(beta)*Math.cos(beta)*(Math.sin(alpha)/alpha)*(Math.sin(alpha)/alpha);                
            // series에다가 (x,y)축 값을 추가
            series.add( theta, amplitude);
        }
        // XY시리즈를 Dataset 형태로 변경
        XYSeriesCollection data = new XYSeriesCollection(series);                  
        final JFreeChart chart = ChartFactory.createXYLineChart("Amplitude of Light","Angle","Amp.",data,PlotOrientation.VERTICAL,true,true,false);
        chart.setTitle("Amplitude of light"); // 차트 타이틀
        return chart;
            
    }
    public class ResultViewPane extends ChartPanel{        
        ResultViewPane(JFreeChart chart){                        
            super(chart);
        }

        public void paintComponent(Graphics g2)
        {
            super.paintComponent(g2);
            int height = this.getHeight();
            int width = this.getWidth();
            if ( isTimerOn ) {
                this.setChart(getResultChart());
            }
        }
    }
    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        theButtonGroup = new javax.swing.ButtonGroup();
        jFileChooser1 = new javax.swing.JFileChooser();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        theStartButton = new javax.swing.JButton();
        theEndButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        theWaveLength = new javax.swing.JSpinner();
        theRadioButton1 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        theDistanceSlits = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        theSingleSlitRadioButton = new javax.swing.JRadioButton();
        theSlitWidth = new javax.swing.JSpinner();
        theWaveLengthSlider = new javax.swing.JSlider();
        ClearButton = new javax.swing.JButton();
        theWidthSlider = new javax.swing.JSlider();
        theDistanceSlider = new javax.swing.JSlider();
        upperView = new UpperViewPane();
        resultView = new ResultViewPane(getResultChart());
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("영의 이중슬릿"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));

        jInternalFrame1.setTitle("Young's double slit experiment");
        jInternalFrame1.setMaximumSize(new java.awt.Dimension(800, 600));
        jInternalFrame1.setMinimumSize(new java.awt.Dimension(800, 600));
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(800, 600));
        jInternalFrame1.setVisible(true);

        jPanel1.setMaximumSize(new java.awt.Dimension(334, 571));
        jPanel1.setMinimumSize(new java.awt.Dimension(334, 571));

        theStartButton.setText("Start");
        theStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theStartButtonActionPerformed(evt);
            }
        });

        theEndButton.setText("Quit");
        theEndButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theEndButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("WaveLength(nm)");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theWaveLengthSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), theWaveLength, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theWaveLength.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theWaveLengthStateChanged(evt);
            }
        });

        theButtonGroup.add(theRadioButton1);
        theRadioButton1.setText("Double Slit Experiment");
        theRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theRadioButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Slit Distance(λ)");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theDistanceSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), theDistanceSlits, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theDistanceSlits.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theDistanceSlitsStateChanged(evt);
            }
        });

        jLabel1.setText("Slit width(λ)");

        theButtonGroup.add(theSingleSlitRadioButton);
        theSingleSlitRadioButton.setSelected(true);
        theSingleSlitRadioButton.setText("Single Slit( d=0 )");
        theSingleSlitRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theSingleSlitRadioButtonStateChanged(evt);
            }
        });
        theSingleSlitRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theSingleSlitRadioButtonActionPerformed(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theWidthSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), theSlitWidth, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theSlitWidth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theSlitWidthStateChanged(evt);
            }
        });

        theWaveLengthSlider.setMaximum(800);
        theWaveLengthSlider.setMinimum(300);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theWaveLength, org.jdesktop.beansbinding.ELProperty.create("${value}"), theWaveLengthSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theWaveLengthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theWaveLengthSliderStateChanged(evt);
            }
        });
        theWaveLengthSlider.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                theWaveLengthSliderPropertyChange(evt);
            }
        });

        ClearButton.setText("Clear");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });

        theWidthSlider.setMaximum(50);
        theWidthSlider.setMinimum(1);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theSlitWidth, org.jdesktop.beansbinding.ELProperty.create("${value}"), theWidthSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theWidthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theWidthSliderStateChanged(evt);
            }
        });
        theWidthSlider.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                theWidthSliderPropertyChange(evt);
            }
        });

        theDistanceSlider.setMaximum(150);
        theDistanceSlider.setValue(0);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theDistanceSlits, org.jdesktop.beansbinding.ELProperty.create("${value}"), theDistanceSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theDistanceSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theDistanceSliderStateChanged(evt);
            }
        });
        theDistanceSlider.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                theDistanceSliderPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(226, 226, 226))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(theDistanceSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                    .addComponent(theWidthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(theWaveLength, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(theSlitWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(theDistanceSlits, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(theStartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(theEndButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(theRadioButton1)
                            .addComponent(theSingleSlitRadioButton)
                            .addComponent(jLabel1)
                            .addComponent(theWaveLengthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(theSingleSlitRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(theRadioButton1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(theSlitWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(theWidthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(theDistanceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(theDistanceSlits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(theWaveLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(theWaveLengthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(theStartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(theEndButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        upperView.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        upperView.setMaximumSize(new java.awt.Dimension(566, 298));
        upperView.setMinimumSize(new java.awt.Dimension(566, 298));

        javax.swing.GroupLayout upperViewLayout = new javax.swing.GroupLayout(upperView);
        upperView.setLayout(upperViewLayout);
        upperViewLayout.setHorizontalGroup(
            upperViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );
        upperViewLayout.setVerticalGroup(
            upperViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );

        resultView.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        resultView.setMaximumSize(new java.awt.Dimension(566, 243));
        resultView.setMinimumSize(new java.awt.Dimension(566, 243));
        resultView.setPreferredSize(new java.awt.Dimension(566, 243));

        javax.swing.GroupLayout resultViewLayout = new javax.swing.GroupLayout(resultView);
        resultView.setLayout(resultViewLayout);
        resultViewLayout.setHorizontalGroup(
            resultViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        resultViewLayout.setVerticalGroup(
            resultViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 239, Short.MAX_VALUE)
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jInternalFrame1.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(upperView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(upperView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void theDistanceSliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_theDistanceSliderPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_theDistanceSliderPropertyChange

    private void theDistanceSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theDistanceSliderStateChanged
        // TODO add your handling code here:
        if ( ! single_mode ) {
            slit_distance = (int) theDistanceSlider.getValue();
            theDistanceSlits.setValue(slit_distance);
        }
        else {
            slit_distance = (int) 0;
            theDistanceSlits.setValue(slit_distance);
        }
    }//GEN-LAST:event_theDistanceSliderStateChanged

    private void theWidthSliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_theWidthSliderPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_theWidthSliderPropertyChange

    private void theWidthSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theWidthSliderStateChanged
        // TODO add your handling code here:
        slit_width = (int)theWidthSlider.getValue();
        theSlitWidth.setValue(slit_width);
    }//GEN-LAST:event_theWidthSliderStateChanged

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButtonActionPerformed
        // 선언은 JPanel로 되어 있기 때문에 사용하려면 형변환 필요.
        ((UpperViewPane)upperView).restart();// TODO add your handling code here:
        theStartButton.setText("Start");
        upperView.repaint();
    }//GEN-LAST:event_ClearButtonActionPerformed

    private void theWaveLengthSliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_theWaveLengthSliderPropertyChange
        // TODO add your handling code here:
        //System.out.println("Found PropertyChanged.");
        String property = evt.getPropertyName();
        if ( "value".equals(property)) {
            wavelength = (int) evt.getNewValue();
        }
    }//GEN-LAST:event_theWaveLengthSliderPropertyChange

    private void theWaveLengthSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theWaveLengthSliderStateChanged
        // TODO add your handling code here:
        //System.out.println("Found state changed!");
        //System.out.println("Source : "+evt.getSource());
        wavelength = (int) theWaveLengthSlider.getValue();
        theWaveLength.setValue((int)theWaveLengthSlider.getValue());
    }//GEN-LAST:event_theWaveLengthSliderStateChanged

    private void theSlitWidthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theSlitWidthStateChanged
        slit_width = (int) theSlitWidth.getValue();  // TODO add your handling code here:
        theWidthSlider.setValue(slit_width);
    }//GEN-LAST:event_theSlitWidthStateChanged

    private void theSingleSlitRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theSingleSlitRadioButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Selected Single Slit");
        single_mode = true;  
        upperView.repaint();
    }//GEN-LAST:event_theSingleSlitRadioButtonActionPerformed

    private void theDistanceSlitsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theDistanceSlitsStateChanged
        // TODO add your handling code here:
        slit_distance = (int) theDistanceSlits.getValue();
        theDistanceSlider.setValue(slit_distance);

    }//GEN-LAST:event_theDistanceSlitsStateChanged

    private void theRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theRadioButton1ActionPerformed
        // TODO add your handling code here:
        System.out.println("Selected Double Slit");
        single_mode = false; 
        upperView.repaint();
    }//GEN-LAST:event_theRadioButton1ActionPerformed

    private void theWaveLengthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theWaveLengthStateChanged
        // TODO add your handling code here:
        //System.out.println("Source : "+evt.getSource());
        wavelength = (int) theWaveLength.getValue();
        theWaveLengthSlider.setValue((int)theWaveLength.getValue());
    }//GEN-LAST:event_theWaveLengthStateChanged

    private void theEndButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theEndButtonActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        //dispose();
        System.exit(0);
    }//GEN-LAST:event_theEndButtonActionPerformed

    private void theStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theStartButtonActionPerformed
        // TODO add your handling code here:
        if ( isTimerOn ) { 
            timerStop();
            theStartButton.setText("Resume");
        }
        else {
            theStartButton.setText("Stop");
            timerStart();
        }
    }//GEN-LAST:event_theStartButtonActionPerformed

    private void theSingleSlitRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theSingleSlitRadioButtonStateChanged
        // TODO add your handling code here:
        //System.out.println(theSingleSlitRadioButton.getSelectedIcon());
        //theSingleSlitRadioButton.get
    }//GEN-LAST:event_theSingleSlitRadioButtonStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClearButton;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel resultView;
    private javax.swing.ButtonGroup theButtonGroup;
    private javax.swing.JSlider theDistanceSlider;
    private javax.swing.JSpinner theDistanceSlits;
    private javax.swing.JButton theEndButton;
    private javax.swing.JRadioButton theRadioButton1;
    private javax.swing.JRadioButton theSingleSlitRadioButton;
    private javax.swing.JSpinner theSlitWidth;
    private javax.swing.JButton theStartButton;
    private javax.swing.JSpinner theWaveLength;
    private javax.swing.JSlider theWaveLengthSlider;
    private javax.swing.JSlider theWidthSlider;
    private javax.swing.JPanel upperView;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
