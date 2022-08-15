package Autopark.Entity;

import Autopark.Exceptions.NotVehicleException;
import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;
import Autopark.Service.TechnicalSpecialist;
import lombok.*;

import java.util.Objects;

@Table(name = "Types")
@Builder
@Data
@ToString
@NoArgsConstructor
public class Types {
    @ID
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "coefTaxes")
    private Double coefTaxes;

    public Types(Long id, String name, Double coefTaxes) {
        this.id = id;
        setName(name);
        setCoefTaxes(coefTaxes);
    }

    public void setName(String name) {
        try {
            if (TechnicalSpecialist.validateTypeName(name)) {
                this.name = name;
            }

            throw new NotVehicleException("Vehicle type name is wrong" + name);
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public void setCoefTaxes(Double coefTaxes) {
        try {
            if (TechnicalSpecialist.validateCoefTaxes(coefTaxes)) {
                this.coefTaxes = coefTaxes;
            }

            throw new NotVehicleException("Vehicle coefTaxes is wrong" + coefTaxes);
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Types types = (Types) o;
        return Double.compare(types.coefTaxes, coefTaxes) == 0 && Objects.equals(types.name, name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coefTaxes);
    }
}