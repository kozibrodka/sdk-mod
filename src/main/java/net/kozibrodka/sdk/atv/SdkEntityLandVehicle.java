package net.kozibrodka.sdk.atv;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.network.CarCrashPacket;
import net.kozibrodka.sdk.network.CarLoadPacket;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.TriState;

import java.util.List;

public abstract class SdkEntityLandVehicle extends Entity
{

    public SdkEntityLandVehicle(World world)
    {
        super(world);
        lastTurnSpeed = 0.0D;
        lastOnGround = true;
        prevMotionX = 0.0D;
        prevMotionY = 0.0D;
        prevMotionZ = 0.0D;
        lastCollidedEntity = null;
        ACCEL_FORWARD_STOPPED = 0.02D;
        ACCEL_FORWARD_FULL = 0.005D;
        ACCEL_BACKWARD_STOPPED = 0.01D;
        ACCEL_BACKWARD_FULL = 0.0025D;
        ACCEL_BRAKE = 0.04D;
        TURN_SPEED_STOPPED = 10D;
        TURN_SPEED_FULL = 2D;
        MAX_SPEED = 0.75D;
        FALL_SPEED = 0.06D;
        ROTATION_PITCH_DELTA_MAX = 10D;
        SPEED_MULT_WATER = 0.9D;
        SPEED_MULT_UNMOUNTED = 0.95D;
        SPEED_MULT_DECEL = 0.95D;
        STOP_SPEED = 0.01D;
        TURN_SPEED_RENDER_MULT = 2D;
        COLLISION_SPEED_MIN = 0.5D;
        COLLISION_DAMAGE_ENTITY = 10;
        COLLISION_DAMAGE_SELF = 10;
        MAX_HEALTH = 100;
        COLLISION_DAMAGE = true;
        COLLISION_FLIGHT_PLAYER = true;
        COLLISION_FLIGHT_ENTITY = true;
        blocksSameBlockSpawning = true;  //preventEntitySpawning
        standingEyeHeight = height / 2.0F;
        health = MAX_HEALTH;
    }

    public SdkEntityLandVehicle(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
        prevX = d;
        prevY = d1;
        prevZ = d2;
    }

    /// Data-Tracking
    @Override
    protected void initDataTracker()
    {
        dataTracker.startTracking(16, (byte) 0); //onGround
        dataTracker.startTracking(17, 0); //Yaw
        dataTracker.startTracking(18, 0); //Gun A
        dataTracker.startTracking(19, 0); //Gun B

        dataTracker.startTracking(29, (byte) 0); //HEALTH
    }

    //GROUND
    public boolean getOnGround()
    {
        return (dataTracker.getByte(16) & 1) != 0;
    }
    public void setOnGround(boolean flag)
    {
        if(flag)
        {
            dataTracker.set(16, (byte) 1);
        } else
        {
            dataTracker.set(16, (byte) 0);
        }
    }

    //YAW
    public void setClientYaw(float age)
    {
        dataTracker.set(17, Float.floatToRawIntBits(age));
    }
    public float getClientYaw()
    {
        return Float.intBitsToFloat(dataTracker.getInt(17));
    }

    ///

    /// Client interpolation and pos/rot
    @Environment(EnvType.CLIENT)
    private int clientInterpolationSteps;
    @Environment(EnvType.CLIENT)
    private double clientX;
    @Environment(EnvType.CLIENT)
    private double clientY;
    @Environment(EnvType.CLIENT)
    private double clientZ;
    @Environment(EnvType.CLIENT)
    private double clientYaw;
    @Environment(EnvType.CLIENT)
    private double clientPitch;
    @Environment(EnvType.CLIENT)
    private double clientPrevY;

    /// Client velocity
    @Environment(EnvType.CLIENT)
    public double clientVelocityX;
    @Environment(EnvType.CLIENT)
    public double clientVelocityY;
    @Environment(EnvType.CLIENT)
    public double clientVelocityZ;
    ///

    public double getClientSpeed()
    {
        return Math.sqrt(clientVelocityX * clientVelocityX + clientVelocityZ * clientVelocityZ);
    }

    public double getClientTurnSpeedRender()
    {
        return scaleOnClientSpeed(TURN_SPEED_STOPPED, TURN_SPEED_FULL);
    }

    public double scaleOnClientSpeed(double d, double d1)
    {
        return d - (d - d1) * (getClientSpeed() / MAX_SPEED);
    }

    @Override
    public Box getCollisionAgainstShape(Entity other) {
        if (world.isRemote) {
            return null;
        }
        return other == passenger ? null : other.boundingBox;
//        return other.boundingBox;
    }

