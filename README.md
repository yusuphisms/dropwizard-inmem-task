# yus

How to start the yus application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/inmem-admin-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

## Notes While Doing My Thing
Wanted to quickly test how to use the Admin endpoints to do some lightweight storage in-memory.
Read through Dropwizard and knew about healthchecks. Was less familiar with how Tasks work.

Seems like they are similar but with Tasks, we have some more flexibility from a first glance. I'm experimenting with Tasks.

Built a starter [DW using archetype](https://www.dropwizard.io/en/latest/getting-started.html#setting-up-using-maven). Then followed instructions to [set up a simple task](https://www.dropwizard.io/en/latest/manual/core.html#tasks).

I opted to set up a PostBodyTask because I think thatServletEnvironment will provide the most flexibility.

I also did a bit of source code reading to understand the default TaskServlet in the AdminEnvironment setup. One interesting thing is that by default it will return only `text/plain;charset=utf-8` content. I can live with that for my use case, but it seems possible to customize this to one's needs with a bit more source code exploration.

I immediately found I was having problems getting the PostBody that I was sending in my simple curl request to go through. The parameter meant for the POST body was always empty.

```shell
curl -XPOST localhost:8081/tasks/flag -d "woooop" -v
```

Turns out an unspoken rule is that by default, it doesn't seem to support `Content-Type: application/x-www-form-urlencoded`.

When I changed to `application/json`, I had more success.
```shell
curl -XPOST localhost:8081/tasks/flag -d "woooop" -v -H "Content-Type: application/json"
```

The final step was to make a proof of concept which this repo does. For the life of the application, the static variable serves as an in-memory storage of some administrative values that we care about.

```shell
# Get the flags, will return empty string body
curl -v -XPOST localhost:8081/tasks/flag

# Set the flags, will set and return the flags.
curl -v -H "Content-Type: application/json" -XPOST localhost:8081/tasks/flag?action=set -d "yusuphisms" 

# Get the flags, subsequent calls will return the last value set for the static member for the life of the application
curl -v -XPOST localhost:8081/tasks/flag
```

Generally happy with this smol proof of concept.