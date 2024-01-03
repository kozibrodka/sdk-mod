package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.TextureListener;
import net.kozibrodka.sdk_api.events.ingame.mod_SdkGuns;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
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
        super(iid, Material.DOODADS);
//        renderType = k;
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        setTicksRandomly(true);
        NO_NOTIFY_ON_META_CHANGE[id] = true; //?
    }

    public Box getCollisionShape(Level world, int i, int j, int k)
    {
        return null;
    }

    public boolean isFullOpaque()
    {
        return false;
    }

    public boolean isFullCube()
    {
        return false;
    }

    public int getTickrate()
    {
        return 100;
    }

    public void onScheduledTick(Level world, int i, int j, int k, Random random)
    {
        int l = world.getTileMeta(i, j, k);
        int i1 = world.getTileId(i, j + 1, k);
        if(!setFireAbove(world, i, j, k, l) && l > 0)
        {
            if(--l == 0)
            {
                if(i1 == BlockBase.FIRE.id)
                {
                    world.setTileInChunk(i, j + 1, k, 0);
                }
                world.setTile(i, j, k, 0);
            } else
            {
                world.setTileMeta(i, j, k, l);
            }
        }
        if(l >= 13)
        {
            world.method_216(i, j, k, id, 1);
        } else
        {
            world.method_216(i, j, k, id, getTickrate());
        }
    }

    private boolean setFireAbove(Level world, int i, int j, int k, int l)
    {
        int i1 = world.getTileId(i, j + 1, k);
        if(l > 0 && i1 == 0)
        {
            world.setTile(i, j + 1, k, BlockBase.FIRE.id);
            return true;
        }
        if(i1 == BlockBase.FIRE.id)
        {
            world.setTileMeta(i, j + 1, k, 15);
        }
        return false;
    }

    public boolean canPlaceAt(Level world, int i, int j, int k)
    {
        return world.isFullOpaque(i, j - 1, k);
    }

    private void notifyOilNeighborsOfNeighborChange(Level world, int i, int j, int k)
    {
        if(world.getTileId(i, j, k) != id)
        {
            return;
        } else
        {
            world.updateAdjacentBlocks(i, j, k, id);
            world.updateAdjacentBlocks(i - 1, j, k, id);
            world.updateAdjacentBlocks(i + 1, j, k, id);
            world.updateAdjacentBlocks(i, j, k - 1, id);
            world.updateAdjacentBlocks(i, j, k + 1, id);
            world.updateAdjacentBlocks(i, j - 1, k, id);
            world.updateAdjacentBlocks(i, j + 1, k, id);
            return;
        }
    }

    public void onBlockPlaced(Level world, int i, int j, int k)
    {
        super.onBlockPlaced(world, i, j, k);
        if(world.isServerSide)
        {
            return;
        }
        world.updateAdjacentBlocks(i, j + 1, k, id);
        world.updateAdjacentBlocks(i, j - 1, k, id);
        notifyOilNeighborsOfNeighborChange(world, i - 1, j, k);
        notifyOilNeighborsOfNeighborChange(world, i + 1, j, k);
        notifyOilNeighborsOfNeighborChange(world, i, j, k - 1);
        notifyOilNeighborsOfNeighborChange(world, i, j, k + 1);
        if(world.isFullOpaque(i - 1, j, k))
        {
            notifyOilNeighborsOfNeighborChange(world, i - 1, j + 1, k);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i - 1, j - 1, k);
        }
        if(world.isFullOpaque(i + 1, j, k))
        {
            notifyOilNeighborsOfNeighborChange(world, i + 1, j + 1, k);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i + 1, j - 1, k);
        }
        if(world.isFullOpaque(i, j, k - 1))
        {
            notifyOilNeighborsOfNeighborChange(world, i, j + 1, k - 1);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i, j - 1, k - 1);
        }
        if(world.isFullOpaque(i, j, k + 1))
        {
            notifyOilNeighborsOfNeighborChange(world, i, j + 1, k + 1);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i, j - 1, k + 1);
        }
        checkForFire(world, i, j, k);
    }

    public void onBlockRemoved(Level world, int i, int j, int k)
    {
        super.onBlockRemoved(world, i, j, k);
        if(world.isServerSide)
        {
            return;
        }
        world.updateAdjacentBlocks(i, j + 1, k, id);
        world.updateAdjacentBlocks(i, j - 1, k, id);
        notifyOilNeighborsOfNeighborChange(world, i - 1, j, k);
        notifyOilNeighborsOfNeighborChange(world, i + 1, j, k);
        notifyOilNeighborsOfNeighborChange(world, i, j, k - 1);
        notifyOilNeighborsOfNeighborChange(world, i, j, k + 1);
        if(world.isFullOpaque(i - 1, j, k))
        {
            notifyOilNeighborsOfNeighborChange(world, i - 1, j + 1, k);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i - 1, j - 1, k);
        }
        if(world.isFullOpaque(i + 1, j, k))
        {
            notifyOilNeighborsOfNeighborChange(world, i + 1, j + 1, k);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i + 1, j - 1, k);
        }
        if(world.isFullOpaque(i, j, k - 1))
        {
            notifyOilNeighborsOfNeighborChange(world, i, j + 1, k - 1);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i, j - 1, k - 1);
        }
        if(world.isFullOpaque(i, j, k + 1))
        {
            notifyOilNeighborsOfNeighborChange(world, i, j + 1, k + 1);
        } else
        {
            notifyOilNeighborsOfNeighborChange(world, i, j - 1, k + 1);
        }
    }

    public void activate(Level world, int i, int j, int k, PlayerBase entityplayer)
    {
        setFireAbove(world, i, j, k, world.getTileMeta(i, j, k));
    }

    public void onNeighborBlockChange(Level world, int i, int j, int k, int l)
    {
        if(world.isServerSide)
        {
            return;
        }
        int i1 = world.getTileMeta(i, j, k);
        boolean flag = canPlaceAt(world, i, j, k);
        if(!flag)
        {
            drop(world, i, j, k, i1);
            world.setTile(i, j, k, 0);
        } else
        {
            checkForFire(world, i, j, k);
        }
        super.onAdjacentBlockUpdate(world, i, j, k, l);
    }

    public boolean checkForFire(Level world, int i, int j, int k)
    {
        if(world.getTileMeta(i, j, k) == 0)
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
                        int l1 = world.getTileId(l, i1, j1);
                        if(l1 != BlockBase.FIRE.id && (l1 != id || world.getTileMeta(l, i1, j1) <= 0))
                        {
                            continue;
                        }
                        world.setTileMeta(i, j, k, 15);
                        setFireAbove(world, i, j, k, 15);
                        world.method_216(i, j, k, id, 1);
                        for(int i2 = i - 1; i2 <= i + 1; i2++)
                        {
                            for(int j2 = k - 1; j2 <= k + 1; j2++)
                            {
                                int k2 = Math.abs(i - l) + Math.abs(k - j1);
                                if(k2 == 1 && world.getTileId(i2, i1 - 1, j2) == id)
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

    public int getDropId(int i, Random random)
    {
        return 0;
    }

    public boolean canUse(Level world, int i, int j, int k, PlayerBase entityplayer)
    {
        if(entityplayer.inventory.getHeldItem() != null && entityplayer.inventory.getHeldItem().itemId == ItemBase.bucket.id)
        {
            entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = new ItemInstance(ItemListener.itemOil);
            mod_SdkGuns.setItemDamage(entityplayer.inventory.getHeldItem(), ItemListener.itemOil.getDurability() - 1);
            world.setTile(i, j, k, 0);
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public boolean renderWorld(BlockRenderer renderblocks, BlockView iblockaccess, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int l = TextureListener.oil_juction;
//        if(renderblocks.overrideBlockTexture >= 0)
//        {
//            l = renderblocks.overrideBlockTexture;
//        }
        float f = getBrightness(iblockaccess, i, j, k);
        tessellator.colour(f, f, f);


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
        boolean flag = iblockaccess.getTileId(i - 1, j, k) == id || !iblockaccess.canSuffocate(i - 1, j, k) && iblockaccess.getTileId(i - 1, j - 1, k) == id;
        boolean flag1 = iblockaccess.getTileId(i + 1, j, k) == id || !iblockaccess.canSuffocate(i + 1, j, k) && iblockaccess.getTileId(i + 1, j - 1, k) == id;
        boolean flag2 = iblockaccess.getTileId(i, j, k - 1) == id || !iblockaccess.canSuffocate(i, j, k - 1) && iblockaccess.getTileId(i, j - 1, k - 1) == id;
        boolean flag3 = iblockaccess.getTileId(i, j, k + 1) == id || !iblockaccess.canSuffocate(i, j, k + 1) && iblockaccess.getTileId(i, j - 1, k + 1) == id;
        if(!iblockaccess.canSuffocate(i, j + 1, k))
        {
            if(iblockaccess.canSuffocate(i - 1, j, k) && iblockaccess.getTileId(i - 1, j + 1, k) == id)
            {
                flag = true;
            }
            if(iblockaccess.canSuffocate(i + 1, j, k) && iblockaccess.getTileId(i + 1, j + 1, k) == id)
            {
                flag1 = true;
            }
            if(iblockaccess.canSuffocate(i, j, k - 1) && iblockaccess.getTileId(i, j + 1, k - 1) == id)
            {
                flag2 = true;
            }
            if(iblockaccess.canSuffocate(i, j, k + 1) && iblockaccess.getTileId(i, j + 1, k + 1) == id)
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
        if(!iblockaccess.canSuffocate(i, j + 1, k))
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
            if(iblockaccess.canSuffocate(i - 1, j, k) && iblockaccess.getTileId(i - 1, j + 1, k) == id)
            {
                tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d1, d2);
                tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d2);
                tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d, d3);
                tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d3);
            }
            if(iblockaccess.canSuffocate(i + 1, j, k) && iblockaccess.getTileId(i + 1, j + 1, k) == id)
            {
                tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d3);
                tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d1, d3);
                tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d2);
                tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d, d2);
            }
            if(iblockaccess.canSuffocate(i, j, k - 1) && iblockaccess.getTileId(i, j + 1, k - 1) == id)
            {
                tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)k + f2, d, d3);
                tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)k + f2, d1, d3);
                tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)k + f2, d1, d2);
                tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)k + f2, d, d2);
            }
            if(iblockaccess.canSuffocate(i, j, k + 1) && iblockaccess.getTileId(i, j + 1, k + 1) == id)
            {
                tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d2);
                tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d2);
                tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d3);
                tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d3);
            }
        }
        int l1 = iblockaccess.getTileMeta(i, j, k);
        if(l1 != 0)
        {
            renderblocks.renderFire(BlockBase.FIRE, i, j, k);
        }
        return true;
    }
}
