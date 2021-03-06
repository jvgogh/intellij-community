/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.structuralsearch.impl.matcher.filters;

import com.intellij.dupLocator.util.NodeFilter;
import com.intellij.psi.PsiElement;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;

/**
 * Created by IntelliJ IDEA.
 * User: maxim.mossienko
 * Date: Oct 12, 2005
 * Time: 4:44:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagValueFilter extends XmlElementVisitor implements NodeFilter {
  private boolean result;

  @Override public void visitXmlText(XmlText text) {
    result = true;
  }

  @Override public void visitXmlTag(XmlTag tag) {
    result = true;
  }

  @Override
  public void visitElement(PsiElement element) {
    // e.g. css inside <style> html tag
    if (element.getParent() instanceof XmlTag) {
      result = true;
    }
  }

  private static class NodeFilterHolder {
    private static final NodeFilter instance = new TagValueFilter();
  }

  public static NodeFilter getInstance() {
    return NodeFilterHolder.instance;
  }

  private TagValueFilter() {
  }

  public boolean accepts(PsiElement element) {
    result = false;
    if (element!=null) element.accept(this);
    return result;
  }
}
