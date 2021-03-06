package arena.advertisement.ad;

import arena.advertisement.adPreference.IAdPreference;
import arena.advertisement.adPreference.AdPreferenceFactory;
import arena.metaInformation.MetaInformation;
import arena.metaInformation.advertisementMetaInformation.AdvertisementMetaInformation;
import javafx.scene.image.Image;

import java.lang.reflect.ParameterizedType;

/**
 * Creates PreferredAds from a given Ad and IAdPreference.
 */
public class PreferredAdFactory {

    public static IPreferredAd newPreferredAd(Ad ad, IAdPreference adPreference, AdvertisementMetaInformation adMetaInformation){
        if(PreferredAdFactory.isImageAd(ad))
            return new PreferredImageAd((Ad<Image>)ad, adPreference, adMetaInformation);
        else
            throw new IllegalArgumentException();
    }

    public static IPreferredAd newPreferredAd(String imageURL, IAdPreference adPreference, AdvertisementMetaInformation adMetaInformation){
        return new PreferredImageAd(new ImageAd(imageURL), adPreference, adMetaInformation);
    }

    /**
     * Returns a IPreferredAd with preference to be displayed
     * in the PlayView.
     *
     * Mainly used for testing purposes.
     * @param imageURL
     * @return
     */
    public static IPreferredAd newPlayerViewPreferredAd(String imageURL){
        return new PreferredImageAd(
                new ImageAd(imageURL),
                AdPreferenceFactory.newPlayViewPreference(), new AdvertisementMetaInformation("lars", "owns this shit", 2,5.5)
        );
    }

    /**
     * Returns a IPreferredAd with preference to be displayed
     * in the MainWindow.
     *
     * Mainly used for testing purposes.
     * @param imageURL
     * @return
     */
    public static IPreferredAd newMainWindowPreferredAd(String imageURL){
        return new PreferredImageAd(
                new ImageAd(imageURL),
                AdPreferenceFactory.newMainWindowPreference(), new AdvertisementMetaInformation("lars", "owns this shit", 2,5.5));

    }

    private static boolean isImageAd(Ad ad){
        ParameterizedType type = (ParameterizedType) ad.getClass().getGenericSuperclass();

        //Check if parameter is of type Image
        return type.getActualTypeArguments()[0] == Image.class;
    }
}
