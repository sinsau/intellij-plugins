// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package jetbrains.communicator.idea.config;

import com.intellij.openapi.actionSystem.AnActionEvent;
import jetbrains.communicator.util.StringUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kir
 */
public class OptionExpandToolWindow_Message extends OptionExpandToolWindow1 {
  public OptionExpandToolWindow_Message() {
    super(IdeaFlags.EXPAND_ON_MESSAGE);
  }

  @Override
  public void update(@NotNull final AnActionEvent e) {
    super.update(e);
    e.getPresentation().setText(StringUtil.getMsg("expand.on.incoming.message"));
    e.getPresentation().setDescription(StringUtil.getMsg("expand.on.incoming.message.description"));
  }
}
