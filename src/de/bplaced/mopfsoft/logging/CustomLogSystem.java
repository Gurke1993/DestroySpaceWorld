package de.bplaced.mopfsoft.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.newdawn.slick.util.LogSystem;


public class CustomLogSystem implements LogSystem{

	public static final int HIGH = 3;
	public static final int MEDIUM = 2;
	public static final int LOW = 1;
	public static final int NONE = 0;
	
	private static final String PREFIX = "[DS] %TIME | ";
	
	private int loggingLevel;

	public CustomLogSystem(int loggingLevel) {
		this.loggingLevel = loggingLevel;
	}
	
	public void setLoggingLevel(int loggingLevel) {
		this.loggingLevel = loggingLevel;
	}
	
	private String getPrefix() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return PREFIX.replaceAll("%TIME", dateFormat.format(date));
	}
	

	@Override
	public void debug(String msg) {
		if (loggingLevel >= HIGH)
			System.out.println(getPrefix()+msg);
	}

	@Override
	public void error(Throwable e) {
		if (loggingLevel >= LOW)
			System.out.println(getPrefix()+System.getProperty("line.separator")+e);
	}

	@Override
	public void error(String msg) {
		if (loggingLevel >= LOW)
			System.out.println(getPrefix()+msg);
	}

	@Override
	public void error(String msg, Throwable e) {
		if (loggingLevel >= LOW)
			System.out.println(getPrefix()+msg+System.getProperty("line.separator")+e);
	}

	@Override
	public void info(String msg) {
		if (loggingLevel >= LOW)
			System.out.println(getPrefix()+msg);
	}

	@Override
	public void warn(String msg) {
		if (loggingLevel >= MEDIUM)
			System.out.println(getPrefix()+msg);
	}

	@Override
	public void warn(String msg, Throwable e) {
		if (loggingLevel >= MEDIUM)
			System.out.println(getPrefix()+msg+System.getProperty("line.separator")+e);
	}

}
