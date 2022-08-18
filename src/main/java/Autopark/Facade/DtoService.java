package Autopark.Facade;

import Autopark.DTO.RentsDto;
import Autopark.DTO.TypesDto;
import Autopark.DTO.VehiclesDto;
import Autopark.EntityCollection.EntityCollection;
import Autopark.Infrastructure.core.annotations.Autowired;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class DtoService {
    @Autowired
    EntityCollection entityCollection;

    public DtoService() {
    }

    public List<TypesDto> getTypes() {
        return entityCollection
                .getTypesList()
                .stream()
                .map(types -> {
                    return TypesDto.builder()
                            .id(types.getId())
                            .name(types.getName())
                            .coefTaxes(types.getCoefTaxes())
                            .build();
                }).collect(Collectors.toList());
    }

    public List<VehiclesDto> getVehicles() {
        return entityCollection
                .getVehiclesList()
                .stream()
                .map(vehicles -> {
                    return VehiclesDto.builder()
                            .id(vehicles.getId())
                            .typeId(vehicles.getTypeId())
                            .typeName(entityCollection.getTypesList().get((int) (vehicles.getTypeId() - 1)).getName())
                            .model(vehicles.getModel())
                            .stateNumber(vehicles.getStateNumber())
                            .weight(vehicles.getWeight())
                            .year(vehicles.getYear())
                            .mileage(vehicles.getMileage())
                            .color(vehicles.getColor())
                            .engineName(vehicles.getEngineName())
                            .tankCapacity(vehicles.getTankCapacity())
                            .engineCapacity(vehicles.getEngineCapacity())
                            .per100Kilometers(vehicles.getConsumptionPerKilometer() * 100)
                            .maxKilometers(vehicles.getEngine().getMaxKilometers())
                            .tax(vehicles.getCalcTaxPerMonth())
                            .income(vehicles.getTotalIncome(entityCollection))
                            .profit(vehicles.getTotalProfit(entityCollection))
                            .build();
                }).collect(Collectors.toList());
    }

    public List<RentsDto> getRents() {
        return entityCollection
                .getRentsList()
                .stream()
                .map(rents -> {
                    return RentsDto.builder()
                            .id(rents.getId())
                            .vehicleId(rents.getVehicleId())
                            .date(rents.getDate())
                            .cost(rents.getCost())
                            .build();
                }).collect(Collectors.toList());
    }
}