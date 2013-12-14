package org.kt3k.straw;

class StrawEvent {

	public final String type;

	public StrawEvent(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "StrawEvent (type=" + this.type + ")";
	}

}
