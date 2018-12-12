package com.jaskiran;

import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


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
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Grid.SelectionMode;


import java.util.ArrayList;
import java.util.List;
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
        String connectionString ="jdbc:sqlserver://sample1ser.database.windows.net:1433;"+
        "database=sample1DB;user=sample1admin@sample1ser;"+
        "password={Test1234};"+
        "encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        Label logo = new Label("<H1>Marty Party Planners</H1> <p/> <h3>Please enter the details below and click Book</h3>",ContentMode.HTML); 
        final HorizontalLayout layout1 = new HorizontalLayout();
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

        
        final TextField name = new TextField();
        name.setCaption("Name of Party :");

        Slider s = new Slider("value",10 , 300);
        s.setWidth("500px");
        s.setCaption("How many people are invited to the party:");

        ComboBox<String> combo1 = new ComboBox<String>("Children Attending?");
        combo1.setItems("Yes", "No");

        List<Party> mylist = new ArrayList<Party>();


        try{
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM RoomService;");
            
            
            
            while(rs.next())
            {   
                // Add a new Customer instantiated with the fields from the record (that we want, we might not want all the fields, note how I skip the id)
                mylist.add(new Party(rs.getString("Room"), 
                            rs.getInt("Capacity"), 
                            rs.getString("Features"),
                            rs.getBoolean("AlcoholAllowed")));
                
            }
            }
            catch (Exception e) 
            {
            
                layout.addComponent(new Label(e.getMessage()));
            }
            
            
               
        Grid<Party> myGrid = new Grid<> ();
        myGrid.setItems(mylist);
        myGrid.addColumn(Party::getRoom).setCaption("Room");
        myGrid.addColumn(Party::getCapacity).setCaption("Capacity");
        myGrid.addColumn(Party::getFeatures).setCaption("Features");
        myGrid.addColumn(Party::isAlcoholAllowed).setCaption("Alcohol Allowed?");
        myGrid.setSelectionMode(SelectionMode.MULTI);
        myGrid.setSizeFull();


        Label msg = new Label("<strong>test</strong>", ContentMode.HTML);

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            if (name.isEmpty()){
                msg.setValue("Please enter ");
                return;
                }
    
    
            if (combo1.isEmpty()){
                            msg.setValue("Please enter ");
                            return;
                        }
            
            
            if (s.isEmpty()){
                            msg.setValue("Please select ");
                            return;}
            
            
            
            
            
            Set<Party> selectedElements = myGrid.getSelectedItems();
            //List<Person> pp = new ArrayList<Person>();
            //pp.addAll(s);

            //msg.setValue("");//

            if(selectedElements.size()<=0){
                msg.setValue("<strong>Sorry you have not selected any  item in grid</strong>");
                return;
            }   

            for(Party p:selectedElements){
                if(p.isAlcoholAllowed()== true && combo1.getValue().equalsIgnoreCase("yes")){
                    msg.setValue("You can not book that room");
                    return;
                }
                if(s.getValue()>p.getCapacity()){
                    msg.setValue("You can not book that room as room capacity is less than your requirment");
                    return;
                }

            }

            msg.setValue("your booking is done");
            
                });
        layout1.addComponents(name,s,combo1);
        layout.addComponents(logo,layout1, button, msg,myGrid);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
