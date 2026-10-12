package com.example.whiteknucklemc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

/**
 * Main mod class for White Knuckle Minecraft
 * Implements the core climbing mechanics where players manage stamina and grip while climbing
 */
public class WhiteknuckleMinecraftMod implements ModInitializer {

    // Configuration values for White Knuckle mechanics
    public static final String MOD_ID = "whiteknucklemc";
    public static final String MOD_NAME = "White Knuckle Minecraft";
    public static final String MOD_VERSION = "1.0.0";

    // Key bindings for climbing mechanics
    private static KeyBinding jumpKeyBinding;
    private static KeyBinding sneakKeyBinding;

    // HUD overlay instance
    private static StaminaHudOverlay staminaHudOverlay;

    @Override
    public void onInitialize() {
        // Initialize the mod
        System.out.println("Initializing White Knuckle Minecraft Mod");

        // Initialize key bindings
        jumpKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.whiteknucklemc.jump",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            "category.whiteknucklemc"
        ));

        sneakKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.whiteknucklemc.sneak",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_SHIFT,
            "category.whiteknucklemc"
        ));

        // Initialize and register HUD overlay
        staminaHudOverlay = new StaminaHudOverlay(MinecraftClient.getInstance());
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            if (staminaHudOverlay != null && MinecraftClient.getInstance().player != null) {
                staminaHudOverlay.render(context);
            }
        });

        // Register client tick events for handling player input and updates
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Handle White Knuckle mechanics on each client tick
            handleWhiteKnuckleMechanics(client);
        });
    }

    /**
     * Handles the core White Knuckle climbing mechanics
     * @param client The Minecraft client instance
     */
    private void handleWhiteKnuckleMechanics(MinecraftClient client) {
        // Only proceed if we're in-game and have a player
        if (client.player == null || client.world == null) {
            return;
        }

        PlayerEntity player = client.player;

        // Update climbing mechanics using the ClimbingManager
        ClimbingManager.updateClimbingMechanics(player, client.world, client.getTickDelta());

        // Handle player input for climbing/gripping actions
        handleClimbingInput(player);
    }

    /**
     * Handles player input for climbing and gripping actions
     * @param player The player entity
     */
    private void handleClimbingInput(PlayerEntity player) {
        // Check if jump key is pressed to initiate climbing
        if (jumpKeyBinding.isPressed()) {
            // Jump key handling would be implemented here
            // For now, we rely on the ClimbingManager to detect gripping state
        }

        // Check if sneak key is pressed for gripping
        if (sneakKeyBinding.isPressed()) {
            // Sneak key handling for gripping would be implemented here
            // For now, we rely on the ClimbingManager to detect gripping state
        }
    }
}