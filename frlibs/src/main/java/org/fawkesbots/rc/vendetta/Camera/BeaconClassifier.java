package org.fawkesbots.rc.vendetta.Camera;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import org.fawkesbots.rc.vendetta.ARGB;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Priansh on 11/2/16.
 */
public class BeaconClassifier {

    final static int WIDTH = 0,HEIGHT=1, LEFT=0, RIGHT=1;
    //processBitmap returns an integer array containing the left and right colors
    public static boolean debugImage(Bitmap bmp, int[] dimensions, ARGB pixel) {
        Bitmap mutableBmp = bmp.copy(bmp.getConfig(),true);
        for(int x = 0; x<dimensions[WIDTH]; x++)
            for (int y = 0; y < dimensions[HEIGHT]; y++)
                mutableBmp.setPixel(x,y, ARGB.convertToColor(pixel.set(bmp.getPixel(x,y)).getColor()));
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            mutableBmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + File.separator + "F_Auton_CV.jpg");
            Log.e("Image","Finished saving CV edit");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return true;
    }


    //BLUE IS NEGATIVE
    public static int[] processBitmap(Bitmap bmp){
        Log.e("Camera","Bitmap processing started");
        int[] colors = {0,0};
        int guess = 0;
        int pix;
        int[] dimensions = {bmp.getHeight(),bmp.getWidth()};
        ARGB pixel = new ARGB(0);
        //we now traverse the left and right sides of the image independently
        for(int y = dimensions[HEIGHT]/3; (y<(dimensions[HEIGHT]*2/3))&&(y<bmp.getHeight()); y=y+5) {
            for (int x = dimensions[WIDTH]/6; x < (dimensions[WIDTH]/3); x=x+5)
                colors[0] = colors[0] + ARGB.classify(pixel.set(bmp.getPixel(x, y)).getColor());
            for (int x = (dimensions[WIDTH]*2/3); x < dimensions[WIDTH]*5/6; x=x+5)
                colors[1] = colors[1] + ARGB.classify(pixel.set(bmp.getPixel(x, y)).getColor());
        }
        return colors;
    }

}
