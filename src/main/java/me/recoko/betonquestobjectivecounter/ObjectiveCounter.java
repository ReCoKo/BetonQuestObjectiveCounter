package me.recoko.betonquestobjectivecounter;

import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.VariableNumber;
import org.betonquest.betonquest.api.Objective;
import org.betonquest.betonquest.api.PlayerObjectiveChangeEvent;
import org.betonquest.betonquest.api.logger.BetonQuestLogger;
import org.betonquest.betonquest.api.profiles.Profile;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.ObjectNotFoundException;
import org.betonquest.betonquest.exceptions.QuestRuntimeException;
import org.betonquest.betonquest.id.ObjectiveID;
import org.betonquest.betonquest.utils.PlayerConverter;
import org.betonquest.betonquest.utils.location.CompoundLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class ObjectiveCounter extends Objective implements Listener {
    private final BetonQuestLogger log = BetonQuest.getInstance().getLoggerFactory().create(this.getClass());
    private ObjectiveID oID;
    private int max;
    private int current;

    public ObjectiveCounter(Instruction instruction) throws InstructionParseException{
        super(instruction);
        this.template = Objective.ObjectiveData.class;
        try {
            this.oID = new ObjectiveID(instruction.getPackage(), instruction.next());
            this.max = Integer.parseInt(instruction.next());
        } catch(ObjectNotFoundException e){
            throw new InstructionParseException("Error while parsing: " + e.getMessage(), e);
        }

        this.current = 0;
    }

    @EventHandler
    public void changeEvent(PlayerObjectiveChangeEvent e){

        if(e.getObjective() == BetonQuest.getInstance().getObjective(oID)){

            if(e.getState() == ObjectiveState.COMPLETED){

                current++;
                if(current >= max) {

                    this.completeObjective(e.getProfile());
                }
            }
        }
    }

    public void start() {
        Bukkit.getPluginManager().registerEvents(this, BetonQuest.getInstance());
    }

    public void stop() {
        HandlerList.unregisterAll(this);
    }

    public String getDefaultDataInstruction() {
        return "";
    }

    public String getProperty(String name, Profile profile) {
        return null;
    }
}
