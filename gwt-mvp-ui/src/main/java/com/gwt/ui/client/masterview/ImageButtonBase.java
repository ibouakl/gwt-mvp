package com.gwt.ui.client.masterview;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;

/**
 * <p>This class represents basic button without text and with image.
 * It needs three images for states: normal, hovered and disabled.</p>
 *
 * @author TheSteam
 */
public class ImageButtonBase extends FocusWidget
{
    protected Image normalPrototype;
    protected Image hoverPrototype;
    protected Image disabledPrototype;
    protected Image image;

    public Image getDisabledPrototype() {
        return disabledPrototype;
    }

    public void setDisabledPrototype(Image disabledPrototype) {
        this.disabledPrototype = disabledPrototype;
    }

    public Image getHoverPrototype() {
        return hoverPrototype;
    }

    public void setHoverPrototype(Image hoverPrototype) {
        this.hoverPrototype = hoverPrototype;
    }

    public Image getNormalPrototype() {
        return normalPrototype;
    }

    public void setNormalPrototype(Image normalPrototype) {
        this.normalPrototype = normalPrototype;
    }

    public ImageButtonBase(Image normalPrototype,
                           Image hoverPrototype,
                           Image disabledPrototype) {
        super();

        this.normalPrototype = normalPrototype;
        this.hoverPrototype = hoverPrototype;
        this.disabledPrototype = disabledPrototype;

        image = new Image(getNormalPrototype().getUrl());
        setElement(image.getElement());

        sinkEvents(Event.ONMOUSEOVER);
        sinkEvents(Event.ONMOUSEOUT);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!enabled) {
            image = new Image(getDisabledPrototype().getUrl());
        } else {
            image = new Image(getNormalPrototype().getUrl());
        }

        super.setEnabled(enabled);
    }

    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);

        if (!isEnabled()) {
            return;
        }

        switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEOVER: {
                image = getHoverPrototype();
                break;
            }
            case Event.ONMOUSEOUT: {
                image = getNormalPrototype();
                break;
            }
            default: {
                // Do nothing
            }
        }
    }
}

