package pcmm.gates;

/**
 * <p>
 * The GateSpec describes some high-level attributes of the Gate, and contains
 * information regarding the treatment of other objects specified in the Gate
 * message.
 * </p>
 * 
 * @author riadh
 * 
 */
public interface IGateSpec {
	/**
	 * <p>
	 * Direction indicates whether the Gate is for an upstream or downstream
	 * flow. Depending on this direction, the CMTS MUST reserve and activate the
	 * DOCSIS flows accordingly. For Multicast Gates the CMTS needs to only
	 * support flows or gates in the downstream direction.
	 * </p>
	 * 
	 * @author riadh
	 * 
	 */
	enum Direction {

		UPSTREAM("Upstream"), DOWNSTREAM("Downstream");

		private Direction(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return getValue();
		}

		private String value;

	};

	/**
	 * <p>
	 * provides a way for the Application Manager and the Policy Server to group
	 * Gates into different classes with different authorization
	 * characteristics. For example, one could use the SessionClassID to
	 * represent some prioritization or preemption scheme that would allow
	 * either the Policy Server or the CMTS to preempt a pre-authorized Gate in
	 * favor of allowing a new Gate with a higher priority to be authorized.
	 * </p>
	 * 
	 * @return session class ID;
	 */
	int getSessionClassID();

	/**
	 * 
	 * @return direction.
	 */
	Direction getDirection();

	/**
	 * Authorized Timer limits the amount of time the authorization must remain
	 * valid before it is reserved
	 * 
	 * @return time in ms;
	 */
	int getAuthorizedTimer();

	/**
	 * Reserved Timer limits the amount of time the reservation must remain
	 * valid before the resources are committed
	 * 
	 * @return time in ms;
	 */
	int getReservedTimer();

	/**
	 * Committed Timer limits the amount of time a committed service flow may
	 * remain idle.
	 * 
	 * @return time in ms;
	 */
	int getCommittedTimer();

	/**
	 * Committed Recovery Timer limits the amount of time that a committed
	 * service flow can remain without a subsequent refresh message from the
	 * PS/AM once the PS/AM has been notified of inactivity
	 * 
	 * @return time in ms;
	 */
	int getCommittedRecoveryTimer();

	int getDSCP_TOSOverwrite();
	
	int getDSCP_TOSMask();

}
