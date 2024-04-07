package me.recoko.betonquestthings.questbook;

import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.api.QuestEvent;
import org.betonquest.betonquest.api.logger.BetonQuestLogger;
import org.betonquest.betonquest.api.profiles.Profile;
import org.betonquest.betonquest.api.quest.event.Event;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.ObjectNotFoundException;
import org.betonquest.betonquest.exceptions.QuestRuntimeException;
import org.betonquest.betonquest.id.EventID;
import org.betonquest.betonquest.id.ItemID;
import org.bukkit.entity.Player;

public class QuestBookAddEvent implements Event {
    private final BetonQuestLogger log = BetonQuest.getInstance().getLoggerFactory().create(this.getClass());
    private EventID eID;
    private String tab;
    private ItemID item;
    private String name;
    private boolean type;

    public QuestBookAddEvent(String name, String tab, boolean type){
        this.name = name;
        this.tab = tab;
        this.type = type;
    }

    public QuestBookAddEvent(String name, boolean type){
        this(name, null, type);
    }

    public QuestBookAddEvent(String name){
        this(name,null, true);
    }


    @Override
    public void execute(Profile profile) throws QuestRuntimeException {
        if(this.type) {
            QuestbookHandler.sendQuest(name, (Player) profile.getPlayer());
        } else {
            QuestbookHandler.unsendQuest(name, (Player) profile.getPlayer());
        }

    }
}
