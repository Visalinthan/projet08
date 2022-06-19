package tourGuide.helper;

public class InternalTestHelper {

	// Set this default up to 100,000 for testing
	private static int internalUserNumber = 100;

	/**
	 * Définir le nombre d'utilisateur interne
	 * @param internalUserNumber
	 */
	public static void setInternalUserNumber(int internalUserNumber) {
		InternalTestHelper.internalUserNumber = internalUserNumber;
	}

	/**
	 * Afficher le nombre d'utilisateur interne
	 * @return
	 */
	public static int getInternalUserNumber() {
		return internalUserNumber;
	}
}
