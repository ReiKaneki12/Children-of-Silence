package net.esquizo.children_of_silence_mod;

import com.mojang.logging.LogUtils;
import net.esquizo.children_of_silence_mod.Init.CreativeTabInit;
import net.esquizo.children_of_silence_mod.Init.ItemInit;
import net.esquizo.children_of_silence_mod.networking.NetworkingHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ChildrenOfSilence.MOD_ID)
public class ChildrenOfSilence {

    public static final String MOD_ID = "children_of_silence";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChildrenOfSilence(FMLJavaModLoadingContext context){
        IEventBus modEventBus = context.getModEventBus();

        ItemInit.register(modEventBus);
        CreativeTabInit.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        NetworkingHandler.register();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event){

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){

        }
    }
}
