package computerGraphics;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;



public class JOGLPractice extends GLJPanel implements GLEventListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	double rotateX = 15;    //Used for rotating the letters and inner frame
    double rotateY = -15;	//Used for rotating the letters
    double rotateZ = 0;		//Used for rotating the outer frame
    double rotateFY = -15;	//Used for rotating the middle frame
    double translateZ = 0;	//Used for translating the outer frame through the z dimension
    double incrementSize = -0.005;	//Used to change the size value for the letters
    double incrementTrans = 0.005;	//Used to change the magnitude of the z dimension translation on the outer frame
    double size = 1;		//Used to change the size of the letters

	private float satelliteAngle;
	

    
    public static void main(String[] args) {
        JFrame window = new JFrame("Letters and Frames");
        JOGLPractice panel = new JOGLPractice();
        window.setContentPane(panel);
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
        
        //Responsible for the animation of the objects
        final FPSAnimator animator = new FPSAnimator(panel, 300, true);
        animator.start();
    }
    
    public JOGLPractice() {
        super(new GLCapabilities(null)); // Makes a panel with default OpenGL "capabilities".
        setPreferredSize(new Dimension(640, 480));
        addGLEventListener(this); // A listener is essential! The listener is where the OpenGL programming lives.
    }
    
    private void square(GL2 gl2, double r, double g, double b) {
        //Square used in the Z and the F
    	gl2.glColor3d(r,g,b);
        gl2.glBegin(GL2.GL_TRIANGLE_FAN);
        gl2.glVertex3f(0f, 0f, 0f);
        gl2.glVertex3f(0f, 1f, 0f);
        gl2.glVertex3f(0f, 1f, -1f);
        gl2.glVertex3f(0f, 0f, -1f);
        gl2.glEnd();
    }
    
    private void rectangle(GL2 gl, double r, double g, double b) {
		//Rectangle used in the Z
    	gl.glColor3d(r,g,b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3f(0f, 0f, 0f);			
			gl.glVertex3f(0f, 1f, 0f);			
			gl.glVertex3f(4f, 1f, 0f);
			gl.glVertex3f(4f, 0f, 0f);			
		gl.glEnd();
    }
    
    private void diagonalFB(GL2 gl, double r, double g, double b) {
    	//Used for front and back of the Z's diagonal
    	gl.glColor3d(r,g,b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3f(0f, 1f, 0f);			
			gl.glVertex3f(1f, 1f, 0f);			
			gl.glVertex3f(4f, 4f, 0f);
			gl.glVertex3f(3f, 4f, 0f);			
		gl.glEnd();
    }
    
    private void diagonalLR(GL2 gl, double r, double g, double b) {
    	//Used for the left and right side of the Z's diagonal
    	gl.glColor3d(r,g,b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3f(0f, 1f, 0f);			
			gl.glVertex3f(0f, 1f, -1f);			
			gl.glVertex3f(3f, 4f, -1f);
			gl.glVertex3f(3f, 4f, 0f);			
		gl.glEnd();
    }
    
    private void theZ(GL2 gl) {
       //Creates the Z. Very much a pain in the butt
    	//Bottom of Z
    		//Front
    	gl.glPushMatrix();
        rectangle(gl, 0, 0, 1);        	
        	//Back
        gl.glPushMatrix();
        gl.glTranslatef(0f, 0f, -1f);
        rectangle(gl, 0 ,0, 0.4);
        gl.glPopMatrix();
        	//Bottom
        gl.glPushMatrix();
        gl.glRotatef(-90, 1f, 0f, 0f);
        rectangle(gl, 0, 0, 0.2);
        gl.glPopMatrix();
        	//Top
        gl.glPushMatrix();
        gl.glRotatef(-90, 1, 0, 0);
        gl.glTranslatef(0f, 0f, 1f);
        rectangle(gl, 0, 0, 0.7);
        gl.glPopMatrix();
        	//Left
        gl.glPushMatrix();
        square(gl, 0, 0, 0.8);
        gl.glPopMatrix();
        	//Right
        gl.glPushMatrix();
        gl.glTranslatef(4f, 0f, 0f);
        square(gl, 0, 0, 0.1);
        gl.glPopMatrix();

        //Middle diagonal of Z
        	//Front
        gl.glPushMatrix();
        diagonalFB(gl, 0, 0, 1);
        gl.glPopMatrix();
        	//Back
        gl.glPushMatrix();
        gl.glTranslatef(0f, 0f, -1f);
        diagonalFB(gl, 0, 0, 0.4);
        gl.glPopMatrix();
        	//Left
        gl.glPushMatrix();
        gl.glTranslatef(0f, 0f, 0f);
        diagonalLR(gl, 0, 0, 0.8);
        gl.glPopMatrix();
        	//Right
        gl.glPushMatrix();
        gl.glTranslatef(1f, 0f, 0f);
        diagonalLR(gl, 0, 0, 0.1);
        gl.glPopMatrix();

        // Top of Z	
        	//Front
        gl.glPushMatrix();
        gl.glTranslatef(0, 4, 0);
        rectangle(gl, 0, 0, 1);
        	//Back
        gl.glPushMatrix();
        gl.glTranslatef(0f, 0f, -1f);
        rectangle(gl, 0, 0, 0.4);
        gl.glPopMatrix();
        	//Bottom
        gl.glPushMatrix();
        gl.glRotatef(-90, 1f, 0f, 0f);
        rectangle(gl, 0, 0, 0.2);
        gl.glPopMatrix();
        	//Top
        gl.glPushMatrix();
        gl.glRotatef(-90, 1, 0, 0);
        gl.glTranslatef(0f, 0f, 1f);
        rectangle(gl, 0, 0, 0.7);
        gl.glPopMatrix();
        	//Left
        gl.glPushMatrix();
        square(gl, 0, 0, 0.8);
        gl.glPopMatrix();
        	//Right
        gl.glPushMatrix();
        gl.glTranslatef(4f, 0f, 0f);
        square(gl, 0, 0, 0.1);
        gl.glPopMatrix();

        
        gl.glPopMatrix(); // Restore matrix to its state before cube() was called.
    }
    
    private void rectangF(GL2 gl, double r, double g, double b) {
		//Used as the rectangle for the F
    	gl.glColor3d(r,g,b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3f(5f, 0f, 0f);			
			gl.glVertex3f(6f, 0f, 0f);			
			gl.glVertex3f(6f, 5f, 0f);
			gl.glVertex3f(5f, 5f, 0f);			
		gl.glEnd();
    }
    
    private void theF(GL2 gl) {
    	//Used to create the F. Also a pain but less so?
    	// Side of F
    		//Front
    	gl.glPushMatrix();
    	rectangF(gl, 1, 0.6, 0);
    	gl.glPopMatrix();
    	
    		//Back
    	gl.glPushMatrix();
    	gl.glTranslated(0, 0, -1);
    	rectangF(gl, 0.5, 0.2, 0);
    	gl.glPopMatrix();
    	
    		//Right side
    	gl.glPushMatrix();
    	gl.glTranslated(6, 0, 5);
    	gl.glRotated(90, 0, 1, 0);
    	rectangF(gl, 0.4, 0.2, 0);
    	gl.glPopMatrix();
    	
    		//Left side
    	gl.glPushMatrix();
    	gl.glTranslated(5, 0, 5);
    	gl.glRotated(90, 0, 1, 0);
    	rectangF(gl, 1, 0.5, 0);
    	gl.glPopMatrix();
    	
    		//Bottom
    	gl.glPushMatrix();
    	gl.glRotated(90, 1, 0, 0);
    	gl.glTranslated(6, -1, 0);
    	gl.glRotated(90, 0, 1, 0);
    	square(gl, 0.3, 0.1, 0);
    	gl.glPopMatrix();
    	
    		//Top
    	gl.glPushMatrix();
    	gl.glRotated(90, 1, 0, 0);
    	gl.glTranslated(6, -1, -5);
    	gl.glRotated(90, 0, 1, 0);
    	square(gl, 0.8, 0.5, 0);
    	gl.glPopMatrix();
    	
    	//Top of F
    		//Front
    	gl.glPushMatrix();
    	gl.glRotated(90, 0, 0, 1);
    	gl.glTranslated(-1, -7.8, 0);
    	gl.glScaled(1, 0.4, 1);
    	rectangF(gl, 1, 0.6, 0);
    	gl.glPopMatrix();
    	
    		//Back
    	gl.glPushMatrix();
    	gl.glRotated(90, 0, 0, 1);
    	gl.glTranslated(-1, -7.8, -1);
    	gl.glScaled(1, 0.4, 1);
    	rectangF(gl, 0.5, 0.2, 0);
    	gl.glPopMatrix();
    	
    		//Top
    	gl.glPushMatrix();
    	gl.glRotated(90, 0, 0, 1);
    	gl.glTranslated(5, -7.8, 5);
    	gl.glRotated(90, 0, 1, 0);
    	gl.glScaled(1, 0.36, 1);
    	rectangF(gl, 0.8, 0.5, 0);
    	gl.glPopMatrix();
    	
    		//Bottom
    	gl.glPushMatrix();
    	gl.glRotated(90, 0, 0, 1);
    	gl.glTranslated(4, -7.8, 5);
    	gl.glRotated(90, 0, 1, 0);
    	gl.glScaled(1, 0.36, 1);
    	rectangF(gl, 0.3, 0.1, 0);
    	gl.glPopMatrix();
    	
    		//Right Side
    	gl.glPushMatrix();
    	gl.glTranslated(7.8, 4, 0);
    	square(gl, 0.4, 0.2, 0);
    	gl.glPopMatrix();
    	
    	//Middle of F
			//Front
		gl.glPushMatrix();
		gl.glRotated(90, 0, 0, 1);
		gl.glTranslated(-3, -7.2, 0);
		gl.glScaled(1, 0.25, 1);
		rectangF(gl, 1, 0.6, 0);
		gl.glPopMatrix();
	
			//Back
		gl.glPushMatrix();
		gl.glRotated(90, 0, 0, 1);
		gl.glTranslated(-3, -7.2, -1);
		gl.glScaled(1, 0.25, 1);
		rectangF(gl, 0.5, 0.2, 0);
		gl.glPopMatrix();
		
			//Top
		gl.glPushMatrix();
		gl.glRotated(90, 0, 0, 1);
		gl.glTranslated(3, -7.2, 5);
		gl.glRotated(90, 0, 1, 0);
		gl.glScaled(1, 0.25, 1);
		rectangF(gl, 0.8, 0.5, 0);
		gl.glPopMatrix();
		
			//Bottom
		gl.glPushMatrix();
		gl.glRotated(90, 0, 0, 1);
		gl.glTranslated(2, -7.2, 5);
		gl.glRotated(90, 0, 1, 0);
		gl.glScaled(1, 0.25, 1);
		rectangF(gl, 0.3, 0.1, 0);
		gl.glPopMatrix();
		
			//Right Side
		gl.glPushMatrix();
		gl.glTranslated(7.2, 2, 0);
		square(gl, 0.4, 0.2, 0);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
    	
    }
    
    private void frameFB(GL2 gl, double r, double g, double b) {
    	//Used to create the Front and back of the frames
    	gl.glColor3d(r,g,b);
    	//Bottom
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(-1.3, -1, 0);			
			gl.glVertex3d(9, -1, 0);			
			gl.glVertex3d(9, -2, 0);
			gl.glVertex3d(-1.3,-2, 0);			
		gl.glEnd();
		//Top
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(-1.3, 6, 0);			
			gl.glVertex3d(9, 6, 0);			
			gl.glVertex3d(9, 7, 0);
			gl.glVertex3d(-1.3, 7, 0);			
		gl.glEnd();
		//left
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(-1.3, -1, 0);			
			gl.glVertex3d(-1.3, 6, 0);			
			gl.glVertex3d(-0.5, 6, 0);
			gl.glVertex3d(-0.5 ,-1, 0);			
		gl.glEnd();
		//right
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(9, -1, 0);			
			gl.glVertex3d(9, 6, 0);			
			gl.glVertex3d(8.2, 6, 0);
			gl.glVertex3d(8.2 ,-1, 0);			
		gl.glEnd();
    }
    
    /*
     * 
     * From here on out I start multiplying each color double by the shade I want.
     * This allows me to input a '1' for the color I want and '0' for the colors
     * AND I get the shade I want for that section. This way I can frames of different
     * colors! I am overly proud of this trick I came up with. It came in SUPER HANDY
     * 
     */
    
    
    private void frameOuter(GL2 gl, double r, double g, double b) {
    	//Creates the outer wall of the frames
    	gl.glColor3d(0.2*r, 0.2*g, 0.2*b);
    	//Bottom
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(-1.3, -2, 0);			
			gl.glVertex3d(9, -2, 0);			
			gl.glVertex3d(9, -2, -1);
			gl.glVertex3d(-1.3, -2, -1);			
		gl.glEnd();
		//Top
    	gl.glColor3d(0.7*r, 0.7*g, 0.7*b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(-1.3, 7, 0);			
			gl.glVertex3d(9, 7, 0);			
			gl.glVertex3d(9, 7, -1);
			gl.glVertex3d(-1.3, 7, -1);			
		gl.glEnd();
		//Left
    	gl.glColor3d(0.8*r, 0.8*g, 0.8*b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(-1.3, -2, 0);			
			gl.glVertex3d(-1.3, 7, 0);			
			gl.glVertex3d(-1.3, 7, -1);
			gl.glVertex3d(-1.3, -2, -1);			
		gl.glEnd();
		//Right
    	gl.glColor3d(0.1*r, 0.1*g, 0.1*b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(9, -2, 0);			
			gl.glVertex3d(9, 7, 0);			
			gl.glVertex3d(9, 7, -1);
			gl.glVertex3d(9, -2, -1);			
		gl.glEnd();
    }
    
    private void frameInner(GL2 gl, double r, double g, double b) {
    	//creates the inner wall of the frames
    	gl.glColor3d(0.7*r, 0.7*g, 0.7*b);
    	//Top Bottom
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(-0.5, -1, 0);			
			gl.glVertex3d(8.2, -1, 0);			
			gl.glVertex3d(8.2, -1, -1);
			gl.glVertex3d(-0.5, -1, -1);			
		gl.glEnd();
		//Bottom Top
		gl.glColor3d(0.2*r, 0.2*g, 0.2*b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(-0.5, 6, 0);			
			gl.glVertex3d(8.2, 6, 0);			
			gl.glVertex3d(8.2, 6, -1);
			gl.glVertex3d(-0.5, 6, -1);			
		gl.glEnd();
		//Left 
    	gl.glColor3d(0.1*r, 0.1*g, 0.1*b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(-0.5, -1, 0);			
			gl.glVertex3d(-0.5, 6, 0);			
			gl.glVertex3d(-0.5, 6, -1);
			gl.glVertex3d(-0.5, -1, -1);			
		gl.glEnd();
		//Right
    	gl.glColor3d(0.8*r, 0.8*g, 0.8*b);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex3d(8.2, -1, 0);			
			gl.glVertex3d(8.2, 6, 0);			
			gl.glVertex3d(8.2, 6, -1);
			gl.glVertex3d(8.2, -1, -1);			
		gl.glEnd();
    }
    
    private void theFrame(GL2 gl, double size, double r, double g, double b) {
    	//Creates the frames, can be resized and have the color changed.
    	//Front
    	gl.glPushMatrix();
    	gl.glScaled(size, size, size);
    	frameFB(gl, 1*r, 1*g, 1*b); 							
    	gl.glPopMatrix();			
    	//Back
    	gl.glPushMatrix();
    	gl.glScaled(size, size, size);
    	gl.glTranslated(0, 0, -1);
    	frameFB(gl, r*0.4, g*0.4, b*0.4);
    	gl.glPopMatrix();
    	//Outer
    	gl.glPushMatrix();
    	gl.glScaled(size, size, size);
    	frameOuter(gl, r, g, b);
    	gl.glPopMatrix();
    	//Inner
    	gl.glPushMatrix();
    	gl.glScaled(size, size, size);
    	frameInner(gl, r, g, b);
    	gl.glPopMatrix();
    	gl.glPopMatrix();

    }
    
    void drawSphere(double r, int lats, int longs,GL2 gl) {
    	double lat0;
        double z0;
        double zr0;
        double lat1;
        double z1;
        double zr1;

    	int i, j;
        for(i = 0; i <= lats; i++) {
        	if(i < 5) {
        		lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
                z0  = Math.sin(lat0);
                zr0 =  Math.cos(lat0);

                lat1 = Math.PI * (-0.5 + (double) i / lats);
                z1 = Math.sin(lat1);
                zr1 = Math.cos(lat1);
        	}
        	lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
            z0  = r * Math.sin(lat0);
            zr0 =  Math.cos(lat0);

            lat1 = Math.PI * (-0.5 + (double) i / lats);
            z1 = r * Math.sin(lat1);
            zr1 = Math.cos(lat1);

            double red = 0;
            double g = 0;
            double b = 1;
            double increment = 0.1;
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for(j = 0; j <= longs; j++) {
            	double lng = 2 * Math.PI * (double) (j - 1) / longs;
                double x = r * Math.cos(lng);
                double y = r * Math.sin(lng);
                
                gl.glColor3d(red, g, b);
                gl.glNormal3d(x * zr0, y * zr0, z0);
                gl.glVertex3d(x * zr0, y * zr0, z0);
                gl.glNormal3d(x * zr1, y * zr1, z1);
                gl.glVertex3d(x * zr1, y * zr1, z1);
                red += increment;
                b -= increment;
                
            }
            gl.glEnd();
        }
    }
    
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
			
		gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
		
		//Handles the letters positioning, rotation and size
        gl.glLoadIdentity(); 
        gl.glRotated(rotateZ,0,0,1);
        gl.glRotated(rotateY,0,1,0);
        gl.glRotated(15,1,0,0);
        gl.glScaled(size, size, size);
        gl.glTranslated(-4, -2.5, 0);
        
		theZ(gl);
		theF(gl);
		
		//Handles the inner frames positioning and rotation
		gl.glLoadIdentity();
		gl.glRotated(0,0,0,1);
	    gl.glRotated(-15,0,1,0);
	    gl.glRotated(rotateX,1,0,0);
        gl.glTranslated(-4, -2.5, 0);
		theFrame(gl, 1, 1, 0, 0);
		
		//Handles the middle frames positioning and rotation
		gl.glLoadIdentity();
		gl.glRotated(0,0,0,1);
	    gl.glRotated(rotateFY,0,1,0);
	    gl.glRotated(15,1,0,0);
        gl.glTranslated(-6, -3.75, 0);
		theFrame(gl, 1.5, 0, 1, 0);
		
		//Handles the outer frames positioning and rotation
		gl.glLoadIdentity();
		gl.glRotated(rotateZ,0,0,1);
	    gl.glRotated(-15,0,1,0);
	    gl.glRotated(15,1,0,0);
        gl.glTranslated(-8, -5, translateZ);
		theFrame(gl, 2, 0.5, 0, 0.5);
		
		gl.glLoadIdentity();
		gl.glRotated(45, 1, 1, 0);
		satelliteAngle = (satelliteAngle + 1f) % 360f;
	    final float distance = 15.000f;
	    final float x = (float) Math.sin(Math.toRadians(satelliteAngle)) * distance;
	    final float y = (float) Math.cos(Math.toRadians(satelliteAngle)) * distance;
	    final float z = 0;
	    gl.glTranslatef(x, y, z);
	    gl.glRotatef(satelliteAngle, 0, 0, -1);
	    gl.glRotatef(45f, 0, 1, 0);
	    drawSphere(2, 15, 15, gl);
		
		//Values for rotation, size and position are changed here
		rotateY -= 0.1;
		rotateX += 0.2;
		rotateZ += 0.2;
		rotateFY -= 0.2;
		if (size < 0.1) {
			incrementSize = 0.005;
		} else if(size >= 1) {
			incrementSize = -0.005;
		}
		if(translateZ < -10) {
			incrementTrans = 0.005;
		} else if(translateZ > 10) {
			incrementTrans = -0.005;
		}
		size += incrementSize;
		translateZ += incrementTrans;

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);

		gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		//Sets up the view and background color and things.
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glOrtho(-15, 15, -15, 15, -15, 15);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
        gl.glEnable(GL2.GL_DEPTH_TEST);
	}

}
