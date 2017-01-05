package jaredbgreat.dldungeons.pieces.chests;


/* 
 * This mod is the creation and copyright (c) 2015 
 * of Jared Blackburn (JaredBGreat).
 * 
 * Forge event code by Charles Howard, 2016.
 * 
 * It is licensed under the creative commons 4.0 attribution license: * 
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/	


import jaredbgreat.dldungeons.ConfigHandler;
import jaredbgreat.dldungeons.api.DLDEvent;
import jaredbgreat.dldungeons.builder.DBlock;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;

/**
 * Represents a typical loot chest, including its coordinates and loot level.
 * 
 * @author Jared Blackburn
 *
 */
public class BasicChest {
	
	public int mx, my, mz, level;
	
	
	public BasicChest(int x, int y, int z, int level) {
		this.mx = x;
		this.my = y;
		this.mz = z;
		this.level = level;
	}
	
	
	/**
	 * This adds a tile entity to the chest, and then calls fillChest to fill it.
	 * The chest block is placed in the maps by Dungeon.addChestBlocks using 
	 * DBlock.placeChest.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param random
	 */
	public void place(World world, int x, int y, int z, Random random) {
		level += random.nextInt(2);
		if(level >= LootCategory.LEVELS) level = LootCategory.LEVELS - 1;
		DBlock.placeChest(world, x, y, z);
		if(world.getBlock(x, y, z) != DBlock.chest) return;
		TileEntityChest contents = (TileEntityChest)world.getTileEntity(x, y, z);
		if(contents == null) {
			System.err.println("DLDUNGEONS: Error, tile entity not found for chest");
			return;
		}
		if(addVanillaLoot(random)) vanillaChest(contents, random);
		int which = random.nextInt(3);
		switch (which) {
		case 0:
			fillChest(contents, LootType.HEAL, random);
			break;
		case 1:
			fillChest(contents, LootType.GEAR, random);
			break;
		case 2:
			fillChest(contents, LootType.RANDOM, random);
			break;
		}
		
		
		MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.AfterChestTileEntity(world, contents, which, x, y, z, random, level));
		
	}
	
	
	private void vanillaChest(TileEntityChest contents, Random random) {
		int which = random.nextInt(6);
		ChestGenHooks chinf;
		switch (which) {
		case 0: 
			chinf = ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST); 
			break;
		case 1:
			chinf = ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST);
			break;
		case 2:
			chinf = ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST);
			break;
		case 3:
			chinf = ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR);
			break;
		case 4:
			chinf = ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH);
			break;
		case 5:
		default:
			chinf = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
			break;
		}		
        WeightedRandomChestContent.generateChestContents(random, 
        		chinf.getItems(random), contents, chinf.getCount(random));
	}
	
	
	/**
	 * Fills the chest with loot of the specified kind (lootType).
	 * 
	 * @param chest
	 * @param kind
	 * @param random
	 */
	protected void fillChest(TileEntityChest chest, LootType kind, Random random) {		
		int num;
		if(ConfigHandler.stingyLoot) num = random.nextInt(2 + (level / 2)) + 1;
		else num = random.nextInt(3 + (level / 2)) + 2;
		for(int i = 0; i < num; i++) {
			ItemStack treasure = LootCategory.getLoot(kind, level, random);
			if(treasure != null) chest.setInventorySlotContents(random.nextInt(27), treasure);
		}
		if(!ConfigHandler.vanillaLoot) {
			ItemStack treasure = LootCategory.getLoot(LootType.HEAL, level, random);
			if(treasure != null) chest.setInventorySlotContents(random.nextInt(27), treasure);
		}
	}
	
	
	protected boolean addVanillaLoot(Random random) {
		boolean out;
		if(ConfigHandler.stingyLoot) {
			out = (random.nextInt(LootCategory.LEVELS * 3) < (level * 2));
		} else {
			out = (random.nextInt(LootCategory.LEVELS * 3) < (level + LootCategory.LEVELS));
		}
		return (out && ConfigHandler.vanillaLoot);
	}
}
