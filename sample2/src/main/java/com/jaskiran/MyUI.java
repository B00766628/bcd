package com.jaskiran;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Grid.SelectionMode;
import java.util.Set;


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

        Connection connection = null;

        final VerticalLayout layout = new VerticalLayout();
        Label logo = new Label("<H1>Fun Bus Bookings</H1> <p/> <h3>Please enter the details below and click Book</h3>",ContentMode.HTML); 

        String connectionString ="jdbc:sqlserver://b00789777ser.database.windows.net:1433;"+
        "database=B00789777-exam;"+
        "user=kamal@b00789777ser;"+
        "password={Test1234};"+
        "encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        
                try 
        {
            // Connect with JDBC driver to a database
            connection = DriverManager.getConnection(connectionString);
            // Add a label to the web app with the message and name of the database we connected to 
            layout.addComponent(new Label("Connected to database: " + connection.getCatalog()));




        } 
        catch (Exception e) 
        {

            layout.addComponent(new Label(e.getMessage()));
        }
        List<Bus> mylist = new ArrayList<Bus>();
        try{
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM BusInformation;");
            
            
            
            while(rs.next())
            {   
                // Add a new Customer instantiated with the fields from the record (that we want, we might not want all the fields, note how I skip the id)
                mylist.add(new Bus(rs.getString("Destination"), 
                            rs.getInt("Capacity"), 
                            rs.getString("Features"), 
                            rs.getString("Accessible")));
            }
            }
            catch (Exception e) 
            {
            
                layout.addComponent(new Label(e.getMessage()));
            }
            final HorizontalLayout layout1 = new HorizontalLayout();
            Label msg = new Label("Your group is not booked.",ContentMode.HTML);

        
        
        final TextField name = new TextField();
        name.setCaption("Name of the Party:");

        Slider s = new Slider(20, 150);
        s.setWidth("500px");
        s.setCaption("How many people:");

        ComboBox<String> combo1 = new ComboBox<String>("Accessible");
        combo1.setItems("yes", "no");

        
               
        Grid<Bus> myGrid = new Grid<> ();
        myGrid.setItems(mylist);
        myGrid.addColumn(Bus::getDestination).setCaption("Destination");
        myGrid.addColumn(Bus::getCapacity).setCaption("Capacity");
        myGrid.addColumn(Bus::getFeature).setCaption("Features");
        myGrid.addColumn(Bus::getAccessible).setCaption("Accessible");
        myGrid.setSelectionMode(SelectionMode.MULTI);
        myGrid.setSizeFull();



        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            if (name.isEmpty()){
               
                msg.setValue("<strong>Please enter group name.</strong> ");
                return;
                }
    
    
    if (combo1.isEmpty()){
                    msg.setValue("<strong>Please confirm if you need an accessible bus</strong>");
                    
                    return;
                }
    
    
    if (s.isEmpty()){
                    msg.setValue("Please select number ");
                    return;}
    
    
                Set<Bus> selectedElements = myGrid.getSelectedItems();
                        
    
             if(selectedElements.size()<=0){
                    msg.setValue("<strong>Please select at least one bus!</strong>");
                    
                    return;
                }   
    
                for(Bus p:selectedElements){
                    if(p.getAccessible().equalsIgnoreCase("no") && combo1.getValue().equalsIgnoreCase("yes")){
                        msg.setValue("<strong>You cannot select a non-accessible bus.</strong>");
                        
                        return;
                    }
                    if(s.getValue()>p.getCapacity()){
                        msg.setValue("Can't select");
                        msg.setValue("<strong>You have selected buses with a max capacity of </strong>" +p.getCapacity() + "which is not enough to hold" + s.getValue()+".");
                        return;
                    }
                }           
                
    
    
     msg.setValue("your booking is done");//final msg
          
        });
        layout1.addComponents(name,s,combo1);
        layout.addComponents(logo,layout1, button, msg,myGrid, new Label("B01234567"));
       
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
