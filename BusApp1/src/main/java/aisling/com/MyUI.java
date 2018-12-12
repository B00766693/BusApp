package aisling.com;

import javax.servlet.annotation.WebServlet;

import java.sql.*;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
        String connectionString = "jdbc:sqlserver://tuesserver.database.windows.net:1433;" + 
        "database=BusDatabase;" + 
        "user=aislingstudent@tuesserver;" + 
        "password=Server123;" + 
        "encrypt=true;" + 
        "trustServerCertificate=false;" + 
        "hostNameInCertificate=*.database.windows.net;" +
        "loginTimeout=30;";
        //to do a connection on the app

        final VerticalLayout layout = new VerticalLayout();
        
        try {
        // Connect with JDBC driver to a database
        connection = DriverManager.getConnection(connectionString);
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM BusTable"); //Is name of table that was created in step 4
        layout.addComponent(new Label("Connected to database: " + connection.getCatalog()));
        }//try

        catch(Exception e)
        {
            // Not success!
            System.out.println(e.getMessage());
        }//catch

        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
