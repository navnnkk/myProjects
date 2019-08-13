package com.naveen;

import javax.swing.*;
import java.awt.*;

class PageSetup {

    PageSetup(JFrame parentFrame) {

        JDialog dialog = new JDialog(parentFrame,"Page Setup",true);
        dialog.setLayout(null);
        dialog.setSize(600,400);

        int width = (parentFrame.getWidth() - dialog.getWidth()) / 2;
        int height = (parentFrame.getHeight() - dialog.getHeight()) / 2;
        dialog.setLocation(width,height);

        JPanel paper = new JPanel();
        paper.setBorder(BorderFactory.createTitledBorder("Paper"));
        paper.setBounds(20,15,350,100);

        // ******* Paper *******
        paper.setLayout(new GridLayout());

        JPanel preview = new JPanel();
        preview.setBorder(BorderFactory.createTitledBorder("Preview"));
        preview.setBounds(380,15,190,285);

        JPanel orientation = new JPanel();
        orientation.setBorder(BorderFactory.createTitledBorder("Orientation"));
        orientation.setBounds(20,130,105,90);

        JPanel margins = new JPanel();
        margins.setBorder(BorderFactory.createTitledBorder("Margins (millimeters)"));
        margins.setBounds(135,130,235,90);

        JLabel header = new JLabel("Header:");
        JLabel footer = new JLabel("Footer:");
        JTextField headerText = new JTextField(250);
        JTextField footerText = new JTextField(250);
        header.setBounds(20,238,45,15);
        footer.setBounds(20,280,45,15);
        headerText.setBounds(95,238,275,20);
        footerText.setBounds(95,279,275,20);

        JButton ok = new JButton("OK");
        ok.setBounds(390,315,80,30);
        JButton cancel = new JButton("Cancel");
        cancel.setBounds(490,315,80,30);


        dialog.add(paper);
        dialog.add(preview);
        dialog.add(orientation);
        dialog.add(margins);
        dialog.add(header);
        dialog.add(footer);
        dialog.add(headerText);
        dialog.add(footerText);
        dialog.add(ok);
        dialog.add(cancel);

        dialog.setVisible(true);
        dialog.dispose();

    }
}
