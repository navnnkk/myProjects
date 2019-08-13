package com.naveen

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import javax.swing.*

class PopUpDialog(parentFrame: JFrame, textArea: JTextArea, title: String, filePath: String?, chosenCase: String) : ActionListener {

    private var title: String
    private var textArea: JTextArea
    private val parentFrame: JFrame
    var filePath: String?
    private val dialog: JDialog
    private val chosenCase: String
    var fileName = ""

    private val save: JButton
    private val dontSave: JButton
    private val cancel: JButton

    init {

        this.title = title
        this.textArea = textArea
        this.parentFrame = parentFrame
        this.filePath = filePath
        this.chosenCase = chosenCase

        dialog = JDialog(parentFrame, "Notepad")
        dialog.layout = null
        dialog.setSize(380, 150)

        val x = parentFrame.width / 2 - dialog.width / 2
        val y = parentFrame.height / 2 - dialog.height / 2
        dialog.setLocation(x, y)

        val label = JLabel("Do you want to save changes to your Notepad File?")
        save = JButton("Save")
        save.addActionListener(this)

        dontSave = JButton("Don't Save")
        dontSave.addActionListener(this)

        cancel = JButton("Cancel")
        cancel.addActionListener(this)

        label.setBounds(10, 20, 300, 20)
        save.setBounds(90, 75, 70, 25)
        dontSave.setBounds(170, 75, 95, 25)
        cancel.setBounds(275, 75, 75, 25)

        dialog.add(label)
        dialog.add(save)
        dialog.add(dontSave)
        dialog.add(cancel)
        dialog.isVisible = true
    }

    override fun actionPerformed(e: ActionEvent?) {

        val obj = e?.source

        when (chosenCase) {

            "New" ->
                if (obj == save) {

                    if (title == "*Untitled - Notepad") {

                        saveAs(chosenCase)
                    } else {

                        val newText = textArea.text
                        try {
                            val bw = BufferedWriter(FileWriter(filePath))
                            bw.write(newText)
                            bw.close()
                        } catch (e1: IOException) {
                            e1.printStackTrace()
                        }
                        textArea.text = ""
                        fileName = "Untitled - Notepad"
                        dialog.isVisible = false
                        dialog.dispose()
                    }

                } else if (obj == dontSave) {

                    textArea.text = ""
                    fileName = "Untitled - Notepad"
                    dialog.isVisible = false
                    dialog.dispose()

                } else if (obj == cancel) {
                    dialog.isVisible = false
                    dialog.dispose()
                }

            "Open..." ->

                if (obj == save) {

                    if (title == "*Untitled - Notepad") {

                        saveAs(chosenCase)
                    } else {

                        val newText = textArea.text
                        try {
                            val bw = BufferedWriter(FileWriter(filePath))
                            bw.write(newText)
                            bw.close()
                        } catch (e1: IOException) {
                            e1.printStackTrace()
                        }

                        parentFrame.title = fileName
                        dialog.isVisible = false
                        dialog.dispose()
                    }

                }

            "Exit" ->

                if (obj == save) {

                    if (title == "*Untitled - Notepad") {

                        saveAs(chosenCase)
                    } else {

                        val newText = textArea.text
                        try {
                            val bw = BufferedWriter(FileWriter(filePath))
                            bw.write(newText)
                            bw.close()
                        } catch (e1: IOException) {
                            e1.printStackTrace()
                        }

                        dialog.isVisible = false
                        dialog.dispose()
                        System.exit(0)
                    }

                } else if (obj == dontSave) {

                    dialog.isVisible = false
                    dialog.dispose()
                    System.exit(0)

                } else if (obj == cancel) {
                    dialog.isVisible = false
                    dialog.dispose()
                }
        }
    }

    private fun saveAs(chosenCase: String) {

        val chooser = JFileChooser()
        chooser.dialogTitle = "Save As"
        val returnValue = chooser.showSaveDialog(parentFrame)

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            val currentOpenedFile = chooser.selectedFile
            filePath = currentOpenedFile.path

            println("file path in save as is $filePath")
            fileName = currentOpenedFile.name + " - Notepad"
            val writingText = textArea.text

            println("HELLO : $filePath")

            try {
                val bw = BufferedWriter(FileWriter(filePath))
                bw.write(writingText)
                bw.close()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }

        when (chosenCase) {

            "New" -> {
                textArea.text = ""
            }

            "Open..." ->
                parentFrame.title = fileName

            "Exit" ->
                System.exit(0)
            }

        }
        dialog.isVisible = false
        dialog.dispose()
    }
}