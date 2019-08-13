package com.naveen;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class MenuItemsActionListener implements ActionListener, KeyListener {

    private JFrame frame;
    private JTextArea textArea;
    private File currentOpenedFile;
    private String fileName,filePath,title;
    private PopUpDialog popUpDialogObj;

    MenuItemsActionListener(JFrame frame, JTextArea textArea) {

        this.frame = frame;
        this.textArea = textArea;
        textArea.addKeyListener(this);
        fileName = "Untitled - Notepad";
        frame.setTitle(fileName);
        title = frame.getTitle();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String chosenCase = e.getActionCommand();

        if (chosenCase.equals("Open...")) {

            title = frame.getTitle();
            if (title.charAt(0) == '*') {
                popUpDialogObj = new PopUpDialog(frame, textArea, title, filePath, chosenCase);

            } else {

                JFileChooser chooser = new JFileChooser();
                int returnValue = chooser.showOpenDialog(frame);

                if (returnValue == JFileChooser.APPROVE_OPTION) {

                    currentOpenedFile = chooser.getSelectedFile();
                    filePath = currentOpenedFile.getPath();
                    fileName = currentOpenedFile.getName() + " - Notepad";

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(filePath));
                        String s1, s2 = "";

                        while ((s1 = br.readLine()) != null)
                            s2 += s1 + "\n";

                        textArea.setText(s2);
                        br.close();

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    frame.setTitle(fileName);
                    title = fileName;
                }
            }
            if(popUpDialogObj != null)
                filePath = popUpDialogObj.getFilePath();

            System.out.println("file path we get is = "+filePath);
        } else if (chosenCase.equals("Save")) {

            if (title.equals("*Untitled - Notepad") || title.equals("Untitled - Notepad")) {

                saveAs();
            } else {

                String newText = textArea.getText();
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
                    bw.write(newText);
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if(popUpDialogObj != null)
                    fileName = popUpDialogObj.getFileName();
            }
            frame.setTitle(fileName);
            title = frame.getTitle();

        } else if(chosenCase.equals("Save As...")) {
            saveAs();

        } else if(chosenCase.equals("Exit")) {

            if(title.charAt(0) == '*') {
                popUpDialogObj = new PopUpDialog(frame,textArea,title,filePath,chosenCase);

            } else {
                System.exit(0);
            }

        } else if(chosenCase.equals("Font...")) {

            new FontDialog(frame, textArea);

        } else if(chosenCase.equals("Page Setup...")) {
            new PageSetup(frame);

        } else if(chosenCase.equals("New")) {

            title = frame.getTitle();
            if (title.charAt(0) == '*') {
                popUpDialogObj = new PopUpDialog(frame, textArea, title, filePath, chosenCase);
                fileName = "Untitled - Notepad";
                frame.setTitle(fileName);

            } else {
                textArea.setText("");
                fileName = "Untitled - Notepad";
                frame.setTitle(fileName);
            }

        } else if(chosenCase.equals("Copy")) {
            textArea.copy();

        } else if(chosenCase.equals("Paste")) {
            textArea.paste();

        } else if(chosenCase.equals("Cut")) {
            textArea.cut();

        } else if(chosenCase.equals("Word Wrap")) {
            textArea.setLineWrap(true);

        }
    }
    // --------------- KeyListener methods -----------------

    @Override
    public void keyTyped(KeyEvent e) {

        fileName = frame.getTitle();
        System.out.println(filePath);

        if(fileName.charAt(0) != '*') {

            title = "*" + fileName;
            frame.setTitle(title);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private void saveAs() {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save as");
        int returnValue = chooser.showSaveDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            currentOpenedFile = chooser.getSelectedFile();
            filePath = currentOpenedFile.getPath();
            fileName = currentOpenedFile.getName() + " - Notepad";
            String s1 = textArea.getText();

            System.out.println("save as file path : "+filePath);

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
                bw.write(s1);
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            frame.setTitle(fileName);
            title = fileName;
        }
    }
}