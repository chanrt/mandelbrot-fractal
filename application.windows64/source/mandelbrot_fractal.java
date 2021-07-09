import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class mandelbrot_fractal extends PApplet {

int num_iterations = 256;
int[] colors;
int [][] fractal;
float zoom = 1;

int x, y;

double x_start, x_stop, y_start, y_stop;

boolean rendered;

public void setup() {
  
  // size(800, 458);
  background(255);

  colors = new int[16];
  colors[0] = color(66, 30, 15);
  colors[1] = color(25, 7, 26);
  colors[2] = color(9, 1, 47);
  colors[3] = color(4, 4, 73);
  colors[4] = color(0, 7, 100);
  colors[5] = color(12, 44, 138);
  colors[6] = color(24, 82, 177);
  colors[7] = color(57, 125, 209);
  colors[8] = color(134, 181, 229);
  colors[9] = color(211, 236, 248);
  colors[10] = color(241, 233, 191);
  colors[11] = color(248, 201, 95);
  colors[12] = color(255, 170, 0);
  colors[13] = color(204, 128, 0);
  colors[14] = color(153, 87, 0);
  colors[15] = color(106, 52, 3);

  rendered = false;

  x_start = -2.5f;
  x_stop = 1;
  y_start = -1;
  y_stop = 1;

  x = width / 2;
  y = height / 2;
}

public void draw() {
  if (!rendered) {
    background(255);
    noStroke();
    println(x_start, x_stop, y_start, y_stop);

    double x_step = (x_stop - x_start) / width;
    double y_step = (y_stop - y_start) / height;

    int row = 0, col = 0;

    for (double y0 = y_start; y0 < y_stop; y0 += y_step, row++) {
      col = 0;
      for (double x0 = x_start; x0 < x_stop; x0 += x_step, col++) {
        double x = 0.0f, y = 0.0f, x_square = 0.0f, y_square = 0.0f;

        int iteration = 0;
        while (x_square + y_square < 4 && iteration < num_iterations) {
          y = 2*x*y + y0;
          x = x_square - y_square + x0;
          x_square = x*x;
          y_square = y*y;
          iteration++;
        }
        if (iteration < num_iterations) {
          fill(colors[iteration % 16]);
        } else {
          fill(0);
        }
        rect(col, row, 1, 1);
      }
    }
    rendered = true;
  }
  stroke(255);
}

public void mouseWheel(MouseEvent event) {
  float e = event.getCount();
  if (e < 0) { 
    if (zoom >= 1) {
      zoom = zoom + 0.1f;
    } else {
      zoom = 1 / (zoom + 0.1f);
    }
    rescale();
  } else if (e > 0) {
    if (zoom > 1) {
      zoom = 1 / (zoom + 0.1f);
    } else {
      zoom -= 0.1f;
    }
    rescale();
  }
}

public void rescale() {
  double x_coord = ((x / (double) width) * (x_stop - x_start)) + x_start;
  double y_coord = ((y / (double) height) * (y_stop - y_start)) + y_start;

  double x_left = (x_coord - x_start) / zoom;
  double x_right = (x_stop - x_coord) / zoom;
  double y_up = (y_coord - y_start) / zoom;
  double y_down = (y_stop - y_coord) / zoom;

  x_start = x_coord - x_left;
  x_stop = x_coord + x_right;
  y_start = y_coord - y_up;
  y_stop = y_coord + y_down;

  double temp;
  if (x_start > x_stop) {
    temp = x_start;
    x_start = x_stop;
    x_stop = temp;
  }
  if (y_start > y_stop) {
    temp = y_start;
    y_start = y_stop;
    y_stop = temp;
  }

  rendered = false;
}

public void mouseMoved() {
  x = mouseX;
  y = mouseY;
}

public void keyPressed() {
  if (key == 'r') {
    x_start = -2.5f;
    x_stop = 1;
    y_start = -1;
    y_stop = 1;
    rendered = false;
  }
  if (key == 'w') {
    if (zoom >= 1) {
      zoom = zoom + 0.1f;
    } else {
      zoom = 1 / (zoom + 0.1f);
    }
    rescale();
  }
  if (key == 's') {
    if (zoom > 1) {
      zoom = 1 / (zoom + 0.1f);
    } else {
      zoom -= 0.1f;
    }
    rescale();
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "mandelbrot_fractal" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
