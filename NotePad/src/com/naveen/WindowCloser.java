package com.naveen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowCloser extends WindowAdapter{

    WindowCloser(JFrame frame, JTextArea textArea) { }

    @Override
    public void windowClosing(WindowEvent e) {

        Window window = e.getWindow();
        window.setVisible(false);
        window.dispose();
        System.exit(0);
    }
}
