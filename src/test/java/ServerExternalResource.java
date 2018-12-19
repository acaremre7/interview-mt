import org.junit.rules.ExternalResource;

public class ServerExternalResource extends ExternalResource {
    @Override
    protected void before() throws Exception {
        Main.initServer();
        Main.server.start();
    }

    @Override
    protected void after() throws Exception {
        if (Main.server.isRunning()) {
            Main.server.stop();
        }
    }
}

