package net.tigereye.chestcavity.chestcavities;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;

public class ChestCavityInventory extends Inventory {

    ChestCavityInstance instance;
    boolean test;

    public ChestCavityInstance getInstance() {
        return instance;
    }

    public void setInstance(ChestCavityInstance instance) {
        this.instance = instance;
    }

    public ChestCavityInventory() {
        super(27);
    }

    public ChestCavityInventory(int size,ChestCavityInstance instance) {
        super(size);
        this.instance = instance;
    }

    public void readTags(ListNBT tags) {
        clearContent();
        for(int j = 0; j < tags.size(); ++j) {
            CompoundNBT NbtCompound = tags.getCompound(j);
            int k = NbtCompound.getByte("Slot") & 255;
            boolean f = NbtCompound.getBoolean("Forbidden");
            if (k >= 0 && k < this.getContainerSize()) {
                this.setItem(k, ItemStack.of(NbtCompound));
            }
        }

    }

    public ListNBT getTags() {
        ListNBT list = new ListNBT();

        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemStack = this.getItem(i);
            if (!itemStack.isEmpty()) {
                CompoundNBT NbtCompound = new CompoundNBT();
                NbtCompound.putByte("Slot", (byte)i);
                itemStack.save(NbtCompound);
                list.add(NbtCompound);
            }
        }

        return list;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {

        if(instance == null) {return true;} //this is for if something goes wrong with that first moment before things sync
        if(instance.owner.isDeadOrDying()){return false;}
        return (player.distanceTo(instance.owner) < 8);
    }
}
