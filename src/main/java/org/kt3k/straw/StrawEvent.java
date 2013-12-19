package org.kt3k.straw;

public class StrawEvent {

	private final String type;

	public StrawEvent(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "StrawEvent (type=" + this.type + ")";
	}

	public static class Type {
		public static final String BACK_PRESSED = "backPressed";
	}

}
