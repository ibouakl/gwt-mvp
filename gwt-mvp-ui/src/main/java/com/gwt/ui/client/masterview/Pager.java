package com.gwt.ui.client.masterview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author ibouakl
 *
 */
public class Pager extends Composite implements ClickHandler {

    public static final String PAGER_STYLE = "masterview-pager";
    public static final String PAGER_STATUS_STYLE = "masterview-pager-status";
    public static final String PAGER_NAVIGATION_STYLE = "masterview-pager-navigation";
    private Label pagerStatusLabel = new Label("");
    private ListBox goToPageListBox = new ListBox();
    private ImageButtonBase firstPageButton;
    private ImageButtonBase previousPageButton;
    private ImageButtonBase nextPageButton;
    private ImageButtonBase lastPageButton;
    private Grid grid;
    private SimplePanel header = new SimplePanel();
    private DockPanel topBar = new DockPanel();
    private VerticalPanel pageablePane = new VerticalPanel();

    public Pager(Grid grid, boolean enablePagination) {
        goToPageListBox.setVisibleItemCount(1);

        this.grid = grid;
        grid.setWidth("100%");
        pageablePane.add(grid);
        if (enablePagination) {
            initializePagination();
        }
        pageablePane.setSpacing(0);
        pageablePane.setCellWidth(grid, "100%");
        pageablePane.setStyleName(PAGER_STYLE);
        initWidget(pageablePane);
    }

    public final void initializePagination() {



        grid.getDataSource().addDataSourceListener(new DataSourceListener() {

            @Override
            public void onDataChanged(int readItemsCount, int allItemsCount) {
                setStatus(readItemsCount, allItemsCount);
                setButtonsState();
            }
        });

        firstPageButton = new ImageButtonBase(new Image(GWT.getModuleBaseURL() + "masterview/page-first.gif"), new Image(GWT.getModuleBaseURL() + "masterview/page-first.gif"), new Image(GWT.getModuleBaseURL() + "masterview/page-first-disabled.gif"));
        firstPageButton.addClickHandler(this);

        previousPageButton = new ImageButtonBase(new Image(GWT.getModuleBaseURL() + "masterview/page-prev.gif"), new Image(GWT.getModuleBaseURL() + "masterview/page-prev.gif"), new Image(GWT.getModuleBaseURL() + "masterview/page-prev-disabled.gif"));
        previousPageButton.addClickHandler(this);

        nextPageButton = new ImageButtonBase(new Image(GWT.getModuleBaseURL() + "masterview/page-next.gif"), new Image(GWT.getModuleBaseURL() + "masterview/page-next.gif"), new Image(GWT.getModuleBaseURL() + "masterview/page-next-disabled.gif"));
        nextPageButton.addClickHandler(this);

        lastPageButton = new ImageButtonBase(new Image(GWT.getModuleBaseURL() + "masterview/page-last.gif"), new Image(GWT.getModuleBaseURL() + "masterview/page-last.gif"), new Image(GWT.getModuleBaseURL() + "masterview/page-last-disabled.gif"));
        lastPageButton.addClickHandler(this);

        firstPageButton.setWidth("16px");
        previousPageButton.setWidth("16px");
        nextPageButton.setWidth("16px");
        lastPageButton.setWidth("16px");

        topBar.setHeight("100%");
        topBar.setWidth("100%");

        pagerStatusLabel.setStyleName(PAGER_STATUS_STYLE);
        pagerStatusLabel.setHeight("100%");

        header.setStylePrimaryName(PAGER_NAVIGATION_STYLE);
        header.setWidget(topBar);
        /*header.setHeight("21px");*/

        topBar.add(firstPageButton, DockPanel.WEST);
        topBar.add(previousPageButton, DockPanel.WEST);
        topBar.add(nextPageButton, DockPanel.WEST);
        topBar.add(lastPageButton, DockPanel.WEST);
        topBar.add(pagerStatusLabel, DockPanel.EAST);

        pageablePane.add(header);

        topBar.setCellWidth(firstPageButton, "16px");
        topBar.setCellWidth(previousPageButton, "16px");
        topBar.setCellWidth(nextPageButton, "16px");
        topBar.setCellWidth(lastPageButton, "16px");

        pageablePane.setCellWidth(header, "100%");

        previousPageButton.setEnabled(false);
        firstPageButton.setEnabled(false);

        topBar.setCellVerticalAlignment(firstPageButton, VerticalPanel.ALIGN_MIDDLE);
        topBar.setCellVerticalAlignment(previousPageButton, VerticalPanel.ALIGN_MIDDLE);
        topBar.setCellVerticalAlignment(nextPageButton, VerticalPanel.ALIGN_MIDDLE);
        topBar.setCellVerticalAlignment(lastPageButton, VerticalPanel.ALIGN_MIDDLE);
        topBar.setCellVerticalAlignment(pagerStatusLabel, VerticalPanel.ALIGN_MIDDLE);

        setButtonsState();

    }

    public final void setButtonsState() {
        int pageCount = grid.getDataSource().getPageCount();
        int currentPageNumber = grid.getDataSource().getCurrentPageNumber();

        previousPageButton.setEnabled(true);
        nextPageButton.setEnabled(true);
        firstPageButton.setEnabled(true);
        lastPageButton.setEnabled(true);

        if (currentPageNumber == 0) {
            previousPageButton.setEnabled(false);
            firstPageButton.setEnabled(false);
        }

        if (currentPageNumber == (pageCount - 1)) {
            nextPageButton.setEnabled(false);
            lastPageButton.setEnabled(false);
        }

    }

    public void setStatus(int displayedItems, int allItems) {
        pagerStatusLabel.setText("Affichage " + String.valueOf(displayedItems) + " ligne(s) sur" + String.valueOf(allItems));
    }

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource() == nextPageButton) {
            grid.getDataSource().toNextPage();
        } else if (event.getSource() == previousPageButton) {
            grid.getDataSource().toPreviousPage();
        } else if (event.getSource() == firstPageButton) {
            grid.getDataSource().toFirstPage();
        } else if (event.getSource() == lastPageButton) {
            grid.getDataSource().toLastPage();
        }
        setButtonsState();
    }
}
