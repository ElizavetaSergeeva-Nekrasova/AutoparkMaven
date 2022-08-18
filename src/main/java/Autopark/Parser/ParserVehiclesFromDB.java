package Autopark.Parser;

import Autopark.EntitiesService.RentsService;
import Autopark.EntitiesService.TypesService;
import Autopark.EntitiesService.VehiclesService;
import Autopark.Entity.Rents;
import Autopark.Entity.Types;
import Autopark.Entity.Vehicles;
import Autopark.Infrastructure.core.annotations.Autowired;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParserVehiclesFromDB {
    @Autowired
    VehiclesService vehiclesService;

    @Autowired
    TypesService typesService;

    @Autowired
    RentsService rentsService;

    public List<Types> loadTypesList() {
        return typesService.getAll();
    }

    public List<Rents> loadRentsList() {
        return rentsService.getAll();
    }

    public List<Vehicles> loadVehiclesList() {
        return vehiclesService.getAll();
    }
}