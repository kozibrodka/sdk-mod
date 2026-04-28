package net.kozibrodka.sdk.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.kozibrodka.sdk_api.utils.SdkTools;
import net.kozibrodka.sdk_api.utils.SdkToolsRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

@HasTrackingParameters(trackingDistance = 240, updatePeriod = 1200, sendVelocity = TriState.TRUE)
public class SdkEntityParachute extends LivingEntity implements EntitySpawnDataProvider
{

    public SdkEntityParachute(World world)
    {
        super(world);
        justServerSpawned = false;
        texture = "/assets/sdk/stationapi/textures/entity/mobParachute.png";
        standingEyeHeight = 0.0F;
        health = 4;
    }

    public SdkEntityParachute(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
        justServerSpawned = true;
    }

    public SdkEntityParachute(World world, Entity entity)
    {
        this(world);
//        changeLookDirection(entity.yaw, 0.0F);
        entityWearing = entity;
        standingEyeHeight = 0.0F;
        health = 4;
        prevX = x;
        prevY = y;
        prevZ = z;
        if(SdkEnvTool.isEnvServ() && entityWearing instanceof PlayerEntity){
            serverIlusion = true;
        }
        setMotionAndPosition();
    }

    public boolean reveived;

//    @Override
//    @Environment(EnvType.CLIENT)
//    public void setPositionAndAnglesAvoidEntities(double x, double y, double z, float pitch, float yaw, int interpolationSteps) {
//
//        this.standingEyeHeight = 0.0F;
//        this.lerpX = x;
//        this.lerpY = y;
////        entityWearing = getNearestPlayer();
////        if(entityWearing instanceof PlayerEntity spadochroniarz && !SdkToolsRender.isPlayerClient(spadochroniarz)){
////            this.lerpY += 1.62D;
////        }
//        this.lerpZ = z;
//        this.lerpYaw = (double)pitch;
//        this.lerpPitch = (double)yaw;
//        this.bodyTrackingIncrements = interpolationSteps;
//    }

    @Override
    public boolean shouldRender(double d)
    {
        return true;
    }

    @Override
    public void tick()
    {
        if(entityWearing == null)
        {
            if(world.isRemote && !justServerSpawned)
            {
                dead = true;
                return;
            }
            entityWearing = getNearestPlayer();
            justServerSpawned = false;
            if(entityWearing == null)
            {
                return;
            }
        }
        if(SdkTools.onGroundOrInWater(world, entityWearing))
        {
            dead = true;
            return;
        }
        if(entityWearing.velocityY < -0.25D)
        {
            entityWearing.velocityY = -0.25D;
        }
        entityWearing.fallDistance = 0.0F;
        setMotionAndPosition();
    }

    public PlayerEntity getNearestPlayer()
    {
        return getNearestPlayer(x, y, z);
    }

    public PlayerEntity getNearestPlayer(double d, double d1, double d2)
    {
        double d3 = -1D;
        PlayerEntity entityplayer = null;
        for(int i = 0; i < world.entities.size(); i++)
        {
            Entity entity = (Entity)world.entities.get(i);
            if(!(entity instanceof PlayerEntity) || !entity.isAlive())
            {
                continue;
            }
            double d4 = entity.getSquaredDistance(d, d1, d2);
            if(d3 == -1D || d4 < d3)
            {
                d3 = d4;
                entityplayer = (PlayerEntity) entity;
            }
        }

        return entityplayer;
    }

    private void setMotionAndPosition()
    {
        double yOff = 0.0D;
        if(serverIlusion || world.isRemote && entityWearing instanceof PlayerEntity spadoP && !SdkToolsRender.isPlayerClient(spadoP)){
            yOff = 1.62D;
        }
        setPosition(entityWearing.x, entityWearing.y + (double)(entityWearing.height / 2.0F) + yOff, entityWearing.z);
        velocityX = entityWearing.velocityX;
        velocityY = entityWearing.velocityY;
        velocityZ = entityWearing.velocityZ;
        yaw = entityWearing.yaw;
    }

    @Override
    public void onPlayerInteraction(PlayerEntity entityplayer)
    {
    }

    Entity entityWearing;
    boolean justServerSpawned;
    private static final float HEIGHT = 0F;
    private static final float MAX_FALL_SPEED = 0.25F;
    public boolean serverIlusion;

    @Override
    public Identifier getHandlerIdentifier()  {
        return Identifier.of(EntityListener.MOD_ID, "Parachute");
    }
}
