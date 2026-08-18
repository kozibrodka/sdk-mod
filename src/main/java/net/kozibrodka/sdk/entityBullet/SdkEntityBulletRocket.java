package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityShell;
import net.kozibrodka.sdk_api.utils.SdkExplosion;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntityBulletRocket extends SdkEntityShell implements EntitySpawnDataProvider {

    public SdkEntityBulletRocket(World world)
    {
        super(world);
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    public SdkEntityBulletRocket(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setBoundingBoxSpacing(0.25F, 0.25F); //todo czy wielkość kuli będzie dobra na serverze?? nie ma setPosition...
        bulletDrop = ((SdkItemGun) ItemListener.itemGunRocketLauncher).bulletDrop;
        exploPower = ((SdkItemGun) ItemListener.itemGunRocketLauncher).explosionPower;
    }

    public SdkEntityBulletRocket(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3, float f4) {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    @Override
    public void addMoveParticle(int tick) {
        if(tick % 2 == 0) {
            double d = 0.625D;
            double d1 = Math.sqrt(velocityX * velocityX + velocityZ * velocityZ + velocityY * velocityY);
            world.addParticle("smoke", x - (velocityX / d1) * d, y - (velocityY / d1) * d, z - (velocityZ / d1) * d, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public String getServerExploSound() {
        return ((SdkItemGun) ItemListener.itemGunRocketLauncher).explosionSound;
    }

    @Override
    public void playServerSound(World world) {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunRocketLauncher).firingSound, ((SdkItemGun)ItemListener.itemGunRocketLauncher).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "BulletRocket");
    }

}
