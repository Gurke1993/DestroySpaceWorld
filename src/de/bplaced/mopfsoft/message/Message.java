package de.bplaced.mopfsoft.message;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.util.Log;


public abstract class Message {
	
	protected Map<String, String> args = null;
	protected String message;

	public Message(String message) {
		this.message = message;
	}
	
	public Message(Map <String,String> args) {
		this.args = args;
	}
	
	public Map <String,String> getArguments() {
		if (args == null) {
		Map<String,String> args= new HashMap<String,String>();
		
		String[] argArray;
		for (String split: message.split(":")) {
			argArray = split.split("=");
			if (argArray.length<2)
				args.put(argArray[0], "");
			else
				args.put(argArray[0], argArray[1]);
		}
		
		}
		return args;
	}
	
	@Override
	public String toString() {
		if (message == null) {
			this.message = "";
			for (Entry <String,String> entry: args.entrySet()) {
				this.message = message+":"+entry.getKey()+"="+entry.getValue();
			}
			this.message = message.substring(1);
		}
		return this.message;
	}
	
	public static Message getMessage(Map <String,String> args)  {
		try {
		Class<?> c = Class.forName("de.bplaced.mopfsoft.message."+args.get("Class"));
		Constructor<?> cons = c.getConstructor(Map.class);
		return (Message)cons.newInstance(args);
		} catch (ClassNotFoundException e) {
			Log.error(e);
			//return new SendOnlyMessage(args);
		} catch (Exception e) {
			Log.error(e);
		}
		return null;
	}
	
	public static Message getMessage(String message) {
		
		Map<String,String> args= new HashMap<String,String>();
		
		String[] argArray;
		for (String split: message.split(":")) {
			argArray = split.split("=");
			if (argArray.length<2)
				args.put(argArray[0], "");
			else
				args.put(argArray[0], argArray[1]);
		}
		
		return getMessage(args);
	}
}
