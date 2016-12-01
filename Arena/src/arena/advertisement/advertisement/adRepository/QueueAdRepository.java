package arena.advertisement.advertisement.adRepository;

import arena.advertisement.advertisement.ad.IPreferredAd;
import arena.advertisement.advertisement.adPreference.IAdPreference;
import arena.advertisement.advertisement.adQueue.AdPreferenceQueue;
import arena.advertisement.advertisement.adQueue.AdQueueFactory;
import arena.advertisement.advertisement.adSpot.AdSpot;
import arena.advertisement.advertisement.adSpot.AdSpotFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import arena.users.advertiser.IAdvertiser;

import java.util.List;

/**
 * Implements {@link AdRepository} with a AdPreferenceQueue.
 */
public class QueueAdRepository implements AdRepository {

    private final AdPreferenceQueue queue = new AdQueueFactory<IPreferredAd>().newPreferenceQueue();

    @Override
    public AdSpot newAdSpot(IAdPreference preference) {
        return AdSpotFactory
                .newAdSpot(this.queue.getPreferredAds(preference));
    }

    @Override
    public AdRepository addPreferredAd(IPreferredAd preferredAd) {
        queue.addAd(preferredAd);

        return this;
    }

    @Override
    public List<IPreferredAd> getAdsFromOwner(IAdvertiser owner) {
        //TODO implement
        throw new NotImplementedException();
    }

    @Override
    public void addAdPreference(IAdPreference preference) {
        //TODO implement
        throw new NotImplementedException();
    }

    @Override
    public List<IAdPreference> getAdPreferences() {
        //TODO implement
        throw new NotImplementedException();
    }
}