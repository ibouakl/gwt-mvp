package com.gwt.ui.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageBundle extends ClientBundle {
   
    @Source("com/gwt/ui/resources/fileIcon.gif")
    public ImageResource fileIcon();

    @Source("com/gwt/ui/resources/folderIcon.gif")
    public ImageResource folderIcon();
    
    @Source("com/gwt/ui/resources/closeWindowIcon.png")
    public ImageResource closeWindowIcon();
    
    @Source("com/gwt/ui/resources/downIcon.png")
    public ImageResource downIcon();
    
    @Source("com/gwt/ui/resources/upIcon.png")
    public ImageResource upIcon();
    
    @Source("com/gwt/ui/resources/startIcon.png")
    public ImageResource startIcon();
    
    @Source("com/gwt/ui/resources/nextIcon.png")
    public ImageResource nextIcon();
    
    @Source("com/gwt/ui/resources/previousIcon.png")
    public ImageResource previousIcon();
    
    @Source("com/gwt/ui/resources/finishIcon.png")
    public ImageResource finishIcon();
    
    @Source("com/gwt/ui/resources/rebuildIcon.png")
    public ImageResource rebuildIcon();
    
    @Source("com/gwt/ui/resources/spacerIcon.png")
    public ImageResource spacerIcon();
    
    @Source("com/gwt/ui/resources/infoBoxIcon.png")
    public ImageResource inputHelpIcon();
}
