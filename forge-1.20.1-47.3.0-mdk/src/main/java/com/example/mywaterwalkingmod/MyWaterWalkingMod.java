package com.example.mywaterwalkingmod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod(MyWaterWalkingMod.MODID)
@EventBusSubscriber(modid = MyWaterWalkingMod.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class MyWaterWalkingMod {

    public static final String MODID = "mywaterwalkingmod";

    public MyWaterWalkingMod() {
        // Constructor vacío
    }

    // Evento para configuración inicial del cliente
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Configuración del cliente, si es necesario
    }

    // Evento para controlar el "tick" del jugador
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Solo ejecuta en el lado del cliente
        if (event.player.getCommandSenderWorld().isClientSide) {
            Player player = event.player;
            BlockPos pos = player.blockPosition().below(); // Obtener la posición del bloque debajo

            // Asegurarse de que el mundo no sea nulo
            if (player.getCommandSenderWorld() != null) {
                BlockState blockBelow = player.getCommandSenderWorld().getBlockState(pos);

                // Verifica si el bloque debajo es agua
                if (blockBelow.is(Blocks.WATER)) {
                    // Activa la capacidad de volar para caminar sobre el agua
                    player.getAbilities().mayfly = true;
                    player.setNoGravity(true); // Evita la gravedad mientras está sobre el agua
                    player.fallDistance = 0.0F;  // Evita el daño por caída
                } else {
                    // Desactiva volar si no está sobre agua
                    player.getAbilities().mayfly = player.isCreative(); // Mantiene 'mayfly' solo en modo creativo
                    player.getAbilities().flying = false;  // Desactiva volar suavemente
                    player.setNoGravity(false); // Restaura la gravedad cuando no está sobre agua
                }
                // Asegura que las habilidades del jugador se actualicen
                player.onUpdateAbilities();
            }
        }
    }

    // Evento cuando el servidor se está iniciando
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Código para inicializar el servidor, si es necesario
    }

    // Evento cuando el servidor se está deteniendo
    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        // Código para limpiar recursos o desregistrar cosas, si es necesario
    }
}
