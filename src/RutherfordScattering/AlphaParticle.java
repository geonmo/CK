/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RutherfordScattering;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Geonmo
 */
public class AlphaParticle {
    Point pos;
    double charge;
    double velocity;
    double mass; 
    Image img;
    int size = 20;
    AlphaParticle(){                
        this.pos = new Point(27,135);        
        this.velocity = 2.0e7;
        this.mass=6.7e-27;
        this.charge = 2*(1.6e-19);      
        
    }
    AlphaParticle(int impactparameter ){                
        this.pos = new Point(27,135-impactparameter);        
        this.velocity = 2.0e7;
        this.mass=6.7e-27;
        this.charge = 2*(1.6e-19);              
    }
    /**
    AlphaParticle(int height, int impact_parameter, double velocity){
        this.pos.setLocation( 0,height/2 + impact_parameter );
        this.velocity = velocity;
        this.mass=6.7e-27;
        this.charge = 2*(1.6e-19);
        
    }
    */
    void nextStep(Point next){
        pos.x = pos.x+next.x;
        pos.y = pos.y+next.y; 
    }
    void setVelocity( double velocity){
        this.velocity = velocity;
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
        }
    }
    
}
