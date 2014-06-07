package com.gwt.mvp.client.presenter;

/**
 *
 *
 *@author jguibert
 */
public interface BoundListener {
   /**
     * This method is called when binding the presenter. Any additional bindings
     * should be done here.
     */
    public void onBind();

    /**
     * This method is called when unbinding the presenter. Any handler
     * registrations recorded with {@link #registerHandler(HandlerRegistration)} will have already been removed at this point.
     */
    public  void onUnbind();
}
