package CommandsAddon;

import java.util.*;

public class CommandGroup {
	public String prefix;
	private List<DiscordCommand> cmds = new ArrayList<DiscordCommand>();
	public CommandGroup(String pre) {
		this.prefix = pre;
	}
	public void addCommand(DiscordCommand cmd) {
		cmds.add(cmd);
	}
	public boolean run(CommandEvent event) {
		if (!event.getMessage().getContent().isPresent()) {
			System.out.println("empty");
			return false;
		}
		String result;
		String message = event.getMessage().getContent().get();
		int index = message.indexOf(this.prefix);
		if (index == -1) {
			return false;
		}
		else
		{
		    result = message.substring(0, index) + 
		                    message.substring(index + this.prefix.length());

		}
		for (DiscordCommand cmd : cmds.toArray(new DiscordCommand[cmds.size()])) {
			System.out.println("hello");
			if (result.toLowerCase().startsWith(cmd.name.toLowerCase())) {
				cmd.run(event);
				return true;
			}
		}
		return false;
	}
}
