package com.haruhifanclub.hstweaker.feature.prism;

import static com.haruhifanclub.hstweaker.HSTweaker.LOGGER;
import java.util.StringJoiner;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import com.haruhifanclub.hstweaker.server.event.impl.MsgCommandEvent;
import com.haruhifanclub.hstweaker.utils.PlayerUtils;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class TellMonitor {

    public static final Marker MARKER = LogUtil.getMarker(TellMonitor.class).addParents(Prism.MARKER);

    public static void onMsgCommand(final MsgCommandEvent event) {
        try {
            var sourceName = PlayerUtils.getName(event.getSource().getPlayerOrException());
            var targets = event.getTargets();
            String targetName;
            if (targets.size() == 1) {
                targetName = PlayerUtils.getName(targets.get(0));
            } else {
                var sj = new StringJoiner(",");
                targets.forEach((p) -> sj.add(PlayerUtils.getName(p)));
                targetName = sj.toString();
            }
            LOGGER.info(MARKER, "<{}:{}> {}", sourceName, targetName, event.getMessage().getString());
        } catch (CommandSyntaxException e) {
        }
    }

}
