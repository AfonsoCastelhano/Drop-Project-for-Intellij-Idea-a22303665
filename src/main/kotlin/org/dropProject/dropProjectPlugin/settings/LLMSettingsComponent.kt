package org.dropProject.dropProjectPlugin.settings

import com.intellij.ui.JBColor
import com.intellij.ui.TitledSeparator
import com.intellij.ui.components.*
import com.intellij.util.ui.FormBuilder
import org.dropProject.dropProjectPlugin.submissionComponents.UIGpt
import java.awt.*
import javax.swing.*

class LLMSettingsComponent {
    private val mainPanel: JPanel
    private val openAiTokenField = JBPasswordField()
    private val showOpenAiToken = JCheckBox("Show")
    private val autoSendPrompt = JCheckBox("<html>Send prompt automatically.<br>If checked, GPT will be prompted as soon as the \"Ask GPT\" button is clicked.</html>")

    private val sentenceListModel = DefaultListModel<String>()
    private val sentenceList = JBList(sentenceListModel)
    private val sentenceTextField = JBTextField()
    private val addButton = JButton("Add")
    private val editButton = JButton("Edit")
    private val removeButton = JButton("Remove")

    init {
        val openAiTokenPanel = JPanel(BorderLayout())
        openAiTokenPanel.add(openAiTokenField, BorderLayout.CENTER)
        openAiTokenPanel.add(showOpenAiToken, BorderLayout.EAST)

        showOpenAiToken.addActionListener {
            openAiTokenField.echoChar = if (showOpenAiToken.isSelected) 0.toChar() else '\u2022'
        }

        addButton.addActionListener { addSentence() }
        editButton.addActionListener { editSentence() }
        removeButton.addActionListener { removeSentence() }

        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("OpenAI API Key: "), openAiTokenPanel, 1, false)
            .addLabeledComponent(JBLabel("Send to ChatGPT: "), autoSendPrompt, 1, false)
            .addComponent(TitledSeparator("Prompt Suffix Settings"))
            .addComponent(createAddEditRemovePanel())
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    private fun createAddEditRemovePanel(): JPanel {
        val panel = JPanel(GridBagLayout())
        val gbc = GridBagConstraints()
        val scrollPane = JBScrollPane(sentenceList).apply { preferredSize = Dimension(300, 100) }
        val buttonPanel = JPanel(GridLayout(1, 0)).apply {
            add(addButton); add(editButton); add(removeButton)
        }

        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0
        panel.add(scrollPane, gbc)
        gbc.gridy = 1; gbc.weighty = 0.0
        panel.add(sentenceTextField, gbc)
        gbc.gridy = 2
        panel.add(buttonPanel, gbc)
        return panel
    }

    private fun addSentence() {
        if (sentenceTextField.text.isNotEmpty()) {
            sentenceListModel.addElement(sentenceTextField.text)
            sentenceTextField.text = ""
        }
    }

    private fun editSentence() {
        val idx = sentenceList.selectedIndex
        if (idx != -1) {
            val edited = JOptionPane.showInputDialog(mainPanel, "Edit:", sentenceListModel.getElementAt(idx))
            if (edited != null) sentenceListModel.setElementAt(edited, idx)
        }
    }

    private fun removeSentence() {
        val idx = sentenceList.selectedIndex
        if (idx != -1) sentenceListModel.remove(idx)
    }

    fun getPanel(): JPanel = mainPanel
    fun getOpenAiToken(): String = String(openAiTokenField.password)
    fun setOpenAiToken(t: String) { openAiTokenField.text = t }
    fun isAutoSend(): Boolean = autoSendPrompt.isSelected
    fun setAutoSend(b: Boolean) { autoSendPrompt.isSelected = b }
    fun getSentences(): List<String> = sentenceListModel.elements().toList()
    fun setSentences(l: List<String>) {
        sentenceListModel.clear()
        l.forEach { sentenceListModel.addElement(it) }
    }
}