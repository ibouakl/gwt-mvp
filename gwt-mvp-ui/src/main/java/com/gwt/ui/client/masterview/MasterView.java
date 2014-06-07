package com.gwt.ui.client.masterview;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;

/**
 * 
 * @author ibouakl
 *
 */
public class MasterView extends Composite {

    private final Grid grid;
    private final DataSource dataSource;
    private final Pager pager;

    public MasterView() {
        this(null);
    }

    /**
     * Constructor
     */
    public MasterView(PropertyMapper mapper) {
        this(false, mapper);
    }

    /**
     * Constructor
     * 
     * @param enabledPagination enable pagination if true, disable f false
     * @author ibouakl
     */
    public MasterView(boolean enabledPagination, PropertyMapper mapper) {
        dataSource = new DataSource(enabledPagination);
        if (mapper != null) {
            dataSource.setPropertyMapper(mapper);
        }
        grid = new Grid(dataSource);
        pager = new Pager(grid, enabledPagination);
        initWidget(pager);
    }

    @SuppressWarnings("unchecked")
    public void setItems(List items) {
        dataSource.setInitialData(items);
    }

    public void append(Column column) {
        grid.append(column);
    }

    public void setFilteringEnabled(boolean filteringEnabled) {
        grid.setFilteringEnabled(filteringEnabled);
    }

    public void setPageSize(int pageSize) {
        dataSource.setPageSize(pageSize);
    }

    public Grid getGrid() {
        return grid;
    }

    public void setPropertyMapper(PropertyMapper propertyMapper) {
        dataSource.setPropertyMapper(propertyMapper);
    }

    public Pager getPager() {
        return pager;
    }

}
