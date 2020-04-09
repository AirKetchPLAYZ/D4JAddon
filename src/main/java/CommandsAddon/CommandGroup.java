package CommandsAddon;

import java.util.*;


public class CommandGroup {
	public String prefix;
	private String name;
	private boolean base;

	private CommandGroup parent;
	private List<DiscordCommand> cmds = new ArrayList<DiscordCommand>();
	private List<DiscordCommand> allcmds = new ArrayList<DiscordCommand>();
	private CommandGroup basegroup;

	private List<CommandGroup> subgroups = new ArrayList<CommandGroup>();
	
	public CommandGroup(String pre, String groupname, boolean isBaseGroup) {
		this.name = groupname;
		this.prefix = pre;
		this.base = isBaseGroup;
		if (base) {
			this.basegroup = this;
		}
	}
	public String getName() {return this.name;}
	public List<DiscordCommand> getCommands() {
		return this.cmds;
	}
	public List<CommandGroup> getSubGroups() {
		return this.subgroups;
	}
	public boolean isBase() {return this.base;}
	public void SetParent(CommandGroup parentgroup) throws IsABaseCommandGroup {
		if (this.base) {
			throw new IsABaseCommandGroup();
		} else {
			this.parent = parentgroup;
			if (!parentgroup.isBase()) {
				this.name = parent.name + "." + this.name;
			}
			parentgroup.subgroups.add(this);
			this.basegroup = parentgroup.basegroup;
		}

	}

	public void addCommand(DiscordCommand cmd) {


		if (!this.isBase()) {
			cmd.fullname = this.name + "." + cmd.getShortName();
			this.basegroup.addCommand(cmd);
			this.cmds.add(cmd);
		} else {
			if(cmd.getFullName() == null) {
				cmd.fullname = cmd.name;
				this.cmds.add(cmd);
			}

			
			this.allcmds.add(cmd);
		}
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


		for (DiscordCommand cmd : allcmds.toArray(new DiscordCommand[allcmds.size()])) {
			if (cmd == null) continue;
			if (result.toLowerCase().startsWith(cmd.getFullName().toLowerCase())) {
				cmd.run(event);
				return true;
			}
		}
		return false;
	}
}
