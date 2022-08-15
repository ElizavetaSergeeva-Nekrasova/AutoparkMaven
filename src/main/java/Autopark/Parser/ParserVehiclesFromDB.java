package Autopark.Parser;

import Autopark.EntitiesService.RentsService;
import Autopark.EntitiesService.TypesService;
import Autopark.EntitiesService.VehiclesService;
import Autopark.Entity.Rents;
import Autopark.Entity.Types;
import Autopark.Entity.Vehicles;
import Autopark.Infrastructure.core.annotations.Autowired;
import java.util.List;

public class ParserVehiclesFromDB {
    @Autowired
    VehiclesService vehiclesService;

    @Autowired
    TypesService typesService;

    @Autowired
    RentsService rentsService;

    public ParserVehiclesFromDB() {
    }

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