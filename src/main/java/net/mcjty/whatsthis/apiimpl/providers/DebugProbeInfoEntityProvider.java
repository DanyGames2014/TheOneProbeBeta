package net.mcjty.whatsthis.apiimpl.providers;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.config.Config;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.mcjty.whatsthis.api.IProbeHitEntityData;
import net.mcjty.whatsthis.api.IProbeInfo;
import net.mcjty.whatsthis.api.IProbeInfoEntityProvider;
import net.mcjty.whatsthis.api.ProbeMode;
import net.mcjty.whatsthis.apiimpl.styles.LayoutStyle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;


public class DebugProbeInfoEntityProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return WhatsThis.NAMESPACE.getName() + ":entity.debug";
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data) {
        if (mode == ProbeMode.DEBUG && Config.MAIN_CONFIG.showDebugInfo) {
            IProbeInfo vertical = null;
            if (entity instanceof LivingEntity entityLivingBase) {
                vertical = probeInfo.vertical(new LayoutStyle().borderColor(0xffff4444).spacing(2));

//                int totalArmorValue = entityLivingBase.getTotalArmorValue();
//                int age = entityLivingBase.getIdleTime();
//                float absorptionAmount = entityLivingBase.getAbsorptionAmount();
//                float aiMoveSpeed = entityLivingBase.getAIMoveSpeed();
//                int revengeTimer = entityLivingBase.getRevengeTimer();
//                vertical
//                        .text(LABEL + "Tot armor: " + INFO + totalArmorValue)
//                        .text(LABEL + "Age: " + INFO + age)
//                        .text(LABEL + "Absorption: " + INFO + absorptionAmount)
//                        .text(LABEL + "AI Move Speed: " + INFO + aiMoveSpeed)
//                        .text(LABEL + "Revenge Timer: " + INFO + revengeTimer);
            }
//            if (entity instanceof EntityAgeable) {
//                if (vertical == null) {
//                    vertical = probeInfo.vertical(new LayoutStyle().borderColor(0xffff4444).spacing(2));
//                }
//
//                EntityAgeable entityAgeable = (EntityAgeable) entity;
//                int growingAge = entityAgeable.getGrowingAge();
//                vertical
//                        .text(LABEL + "Growing Age: " + INFO + growingAge);
//            }
        }
    }
}
