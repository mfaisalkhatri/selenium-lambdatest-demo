package io.github.mfaisalkhatri.visualtestopencv;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageComparision {
    static {System.loadLibrary (Core.NATIVE_LIBRARY_NAME);}

    public static void main (final String[] args) {
        final Mat img1 = Imgcodecs.imread ("screenshots/baseline.png");
        final Mat img2 = Imgcodecs.imread ("screenshots/actual.png");

        final Mat diff = new Mat ();
        Core.absdiff (img1, img2, diff);

        Imgcodecs.imwrite ("difference.png", diff); // Save diff image
        System.out.println ("Image comparison completed!");
    }

}

public class ImageComparison {
}