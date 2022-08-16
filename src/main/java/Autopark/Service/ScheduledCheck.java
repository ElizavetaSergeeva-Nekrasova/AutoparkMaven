package Autopark.Service;

import Autopark.EntityCollection.EntityCollection;
import Autopark.Infrastructure.threads.annotations.Schedule;

public class ScheduledCheck {
    @Schedule(timeout = 10000, delta = 10000)
    public void scheduledCheck(Workroom workroom, EntityCollection entityCollection) {
        workroom.checkAllVehicle(entityCollection.getVehiclesList());
    }
}