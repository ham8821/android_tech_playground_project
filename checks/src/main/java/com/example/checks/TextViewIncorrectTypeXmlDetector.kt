package com.example.checks

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

class TextViewIncorrectTypeXmlDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "InputEditTextViewTypeXml",
            briefDescription = "Don't use `TextInputEditText` in layout XML",
            explanation = "It is encouraged to replace `TextInputEditText` with `CustomTextInputEditText`.",
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                TextViewIncorrectTypeXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        // Return true if we want to analyze resource files in the specified resource
        // folder type. In this case we only need to analyze layout resource files.
        return folderType == ResourceFolderType.LAYOUT
    }

    override fun getApplicableElements(): Collection<String>? {
        // Return the set of elements we want to analyze. The `visitElement` method
        // below will be called each time lint sees one of these elements in a
        // layout XML resource file.
        return setOf("com.google.android.material.textfield.TextInputEditText")
    }

    override fun visitElement(context: XmlContext, element: Element) {
        context.report(
            issue = ISSUE,
            scope = element,
            location = context.getNameLocation(element),
            message = "It is encouraged to replace `TextInputEditText` with `CustomTextInputEditText`."
        )
    }
}