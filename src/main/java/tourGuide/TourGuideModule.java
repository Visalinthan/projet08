package tourGuide;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;
import tourGuide.service.RewardsService;

@Configuration
public class TourGuideModule {

	/**
	 * GpsUtil collecte l'emplacement du téléphone mobile ou de l’ordinateur portable de l'utilisateur.
	 * @return GpsUtil
	 */
	@Bean
	public GpsUtil getGpsUtil() {
		return new GpsUtil();
	}


	/**
	 * RewardService fait appel à des méthodes en rapport avec les récompenses d'un utilisateur
	 * @return RewardsService
	 */
	@Bean
	public RewardsService getRewardsService() {
		return new RewardsService(getGpsUtil(), getRewardCentral());
	}

	/**
	 * RewardCentral fait appel à un réseau de fournisseurs pour regroupe un ensemble de valeurs
	 * et déterminer les récompenses offertes pour chaque attraction touristique.
	 * @return RewardCentral
	 */
	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}
	
}