    public void remoteTick(){
        if(clientInterpolationSteps == 0){
            return;
        }
        onGround = getOnGround();
        double xt = x + (clientX - x) / clientInterpolationSteps;
        double yt = y + (clientY - y) / 2;
        double zt = z + (clientZ - z) / clientInterpolationSteps;

            boolean flag1 = true;
            if(getClientSpeed() != 0.0D)
            {
//                double d2 = (clientYaw * 3.1415926535897931D) / 180D;
                double d2 = (yaw * 3.1415926535897931D) / 180D;
                double d6 = Math.cos(d2);
                flag1 = -d6 > 0.0D && clientVelocityX > 0.0D || -d6 < 0.0D && clientVelocityX < 0.0D;
            }
            int i = flag1 ? 1 : -1;
            if(onGround && lastOnClientGround)
            {
                if(clientPrevY - clientY > 0.2D)
                {
                    pitch = 45 * i;
                } else
                if(clientPrevY - clientY < -0.2D)
                {
                    pitch = -45 * i;
                } else
                {
                    pitch = 0.0F;
                }
            } else
            {
                setRotationPitch(Math.max(Math.min((float)((-90D * clientVelocityY) / getClientSpeed()) * (float)i, 90F), -90F) / 2.0F);
            }
            lastOnClientGround = onGround;
            clientPrevY = clientY;

        float merkar2 = getClientYaw();
        float angleYaw = merkar2 % 360.0F;

        float prevRYaw = yaw;
        double yrd = angleYaw - yaw;
        while (yrd < 180F) yrd += 360F;
        while (yrd > 180.0F) yrd -= 360.0F;
        yaw += (float) (yrd / (clientInterpolationSteps - 2)); /// 0

        double pyrd1 = yaw - prevRYaw; //
        setPosition(xt, yt, zt);
        setRotation(yaw, pitch);
        clientInterpolationSteps--;

        double d4 = getClientTurnSpeedRender();
        if(pyrd1 == 0.0D){ // yrd
            lastTurnSpeed = 0.0D;
        }
        if(pyrd1 < 0.0D){ // yrd
            lastTurnSpeed = d4 * -1;
        }
        if(pyrd1 > 0.0D){ // yrd
            lastTurnSpeed = d4;
        }

        health = dataTracker.getByte(29);
        gunIdA = dataTracker.getInt(18);
        gunIdB = dataTracker.getInt(19);
    }


