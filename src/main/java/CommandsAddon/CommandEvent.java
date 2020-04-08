package CommandsAddon;

import discord4j.core.event.domain.message.*;
import discord4j.core.object.entity.*;

public class CommandEvent {
	private MessageCreateEvent event;
	private MessageChannel channel;
	final public MessageCreateEvent getBaseEvent() {
		return event;
	}
	final public Message send (String content) {
		return channel.createMessage(content).block();
	}
	final public Message getMessage() {
		return event.getMessage();
	}
	public CommandEvent(MessageCreateEvent event) {
		this.event = event;
		this.channel = event.getMessage().getChannel().block();
	}
}
