package com.example.test.util;

import android.util.Log;

/**
 * Android Log封装类，将Log的调用类、方法、位置进行输出，方便快速定位
 * 
 * @author Leng Xiaoming
 *
 */
public class Loger {
	/**
	 * Environment of the code. It can be Android, Computer or others.
	 */
	private static String environment = "Android";

	/**
	 * A Singleton Class named Loger which is to configure the initializing
	 * values.
	 */
	private Loger() {
	}

	/**
	 * 
	 * @author Chunk
	 *
	 */
	private static class LogerHolder {
		private static final Loger instance = new Loger();
	}

	/**
	 * Get the instance of the Singleton Class Loger
	 * 
	 * @return instance of the Singleton Class Loger
	 */
	public static Loger getInstance() {
		return LogerHolder.instance;
	}

	/**
	 * A special tag which is to display in all Loger.
	 */
	private static String TAG = "{Lengxiaoming} ";

	/**
	 * Set TAG to tag.
	 * 
	 * @param tag
	 *            The TAG you would like logged.
	 */
	public static void setTAG(String tag) {
		TAG = tag + " ";
	}

	/**
	 * Clear the TAG to null string.
	 */
	public static void clearTAG() {
		TAG = "";
	}

	/**
	 * Send an WARN log to position the program with class name, method name and
	 * number of line of the invoke position.
	 * 
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void l() {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String tag = stacks[1].getClassName() + " " + stacks[1].getMethodName()
				+ " (" + stacks[1].getLineNumber() + ")";
		tag = tag.substring(tag.lastIndexOf(".") + 1);
		if (environment.equals("Android")) {
			Log.w(TAG + tag, "Locate.");
		} else if (environment.equals("Computer")) {
			System.out.println("l: " + TAG + tag);
		}
	}

	/**
	 * Send an DEBUG log message with class name, method name and number of line
	 * of the invoke position.
	 * 
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void d(String msg) {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String tag = stacks[1].getClassName() + " " + stacks[1].getMethodName()
				+ " (" + stacks[1].getLineNumber() + ")";
		tag = tag.substring(tag.lastIndexOf(".") + 1);
		if (environment.equals("Android")) {
			if (msg != null)
				Log.d(TAG + tag, msg);
		} else if (environment.equals("Computer")) {
			System.out.println("d: " + TAG + tag + " : " + msg);
		}
	}

	/**
	 * Send an DEBUG log message with tag of the invoke position.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void d(String tag, String msg) {
		if (environment.equals("Android")) {
			if (msg != null)
				Log.d(TAG + tag, msg);
		} else if (environment.equals("Computer")) {
			System.out.println("d: " + TAG + tag + " : " + msg);
		}
	}

	/**
	 * Send an INFO log message with class name, method name and number of line
	 * of the invoke position.
	 * 
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void i(String msg) {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String tag = stacks[1].getClassName() + " " + stacks[1].getMethodName()
				+ " (" + stacks[1].getLineNumber() + ")";
		tag = tag.substring(tag.lastIndexOf(".") + 1);
		if (environment.equals("Android")) {
			if (msg != null)
				Log.i(TAG + tag, msg);
		} else if (environment.equals("Computer")) {
			System.out.println("i: " + TAG + tag + " : " + msg);
		}
	}

	/**
	 * Send an INFO log message with tag of the invoke position.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void i(String tag, String msg) {
		if (environment.equals("Android")) {
			if (msg != null)
				Log.i(TAG + tag, msg);
		} else if (environment.equals("Computer")) {
			System.out.println("i: " + TAG + tag + " : " + msg);
		}
	}

	/**
	 * Send an WARN log message with class name, method name and number of line
	 * of the invoke position.
	 * 
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void w(String msg) {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String tag = stacks[1].getClassName() + " " + stacks[1].getMethodName()
				+ " (" + stacks[1].getLineNumber() + ")";
		tag = tag.substring(tag.lastIndexOf(".") + 1);
		if (environment.equals("Android")) {
			if (msg != null)
				Log.w(TAG + tag, msg);
		} else if (environment.equals("Computer")) {
			System.out.println("w: " + TAG + tag + " : " + msg);
		}
	}

	/**
	 * Send an WARN log message with tag of the invoke position.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void w(String tag, String msg) {
		if (environment.equals("Android")) {
			if (msg != null)
				Log.w(TAG + tag, msg);
		} else if (environment.equals("Computer")) {
			System.out.println("w: " + TAG + tag + " : " + msg);
		}
	}

	/**
	 * Send an ERROR log message with class name, method name and number of line
	 * of the invoke position.
	 * 
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void e(String msg) {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String tag = stacks[1].getClassName() + " " + stacks[1].getMethodName()
				+ " (" + stacks[1].getLineNumber() + ")";
		tag = tag.substring(tag.lastIndexOf(".") + 1);
		if (environment.equals("Android")) {
			if (msg != null)
				Log.e(TAG + tag, msg);
		} else if (environment.equals("Computer")) {
			System.out.println("e: " + TAG + tag + " : " + msg);
		}
	}

	/**
	 * Send an ERROR log message with tag of the invoke position.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void e(String tag, String msg) {
		if (environment.equals("Android")) {
			if (msg != null)
				Log.e(TAG + tag, msg);
		} else if (environment.equals("Computer")) {
			System.out.println("e: " + TAG + tag + " : " + msg);
		}
	}
}