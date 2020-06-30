package dev.dannychoi.colosseum;

import java.util.HashMap;
import java.util.Optional;

public class StatusManager {
    // Below HashMap stores miscellaneous objects and indicates their status.
    // Example: An active explosive arrow fired by a player would have a code dependent on if in the air, if hit ground, etc.
    // Map<PlayerProfile, Entry<Cooldown Code, Time in seconds>>
    private HashMap<Object, Integer> status;

    public StatusManager() {
        status = new HashMap<Object, Integer>();
    }

    public Optional<Integer> getStatus(Object o) {
        if (statusExistsFor(o)) return Optional.of(status.get(o));
        else return Optional.empty();
    }

    public boolean statusExistsFor(Object o) {
        return status.containsKey(o);
    }

    public void deleteStatus(Object o) {
        status.remove(o);
    }

    public void setStatus(Object o, int code) {
        status.put(o, code);
    }
}
