package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class HookListener {
    public static Map<PlayerEntity, SdkEntityGrapplingHook> grapplingHooks = new HashMap<>();
}
