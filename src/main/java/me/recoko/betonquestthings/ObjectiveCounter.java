package me.recoko.betonquestthings;

import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.api.CountingObjective;
import org.betonquest.betonquest.api.PlayerObjectiveChangeEvent;
import org.betonquest.betonquest.api.profiles.Profile;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.ObjectNotFoundException;
import org.betonquest.betonquest.id.ObjectiveID;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class ObjectiveCounter extends CountingObjective implements Listener {
    private final ObjectiveID oID;

    public ObjectiveCounter(Instruction instruction) throws InstructionParseException{
        super(instruction);
        this.template = CountingObjective.CountingData.class;
        try {
            this.oID = new ObjectiveID(instruction.getPackage(), instruction.next());
            this.targetAmount = instruction.getVarNum();
        } catch(ObjectNotFoundException e){
            throw new InstructionParseException("Error while parsing: " + e.getMessage(), e);
        }


    }

    @EventHandler
    public void changeEvent(PlayerObjectiveChangeEvent e) {
        if (this.containsPlayer(e.getProfile()) &&
        e.getObjective() == BetonQuest.getInstance().getObjective(oID) &&
        e.getState() == ObjectiveState.COMPLETED){

            this.getCountingData(e.getProfile()).add(1);
            this.completeIfDoneOrNotify(e.getProfile());

        }

    }
    public void start() {
        Bukkit.getPluginManager().registerEvents(this, BetonQuest.getInstance());
    }

    public void stop() {
        HandlerList.unregisterAll(this);
    }

    public String getProperty(String name, Profile profile) {
        return null;
    }


}
