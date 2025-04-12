package net.mcjty.whatsthis.apiimpl.providers;

import net.mcjty.whatsthis.Tools;
import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.*;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.text.DecimalFormat;

import static net.mcjty.whatsthis.api.TextStyleClass.*;

public class DefaultProbeInfoEntityProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return WhatsThis.NAMESPACE.id("entity_default").toString();
    }

    private static DecimalFormat dfCommas = new DecimalFormat("##.#");

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data) {
        IProbeConfig config = ConfigSetup.getRealConfig();

        boolean handled = false;
        for (IEntityDisplayOverride override : WhatsThis.theOneProbeImp.getEntityOverrides()) {
            if (override.overrideStandardInfo(mode, probeInfo, player, world, entity, data)) {
                handled = true;
                break;
            }
        }

        if (!handled) {
            showStandardInfo(mode, probeInfo, entity, config);
        }

        if (entity instanceof LivingEntity livingEntity) {
            if (Tools.show(mode, config.getShowMobHealth())) {
                int health = livingEntity.health;
                int maxHealth = livingEntity.maxHealth;

                probeInfo.progress(health, maxHealth, probeInfo.defaultProgressStyle().lifeBar(true).showText(false).width(150).height(10));

                if (mode == ProbeMode.EXTENDED) {
                    probeInfo.text(LABEL + "Health: " + INFOIMP + health + " / " + maxHealth);
                }

                if (entity instanceof PlayerEntity playerEntity) {
                    if (playerEntity.inventory != null) {
                        int armor = playerEntity.inventory.getTotalArmorDurability();

                        if (armor > 0) {
                            probeInfo.progress(armor, armor, probeInfo.defaultProgressStyle().armorBar(true).showText(false).width(80).height(10));
                        }
                    }
                }
            }

            // TODO: Effects API Integration
//            if (Tools.show(mode, config.getShowMobPotionEffects())) {
//                Collection<PotionEffect> effects = livingBase.getActivePotionEffects();
//                if (!effects.isEmpty()) {
//                    IProbeInfo vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(0xffffffff));
//                    float durationFactor = 1.0f;
//                    for (PotionEffect effect : effects) {
//                        String s1 = STARTLOC + effect.getEffectName() + ENDLOC;
//                        Potion potion = effect.getPotion();
//                        if (effect.getAmplifier() > 0) {
//                            s1 = s1 + " " + STARTLOC + "potion.potency." + effect.getAmplifier() + ENDLOC;
//                        }
//
//                        if (effect.getDuration() > 20) {
//                            s1 = s1 + " (" + getPotionDurationString(effect, durationFactor) + ")";
//                        }
//
//                        if (potion.isBadEffect()) {
//                            vertical.text(ERROR + s1);
//                        } else {
//                            vertical.text(OK + s1);
//                        }
//                    }
//                }
//            }

        } else if (entity instanceof PaintingEntity painting) {
            probeInfo.text(LABEL + "Variant: " + INFO + painting.variant.id);
        }
        
//        else if (entity instanceof EntityItemFrame) {
//            EntityItemFrame itemFrame = (EntityItemFrame)entity;
//            ItemStack stack = itemFrame.getDisplayedItem();
//            if(!stack.isEmpty()) {
//                probeInfo.horizontal(new LayoutStyle().spacing(10).alignment(ElementAlignment.ALIGN_CENTER))
//                        .item(stack, new ItemStyle().width(16).height(16))
//                        .text(INFO + stack.getDisplayName());
//                if (mode == ProbeMode.EXTENDED) {
//                    probeInfo.text(LABEL + "Rotation: " + INFO + itemFrame.getRotation());
//                }
//            } else {
//                probeInfo.text(LABEL + "Empty");
//            }
//        }

        if (Tools.show(mode, config.getAnimalOwnerSetting())) {
            if (entity instanceof WolfEntity wolf) {

                if (wolf.isTamed()) {
                    String ownerName = wolf.getOwnerName();

                    if (ownerName == null || ownerName.isEmpty()) {
                        probeInfo.text(WARNING + "Unknown owner");
                    } else {
                        probeInfo.text(LABEL + "Owned by: " + INFO + ownerName);
                    }
                } else {
                    probeInfo.text(LABEL + "Tameable");
                }

            }
        }
    }

//    public static String getPotionDurationString(PotionEffect effect, float factor) {
//        if (effect.getDuration() == 32767) {
//            return "**:**";
//        } else {
//            int i = MathHelper.floor(effect.getDuration() * factor);
//            return ticksToElapsedTime(i);
//        }
//    }

    public static String ticksToElapsedTime(int ticks) {
        int i = ticks / 20;
        int j = i / 60;
        i = i % 60;
        return i < 10 ? j + ":0" + i : j + ":" + i;
    }


    public static void showStandardInfo(ProbeMode mode, IProbeInfo probeInfo, Entity entity, IProbeConfig config) {
        String modid = Tools.getModName(entity);

        if (Tools.show(mode, config.getShowModName())) {
            probeInfo.horizontal()
                    .entity(entity)
                    .vertical()
                    .text(NAME + EntityRegistry.getId(entity))
                    // TODO : Proper Entity Names
                    .text(MODNAME + modid);
        } else {
            probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                    .entity(entity)
                    .text(NAME + EntityRegistry.getId(entity));
        }
    }
}
