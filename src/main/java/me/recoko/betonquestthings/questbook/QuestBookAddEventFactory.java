package me.recoko.betonquestthings.questbook;

import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.api.quest.event.Event;
import org.betonquest.betonquest.api.quest.event.EventFactory;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.ObjectNotFoundException;
import org.betonquest.betonquest.id.EventID;
import org.betonquest.betonquest.id.ItemID;

public class QuestBookAddEventFactory implements EventFactory {

    public QuestBookAddEventFactory() {

    }

    @Override
    public Event parseEvent(Instruction instruction) throws InstructionParseException {
        QuestBookAddEvent e;
        try {
            //this.eID = new EventID(instruction.getPackage(), instruction.next());
            //this.tab = instruction.next();
            String type = instruction.next();
            boolean addremove = getType(type);
            String name = instruction.next();
            if(addremove) {
                ItemID item = instruction.getItem();
                QuestbookHandler.addQuest(name, item);
            }

            e = new QuestBookAddEvent(name, addremove);
        } catch (ObjectNotFoundException ex) {
            throw new InstructionParseException("Error while parsing: " + ex.getMessage(), ex);
        }
        return e;
    }


    private boolean getType(String type) throws ObjectNotFoundException {
        if (type.equalsIgnoreCase("add")) {
            return true;
        } else if (type.equalsIgnoreCase("remove")) {
            return false;
        }
        throw new ObjectNotFoundException("Cannot parse event type");
    }
}


