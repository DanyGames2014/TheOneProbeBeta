//package net.mcjty.whatsthis;
//
//import net.mcjty.whatsthis.api.ITheOneProbe;
//import net.mcjty.whatsthis.apiimpl.TheOneProbeImp;
//import net.mcjty.whatsthis.setup.IProxy;
//import net.mcjty.whatsthis.setup.ModSetup;
//import net.minecraft.item.ItemStack;
//
//import java.util.Optional;
//import java.util.function.Function;
//
//public class TheOneProbe {
//    public static final String MODID = "theoneprobe";
//    public static final String VERSION = "1.4.28";
//    public static final String MIN_FORGE11_VER = "13.19.0.2176";
//
//    @SidedProxy(clientSide = "mcjty.theoneprobe.setup.ClientProxy", serverSide = "mcjty.theoneprobe.setup.ServerProxy")
//    public static IProxy proxy;
//    public static ModSetup setup = new ModSetup();
//
//    @Mod.Instance
//    public static TheOneProbe instance;
//
//    public static TheOneProbeImp theOneProbeImp = new TheOneProbeImp();
//
//    @Mod.EventHandler
//    public void preInit(FMLPreInitializationEvent e) {
//        setup.preInit(e);
//        proxy.preInit(e);
//    }
//
//    @Mod.EventHandler
//    public void init(FMLInitializationEvent e) {
//        setup.init(e);
//        proxy.init(e);
//    }
//
//    @Mod.EventHandler
//    public void postInit(FMLPostInitializationEvent e) {
//        setup.postInit(e);
//        proxy.postInit(e);
//    }
//
//    @Mod.EventHandler
//    public void imcCallback(FMLInterModComms.IMCEvent event) {
//        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
//            if (message.key.equalsIgnoreCase("getTheOneProbe")) {
//                Optional<Function<ITheOneProbe, Void>> value = message.getFunctionValue(ITheOneProbe.class, Void.class);
//                if (value.isPresent()) {
//                    value.get().apply(theOneProbeImp);
//                } else {
//                    setup.getLogger().warn("Some mod didn't return a valid result with getTheOneProbe!");
//                }
//            }
//        }
//    }
//}
