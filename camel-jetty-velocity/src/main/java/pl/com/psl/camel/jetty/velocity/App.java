package pl.com.psl.camel.jetty.velocity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by psl on 14.04.17.
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        new App().start();
    }

    private void start() {
        LOGGER.info("Starting application");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        addShutdownHook(context);
        context.register(Config.class);
        context.refresh();
        LOGGER.info("Application started");
    }

    private void addShutdownHook(final AnnotationConfigApplicationContext context) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Running shutdown hook");
            context.close();
            LOGGER.info("Shutdown hook completed");
        }));
    }
}
