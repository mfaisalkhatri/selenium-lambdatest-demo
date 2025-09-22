package io.github.mfaisalkhatri.uploaddownloaddemo;

import static org.testng.Assert.assertTrue;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class RobotClassFileUploadDownloadTest {

    public boolean checkFileDownload (final String downloadedFileName) {
        final File directory = new File (String.valueOf (Paths.get (System.getProperty ("user.home"), "Downloads")));
        final String[] fileList = directory.list ();

        int flag = 0;
        if (fileList != null) {
            for (final String fileName : fileList) {
                if (fileName.equalsIgnoreCase (downloadedFileName)) {
                    System.out.println ("Downloaded file Found: " + directory + " " + fileName);
                    flag = 1;
                }
            }
        } else {
            System.out.println ("Downloads directory is Empty!" + directory);
            return false;
        }
        if (flag == 0) {
            System.out.println ("Error: Downloaded File not found in the path!!" + directory);
            return false;
        }
        return true;
    }

    public void selectFile (final String path) throws AWTException {
        final StringSelection strSelection = new StringSelection (path);
        final Clipboard clipboard = Toolkit.getDefaultToolkit ()
            .getSystemClipboard ();
        clipboard.setContents (strSelection, null);

        final Robot robot = new Robot ();

        robot.delay (2000);
        robot.keyPress (KeyEvent.VK_CONTROL);
        robot.keyPress (KeyEvent.VK_V);
        robot.keyRelease (KeyEvent.VK_V);
        robot.keyRelease (KeyEvent.VK_CONTROL);
        robot.keyPress (KeyEvent.VK_ENTER);
        robot.delay (4000);
        robot.keyRelease (KeyEvent.VK_ENTER);
    }

    @Test
    public void testFileDownload () throws AWTException {
        final WebDriver driver = new ChromeDriver ();
        driver.get ("https://the-internet.herokuapp.com/download");

        final String fileName = driver.findElement (By.cssSelector ("#content > div > a:nth-child(2)"))
            .getText ();
        driver.findElement (By.cssSelector ("#content > div > a:nth-child(2)"))
            .click ();
        selectFile ("C:\\Users\\Faisal\\Downloads\\");

        assertTrue (checkFileDownload (fileName));
        driver.quit ();

    }

    @Test
    public void testFileUpload () throws AWTException {
        final WebDriver driver = new ChromeDriver ();
        driver.get ("https://www.ilovepdf.com/pdf_to_word");

        driver.findElement (By.id ("pickfiles"))
            .click ();
        selectFile ("C:\\Users\\Faisal\\Downloads\\Smallpdf.pdf");

        final WebElement convertToWordBtn = driver.findElement (By.id ("processTask"));
        assertTrue (convertToWordBtn.isDisplayed ());

        driver.quit ();
    }
}