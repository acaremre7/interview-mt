import common.ApplicationConstants;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.servlet.ServletContainer;
import rest.AccountWebResource;
import rest.TransactionWebResource;

public class Main {
    public static Server server;

    public static void main(String[] args) throws Exception {
        initServer();
        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    public static void initServer(){
        if(server == null){
            synchronized (Main.class) {
                if(server == null) {
                    QueuedThreadPool threadPool = new QueuedThreadPool(100, 10, 120);
                    server = new Server(threadPool);
                    ServerConnector connector = new ServerConnector(server);
                    connector.setPort(ApplicationConstants.WEB_APPLICATION_PORT);
                    server.setConnectors(new Connector[] { connector });
                    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
                    context.setContextPath("/");
                    server.setHandler(context);
                    ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
                    jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                            AccountWebResource.class.getCanonicalName() + "," +
                            TransactionWebResource.class.getCanonicalName());
                }
            }
        }
    }
}
