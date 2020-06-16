/*
 * File:	ZProject1FromTemplate.java
 * Author:	Zack Finnegan + jim robertson
 * Date:	3/26/2020
 * Purpose:	This class holds the images that will be display by the Panel class that
 * 			was poorly named. It holds "ZACK!". In addition the method in this class
 * 			provides the color to the objects.
 */
package computerGraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class ZProject1FromTemplate extends JPanel {

    // A counter that increases by one in each frame.
    private int frameNumber;
    // The time, in milliseconds, since the animation started.
    @SuppressWarnings("unused")
	private long elapsedTimeMillis;
    // This is the measure of a pixel in the coordinate system
    // set up by calling the applyLimits method.  It can be used
    // for setting line widths, for example.
    @SuppressWarnings("unused")
	private float pixelSize;

    static int translateX = 0;
    static int translateY = 0;
    static double rotation = -90*Math.PI / 180.0;
    static double scaleX = 1.0;
    static double scaleY = 1.0;
    ImageTemplate myImages = new ImageTemplate();
    BufferedImage zImage = myImages.getImage(ImageTemplate.letterZ);
    BufferedImage aImage = myImages.getImage(ImageTemplate.letterA);
    BufferedImage cImage = myImages.getImage(ImageTemplate.letterC);
    BufferedImage kImage = myImages.getImage(ImageTemplate.letterK);
    BufferedImage exclaimImage = myImages.getImage(ImageTemplate.exclamation);

    public static void main(String[] args) {
        JFrame window;
        window = new JFrame("Zack's 2D Graphic Play"); 
        final ZProject1FromTemplate panel = new ZProject1FromTemplate();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        window.pack();
        window.setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(
                (screen.width - window.getWidth()) / 2,
                (screen.height - window.getHeight()) / 2);
        Timer animationTimer;  // A Timer that will emit events to drive the animation.
        final long startTime = System.currentTimeMillis();
        // Taken from AnimationStarter
        // Modified to change timing and allow for recycling
        animationTimer = new Timer(1600, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (panel.frameNumber > 6) {
                    panel.frameNumber = 0;
                } else {
                    panel.frameNumber++;
                }
                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });
        window.setVisible(true); 
        animationTimer.start(); 
    }

    public ZProject1FromTemplate() {
        // Size of Frame
        setPreferredSize(new Dimension(800, 600));
    }

    // This is where all of the action takes place
    // Code taken from CMSC405P1Template which was take from AnimationStarter.java
    // but modified to add the specific Images
    // Also added looping structure for Different transformations
    protected void paintComponent(Graphics g) {

        /* First, create a Graphics2D drawing context for drawing on the panel.
         * (g.create() makes a copy of g, which will draw to the same place as g,
         * but changes to the returned copy will not affect the original.)
         */
        Graphics2D g2 = (Graphics2D) g.create();

        /* Turn on antialiasing in this graphics context, for better drawing.
         */
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /* Fill in the entire drawing area with white.
         */
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight()); // From the old graphics API!

        /* Here, someone set up a new coordinate system on the drawing area, by calling
         * the applyLimits() method that is defined below.  Without this call, I
         * would be using regular pixel coordinates.  This function sets the value
         * of the global variable pixelSize, which I need for stroke widths in the
         * transformed coordinate system.
         */
        // Controls your zoom and area you are looking at
        applyWindowToViewportTransformation(g2, -40, 100, -20, 20, true);

        AffineTransform savedTransform = g2.getTransform();
        System.out.println("Frame is " + frameNumber);
        switch (frameNumber) {
            case 1: // First frame is translated by (-5,7).
                 translateX = -5;
                 translateY = 7;
                break;
            case 2: // Second frame rotates each image counter clockwise by 45 degrees
                rotation = -135*Math.PI / 180.0;
                break;
            case 3: // Third frame rotates each image clockwise by 90 degrees 
                rotation = -45*Math.PI / 180.0;
                break;
            case 4: // Fourth frame scales each image by 2 for x and .5 for y.
            	scaleX = 2.0;
                scaleY = 0.5;
                break;
            case 5: // Fifth frame returns the image to the original position
            	  translateX = 0;
                  translateY = 0;
                  scaleX = 1.0;
                  scaleY = 1.0;
                  rotation = -90*Math.PI / 180.0;
                break;
            case 6: // Sixth frame rotates each image by 60 degrees Counter
                translateX = -5;
                translateY = -10;
                rotation = -120*Math.PI / 180.0;
                break;
            case 7: // Seventh frame rotates each image by 60 degrees Counter
                translateX = 0;
                translateY = -5;
                rotation = -160*Math.PI / 180.0;
                break;
            default:
                break;
        }
        
        // Adds the Z
        g2.translate(translateX, translateY); // Move image.
        // To offset translate again
        g2.translate(-10,10);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(zImage, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
        
        // Adds the A
         g2.translate(translateX, translateY); // Move image.
        // To offset translate again
        // This allows you to place your images across your graphic
        g2.translate(10,10);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(aImage, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);

        // Adds the C
        g2.translate(translateX, translateY); // Move image.
        // To offset translate again
        // This allows you to place your images across your graphic
        g2.translate(30,10);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(cImage, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
        
        // Adds the K
        g2.translate(translateX, translateY); // Move image.
        // To offset translate again
        // This allows you to place your images across your graphic
        g2.translate(50,10);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(kImage, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
        
        // Adds the exclamation point
        g2.translate(translateX, translateY); // Move image.
        // To offset translate again
        // This allows you to place your images across your graphic
        g2.translate(70,10);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(exclaimImage, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
        
        // Create a Gradient from red to blue for th underline under ZACK!
        GradientPaint gradie = new GradientPaint(0, 0, Color.BLUE, 70, 0, Color.RED);
        g2.setPaint(gradie);
        // Adds the underline under ZACK!
        g2.translate(translateX, translateY);
        g2.translate(74, -15);
        g2.rotate(rotation + -90*Math.PI / 180.0);
        g2.scale(scaleX, scaleY);
        g2.fill(new Rectangle2D.Float(0, 0, 83, 5));
        g2.setTransform(savedTransform);
    }

    // Method taken from CMSC405P1Template which took it directly from AnimationStarter.java Code
    private void applyWindowToViewportTransformation(Graphics2D g2,
            double left, double right, double bottom, double top,
            boolean preserveAspect) {
        int width = getWidth();   // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }

}
