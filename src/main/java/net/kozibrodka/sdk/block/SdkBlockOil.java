package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.TextureListener;
import net.kozibrodka.sdk_api.ingame.mod_SdkBase;
import net.kozibrodka.sdk_api.ingame.mod_SdkGuns;
import net.kozibrodka.sdk_api.utils.SdkTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.model.block.BlockWithWorldRenderer;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class SdkBlockOil extends TemplateBlock implements BlockWithWorldRenderer
{

    public SdkBlockOil(Identifier iid)
    {
        super(iid, Material.PISTON_BREAKABLE);
//        renderType = k;
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        setTickRandomly(true);
        BLOCKS_IGNORE_META_UPDATE[id] = true; //?
    }

    public Box getCollisionShape(World world, int i, int j, int k)
    {
        return null;
    }

    public boolean isOpaque()
    {
        return false;
    }

    public boolean isFullCube()
    {
        return false;
    }

    public int getTickRate()
    {
        return 100;
    }

    public void onTick(World world, int i, int j, int k, Random random)
    {
        int l = world.getBlockMeta(i, j, k);
        int i1 = world.getBlockId(i, j + 1, k);
        if(!setFireAbove(world, i, j, k, l) && l > 0)
        {
            if(--l == 0)
            {
                if(i1 == Block.FIRE.id)
                {
                    world.setBlockWithoutNotifyingNeighbors(i, j + 1, k, 0);
                }
                world.setBlock(i, j, k, 0);
            } else
            {
                world.setBlockMeta(i, j, k, l);
            }
        }
        if(l >= 13)
        {
            world.scheduleBlockUpdate(i, j, k, id, 1);
        } else
        {
            world.scheduleBlockUpdate(i, j, k, id, getTickRate());
        }
    }

    private boolean setFireAbove(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockId(i, j + 1, k);
        if(l > 0 && i1 == 0)
        {
            world.setBlock(i, j + 1, k, Block.FIRE.id);
            return true;
        }
        if(i1 == Block.FIRE.id)
        {
            world.setBlockMeta(i, j + 1, k, 15);
        }
        return false;
    }

    public boolean canPlaceAt(World world, int i, int j, int k)
    {
        return world.method_1783(i, j - 1, k);
    }

    private void notifyOilNeighborsOfNeighborChange(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j, k) != id)
        {
            return;
        } else
        {
            world.notifyNeighbors(i, j, k, id);
            world.notifyNeighbors(i - 1, j, k, id);
            world.notifyNeighbors(i + 1, j, k, id);
            world.notifyNeighbors(i, j, k - 1, id);
            world.notifyNeighbors(i, j, k + 1, id);
            world.notifyNeighbors(i, j - 1, k, id);
            world.notifyNeighbors(i, j + 1, k, id);
            return;
        }
    }

    public void onPlaced(World world, int i, int j, int k)
    {
        super.onPlaced(world, i, j, k);
        if(world.isRemote)
        {
            return;
        }
        world.notifyNeighbors(i, j + 1, k, id);
        world.notifyNeighbors(i, j - 1, k, id);
        notifyOilNeighborsOfNeighborChange(world, i - 1, j, k);
        notifyOilNeighborsOfNeighborChange(world, i + 1, j, k);
        notifyOilNeighborsOfNeighborChange(world, i, j, k - 1);
        notifyOilNeighborsOfNeighborChange(world, i, j, k + 1);
        if(world.method_1783(i - 1, j, k))
        {
            notifyOilNeighborsOfNeighborChange(world, i - 1, j + 1, k);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i - 1, j - 1, k);
        }
        if(world.method_1783(i + 1, j, k))
        {
            notifyOilNeighborsOfNeighborChange(world, i + 1, j + 1, k);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i + 1, j - 1, k);
        }
        if(world.method_1783(i, j, k - 1))
        {
            notifyOilNeighborsOfNeighborChange(world, i, j + 1, k - 1);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i, j - 1, k - 1);
        }
        if(world.method_1783(i, j, k + 1))
        {
            notifyOilNeighborsOfNeighborChange(world, i, j + 1, k + 1);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i, j - 1, k + 1);
        }
        checkForFire(world, i, j, k);
    }

    public void onBreak(World world, int i, int j, int k)
    {
        super.onBreak(world, i, j, k);
        if(world.isRemote)
        {
            return;
        }
        world.notifyNeighbors(i, j + 1, k, id);
        world.notifyNeighbors(i, j - 1, k, id);
        notifyOilNeighborsOfNeighborChange(world, i - 1, j, k);
        notifyOilNeighborsOfNeighborChange(world, i + 1, j, k);
        notifyOilNeighborsOfNeighborChange(world, i, j, k - 1);
        notifyOilNeighborsOfNeighborChange(world, i, j, k + 1);
        if(world.method_1783(i - 1, j, k))
        {
            notifyOilNeighborsOfNeighborChange(world, i - 1, j + 1, k);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i - 1, j - 1, k);
        }
        if(world.method_1783(i + 1, j, k))
        {
            notifyOilNeighborsOfNeighborChange(world, i + 1, j + 1, k);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i + 1, j - 1, k);
        }
        if(world.method_1783(i, j, k - 1))
        {
            notifyOilNeighborsOfNeighborChange(world, i, j + 1, k - 1);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i, j - 1, k - 1);
        }
        if(world.method_1783(i, j, k + 1))
        {
            notifyOilNeighborsOfNeighborChange(world, i, j + 1, k + 1);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i, j - 1, k + 1);
        }
    }

    public void onBlockBreakStart(World world, int i, int j, int k, PlayerEntity entityplayer)
    {
        setFireAbove(world, i, j, k, world.getBlockMeta(i, j, k));
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(world.isRemote)
        {
            return;
        }
        int i1 = world.getBlockMeta(i, j, k);
        boolean flag = canPlaceAt(world, i, j, k);
        if(!flag)
        {
            dropStacks(world, i, j, k, i1);
            world.setBlock(i, j, k, 0);
        } else
        {
            checkForFire(world, i, j, k);
        }
        super.neighborUpdate(world, i, j, k, l);
    }

    public boolean checkForFire(World world, int i, int j, int k)
    {
        if(world.getBlockMeta(i, j, k) == 0)
        {
            for(int l = i - 1; l <= i + 1; l++)
            {
                for(int i1 = j - 1; i1 <= j + 1; i1++)
                {
                    for(int j1 = k - 1; j1 <= k + 1; j1++)
                    {
                        int k1 = Math.abs(i - l) + Math.abs(k - j1);
                        if((k1 != 0 || i1 == 0) && (k1 != 1 || i1 < 0))
                        {
                            continue;
                        }
                        int l1 = world.getBlockId(l, i1, j1);
                        if(l1 != Block.FIRE.id && (l1 != id || world.getBlockMeta(l, i1, j1) <= 0))
                        {
                            continue;
                        }
                        world.setBlockMeta(i, j, k, 15);
                        setFireAbove(world, i, j, k, 15);
                        world.scheduleBlockUpdate(i, j, k, id, 1);
                        for(int i2 = i - 1; i2 <= i + 1; i2++)
                        {
                            for(int j2 = k - 1; j2 <= k + 1; j2++)
                            {
                                int k2 = Math.abs(i - l) + Math.abs(k - j1);
                                if(k2 == 1 && world.getBlockId(i2, i1 - 1, j2) == id)
                                {
                                    onNeighborBlockChange(world, i2, i1 - 1, j2, id);
                                }
                            }

                        }

                        return true;
                    }

                }

            }

        }
        return false;
    }

    public int getDroppedItemId(int i, Random random)
    {
        return 0;
    }

    public boolean onUse(World world, int i, int j, int k, PlayerEntity entityplayer)
    {
        if(entityplayer.inventory.getSelectedItem() != null && entityplayer.inventory.getSelectedItem().itemId == Item.BUCKET.id)
        {
            entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = new ItemStack(ItemListener.itemOil);
            mod_SdkBase.setItemDamage(entityplayer.inventory.getSelectedItem(), ItemListener.itemOil.getMaxDamage() - 1); //TODO
            world.setBlock(i, j, k, 0);
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public boolean renderWorld(BlockRenderManager renderblocks, BlockView iblockaccess, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int l = TextureListener.oil_juction;
//        if(renderblocks.overrideBlockTexture >= 0)
//        {
//            l = renderblocks.overrideBlockTexture;
//        }
        float f = getLuminance(iblockaccess, i, j, k);
        tessellator.color(f, f, f);


//        int i1 = (l & 0xf) << 4;
//        int j1 = l & 0xf0;
//        double d = (float)i1 / 256F;
//        double d1 = ((float)i1 + 15.99F) / 256F;
//        double d2 = (float)j1 / 256F;
//        double d3 = ((float)j1 + 15.99F) / 256F;

        Atlas.Sprite atlasTX =  Atlases.getTerrain().getTexture(l);
        double d = atlasTX.getStartU();
        double d1 = atlasTX.getEndU();
        double d2 = atlasTX.getStartV();
        double d3 = atlasTX.getEndV();

        float f1 = 0.0F;
        float f2 = 0.03125F/16;
        boolean flag = iblockaccess.getBlockId(i - 1, j, k) == id || !iblockaccess.shouldSuffocate(i - 1, j, k) && iblockaccess.getBlockId(i - 1, j - 1, k) == id;
        boolean flag1 = iblockaccess.getBlockId(i + 1, j, k) == id || !iblockaccess.shouldSuffocate(i + 1, j, k) && iblockaccess.getBlockId(i + 1, j - 1, k) == id;
        boolean flag2 = iblockaccess.getBlockId(i, j, k - 1) == id || !iblockaccess.shouldSuffocate(i, j, k - 1) && iblockaccess.getBlockId(i, j - 1, k - 1) == id;
        boolean flag3 = iblockaccess.getBlockId(i, j, k + 1) == id || !iblockaccess.shouldSuffocate(i, j, k + 1) && iblockaccess.getBlockId(i, j - 1, k + 1) == id;
        if(!iblockaccess.shouldSuffocate(i, j + 1, k))
        {
            if(iblockaccess.shouldSuffocate(i - 1, j, k) && iblockaccess.getBlockId(i - 1, j + 1, k) == id)
            {
                flag = true;
            }
            if(iblockaccess.shouldSuffocate(i + 1, j, k) && iblockaccess.getBlockId(i + 1, j + 1, k) == id)
            {
                flag1 = true;
            }
            if(iblockaccess.shouldSuffocate(i, j, k - 1) && iblockaccess.getBlockId(i, j + 1, k - 1) == id)
            {
                flag2 = true;
            }
            if(iblockaccess.shouldSuffocate(i, j, k + 1) && iblockaccess.getBlockId(i, j + 1, k + 1) == id)
            {
                flag3 = true;
            }
        }
        //TODO FIX
        float f3 = 0.3125F/16;
        float f4 = i + 0;
        float f5 = i + 1;
        float f6 = k + 0;
        float f7 = k + 1;
        float f16 = 256;
        byte byte0 = 0;
        if((flag || flag1) && !flag2 && !flag3)
        {
            byte0 = 1;
        }
        if((flag2 || flag3) && !flag1 && !flag)
        {
            byte0 = 2;
        }
        if(byte0 != 0)
        {
            int k1 = TextureListener.oil_line;
//            i1 = (k1 & 0xf) << 4;
//            j1 = k1 & 0xf0;
//            d = (float)i1 / 256F;
//            d1 = ((float)i1 + 15.99F) / 256F;
//            d2 = (float)j1 / 256F;
//            d3 = ((float)j1 + 15.99F) / 256F;

             atlasTX =  Atlases.getTerrain().getTexture(k1);
             d = atlasTX.getStartU();
             d1 = atlasTX.getEndU();
             d2 = atlasTX.getStartV();
             d3 = atlasTX.getEndV();
        }
        if(byte0 == 0)
        {
            if(flag1 || flag2 || flag3 || flag)
            {
                if(!flag)
                {
                    f4 += f3;
                }
                if(!flag)
                {
                    d += f3 / f16;
                }
                if(!flag1)
                {
                    f5 -= f3;
                }
                if(!flag1)
                {
                    d1 -= f3 / f16;
                }
                if(!flag2)
                {
                    f6 += f3;
                }
                if(!flag2)
                {
                    d2 += f3 / f16;
                }
                if(!flag3)
                {
                    f7 -= f3;
                }
                if(!flag3)
                {
                    d3 -= f3 / f16;
                }
            }
            tessellator.vertex(f5 + f1, (float)j + f2, f7 + f1, d1, d3);
            tessellator.vertex(f5 + f1, (float)j + f2, f6 - f1, d1, d2);
            tessellator.vertex(f4 - f1, (float)j + f2, f6 - f1, d, d2);
            tessellator.vertex(f4 - f1, (float)j + f2, f7 + f1, d, d3);
        }
        if(byte0 == 1)
        {
            tessellator.vertex(f5 + f1, (float)j + f2, f7 + f1, d1, d3);
            tessellator.vertex(f5 + f1, (float)j + f2, f6 - f1, d1, d2);
            tessellator.vertex(f4 - f1, (float)j + f2, f6 - f1, d, d2);
            tessellator.vertex(f4 - f1, (float)j + f2, f7 + f1, d, d3);
        }
        if(byte0 == 2)
        {
            tessellator.vertex(f5 + f1, (float)j + f2, f7 + f1, d1, d3);
            tessellator.vertex(f5 + f1, (float)j + f2, f6 - f1, d, d3);
            tessellator.vertex(f4 - f1, (float)j + f2, f6 - f1, d, d2);
            tessellator.vertex(f4 - f1, (float)j + f2, f7 + f1, d1, d2);
        }
        if(!iblockaccess.shouldSuffocate(i, j + 1, k))
        {
            if(byte0 == 0)
            {
//                d = (float)(i1 + 16) / 256F;
//                d1 = ((float)(i1 + 16) + 15.99F) / 256F;
//                d2 = (float)j1 / 256F;
//                d3 = ((float)j1 + 15.99F) / 256F;
                atlasTX =  Atlases.getTerrain().getTexture(TextureListener.oil_line);
                d = atlasTX.getStartU();
                d1 = atlasTX.getEndU();
                d2 = atlasTX.getStartV();
                d3 = atlasTX.getEndV();
            }
            if(iblockaccess.shouldSuffocate(i - 1, j, k) && iblockaccess.getBlockId(i - 1, j + 1, k) == id)
            {
                tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d1, d2);
                tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d2);
                tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d, d3);
                tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d3);
            }
            if(iblockaccess.shouldSuffocate(i + 1, j, k) && iblockaccess.getBlockId(i + 1, j + 1, k) == id)
            {
                tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d3);
                tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d1, d3);
                tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d2);
                tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d, d2);
            }
            if(iblockaccess.shouldSuffocate(i, j, k - 1) && iblockaccess.getBlockId(i, j + 1, k - 1) == id)
            {
                tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)k + f2, d, d3);
                tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)k + f2, d1, d3);
                tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)k + f2, d1, d2);
                tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)k + f2, d, d2);
            }
            if(iblockaccess.shouldSuffocate(i, j, k + 1) && iblockaccess.getBlockId(i, j + 1, k + 1) == id)
            {
                tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d2);
                tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d2);
                tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d3);
                tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d3);
            }
        }
        int l1 = iblockaccess.getBlockMeta(i, j, k);
        if(l1 != 0)
        {
            renderblocks.renderFire(Block.FIRE, i, j, k);
        }
        return true;
    }
}
