package com.naveen;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;

public class Main implements CaretListener {

    private JLabel statusBar;

    private Main() {

        JFrame frame = new JFrame("Notepad");
        JTextArea textArea = new JTextArea(270, 180);
        JScrollPane jsp = new JScrollPane(textArea);
        WindowCloser wc = new WindowCloser(frame,textArea);
        frame.addWindowListener(wc);
        textArea.addCaretListener(this);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusBar = new JLabel();
        statusPanel.add(statusBar);

        frame.add(jsp);
        frame.add(statusPanel,BorderLayout.SOUTH);
        frame.setJMenuBar(new NotepadDesign(frame, textArea));
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        updateStatus(1,1);
    }

    @Override
    public void caretUpdate(CaretEvent e) {

        JTextArea editArea = (JTextArea)e.getSource();

        try {

            int caretpos = editArea.getCaretPosition();
            int lineNo = editArea.getLineOfOffset(caretpos);
            int columnNo = caretpos - editArea.getLineStartOffset(lineNo);

            lineNo++;
            columnNo++;

            updateStatus(lineNo, columnNo);

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateStatus(int lineNumber, int columnNumber) {
        statusBar.setText("Ln " + lineNumber + ", Col " + columnNumber + "    ");
    }

    public static void main(String[] args) {
        new Main();
    }
}


