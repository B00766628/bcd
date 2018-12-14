package com.jaskiran;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        Label msg = new Label("Hello Sir");
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        List<Person> myList = new ArrayList<>();
        myList.add(new Person("John",34, "M",true));
        myList.add(new Person("Tom",44, "M",false));
        myList.add(new Person("Alex",24, "M",false));
        myList.add(new Person("Mary",19, "F",true));
        myList.add(new Person("Fionna",46, "F",false));

        Grid<Person> myGrid = new Grid<>();
        myGrid.setItems(myList);
        myGrid.addColumn(Person:: getName).setCaption("Name");
        myGrid.addColumn(Person:: getAge).setCaption("Age");
        myGrid.addColumn(Person:: getGender).setCaption("Gender");
        myGrid.addColumn(Person:: isDisable).setCaption("Disabled");
        myGrid.setSelectionMode(SelectionMode.MULTI);


        Button button = new Button("Click Me");
        button.addClickListener(e -> {
           Set<Person> selectedGrid =myGrid.getSelectedItems();
           int selectedPeople = selectedGrid.size();

           if(selectedPeople<= 0){
               msg.setValue("No Person Selected");
               return;

           }
           if(selectedPeople<= 1){
            msg.setValue("You need atleast 2 people to pair");
            return;

            }
            if(selectedPeople> 2){
                msg.setValue("Sorry select only 2");
                return;
            }

            String g[] = new String[2];
            boolean d[] = new boolean[2];
            
            int i = 0;
            for(Person p:selectedGrid){
                g[i] = p.getGender();
                d[i] = p.isDisable();
                i++;
            }

            if(g[0]==g[1]){
                msg.setValue("Sorry select different genders");
                return;
            }

            if(d[0]==true && d[1]==true){
                msg.setValue("Sorry can not pair both disabled");
                return;
            }

            msg.setValue("Perfect match");
        });
        
        layout.addComponents(msg, button, myGrid);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
