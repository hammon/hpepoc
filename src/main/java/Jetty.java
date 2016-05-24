import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Jetty {
    final static Logger log = LoggerFactory.getLogger(Jetty.class);
    int _port = 8080;
    String _rootPath = "/";

    public static void main(String[] args) {

        Properties p = new Properties();

        try {
            p.load(new FileInputStream("./log4j.properties"));
            PropertyConfigurator.configure(p);
            log.info("Starting ...");
        } catch (IOException e) {
            e.printStackTrace();

        }

        Jetty jetty = new Jetty();
        jetty.start(8081,"/home/michael/dev/horny");///scripts/lib
    }

    public void start(int port){
        _port = port;
        start();
    }

    public void start(int port,String rootPath){
        _port = port;
        _rootPath = rootPath;
        start();
    }

    public void start(){
        try {
            Server server = new Server();

            ServerConnector connector = new ServerConnector(server);
            connector.setPort(_port);

            server.setConnectors(new Connector[]{connector});

//            ContextHandler staticContext = new ContextHandler();
//            staticContext.setContextPath("/");
//
//            ResourceHandler staticHandler = new ResourceHandler();
//            staticHandler.setBaseResource(Resource.newResource(Jetty.class.getClassLoader().getResource("webStatic")));
//
//            staticContext.setHandler(staticHandler);


            ServletContextHandler apiContext = initFlowsServletContextHandler();

            ContextHandlerCollection contexts = new ContextHandlerCollection();

//            contexts.setHandlers(new Handler[]{staticContext, apiContext});
            contexts.setHandlers(new Handler[]{apiContext});
            server.setHandler(contexts);

            System.err.println(server.dump());



            server.start();
//            server.join();
        }
        catch(Exception e){
            log.error("Jetty Oops...",e);
        }
    }

    private ServletContextHandler initFlowsServletContextHandler() {
        ServletContextHandler apiContext = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ServletHolder servletHolder = new ServletHolder(TestServlet.class);
        apiContext.addServlet(servletHolder, "/api/test");

        apiContext.setAttribute("rootPath", _rootPath);

        return apiContext;
    }

}
