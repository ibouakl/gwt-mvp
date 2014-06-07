package com.gwt.ui.client.groupboxpanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class Tooltip implements MouseOverHandler, MouseOutHandler {


    private static final int DELAY_TO_SHOW = 300;

    private static TooltipPanel tooltipPanel;

    private String text;

    private final Widget target;

    public static Tooltip tooltip(HasTooltipMouseHandlers sender, String text) {
        return new Tooltip(sender, text);
    }

    public static Tooltip tooltip(HasAllMouseHandlers sender, String text) {
        return new Tooltip(sender, text);
    }

    private static class TooltipPanel extends PopupPanel {

        private final HTML textPane;

        private Timer delayShowTimer;

        private Timer delayHideTimer;

        private long hideTimeStamp;

        public TooltipPanel() {
            super(true);
            SimplePanel content = new SimplePanel();
            content.getElement().getStyle().setProperty("boxShadow", "5px 2px 2px #888");
            content.getElement().getStyle().setProperty("MozBoxShadow", "5px 2px 2px #888");
            content.getElement().getStyle().setProperty("WebkitBoxShadow", "5px 2px 2px #888");
            content.getElement().getStyle().setMargin(20, Unit.PX);
            add(content);

            textPane = new HTML();
            content.setWidget(textPane);

            textPane.getElement().getStyle().setZIndex(30);
        }

        private void scheduleShow(final Widget target, final String text) {
            if (delayShowTimer != null) {
                delayShowTimer.cancel();
            }
            delayShowTimer = new Timer() {
                @Override
                public void run() {
                    delayShowTimer = null;
                    if (target.isAttached() && target.isVisible()) {
                        textPane.setHTML(text);
                        setWidth((Math.min(text.length(), 80)) / 2 + "em");
                        TooltipPanel.this.showRelativeTo(target);
                    }
                }
            };
            delayShowTimer.schedule((System.currentTimeMillis() - hideTimeStamp > 200) ? DELAY_TO_SHOW : 200);
        }

        @Override
        public void hide() {
            if (delayShowTimer != null) {
                delayShowTimer.cancel();
                delayShowTimer = null;
            }
            if (delayHideTimer != null) {
                delayHideTimer.cancel();
                delayHideTimer = null;
            }
            hideTimeStamp = System.currentTimeMillis();
            TooltipPanel.super.hide();
        }

    }

    protected Tooltip(HasTooltipMouseHandlers target, String text) {
        this.text = text;
        this.target = (Widget) target;
        target.addMouseOverHandler(this);
        target.addMouseOutHandler(this);
    }

    protected Tooltip(HasAllMouseHandlers target, String text) {
        this.text = text;
        this.target = (Widget) target;
        target.addMouseOverHandler(this);
        target.addMouseOutHandler(this);
    }

    public void setTooltipText(String text) {
        this.text = text;
        //TODO update text if it is shown
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
        if (this.text != null) {
            if (tooltipPanel == null) {
                tooltipPanel = new TooltipPanel();
            }
            tooltipPanel.setPopupPosition(event.getClientX(), event.getClientY());
            tooltipPanel.scheduleShow(target, this.text);

        }
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        if (tooltipPanel != null) {
            tooltipPanel.hide();
        }
    }

}