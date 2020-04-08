# D4JAddon 
A command parser for Discord4J 

PRs welcome!

to install (maven):
```xml
<repositories>

  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>

  <dependency>
    <groupId>com.github.airketchplayz</groupId>
    <artifactId>D4JAddon</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

# Usage: 
Extend DiscordCommand
```java

class Ping extends DiscordCommand {
	public Ping(CommandGroup g) {
		super(g);
		super.usage = "ping args";
		super.name = "ping";
	}

	@Override
	public boolean execute(CommandEvent event, String[] args) {
    		event.send("Pong!")
		return true; // If false is returned, it will send the prefix + usage
	}
}
```
Make bot
```java
public class CCBot {
	private static CommandGroup CMDGroup = new CommandGroup("cc/"); // new CommandGroup(prefix)


	public static void main(String[] args) {
		CMDGroup.addCommand(new Ping(CMDGroup)); // CommandGroup.addCommand(new DiscordCommand(CommandGroup))

		final DiscordClient client = DiscordClientBuilder.create("Token here").build();
		client.getEventDispatcher().on(MessageCreateEvent.class)
		.subscribe(event -> {
			if (event.getMessage().getAuthor().get().getId() == client.getSelf().block().getId()) //Ignore messages from self
				return;
			System.out.println(CMDGroup.run(new CommandEvent(event))); //CommandGroup.run(new CommandEvent(MessageCreateEvent)) : returns boolean - whether command was a valid command
		});
		client.login().block();
	}

}
```
# CommandEvent:  
constructor CommandEvent(MessageCreateEvent event)  
getMessage()  
send(String text)  
getBaseEvent()  
# CommandGroup:  
constructor CommandGroup(String prefix)  
run(CommandEvent event)  
addCommand(DiscordCommand cmd)  
# DiscordCommand  
constructor DiscordCommand(CommandGroup g)  
run(CommandEvent event)  
getUsage()  
overridable execute(CommandEvent event, String[] args) 
