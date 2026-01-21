package org.dropProject.dropProjectPlugin.settings

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel

class GeneralSettingsComponent {
    private val mainPanel: JPanel
    private val serverURL = JBTextField()
    private val nameField = JBTextField()
    private val numberField = JBTextField()
    private val tokenField = JBPasswordField()
    private val showToken = JCheckBox("Show")
    private val tokenPanel = JPanel(BorderLayout())

    init {
        tokenPanel.add(tokenField, BorderLayout.CENTER)
        tokenPanel.add(showToken, BorderLayout.EAST)

        showToken.addActionListener {
            tokenField.echoChar = if (showToken.isSelected) 0.toChar() else '\u2022'
        }

        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Server URL: "), serverURL, 1, false)
            .addLabeledComponent(JBLabel("Name: "), nameField, 1, false)
            .addLabeledComponent(JBLabel("Number: "), numberField, 1, false)
            .addLabeledComponent(JBLabel("Token: "), tokenPanel, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel = mainPanel
    fun getPreferredFocusedComponent(): JComponent = nameField

    fun getServerURL(): String = serverURL.text
    fun setServerURL(text: String) { serverURL.text = text }

    fun getName(): String = nameField.text
    fun setName(text: String) { nameField.text = text }

    fun getNumber(): String = numberField.text
    fun setNumber(text: String) { numberField.text = text }

    fun getToken(): String = String(tokenField.password)
    fun setToken(text: String) { tokenField.text = text }
}