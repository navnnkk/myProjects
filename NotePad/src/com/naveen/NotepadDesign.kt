package com.naveen

import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.*
import javax.swing.*

internal class NotepadDesign(frame: JFrame, textArea : JTextArea) : JMenuBar() {

    init {

        val configFile = File("temp/obj.dat")
        val fontModel: FontModel

        if(configFile.exists()) {   // If file exists
            val fis = FileInputStream("temp/obj.dat")
            val ois = ObjectInputStream(fis)

            fontModel = ois.readObject() as FontModel
            ois.close()
            fis.close()

        } else {    // If file doesn't exists then, setting up the default font configurations

            fontModel = FontModel(Font("Consolas", Font.PLAIN, 10), 9,0,2)

            // Writing to file
            val fos = FileOutputStream("temp/obj.dat")
            val oos = ObjectOutputStream(fos)
            oos.writeObject(fontModel)

            oos.close()
            fos.close()
        }

        // Setting specific/default font of Text Area
        textArea.font = fontModel.font

        val file = JMenu("File")
        val edit = JMenu("Edit")
        val format = JMenu("Format")
        val view = JMenu("View")
        val help = JMenu("Help")
        val fileitems = arrayOf("New", "Open...", "Save", "Save As...", "Page Setup...", "Print", "Exit")
        //val shortCutFileItems = arrayOf(KeyEvent.VK_N,KeyEvent.VK_O,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_U,KeyEvent.VK_P,KeyEvent.VK_X)
        val edititems = arrayOf("Undo", "Cut", "Copy", "Paste", "Delete", "Find...", "Find Next", "Replace...", "Go To...", "Select All", "Time/Date")
        val formatitems = arrayOf("Word Wrap", "Font...")
        val viewitems = arrayOf("Status Bar")
        val helpitems = arrayOf("View Help", "About Notepad")

        val menuItemAl = MenuItemsActionListener(frame, textArea)
        for (i in 0 until fileitems.size) {
            val item = JMenuItem(fileitems[i])
            //item.accelerator(KeyStroke.getKeyStroke(shortCutFileItems[i]), ActionEvent.CTRL_MASK)

            item.addActionListener(menuItemAl)
            file.add(item)
        }
        file.insertSeparator(4)
        file.insertSeparator(7)

        for (edit_item in edititems) {
            val item = JMenuItem(edit_item)
            item.addActionListener(menuItemAl)
            edit.add(item)
        }
        edit.insertSeparator(1)
        edit.insertSeparator(6)
        edit.insertSeparator(11)
        for (format_item in formatitems) {
            val item = JMenuItem(format_item)
            item.addActionListener(menuItemAl)
            format.add(item)
        }
        for (view_item in viewitems) {
            val item = JMenuItem(view_item)
            item.addActionListener(menuItemAl)
            view.add(item)
        }
        for (help_item in helpitems) {
            val item = JMenuItem(help_item)
            item.addActionListener(menuItemAl)
            help.add(item)
        }
        help.insertSeparator(1)
        add(file)
        add(edit)
        add(format)
        add(view)
        add(help)
    }

    //--------------------------------
    public fun keyStroke(keyChar: Char, modifiers: Int?): KeyStroke? {
        return if (modifiers != null) {
            KeyStroke.getKeyStroke(keyChar, modifiers)
        } else {
            KeyStroke.getKeyStroke(keyChar)
        }
    }
}