    @Override
    @Environment(EnvType.CLIENT)
    public void setPositionAndAnglesAvoidEntities(double x, double y, double z, float pitch, float yaw, int interpolationSteps) {
        clientX = x;
        clientY = y;
        clientZ = z;
        clientYaw = pitch;
        clientPitch = yaw;
        clientInterpolationSteps = interpolationSteps + 1;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setVelocityClient(double x, double y, double z) {
        clientVelocityX = x;
        clientVelocityY = y;
        clientVelocityZ = z;
    }
    ///

    @Override
    public Box getBoundingBox()
    {
        return boundingBox;
    }

    @Override
    public boolean damage(Entity entity, int i)
    {
        if(world.isRemote){
            return false;
        }
        if(MAX_HEALTH != -1)
        {
            world.broadcastEntityEvent(this, (byte)6);
            onHurt();
            health -= i;
            if(health <= 0)
            {
                onDeath();
            }
        }
        return true;
    }

    public void onHurt()
    {
    }

    public void onDeath()
    {
        markDead();
    }

    @Override
    public boolean isCollidable() //canBeCollidedWith
    {
        return !dead;
    }

    @Override
    public float getShadowRadius()
    {
        return 0.0F;
    }

    @Override
    public boolean interact(PlayerEntity entityplayer)
    {
        if(passenger != null && (passenger instanceof PlayerEntity) && passenger != entityplayer)
        {
            return true;
        }
        if(!world.isRemote)
        {
            entityplayer.setVehicle(this);
        }
        return true;
    }

    @Override
    public void tick()
    {
        super.tick();
        if(world.isRemote){ //&& passenger != null
            if(!receivedP){
                receivedP = true;
                PacketHelper.send(new CarLoadPacket(this.id));
            }
            remoteTick();
            return;
        }
        prevX = x;
        prevY = y;
        prevZ = z;
        if(getSpeed() > 0.0D)
        {
            double d = getMotionYaw();
            double d1 = (double)yaw - d;
            projectMotion(d1);
        }
        boolean flag = false;
        boolean flag1 = true;
        if(getSpeed() != 0.0D)
        {
            double d2 = ((double)yaw * 3.1415926535897931D) / 180D;
            double d6 = Math.cos(d2);
            flag1 = -d6 > 0.0D && velocityX > 0.0D || -d6 < 0.0D && velocityX < 0.0D;
        }
        double dc4 = 0.0D;
        if(onGround)
        {
            if(passenger != null)
            {
                double d4 = 0.0D;
                if(getSpeed() != 0.0D)
                {
                    if(this.clientLEFT)
                    {
                        d4 = -getTurnSpeed() * (double)(flag1 ? 1 : -1);
                    } else
                    if(this.clientRIGHT)
                    {
                        d4 = getTurnSpeed() * (double)(flag1 ? 1 : -1);
                    }
                    if(d4 != 0.0D)
                    {
                        yaw += (float) d4; ///CAST addon
                        projectMotion(d4);
                    }
                    lastTurnSpeed = d4 * (double)(flag1 ? 1 : -1);
                }
                dc4 = d4;
                double d5 = 0.0D;
                if(passenger != null)
                {
                    if(this.clientFORWARD)
                    {
                        d5 = -(flag1 ? getAccelForward() : ACCEL_BRAKE);
                        flag = true;
                    } else
                    if(this.clientBACK)
                    {
                        d5 = flag1 ? ACCEL_BRAKE : getAccelBackward();
                        flag = true;
                    }
                }
                if(d5 != 0.0D)
                {
                    double d7 = ((double)yaw * 3.1415926535897931D) / 180D;
                    double d8 = Math.cos(d7);
                    double d9 = Math.sin(d7);
                    velocityX += d5 * d8;
                    velocityZ += d5 * d9;
                }
            }
            if(!flag)
            {
                multiplySpeed(SPEED_MULT_DECEL);
            }
            if(passenger == null)
            {
                multiplySpeed(SPEED_MULT_UNMOUNTED);
            }
            double d3 = getSpeed();
            if(d3 > MAX_SPEED)
            {
                multiplySpeed(MAX_SPEED / d3);
            }
        }
        if(checkWaterCollisions()) //handle water mv
        {
            multiplySpeed(SPEED_MULT_WATER);
        }
        if(!flag && getSpeed() < STOP_SPEED)
        {
            multiplySpeed(0.0D);
        }
        move(velocityX, velocityY, velocityZ);
        int i = flag1 ? 1 : -1;
        if(onGround && lastOnGround)
        {
            if(prevY - y > 0.01D)
            {
                pitch = 45 * i;
            } else
            if(prevY - y < -0.01D)
            {
                pitch = -45 * i;
            } else
            {
                pitch = 0.0F;
            }
            velocityY -= 0.001D;
        } else
        {
            setRotationPitch(Math.max(Math.min((float)((-90D * velocityY) / getSpeed()) * (float)i, 90F), -90F) / 2.0F);
            velocityY = y - prevY - FALL_SPEED;
        }
        lastOnGround = onGround;
        List list = world.getEntities(this, boundingBox.expand(0.20000000000000001D, 0.0D, 0.20000000000000001D));
        if(list != null && list.size() > 0)
        {
            for(int j = 0; j < list.size(); j++)
            {
                Entity entity = (Entity)list.get(j);
                if(entity != passenger && entity.isPushable())
                {
                    handleCollision(entity);
                }
            }

        }
        if(passenger != null && getPrevSpeed() - getSpeed() > COLLISION_SPEED_MIN)
        {
            if(lastCollidedEntity != null)
            {
                if(COLLISION_FLIGHT_ENTITY)
                {
                    lastCollidedEntity.addVelocity(prevMotionX, prevMotionY + 1.0D, prevMotionZ);
                }
                if(COLLISION_DAMAGE)
                {
                    lastCollidedEntity.damage(passenger, COLLISION_DAMAGE_ENTITY);
                }
            }
            if(COLLISION_DAMAGE)
            {
                damage(lastCollidedEntity, COLLISION_DAMAGE_SELF);
            }
            if(COLLISION_FLIGHT_PLAYER)
            {
                PlayerEntity entityplayerP = (PlayerEntity)passenger;
                passenger.addVelocity(prevMotionX, prevMotionY + 1.0D, prevMotionZ);
                passenger.setVehicle(null);
                world.broadcastEntityEvent(this, (byte)10);
                if(SdkEnvTool.isEnvServ()) {
                    PacketHelper.sendTo(entityplayerP, new CarCrashPacket(prevMotionX, prevMotionY + 1.0D, prevMotionZ));
                }
            }
        }
        lastCollidedEntity = null;
        prevMotionX = velocityX;
        prevMotionY = velocityY;
        prevMotionZ = velocityZ;
        if(passenger != null && (passenger.dead || !passenger.isAlive()))
        {
            passenger.setVehicle(null);
            world.broadcastEntityEvent(this, (byte)10);
        }
        ///
        if(!world.isRemote){
            setOnGround(this.onGround);
            setClientYaw(yaw);
            this.dataTracker.set(29, (byte) health);
        }
    }

    public double getMotionYaw()
    {
        double d;
        if(velocityX >= 0.0D && velocityZ >= 0.0D)
        {
            d = Math.atan(Math.abs(velocityZ / velocityX)) * 57.295779513082323D + 180D;
        } else
        if(velocityX >= 0.0D && velocityZ <= 0.0D)
        {
            d = Math.atan(Math.abs(velocityX / velocityZ)) * 57.295779513082323D + 90D;
        } else
        if(velocityX <= 0.0D && velocityZ >= 0.0D)
        {
            d = Math.atan(Math.abs(velocityX / velocityZ)) * 57.295779513082323D + 270D;
        } else
        {
            d = Math.atan(Math.abs(velocityZ / velocityX)) * 57.295779513082323D;
        }
        return d;
    }

    public void projectMotion(double d)
    {
        double d1 = (d * 3.1415926535897931D) / 180D;
        double d2 = Math.cos(d1) * velocityX - Math.sin(d1) * velocityZ;
        double d3 = Math.sin(d1) * velocityX + Math.cos(d1) * velocityZ;
        double d4 = getSpeed();
        double d5 = d4 * Math.cos(d1);
        d2 *= d5 / d4;
        d3 *= d5 / d4;
        velocityX = d2;
        velocityZ = d3;
    }

    public double getSpeed()
    {
        return Math.sqrt(velocityX * velocityX + velocityZ * velocityZ);
    }

    public void multiplySpeed(double d)
    {
        velocityX *= d;
        velocityZ *= d;
    }

    public double getTurnSpeed()
    {
        return scaleOnSpeed(TURN_SPEED_STOPPED, TURN_SPEED_FULL);
    }

    public double getAccelForward()
    {
        return scaleOnSpeed(ACCEL_FORWARD_STOPPED, ACCEL_FORWARD_FULL);
    }

    public double getAccelBackward()
    {
        return scaleOnSpeed(ACCEL_BACKWARD_STOPPED, ACCEL_BACKWARD_FULL);
    }

    public double scaleOnSpeed(double d, double d1)
    {
        return d - (d - d1) * (getSpeed() / MAX_SPEED);
    }

    public void handleCollision(Entity entity)
    {
        entity.onCollision(this);  //apply entity collision
        if(entity.passenger != this && entity.vehicle != this)
        {
            lastCollidedEntity = entity;
        }
    }

    public void setRotationPitch(float f)
    {
        if((double)(f - pitch) > ROTATION_PITCH_DELTA_MAX)
        {
            pitch += ROTATION_PITCH_DELTA_MAX;
        } else
        if((double)(pitch - f) > ROTATION_PITCH_DELTA_MAX)
        {
            pitch -= ROTATION_PITCH_DELTA_MAX;
        } else
        {
            pitch = f;
        }
    }

    public double getPrevSpeed()
    {
        return Math.sqrt(prevMotionX * prevMotionX + prevMotionZ * prevMotionZ);
    }

    public float getTurnSpeedForRender()
    {
        return (float)(lastTurnSpeed * TURN_SPEED_RENDER_MULT);
    }

    boolean clientFORWARD = false;
    boolean clientBACK = false;
    boolean clientLEFT= false;
    boolean clientRIGHT= false;
    boolean clientUP= false;
    boolean clientDOWN= false;
    boolean clientFIRE= false;

    public int gunIdA = 0;
    public int gunIdB = 0;
    public boolean receivedP = false;
    private double lastTurnSpeed;
    public boolean lastOnGround;
    public boolean lastOnClientGround;
    public int health;
    public double prevMotionX;
    public double prevMotionY;
    public double prevMotionZ;
    public Entity lastCollidedEntity;
    public double ACCEL_FORWARD_STOPPED;
    public double ACCEL_FORWARD_FULL;
    public double ACCEL_BACKWARD_STOPPED;
    public double ACCEL_BACKWARD_FULL;
    public double ACCEL_BRAKE;
    public double TURN_SPEED_STOPPED;
    public double TURN_SPEED_FULL;
    public double MAX_SPEED;
    public double FALL_SPEED;
    public double ROTATION_PITCH_DELTA_MAX;
    public double SPEED_MULT_WATER;
    public double SPEED_MULT_UNMOUNTED;
    public double SPEED_MULT_DECEL;
    public double STOP_SPEED;
    public double TURN_SPEED_RENDER_MULT;
    public double COLLISION_SPEED_MIN;
    public int COLLISION_DAMAGE_ENTITY;
    public int COLLISION_DAMAGE_SELF;
    public int MAX_HEALTH;
    public boolean COLLISION_DAMAGE;
    public boolean COLLISION_FLIGHT_PLAYER;
    public boolean COLLISION_FLIGHT_ENTITY;
}
