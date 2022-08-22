package Autopark.Service;

import Autopark.EntityCollection.EntityCollection;
import Autopark.Infrastructure.threads.annotations.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ScheduledCheck {
    List<Long> repairedVehiclesId = new ArrayList<>();

    @Schedule(timeout = 5000, delta = 5000)
    public void scheduledCheck(Workroom workroom, EntityCollection entityCollection) {
        repairedVehiclesId = workroom.getRepairedVehiclesId(entityCollection.getVehiclesList());
    }
}