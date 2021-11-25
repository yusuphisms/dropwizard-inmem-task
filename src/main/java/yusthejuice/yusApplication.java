package yusthejuice;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import yusthejuice.admin.InMemoryFlagTask;

public class yusApplication extends Application<yusConfiguration> {

    public static void main(final String[] args) throws Exception {
        new yusApplication().run(args);
    }

    @Override
    public String getName() {
        return "yus";
    }

    @Override
    public void initialize(final Bootstrap<yusConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final yusConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        environment.admin().addTask(new InMemoryFlagTask());
    }

}
