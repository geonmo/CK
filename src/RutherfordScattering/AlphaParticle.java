/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RutherfordScattering;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.imageio.ImageIO;

/**
 *
 * @author Geonmo
 */
public class AlphaParticle {
    Point pos;
    double charge;    
    double mass; 
    int size = 20;
    
    Image img;    
    
    double[] velocity = new double[2];
    AlphaParticle(){                
        this.pos = new Point(27,135);                
        this.mass=1.67e-27;
        this.charge = 2*(1.6e-19);      
        this.velocity[0] = 2.0e7;
        this.loadImage();
    }
    AlphaParticle(int impactparameter,double velocity ){                        
        this.pos = new Point(27,135-impactparameter*(int)(this.size/4));                
        this.mass=1.67e-27;
        this.charge = 2*(1.6e-19);              
        this.velocity[0] = velocity;        
        this.loadImage();
    }
    /**
    AlphaParticle(int height, int impact_parameter, double velocity){
        this.pos.setLocation( 0,height/2 + impact_parameter );
        this.velocity = velocity;
        this.mass=6.7e-27;
        this.charge = 2*(1.6e-19);
        
    }
    */
    
    void nextStep(Nucleus atom){
        final double mappingScale = 1e7;
        //final int velocity_scale = 10;
        //System.out.printf("pre_x : %d, pre_y : %d\n",pos.x,pos.y);
        //System.out.printf("energy_x : %e, energy_y : %e\n",this.energy[0],this.energy[1]);
        
        
        double[] dVel = cal_dV(atom);       
        dVel[0] = dVel[0]*mappingScale;
        dVel[1] = dVel[1]*mappingScale;
        //System.out.printf("dVel_x : %e, dVel_y : %e\n",dVel[0],dVel[1]);
        this.velocity[0] = this.velocity[0]+dVel[0];
        this.velocity[1] = this.velocity[1]+dVel[1];
        //System.out.printf("vel_x : %f, vel_y : %f\n",this.velocity[0],this.velocity[1]);
        int vel_x = (int)( this.velocity[0]/mappingScale);
        int vel_y = (int)( this.velocity[1]/mappingScale);
        //System.out.printf("vel_x : %d, vel_y : %d\n",vel_x,vel_y);
        
        pos.x = pos.x+vel_x;
        pos.y = pos.y+vel_y;
        //pos.x = pos.x;
        //pos.y = pos.y;
        //System.out.printf("post_x : %d, post_y : %d\n",pos.x,pos.y);
    }

    double[] cal_dV( Nucleus atom){    
        
        double distance = this.pos.distance(atom.pos)*1e-15;
        double distance_x = (this.pos.x - atom.pos.x)*1e-15;
        double distance_y = (this.pos.y - atom.pos.y)*1e-15;
        //System.out.printf("distance x : %e, distance y :%e\n",distance_x, distance_y);
        double k = 8.988e9; // 1/4pi epsion0
        double dvel= k*this.charge*atom.charge/distance/distance ;
        double dvel_x = dvel * (distance_x /distance);
        double dvel_y = dvel * (distance_y /distance);
        
        double[] ar_energy = {dvel_x, dvel_y};        
        return ar_energy;
    }
     
    public void loadImage() {
        String image_path = String.format("/RutherfordScattering/resource/alpha.png");        
        Image image = null;
        try {
            image = ImageIO.read( this.getClass().getResource(image_path));
            } catch (IOException ex) {
            Logger.getLogger(ImpactViewPanel.class.getName()).log(Level.SEVERE, null, ex);            
            }
        //System.out.println(image);        
        if ( image != null) {
            this.img = image.getScaledInstance(size,size,Image.SCALE_DEFAULT);            
            System.out.println("Setting!! image!");
        }
    }
    
}
