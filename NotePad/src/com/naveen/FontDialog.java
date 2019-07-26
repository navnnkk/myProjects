package com.naveen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FontDialog extends MouseAdapter implements ActionListener, Serializable {

    private JDialog dialog;
    private JTextArea textArea;

    private JList<String> fontList;
    private JList<String> fontStyleList;
    private JList<String> sizeList;

    private JTextField dispFont;
    private JTextField dispFontStyle;
    private JTextField dispSize;
    private JLabel sampleLabel;

    private JButton ok;
    private JButton cancel;

    FontDialog(JFrame parentFrame, JTextArea textArea) {

        FontModel fontModel = null;

        try {
            FileInputStream fis = new FileInputStream("temp/obj.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            fontModel = (FontModel) ois.readObject();

            ois.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.textArea = textArea;

        dialog = new JDialog(parentFrame, "Font", true);
        dialog.setLayout(new GridLayout(2, 1, 0, 10));
        JPanel rootPanel1 = new JPanel(new GridLayout(1, 3, 10, 10));
        rootPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel rootPanel2 = new JPanel(new GridLayout(1, 2, 10, 10));
        rootPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dialog.setSize(584, 400);

        int x = parentFrame.getWidth() / 2 - dialog.getWidth() / 2;
        int y = parentFrame.getHeight() / 2 - dialog.getHeight() / 2;
        dialog.setLocation(x, y);

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p6 = new JPanel();

        JLabel font = new JLabel("Font:");
        JLabel fontStyle = new JLabel("Font style:");
        JLabel size = new JLabel("Size:");

        ok = new JButton("OK");
        ok.addActionListener(this);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);

        JPanel samplePanel = new JPanel();
        sampleLabel = new JLabel("AaBbYyZz");
        samplePanel.add(sampleLabel);
        samplePanel.setBorder(BorderFactory.createTitledBorder("Sample"));

        dispFont = new JTextField(5);
        dispFontStyle = new JTextField(5);
        dispSize = new JTextField(3);

        // Making list items selected of respective JLists according to saved info or defaults

        int fontIndex = fontModel.getFontIndex();
        int fontStyleIndex = fontModel.getFontStyleIndex();
        int fontSizeIndex = fontModel.getFontSizeIndex();

        String fontName[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontList = new JList<>(fontName);
        fontList.addMouseListener(this);
        fontList.setSelectedIndex(fontIndex);
        JScrollPane fontScroll = new JScrollPane(fontList);

        String fontStyleName[] = {"Regular", "Italic", "Bold", "Bold Italic"};
        fontStyleList = new JList<>(fontStyleName);
        fontStyleList.addMouseListener(this);
        JScrollPane fontStyleScroll = new JScrollPane(fontStyleList);
        fontStyleList.setSelectedIndex(fontStyleIndex);

        String sizeNo[] = {"10", "11", "12", "14", "16", "18", "22", "26", "28", "34", "40", "46", "52", "58", "64"};
        sizeList = new JList<>(sizeNo);
        sizeList.addMouseListener(this);
        sizeList.setSelectedIndex(fontSizeIndex);
        JScrollPane sizeScroll = new JScrollPane(sizeList);

        dispFont.setText(fontList.getSelectedValue());
        dispFontStyle.setText(fontStyleList.getSelectedValue());
        dispSize.setText(sizeList.getSelectedValue());

        sampleLabel.setFont(new Font(fontList.getSelectedValue(), FontDialog.fontStyleNo(fontStyleList.getSelectedValue()), Integer.parseInt(sizeList.getSelectedValue())));

        //-------- Panel work ---------
        GridLayout gd = new GridLayout(2, 1);

        JPanel headPanel1 = new JPanel(gd);
        headPanel1.add(font);
        headPanel1.add(dispFont);

        JPanel headPanel2 = new JPanel(gd);
        headPanel2.add(fontStyle);
        headPanel2.add(dispFontStyle);

        JPanel headPanel3 = new JPanel(gd);
        headPanel3.add(size);
        headPanel3.add(dispSize);

        JPanel headPanel4 = new JPanel(new GridLayout(1, 1));
        headPanel4.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        headPanel4.add(samplePanel);

        JPanel headPanel5 = new JPanel(new GridLayout(1, 2, 20, 0));
        headPanel5.add(ok);
        headPanel5.add(cancel);

        //--------------------------------

        p1.setLayout(new BorderLayout());
        p1.add(headPanel1, BorderLayout.NORTH);
        p1.add(fontScroll);

        p2.setLayout(new BorderLayout());
        p2.add(headPanel2, BorderLayout.NORTH);
        p2.add(fontStyleScroll);

        p3.setLayout(new BorderLayout());
        p3.add(headPanel3, BorderLayout.NORTH);
        p3.add(sizeScroll);

        p6.setLayout(new BorderLayout());
        p6.add(headPanel4);
        p6.add(headPanel5, BorderLayout.SOUTH);

        //---------------------

        rootPanel1.add(p1);
        rootPanel1.add(p2);
        rootPanel1.add(p3);
        rootPanel2.add(p4);
        rootPanel2.add(p6);

        dialog.add(rootPanel1);
        dialog.add(rootPanel2);

        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object obj = e.getSource();
        if (obj == ok) {

            try {

                // Fetching current selected respective list items names
                String fontName = fontList.getSelectedValue();
                String fontStyleName = fontStyleList.getSelectedValue();
                String fontSize = sizeList.getSelectedValue();

                // Creating font according to selected info
                Font font = new Font(fontName, FontDialog.fontStyleNo(fontStyleName), Integer.parseInt(fontSize));

                // Fetching current selected respective list items indexes
                int selectedFontIndex = fontList.getSelectedIndex();
                int selectedFontStyleIndex = fontStyleList.getSelectedIndex();
                int selectedFontSizeIndex = sizeList.getSelectedIndex();

                // Creating FontModel according to above info
                FontModel fontModel = new FontModel(font, selectedFontIndex, selectedFontStyleIndex, selectedFontSizeIndex);

                // Writing to file
                FileOutputStream fos = new FileOutputStream("temp/obj.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(fontModel);

                oos.close();
                fos.close();

                // Finally changing font of text area
                textArea.setFont(font);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            dialog.setVisible(false);
            dialog.dispose();

        } else if (obj == cancel) {

            dialog.setVisible(false);
            dialog.dispose();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() == 1 || e.getClickCount() == 2) {

            String fontName = fontList.getSelectedValue();
            String fontStyleName = fontStyleList.getSelectedValue();
            String fontSize = sizeList.getSelectedValue();
            dispFont.setText(fontName);
            dispFontStyle.setText(fontStyleList.getSelectedValue());
            dispSize.setText(fontSize);

            Font font = new Font(fontName, FontDialog.fontStyleNo(fontStyleName), Integer.parseInt(fontSize));
            sampleLabel.setFont(font);
        }
    }

    private static int fontStyleNo(String fontStyleName) {

        int fontStyle = 0;  // default value: 0 --> Font.PLAIN

        switch (fontStyleName) {

            case "Italic":
                fontStyle = Font.ITALIC;
                break;

            case "Bold":
                fontStyle = Font.BOLD;
                break;

            case "Bold Italic":
                fontStyle = Font.BOLD + Font.ITALIC;
        }
        return fontStyle;
    }
}