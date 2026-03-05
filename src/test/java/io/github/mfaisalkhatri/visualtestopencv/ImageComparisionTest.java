package io.github.mfaisalkhatri.visualtestopencv;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageComparisionTest {
    static {
        System.load ("/Users/faisalkhatri/Github/opencv/build/lib/libopencv_java4130.dylib");
    }

    public static void main (final String[] args) throws IOException {
        CaptureScreenshot.captureActualImage ();
        final Mat img1 = Imgcodecs.imread ("screenshots/baseline.png");
        final Mat img2 = Imgcodecs.imread ("screenshots/actual.png");

        if (img1.empty () || img2.empty ()) {
            System.out.println ("Error: One or both images could not be loaded.");
            return;
        }

        if (img1.size ().width != img2.size ().width || img1.size ().height != img2.size ().height) {
            System.out.println ("Images have different sizes — resizing img2 to match img1.");
            Imgproc.resize (img2, img2, img1.size ());
        }

        final Mat diff = new Mat ();
        Core.absdiff (img1, img2, diff);

        final Scalar sumScalar = Core.sumElems (diff);
        final double totalDiff = sumScalar.val[0] + sumScalar.val[1] + sumScalar.val[2];

        if (totalDiff == 0) {
            System.out.println ("Images are IDENTICAL.");
        } else {
            System.out.println ("Images are DIFFERENT. Total pixel difference: " + totalDiff);
            Imgcodecs.imwrite ("screenshots/comparison_diff_raw.png", diff);
            System.out.println ("Diff images saved.");
        }
    }
}