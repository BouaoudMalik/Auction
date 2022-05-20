package entities;

/**
 * Represents the possible behavior of a bidder
 *
 */
public enum Behavior {
	Aggressive(60), Active(40), Passive(10);

	public final int percent;

	Behavior(int percent) {
		this.percent = percent;
	}

}
