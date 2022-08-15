package Autopark.EntityCollection;

import Autopark.Entity.Rents;
import Autopark.Entity.Types;
import Autopark.Entity.Vehicles;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Parser.ParserVehiclesFromDB;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EntityCollection {
    List<Vehicles> vehiclesList;
    List<Types> typesList;
    List<Rents> rentsList;

    @Autowired
    ParserVehiclesFromDB parserVehiclesFromDB;

    @InitMethod
    public void init() {
        rentsList = parserVehiclesFromDB.loadRentsList();
        typesList = parserVehiclesFromDB.loadTypesList();
        vehiclesList = parserVehiclesFromDB.loadVehiclesList();
    }

    public List<Rents> getRentsListForVehicle(Long id) {
        List<Rents> rents = new ArrayList<>();

        for (int i = 0; i < rentsList.size(); i++) {
            if (rentsList.get(i).getVehicleId() == id) {
                rents.add(rentsList.get(i));
            }
        }

        return rents;
    }
}