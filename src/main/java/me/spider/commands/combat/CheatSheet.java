package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class CheatSheet extends SlashCommand {
    public CheatSheet(){
        this.name = "cheatsheet";
        this.help = "Shows a cheatsheet for 2.5 combat.";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("""
                **1) Declaration:**
                    Declare Atk, Charms - declare any perfect Atk
                **2) Response:**
                    Declare Parry/Dodge/Charms
                **3) Atk Roll:**
                    Atker Rolls Dex + Ability at difficulty 1, subject to the usual order of modifiers (see table on p. 124).
                **4) Atk RR:**
                    Declare if applicable.
                **5) Penalties/Defenses:**
                    Remove successes for any external penalties that apply to atk roll.
                    Roll dice granted by stunts & charms adding successes to DV.
                **6) Defense RR:**
                    Defenders may use RR charms if their DV is insufficient to completely stop atk.
                **7) Calculate Raw dmg:**
                    On hit, atk has raw dmg equal to (str + weap value), + dice equal to successes remaining after step 5.
                    Effects modifying raw dmg of an atk apply.
                **8) Apply Hardness + Soak, Roll dmg:**
                    If victim has a Hardness rating against atks dmg type, compare Hardness with raw dmg.
                    If Hardness is equal or greater, defense absorbs atk without effect.
                    Otherwise, dmg ignores defenders Hardness.
                    Next, remove target's appropriate soak rating from dmg.
                    If post-soak dmg is less than  atks innate minimum dmg or atkers Essence, raw dmg has a final value equal to the greater of the two values.
                    Apply any effects that increase or decrease post-soak dmg to the final value.
                    Roll dice equal to the final dmg of the atk, applying successes as health levels of the appropriate type of dmg to the defender.
                **9) Counterattack:**
                    If victim retaliates, apply steps 1-8.
                **10) Apply Results:**
                    Apply non-dmg effects & dmg and effects from counterattack.
                """).setEphemeral(true).queue();
    }
}
