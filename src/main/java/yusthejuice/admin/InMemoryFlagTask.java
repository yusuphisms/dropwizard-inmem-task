package yusthejuice.admin;

import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.PostBodyTask;

import java.io.PrintWriter;

public class InMemoryFlagTask extends PostBodyTask {

    static String flags = "";

    public InMemoryFlagTask() {
        super("flag");
    }

    @Override
    public void execute(ImmutableMultimap<String, String> immutableMultimap, String body, PrintWriter printWriter) throws Exception {

        if (immutableMultimap.containsKey("action")) {
            if (immutableMultimap.get("action").stream().anyMatch(param -> param.equals("set"))) {
                flags = body;
            }
        }

        printWriter.write(flags);
        printWriter.flush();
    }
}
