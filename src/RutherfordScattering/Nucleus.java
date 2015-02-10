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
public class Nucleus extends AlphaParticle {
        Nucleus(){                
        this.pos = new Point(500,135);        
        this.velocity = 0;
        this.mass=1000;
        this.charge = 1*(1.6e-19);  
        this.size = 50;
    }    
    public void loadImage() {
        String image_path = String.format("/RutherfordScattering/resource/nucl.png");        
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
