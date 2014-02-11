package com.jetbrains.lang.dart.ide.runner.client;

import com.intellij.chromeConnector.debugger.ChromeDebuggerEngine;
import com.intellij.execution.configurations.RuntimeConfigurationError;
import com.intellij.ide.browsers.WebBrowser;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.HtmlUtil;
import com.jetbrains.lang.dart.ide.settings.DartSettingsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DartiumDebuggerEngine extends ChromeDebuggerEngine {
  public DartiumDebuggerEngine() {
    super("dartium");
  }

  //@Override
  //@NotNull
  //public WebBrowser getBrowser() {
  //  return DartSettingsUtil.DARTIUM;
  //}

  @Override
  public void checkAvailability(@NotNull final Project project) throws RuntimeConfigurationError {
    DartSettingsUtil.getDartiumPathOrThrowErrorWithQuickFix(project);
  }

  @Override
  protected boolean isPreferredEngineForFile(@NotNull PsiFile psiFile) {
    return psiFile instanceof XmlFile &&
           DartSettingsUtil.getDartiumPath() != null &&
           isHtmlFileWithDartScript(psiFile);
  }

  private static boolean isHtmlFileWithDartScript(@Nullable PsiFile psiFile) {
    if (psiFile == null || !HtmlUtil.isHtmlFile(psiFile)) {
      return false;
    }

    final String text = psiFile.getText();
    int i = -1;
    while ((i = text.indexOf("application/dart", i + 1)) != -1) {
      final PsiElement element = psiFile.findElementAt(i);
      if (element != null && element.getParent() instanceof XmlAttributeValue) {
        if (HtmlUtil.isScriptTag(PsiTreeUtil.getParentOfType(element, XmlTag.class))) {
          return true;
        }
      }
    }
    return false;
  }
}
