package io.github.mfaisalkhatri.uploaddownloaddemo;

import static org.testng.Assert.assertEquals;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class RobotClassFileUploadTest {

    @Test
    public void testFileUpload () throws AWTException {
        WebDriver driver = new ChromeDriver ();
        driver.get ("https://filebin.net/");

        driver.findElement (By.id ("#fileField"))
            .click ();
        selectFile ("C:\\Users\\Faisal\\Downloads\\Smallpdf.pdf");

        WebElement tableRow = driver.findElement (By.cssSelector ("table > tbody > tr"));
        String fileNameText = tableRow.findElement (By.cssSelector ("td:nth-child(1) > a"))
            .getText ();
        assertEquals (fileNameText, "file_example_JPG_100kB.jpg");

        driver.quit ();

    }

    public void selectFile (String path) throws AWTException {
        StringSelection strSelection = new StringSelection (path);
        Clipboard clipboard = Toolkit.getDefaultToolkit ()
            .getSystemClipboard ();
        clipboard.setContents (strSelection, null);

        Robot robot = new Robot ();

        robot.delay (2000);
        robot.keyPress (KeyEvent.VK_CONTROL);
        robot.keyPress (KeyEvent.VK_V);
        robot.keyRelease (KeyEvent.VK_V);
        robot.keyRelease (KeyEvent.VK_CONTROL);
        robot.keyPress (KeyEvent.VK_ENTER);
        robot.delay (4000);
        robot.keyRelease (KeyEvent.VK_ENTER);
    }
}
