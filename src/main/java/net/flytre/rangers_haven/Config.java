package net.flytre.rangers_haven;

import com.google.gson.annotations.SerializedName;
import net.flytre.flytre_lib.api.config.annotation.Description;

public class Config {

    @Description("Whether explosions from bow enchantments should destroy blocks")
    @SerializedName("explosion_griefing")
    public boolean explosionGriefing = true;
}
