package ui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

//takes place when the initial ClosetAppGUI frame is closed; prints to console a log of events
public class ClosingWindowAction extends WindowAdapter {

    //effects: prints to console the adding/loading/filtering events done by the user
    @Override
    public void windowClosing(WindowEvent e) {
        Iterator<Event> it = EventLog.getInstance().iterator();
        while (it.hasNext()) {
            String event = it.next().getDescription();
            System.out.println(event);
        }
    }

}
