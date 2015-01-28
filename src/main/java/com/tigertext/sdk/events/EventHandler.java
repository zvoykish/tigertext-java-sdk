package com.tigertext.sdk.events;

import com.tigertext.sdk.TigerTextSdk;

/**
 * Created by Zvika on 1/27/15.
 */
public interface EventHandler {
    /**
     * Any implementing class should implement this method to perform any required event handling.<br/>
     * the SDK is passed for convinience
     *
     * @param event The event
     * @param sdk   The TigerText SDK, sent as an argument for convinience to allow depndency injection for those cases
     */
    void onEvent(TigerTextEvent event, TigerTextSdk sdk);
}
