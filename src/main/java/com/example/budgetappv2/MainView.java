package com.example.budgetappv2;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        addClassName("list-view");
        setSizeFull();
    }

}
