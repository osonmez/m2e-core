/*******************************************************************************
 * Copyright (c) 2008-2015 Sonatype, Inc. and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Sonatype, Inc. - initial API and implementation
 *      Anton Tanasenko - Refactor marker resolutions and quick fixes (Bug #484359)
 *******************************************************************************/

package org.eclipse.m2e.editor.xml.internal.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;


/**
 * Wrapper for marker resolutions that can be used as a completion proposal
 * 
 * @author mkleint
 */
public class MarkerResolutionWrapper implements ICompletionProposal {

  private final IMarkerResolution resolution;

  private final IMarker marker;

  public MarkerResolutionWrapper(IMarkerResolution resolution, IMarker marker) {
    this.resolution = resolution;
    this.marker = marker;
  }

  public void apply(IDocument document) {
    resolution.run(marker);
  }

  public String getAdditionalProposalInfo() {
    if(resolution instanceof IMarkerResolution2) {
      return ((IMarkerResolution2) resolution).getDescription();
    }
    String problemDesc = marker.getAttribute(IMarker.MESSAGE, null);
    if(problemDesc != null) {
      return problemDesc;
    }
    return null;
  }

  public IContextInformation getContextInformation() {
    return null;
  }

  public String getDisplayString() {
    return resolution.getLabel();
  }

  public Image getImage() {
    if(resolution instanceof IMarkerResolution2) {
      return ((IMarkerResolution2) resolution).getImage();
    }
    return null; //what is the default image here??
  }

  public Point getSelection(IDocument document) {
    return null;
  }
}
