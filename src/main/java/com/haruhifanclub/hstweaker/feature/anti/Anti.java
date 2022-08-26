package com.haruhifanclub.hstweaker.feature.anti;

import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.game.MessageHelper;
import com.haruhifanclub.hstweaker.HSTweaker;

public final class Anti {

    public static final Marker MARKER = LogUtil.getMarker(Anti.class);

    protected static final MessageHelper MSGH = new MessageHelper(HSTweaker.MESSAGE_PREFIX, (key) -> HSTweaker.i18n("anti." + key));

}
