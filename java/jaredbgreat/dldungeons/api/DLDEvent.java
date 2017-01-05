package jaredbgreat.dldungeons.api;

/* 
 * This mod is the creation and copyright (c) 2015 
 * of Jared Blackburn (JaredBGreat).
 * 
 * This class (and all lines calling its methods elsewhere thus far) is by 
 * Charles Howard, late 2016, and has been back-ported for 
 * Minecraft 1.7.10 by Jared Blackburn.
 * 
 * It is licensed under the creative commons 4.0 attribution license: * 
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/	

import jaredbgreat.dldungeons.builder.DBlock;
import jaredbgreat.dldungeons.planner.Dungeon;
import jaredbgreat.dldungeons.planner.mapping.MapMatrix;
import jaredbgreat.dldungeons.rooms.Room;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

public class DLDEvent extends Event {

	public DLDEvent() {

	}

	@Cancelable
	public abstract static class Place extends DLDEvent {
		protected final World world;
		protected int blockX, blockY, blockZ;

		public Place(World world, int x, int y, int z) {
			this.world = world;
			blockX = x;
			blockX = y;
			blockX = y;
		}

		public World getWorld() {
			return world;
		}

		public int getX() {
			return blockX;
		}

		public int getY() {
			return blockY;
		}

		public int getZ() {
			return blockZ;
		}

		public void setX(int x) {
			blockX = x;
		}

		public void setY(int y) {
			blockY = y;
		}

		public void setZ(int z) {
			blockY = z;
		}

	}

	@Cancelable
	public static class PlaceDBlock extends Place {

		protected final DBlock block;

		public PlaceDBlock(World world, int x, int y, int z, DBlock block) {
			super(world, x, y, z);
			this.block = block;
		}

		public DBlock getBlock() {
			return block;
		}

	}

	@Cancelable
	public static class PlaceBlock extends Place {

		protected final Block block;

		public PlaceBlock(World world, int x, int y, int z, Block block) {
			super(world, x, y, z);
			this.block = block;
		}

		public Block getBlock() {
			return block;
		}

	}

	public static class DungeonRoom extends DLDEvent {
		protected final Dungeon dungeon;
		protected final Room room;

		public DungeonRoom(Dungeon dungeon, Room room) {
			this.dungeon = dungeon;
			this.room = room;
		}

		public Dungeon getDungeon() {
			return dungeon;
		}

		public Room getRoom() {
			return room;
		}

	}

	@Cancelable
	public static class AddChestBlocksToRoom extends DungeonRoom {
		public AddChestBlocksToRoom(Dungeon dungeon, Room room) {
			super(dungeon, room);
		}
	}

	@Cancelable
	public static class BeforePlaceSpawner extends DLDEvent {

		private final World world;
		private final int x, y, z;
		private final String mob;

		public BeforePlaceSpawner(World world, int x, int y, int z, String mob) {
			this.world = world;
			this.x = x;
			this.y = y;
			this.z = z;
			this.mob = mob;
		}

		public World getWorld() {
			return world;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZ() {
			return z;
		}

		public String getMob() {
			return mob;
		}

	}

	public static class AfterChestTileEntity extends DLDEvent {

		private final World world;
		private final TileEntityChest contents;
		private final int which;
		private final int x;
		private final int y;
		private final int z;
		private final Random random;
		private final int level;

		public AfterChestTileEntity(World world, TileEntityChest contents, int which, int x, int y, int z, Random random, int level) {
			this.world = world;
			this.contents = contents;
			this.which = which;
			this.x = x;
			this.y = y;
			this.z = z;
			this.random = random;
			this.level = level;
		}

		public World getWorld() {
			return world;
		}

		public TileEntityChest getContents() {
			return contents;
		}

		public int getWhich() {
			return which;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZ() {
			return z;
		}

		public Random getRandom() {
			return random;
		}

		public int getLevel() {
			return level;
		}

	}

	@Cancelable
	public static class AddTileEntitiesToRoom extends DungeonRoom {
		public AddTileEntitiesToRoom(Dungeon dungeon, Room room) {
			super(dungeon, room);
		}
	}

	@Cancelable
	public static class AddEntrance extends DungeonRoom {
		public AddEntrance(Dungeon dungeon, Room room) {
			super(dungeon, room);
		}
	}

	public static class BeforeBuild extends DLDEvent {
		protected final MapMatrix mapMatrix;
		protected final int shiftX;
		protected final int shiftZ;
		protected final boolean flooded;

		public BeforeBuild(MapMatrix mapMatrix, int shiftX, int shiftZ, boolean flooded) {
			this.mapMatrix = mapMatrix;
			this.shiftX = shiftX;
			this.shiftZ = shiftZ;
			this.flooded = flooded;
		}

		public MapMatrix getMapMatrix() {
			return mapMatrix;
		}

		public int getShiftX() {
			return shiftX;
		}

		public int getShiftZ() {
			return shiftZ;
		}

		public boolean isFlooded() {
			return flooded;
		}

	}

	public static class AfterBuild extends BeforeBuild {
		public AfterBuild(MapMatrix mapMatrix, int shiftX, int shiftZ, boolean flooded) {
			super(mapMatrix, shiftX, shiftZ, flooded);
		}
	}

	@Cancelable
	public abstract static class PlaceDungeon extends DLDEvent {

		protected final Random random;
		protected final int chunkX;
		protected final int chunkZ;
		protected final World world;

		public PlaceDungeon(Random random, int chunkX, int chunkZ, World world) {
			this.random = random;
			this.chunkX = chunkX;
			this.chunkZ = chunkZ;
			this.world = world;
		}

		public Random getRandom() {
			return random;
		}

		public int getChunkX() {
			return chunkX;
		}

		public int getChunkZ() {
			return chunkZ;
		}

		public World getWorld() {
			return world;
		}

	}

	@Cancelable
	public static class PlaceDungeonBegin extends PlaceDungeon {
		public PlaceDungeonBegin(Random random, int chunkX, int chunkZ, World world) {
			super(random, chunkX, chunkZ, world);
		}
	}

	public static class PlaceDungeonFinish extends PlaceDungeon {
		private final Dungeon dungeon;

		public PlaceDungeonFinish(Random random, int chunkX, int chunkZ, World world, Dungeon dungeon) {
			super(random, chunkX, chunkZ, world);
			this.dungeon = dungeon;
		}

		public Dungeon getDungeon() {
			return dungeon;
		}

	}

}