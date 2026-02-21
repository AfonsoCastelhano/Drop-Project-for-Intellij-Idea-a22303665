package org.dropProject.dropProjectPlugin.settings

import com.intellij.openapi.options.Configurable
import org.dropProject.dropProjectPlugin.submissionComponents.UIGpt
import javax.swing.JComponent

class GeneralSettingsConfigurable : Configurable {
    private var component: GeneralSettingsComponent? = null

    override fun getDisplayName(): String = "DropProject General Settings"

    override fun createComponent(): JComponent {
        component = GeneralSettingsComponent()
        return component!!.getPanel()
    }

    override fun isModified(): Boolean {
        val s = SettingsState.getInstance()
        return component?.getServerURL() != s.serverURL ||
                component?.getName() != s.username ||
                component?.getNumber() != s.usernumber ||
                component?.getToken() != s.token
    }

    override fun apply() {
        val s = SettingsState.getInstance()
        s.serverURL = component?.getServerURL() ?: ""
        s.username = component?.getName() ?: ""
        s.usernumber = component?.getNumber() ?: ""
        s.token = component?.getToken() ?: ""
    }

    override fun reset() {
        val s = SettingsState.getInstance()
        component?.setServerURL(s.serverURL)
        component?.setName(s.username)
        component?.setNumber(s.usernumber)
        component?.setToken(s.token)
    }

    override fun disposeUIResources() { component = null }
}

class LLMSettingsConfigurable : Configurable {
    private var component: LLMSettingsComponent? = null

    override fun getDisplayName(): String = "LLM/GenAI Settings"

    override fun createComponent(): JComponent {
        component = LLMSettingsComponent()
        return component!!.getPanel()
    }

    override fun isModified(): Boolean {
        val s = SettingsState.getInstance()
        return component?.getLlmServerURL() != s.llmServerURL ||
                component?.getOpenAiToken() != s.openAiToken ||
                component?.isAutoSend() != s.autoSendPrompt ||
                component?.getSentences() != s.sentenceList
    }

    override fun apply() {
        val s = SettingsState.getInstance()
        s.llmServerURL = component?.getLlmServerURL() ?: ""
        s.openAiToken = component?.getOpenAiToken() ?: ""
        s.autoSendPrompt = component?.isAutoSend() ?: false
        s.sentenceList = component?.getSentences()?.toMutableList() ?: mutableListOf()
        UIGpt.instance1?.updatePhrases(s.sentenceList)
    }

    override fun reset() {
        val s = SettingsState.getInstance()
        component?.setLlmServerURL(s.llmServerURL)
        component?.setOpenAiToken(s.openAiToken)
        component?.setAutoSend(s.autoSendPrompt)
        component?.setSentences(s.sentenceList)
    }

    override fun disposeUIResources() { component = null }
}