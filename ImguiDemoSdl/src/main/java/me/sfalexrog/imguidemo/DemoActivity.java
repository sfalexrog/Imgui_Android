package me.sfalexrog.imguidemo;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import org.libsdl.app.SDLActivity;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sf on 8/7/17.
 */

public class DemoActivity extends SDLActivity {

    /* A fancy way of getting the class name */
    private static final String TAG = DemoActivity.class.getSimpleName();

    /* A list of assets to copy to internal directory */
    private static final String[] ASSET_NAMES = new String[]{"bump.jpg",
            "skybox-negx.jpg",
            "skybox-negy.jpg",
            "skybox-negz.jpg",
            "skybox-posx.jpg",
            "skybox-posy.jpg",
            "skybox-posz.jpg",
            "Roboto-Medium.ttf"
    };

    @Override
    protected String[] getLibraries() {
        return new String[]{"demo"};
    }

    @Override
    protected String[] getArguments() {
        return new String[]{getFilesDir().getAbsolutePath()};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /* We're going to unpack our assets to a location that's accessible
         * via the usual stdio means. This is to avoid using AAssetManager
         * in our native code. */
        Log.v(TAG, "Copying assets to accessible locations");
        AssetManager assetManager = this.getAssets();
        for (String assetName: ASSET_NAMES) {
            try {
                Log.v(TAG, "Copying " + assetName);
                InputStream ais = assetManager.open(assetName);
                FileOutputStream fos = openFileOutput(assetName, MODE_PRIVATE);
                final int BUFSZ = 8192;
                byte[] buffer = new byte[BUFSZ];
                int readlen = 0;
                do {
                    readlen = ais.read(buffer, 0, BUFSZ);
                    if (readlen < 0) {
                        break;
                    }
                    fos.write(buffer, 0, readlen);
                } while (readlen > 0);
                fos.close();
                ais.close();
            } catch(IOException e){
                Log.e(TAG, "Could not open " + assetName + " from assets, that should not happen", e);
            }
        }

    }
}
