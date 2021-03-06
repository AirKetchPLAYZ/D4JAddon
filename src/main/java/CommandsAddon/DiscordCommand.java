package CommandsAddon;


public class DiscordCommand {
	protected String name;
	protected String usage = name + " - Usage Unspecified";
	protected CommandGroup group;
	public boolean allInOneArg = false;
	private CommandEvent ev;
	public String fullname;
	protected boolean execute(CommandEvent event, String[] args) {
		return false;
	}
	public CommandGroup getGroup() {
		return this.group;
	}
	public String getShortName() {
		return this.name;
	}
	public String getFullName() {
		return this.fullname;
	}
	public String getUsage() {
		return group.prefix + this.fullname + " " + this.usage;
	}
	public void run(CommandEvent eve) {
		ev = eve;
		int index = ev.getMessage().getContent().get().indexOf(group.prefix + name) + (group.prefix + name).length();
		String rawInput = (ev.getMessage().getContent()).get().substring(index).trim();
		if (allInOneArg) {
			
			
			String[] args = {rawInput,};
			this.execute(ev, args);
		} else {
			String[] splits;
			if (rawInput.equals("")) {
				splits = new String[0];
			} else {
				splits = rawInput.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			}
			for (int i = 0; i < splits.length; i++) {
			    splits[i] = splits[i].trim();
			    if (splits[i].startsWith("\"") && splits[i].endsWith("\"")) {
			    	splits[i] = splits[i].substring(1,splits[i].length()-1);
			    }
			}
			
			if (!this.execute(ev, splits)) {
				ev.getMessage().getChannel().block().createMessage("Usage: `" + getUsage() + "`").block();
			}
		}
	}
	public DiscordCommand(CommandGroup g) {
		group = g;
	}

}
